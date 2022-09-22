package com.devfortech.crudservice.rest.dto;

import com.devfortech.crudservice.domain.entity.TeacherEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO extends RepresentationModel<TeacherDTO> implements Serializable {
    private static final long serialVersionUID = -5407563236784903012L;

    private Long id;
    private BigDecimal salary;
    private PessoaDTO pessoa;

    public TeacherDTO(TeacherEntity entity) {
        this.id = entity.getId();
        this.salary = entity.getSalary();
        this.pessoa = new PessoaDTO(entity.getPessoa());
    }

}
