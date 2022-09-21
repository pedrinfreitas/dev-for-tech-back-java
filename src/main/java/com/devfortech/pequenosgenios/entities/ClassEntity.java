package com.devfortech.pequenosgenios.entities;

import com.devfortech.models.Estudante;
import com.devfortech.models.Professor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_class")
public class ClassEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String grade;

    @OneToOne
    @JoinColumn(name = "professor_id")
    TeacherEntity professor;

    @OneToMany
    List<StudentEntity> estudantes;
}
