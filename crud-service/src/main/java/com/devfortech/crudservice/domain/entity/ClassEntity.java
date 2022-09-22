package com.devfortech.crudservice.domain.entity;


import lombok.*;

import javax.persistence.*;
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

    @ManyToMany(targetEntity=GradeEntity.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<GradeEntity> grades = new HashSet<>();;

    @OneToMany(targetEntity=StudentEntity.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<StudentEntity> students = new HashSet<>();

    @OneToOne
    private TeacherEntity teacher;

}
