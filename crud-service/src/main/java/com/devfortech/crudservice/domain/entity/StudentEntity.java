package com.devfortech.crudservice.domain.entity;

import com.devfortech.crudservice.rest.dto.StudentDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_student")
@EqualsAndHashCode
public class StudentEntity implements Serializable {
    private static final long serialVersionUID = 5852269417245222814L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private BigDecimal fees;

    @ManyToOne
    private ClassEntity classe;

    @OneToOne
    private PessoaEntity pessoa;

    private boolean createUser;

    public StudentEntity(StudentDTO dto) {
        this.id = dto.getId();
        this.fees = dto.getFees();
        this.pessoa = new PessoaEntity(dto.getPessoa());
        this.createUser = dto.isCreateUser();
    }
}
