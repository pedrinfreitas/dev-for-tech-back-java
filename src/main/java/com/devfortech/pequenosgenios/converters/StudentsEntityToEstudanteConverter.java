package com.devfortech.pequenosgenios.converters;

import com.devfortech.models.Estudante;
import com.devfortech.pequenosgenios.entities.StudentEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class StudentsEntityToEstudanteConverter {

    public static List<Estudante> convert(List<StudentEntity> studentEntity) {
        List<Estudante> estudanteList = new ArrayList<>();

        for (StudentEntity student : studentEntity) {
            Estudante estudante = new Estudante();
            BeanUtils.copyProperties(student,estudante);

            estudanteList.add(estudante);
        }
        return estudanteList;
    }

    public static Estudante convert(StudentEntity studentEntity) {
        Estudante estudante = new Estudante();
        BeanUtils.copyProperties(studentEntity,estudante);

        return estudante;
    }


}
