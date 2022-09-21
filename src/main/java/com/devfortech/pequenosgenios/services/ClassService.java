package com.devfortech.pequenosgenios.services;

import com.devfortech.models.Classe;
import com.devfortech.models.ClasseRequest;
import com.devfortech.pequenosgenios.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

    @Autowired
    ClassRepository classRepository;
    public Classe create(ClasseRequest body) {
        return null;
    }

    public ResponseEntity<Void> delete(Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
