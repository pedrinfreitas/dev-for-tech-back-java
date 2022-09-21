package com.devfortech.pequenosgenios.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_teacher")
public class TeacherEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String state;


}
