package com.devfortech.pequenosgenios.controllers;

import com.devfortech.api.ClassesApi;
import com.devfortech.models.Classe;
import com.devfortech.models.ClasseRequest;
import com.devfortech.pequenosgenios.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassController implements ClassesApi {
    @Autowired
    ClassService classService;

    @Override
    public ResponseEntity<Classe> classesCreate(ClasseRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classService.create(body));
    }

    @Override
    public ResponseEntity<Void> classesDelete(Long id) {
        return classService.delete(id);
    }

    @Override
    public ResponseEntity<List<Classe>> classesFindAll() {
        return null;
    }

    @Override
    public ResponseEntity<Classe> classesUpdate(ClasseRequest body, Long id) {
        return null;
    }
}
