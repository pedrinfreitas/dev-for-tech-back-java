package com.devfortech.authorizationservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class SignupException extends RuntimeException{

    public SignupException(String msg) {
        super(String.format(msg));
    }

}
