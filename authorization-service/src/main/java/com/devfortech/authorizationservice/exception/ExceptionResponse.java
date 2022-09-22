package com.devfortech.authorizationservice.exception;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = -1519357818944068567L;

    private Date timestamp;
    private String message;
    private String details;

}
