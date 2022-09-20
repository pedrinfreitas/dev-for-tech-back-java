package com.devfortech.pequenosgenios.converters;

import com.devfortech.models.EstudanteRequest;
import com.devfortech.pequenosgenios.entities.StudentEntity;

public abstract class EstudanteToStudentEntityConverter {

    public static StudentEntity convert(Long id, EstudanteRequest estudante) {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId(id);
        studentEntity.setName(estudante.getName());
        studentEntity.setPhoneNumber(estudante.getPhoneNumber());
        studentEntity.setFees(estudante.getFees());
        studentEntity.setEmailAddress(estudante.getEmailAddress());
        studentEntity.setStreet(estudante.getStreet());
        studentEntity.setCity(estudante.getCity());
        studentEntity.setCountry(estudante.getCountry());
        studentEntity.setPostalCode(estudante.getPostalCode());
        studentEntity.setState(estudante.getState());


        return studentEntity;
    }
}
