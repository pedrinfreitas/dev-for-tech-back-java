package com.devfortech.crudservice.domain.entity;

import com.devfortech.crudservice.rest.dto.AddresDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_addres")
@EqualsAndHashCode
public class AddresEntity implements Serializable {
    private static final long serialVersionUID = -7496994092969391026L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String street;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Column(nullable = false)
    private String country;

    @NotNull
    @Column(nullable = false)
    private String postalCode;

    @NotNull
    @Column(nullable = false)
    private String state;

    public AddresEntity(AddresDTO dto){
        this.id = dto.getId();
        this.street = dto.getStreet();
        this.city = dto.getCity();
        this.country = dto.getCountry();
        this.postalCode = dto.getPostalCode();
        this.state = dto.getState();
    }
}
