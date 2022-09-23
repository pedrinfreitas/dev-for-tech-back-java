package com.devfortech.authorizationservice.rest.message;

import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.rest.dto.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSendMessage {

    @Value("${spring.rabbitmq.email.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.email.routingkey}")
    private String routingkey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public EmailSendMessage(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    @RabbitListener
    public void sendEmailSignup(User user, String password){
        rabbitTemplate.convertAndSend(exchange, routingkey,
                EmailDTO.builder()
                        .emailTo(user.getEmail())
                        .ownerRef(user.getId().toString())
                        .subject("Seja Bem-vindo a escola pequenos genios!")
                        .text("Ola " + user.getNome() + ", agora voce faz parte da escola pequenos genios, segue seus dados de acesso a plataforma." +
                                "   email:  " + user.getEmail() + ",  senha/password: " + password)
                .build());
    }
}
