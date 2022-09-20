package com.devfortech.pequenosgenios.converters;

import com.devfortech.models.Professor;
import com.devfortech.pequenosgenios.entities.TeacherEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class TeacherEntityToProfessorConverter {

    public static List<Professor> convert(List<TeacherEntity> teachers) {
        List<Professor> professorList = new ArrayList<>();
        for (TeacherEntity entity : teachers) {
            Professor professor = new Professor();
            BeanUtils.copyProperties(entity, professor);
            professorList.add(professor);
        }

        return professorList;
    }

    public static Professor convert(TeacherEntity teacherEntity) {
        Professor professor = new Professor();
        BeanUtils.copyProperties(teacherEntity, professor);

        return professor;
    }
}
