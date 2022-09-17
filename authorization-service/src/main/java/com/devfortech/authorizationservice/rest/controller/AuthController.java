package com.devfortech.authorizationservice.rest.controller;

import com.devfortech.authorizationservice.rest.dto.JwtAuthenticationResponse;
import com.devfortech.authorizationservice.rest.dto.LoginRequest;
import com.devfortech.authorizationservice.rest.dto.SignUpRequest;
import com.devfortech.authorizationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new JwtAuthenticationResponse(service.login(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequest dto) {
        service.signup(dto);
        return new ResponseEntity<>("Usuario registrado com sucesso", OK);
    }

}
