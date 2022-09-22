package com.devfortech.crudservice.domain.entity;

import com.devfortech.crudservice.rest.dto.GradeDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_grade")
@EqualsAndHashCode
public class GradeEntity implements Serializable {
    private static final long serialVersionUID = -5451069958689114135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String descricao;

    public GradeEntity(GradeDTO dto) {
        this.id = dto.getId();
        this.descricao = dto.getDescricao();
    }
}
