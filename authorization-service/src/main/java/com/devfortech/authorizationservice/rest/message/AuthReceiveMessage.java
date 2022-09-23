package com.devfortech.authorizationservice.rest.message;

import com.devfortech.authorizationservice.rest.dto.ChangeUserRequest;
import com.devfortech.authorizationservice.rest.dto.SignUpRequest;
import com.devfortech.authorizationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthReceiveMessage {

    private final AuthService authService;

    @RabbitListener(queues = "${spring.rabbitmq.auth.queue}")
    public void receive(@Payload SignUpRequest signUpRequest) {
        authService.signup(signUpRequest);
    }

    @RabbitListener(queues = "auth.delete.user")
    public void receiveDeleteUser(@Payload String email) {
        authService.delete(email);
    }

    @RabbitListener(queues = "auth.update.user")
    public void receiveUpdateUser(@Payload ChangeUserRequest req) {
        authService.updateUser(req.getOldEmail(), req);
    }
}
