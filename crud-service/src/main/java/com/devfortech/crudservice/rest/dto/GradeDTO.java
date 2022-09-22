package com.devfortech.crudservice.rest.dto;

import com.devfortech.crudservice.domain.entity.GradeEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GradeDTO implements Serializable {
    private static final long serialVersionUID = 1640030651108479156L;

    private Long id;
    private String descricao;

    public GradeDTO(GradeEntity entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
    }
}
