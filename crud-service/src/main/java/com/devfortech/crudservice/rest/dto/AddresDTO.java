package com.devfortech.crudservice.rest.dto;

import com.devfortech.crudservice.domain.entity.AddresEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddresDTO implements Serializable {
    private static final long serialVersionUID = -1047234027470646321L;

    private Long id;
    private String street;
    private String city;
    private String country;
    private long postalCode;
    private String state;

    public AddresDTO(AddresEntity entity){
        this.id = entity.getId();
        this.street = entity.getStreet();
        this.city = entity.getCity();
        this.country = entity.getCountry();
        this.postalCode = entity.getPostalCode();
        this.state = entity.getState();
    }
}
