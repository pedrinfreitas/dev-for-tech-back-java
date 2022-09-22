package com.devfortech.crudservice.rest.controller;

import com.devfortech.crudservice.rest.dto.GradeDTO;
import com.devfortech.crudservice.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService service;

    @PostMapping
    public GradeDTO create(@RequestBody GradeDTO dto) {
        GradeDTO createDto = service.create(dto);
        return createDto;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
