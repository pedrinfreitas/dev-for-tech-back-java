package com.devfortech.crudservice.rest.dto;

import com.devfortech.crudservice.domain.entity.PessoaEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO implements Serializable {
    private static final long serialVersionUID = -8351398935458854586L;

    private Long id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private AddresDTO addres;

    public PessoaDTO(PessoaEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.phoneNumber = entity.getPhoneNumber();
        this.emailAddress = entity.getEmailAddress();
        this.addres = new AddresDTO(entity.getAddress());
    }
}
