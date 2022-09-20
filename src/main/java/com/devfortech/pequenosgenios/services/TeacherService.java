package com.devfortech.pequenosgenios.services;

import com.devfortech.models.Professor;
import com.devfortech.models.ProfessorRequest;
import com.devfortech.pequenosgenios.converters.ProfessorToTeacherEntityConverter;
import com.devfortech.pequenosgenios.converters.TeacherEntityToProfessorConverter;
import com.devfortech.pequenosgenios.entities.TeacherEntity;
import com.devfortech.pequenosgenios.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    public List<Professor> findAll() {
        List<TeacherEntity> teachers = teacherRepository.findAll();
        return TeacherEntityToProfessorConverter.convert(teachers);
    }

    public Professor save(ProfessorRequest body) {
        var entity = new TeacherEntity();
        BeanUtils.copyProperties(body, entity);
        teacherRepository.save(entity);

        Professor professor = new Professor();
        BeanUtils.copyProperties(entity, professor);

        return professor;
    }

    public ResponseEntity<Void> delete(Long id) {
        Optional<TeacherEntity> teacherEntity = teacherRepository.findById(id);
        if (teacherEntity.isPresent()) {
            teacherRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }

    public Professor update(Long id, ProfessorRequest body) {
        Optional<TeacherEntity> teacherEntity = teacherRepository.findById(id);
        if(teacherEntity.isPresent()){
            TeacherEntity response = teacherRepository.save(ProfessorToTeacherEntityConverter.convert(id,body));
            return TeacherEntityToProfessorConverter.convert(response);
        }
        return null;
    }
}
