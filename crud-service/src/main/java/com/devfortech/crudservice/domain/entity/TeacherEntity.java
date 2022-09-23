package com.devfortech.crudservice.domain.entity;

import com.devfortech.crudservice.rest.dto.TeacherDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_teacher")
@EqualsAndHashCode
public class TeacherEntity implements Serializable {
    private static final long serialVersionUID = -7479921461937858813L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private BigDecimal salary;

    @NotNull
    @OneToOne
    private PessoaEntity pessoa;

    private boolean createUser;

    public TeacherEntity(TeacherDTO dto) {
        this.id = dto.getId();
        this.salary = dto.getSalary();
        this.pessoa = new PessoaEntity(dto.getPessoa());
        this.createUser = dto.isCreateUser();
    }
}
