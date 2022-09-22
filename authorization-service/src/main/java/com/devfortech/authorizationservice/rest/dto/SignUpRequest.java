package com.devfortech.authorizationservice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String email;
    private String nome;
    private String password;
    private String role;

}
