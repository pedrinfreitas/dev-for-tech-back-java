package com.devfortech.pequenosgenios.controllers;


import com.devfortech.api.EstudantesApi;
import com.devfortech.models.Estudante;
import com.devfortech.models.EstudanteRequest;
import com.devfortech.pequenosgenios.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController implements EstudantesApi {
    @Autowired
    StudentService studentService;

    @Override
    public ResponseEntity<List<Estudante>> estudantesGet() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll());
    }

    @Override
    public ResponseEntity<Void> estudantesIdDelete(Long id) {
        return studentService.delete(id);
    }

    @Override
    public ResponseEntity<Estudante> estudantesIdPut(EstudanteRequest body, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.update(id,body));
    }

    @Override
    public ResponseEntity<Estudante> estudantesPost(EstudanteRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(body));
    }
}
