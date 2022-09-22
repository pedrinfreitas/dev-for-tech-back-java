package com.devfortech.crudservice.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRequestDTO implements Serializable {
    private static final long serialVersionUID = 6669918610297122570L;

    private Long id;
    private List<String> grades;
    private List<Long> students;
    private Long teacher;

}
