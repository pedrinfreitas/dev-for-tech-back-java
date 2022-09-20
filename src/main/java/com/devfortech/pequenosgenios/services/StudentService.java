package com.devfortech.pequenosgenios.services;

import com.devfortech.models.Estudante;
import com.devfortech.models.EstudanteRequest;
import com.devfortech.pequenosgenios.converters.EstudanteToStudentEntityConverter;
import com.devfortech.pequenosgenios.converters.StudentsEntityToEstudanteConverter;
import com.devfortech.pequenosgenios.entities.StudentEntity;
import com.devfortech.pequenosgenios.repositories.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public List<Estudante> findAll() {
        List<StudentEntity> students = studentRepository.findAll();
        return StudentsEntityToEstudanteConverter.convert(students);
    }

    public Estudante save(EstudanteRequest body) {
        var entity = new StudentEntity();
        BeanUtils.copyProperties(body, entity);
        studentRepository.save(entity);

        Estudante estudante = new Estudante();
        BeanUtils.copyProperties(entity, estudante);

        return estudante;
    }

    public ResponseEntity<Void> delete(Long id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if (studentEntity.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }

    public Estudante update(Long id, EstudanteRequest body) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if(studentEntity.isPresent()){
            StudentEntity response = studentRepository.save(EstudanteToStudentEntityConverter.convert(id,body));
            return StudentsEntityToEstudanteConverter.convert(response);
        }
        return null;
    }
}
