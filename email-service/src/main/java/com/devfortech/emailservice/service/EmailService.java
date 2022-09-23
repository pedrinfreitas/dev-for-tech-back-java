package com.devfortech.emailservice.service;

import com.devfortech.emailservice.domain.entity.EmailModel;
import com.devfortech.emailservice.domain.enums.StatusEnvio;
import com.devfortech.emailservice.domain.repository.EmailModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailModelRepository emailRepository;
    private final JavaMailSender emailSender;

    @Transactional
    public void sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("devfortechschool@pequenosgenios.com");
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEnvio(StatusEnvio.SENT);
        } catch (MailException e){
            emailModel.setStatusEnvio(StatusEnvio.ERROR);
        } finally {
            emailRepository.save(emailModel);
        }
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(Long id) {
        return emailRepository.findById(id);
    }
}
