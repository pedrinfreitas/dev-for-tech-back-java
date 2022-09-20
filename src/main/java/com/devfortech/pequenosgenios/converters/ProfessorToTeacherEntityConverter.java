package com.devfortech.pequenosgenios.converters;

import com.devfortech.models.ProfessorRequest;
import com.devfortech.pequenosgenios.entities.TeacherEntity;

public abstract class ProfessorToTeacherEntityConverter {
    public static TeacherEntity convert(Long id, ProfessorRequest professorRequest) {
        TeacherEntity entity = new TeacherEntity();

        entity.setId(id);
        entity.setName(professorRequest.getName());
        entity.setPhoneNumber(professorRequest.getPhoneNumber());
        entity.setEmailAddress(professorRequest.getEmailAddress());
        entity.setSalary(professorRequest.getSalary());
        entity.setCountry(professorRequest.getCountry());
        entity.setCity(professorRequest.getCity());
        entity.setStreet(professorRequest.getStreet());
        entity.setPostalCode(professorRequest.getPostalCode());
        entity.setState(professorRequest.getState());

        return entity;
    }
}
