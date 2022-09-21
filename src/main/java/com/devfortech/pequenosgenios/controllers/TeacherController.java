package com.devfortech.pequenosgenios.controllers;

import com.devfortech.api.ProfessoresApi;
import com.devfortech.models.Professor;
import com.devfortech.models.ProfessorRequest;
import com.devfortech.pequenosgenios.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController implements ProfessoresApi {

    @Autowired
    TeacherService teacherService;
    @Override
    public ResponseEntity<Professor> professoresCreate(ProfessorRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.save(body));
    }

    @Override
    public ResponseEntity<Void> professoresDelete(Long id) {
        return teacherService.delete(id);
    }

    @Override
    public ResponseEntity<List<Professor>> professoresFindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.findAll());
    }

    @Override
    public ResponseEntity<Professor> professoresUpdate(ProfessorRequest body, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.update(id,body));
    }
}
