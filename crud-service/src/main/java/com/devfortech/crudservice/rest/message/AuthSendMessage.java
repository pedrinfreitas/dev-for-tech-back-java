package com.devfortech.crudservice.rest.message;

import com.devfortech.crudservice.domain.entity.PessoaEntity;
import com.devfortech.crudservice.rest.dto.ChangeUserRequest;
import com.devfortech.crudservice.rest.dto.SignUpRequest;
import com.devfortech.crudservice.rest.dto.StudentDTO;
import com.devfortech.crudservice.rest.dto.TeacherDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthSendMessage {

    @Value("${spring.rabbitmq.auth.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.auth.routingkey}")
    private String routingkey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public AuthSendMessage(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    @RabbitListener()
    public void sendMessageCreateUser(StudentDTO studentDTO){
        rabbitTemplate.convertAndSend(exchange, routingkey,
                SignUpRequest.builder()
                        .email(studentDTO.getPessoa().getEmailAddress())
                        .role("STUDENT")
                        .nome(studentDTO.getPessoa().getName())
                .build());
    }

    @RabbitListener()
    public void sendMessageCreateUser(TeacherDTO teacherDto){
        rabbitTemplate.convertAndSend(exchange, routingkey,
                SignUpRequest.builder()
                        .email(teacherDto.getPessoa().getEmailAddress())
                        .role("TEACHER")
                        .nome(teacherDto.getPessoa().getName())
                .build());
    }

    @RabbitListener(queues = "auth.delete.user")
    public void sendMessageDeleteUser(String email){
        rabbitTemplate.convertAndSend(email);
    }

    @RabbitListener(queues = "auth.update.user")
    public void sendMessageUpdateUser(PessoaEntity pessoaEntity, String oldEmail){
        ChangeUserRequest req = new ChangeUserRequest();

        req.setName(pessoaEntity.getName());
        req.setNewEmail(pessoaEntity.getEmailAddress());
        req.setOldEmail(oldEmail);

        rabbitTemplate.convertAndSend(req);
    }



}
