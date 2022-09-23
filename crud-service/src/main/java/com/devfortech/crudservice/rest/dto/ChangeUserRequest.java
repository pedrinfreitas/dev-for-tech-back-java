package com.devfortech.crudservice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserRequest {

    private String name;
    private String oldEmail;
    private String newEmail;
    private String oldPassword;
    private String newPassword;

}
