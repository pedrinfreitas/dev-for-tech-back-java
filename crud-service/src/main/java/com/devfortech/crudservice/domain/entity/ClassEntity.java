package com.devfortech.crudservice.domain.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_class")
@EqualsAndHashCode
public class ClassEntity implements Serializable {
    private static final long serialVersionUID = -1793343699650010594L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToMany
    private Set<GradeEntity> grades;;

    @NotNull
    @OneToMany
    @JoinColumn
    private Set<StudentEntity> students;

    @NotNull
    @OneToOne
    private TeacherEntity teacher;

}
