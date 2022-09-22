package com.devfortech.crudservice.domain.entity;

import com.devfortech.crudservice.rest.dto.PessoaDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pessoa")
@EqualsAndHashCode
public class PessoaEntity implements Serializable {
    private static final long serialVersionUID = -4932192937656310820L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String emailAddress;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddresEntity address;

    public PessoaEntity(PessoaDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
        this.emailAddress = dto.getEmailAddress();
        this.address = new AddresEntity(dto.getAddres());
    }
}
