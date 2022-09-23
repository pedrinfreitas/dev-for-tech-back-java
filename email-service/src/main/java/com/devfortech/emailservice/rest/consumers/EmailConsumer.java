package com.devfortech.emailservice.rest.consumers;

import com.devfortech.emailservice.domain.entity.EmailModel;
import com.devfortech.emailservice.rest.dto.EmailDTO;
import com.devfortech.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.email.queue}")
    public void receive(@Payload EmailDTO emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        System.out.println("Email Status: " + emailModel.getStatusEnvio().toString());
    }
}