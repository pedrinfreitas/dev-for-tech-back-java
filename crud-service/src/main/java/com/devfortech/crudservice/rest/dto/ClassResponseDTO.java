package com.devfortech.crudservice.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponseDTO extends RepresentationModel<ClassResponseDTO> implements Serializable {
    private static final long serialVersionUID = 6669918610297122570L;

    private Long id;
    private Set<GradeDTO> grades = new HashSet<>();
    private HashMap<Long, String> students = new HashMap<>();
    private TeacherDTO teacher;

}
