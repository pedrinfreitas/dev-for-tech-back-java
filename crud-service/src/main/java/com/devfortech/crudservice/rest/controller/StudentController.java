package com.devfortech.crudservice.rest.controller;

import com.devfortech.crudservice.rest.dto.StudentDTO;
import com.devfortech.crudservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    private final PagedResourcesAssembler<StudentDTO> assembler;

    @PostMapping
    public StudentDTO create(@RequestBody StudentDTO dto) {
        StudentDTO createDto = service.create(dto);
        createDto.add(linkById(createDto.getId()));
        return createDto;
    }

    @GetMapping(value = "{id}")
    public StudentDTO findById(@PathVariable Long id){
        StudentDTO dto = service.findByID(id);
        dto.add(linkById(id));
        return dto;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<StudentDTO> dto = service.findAll(pageable);
        dto.stream().forEach(v -> v.add(linkById(v.getId())));
        PagedModel<EntityModel<StudentDTO>> pagedModel = assembler.toModel(dto);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody StudentDTO dto){
        var response = service.update(id,dto);
        response.add(linkById(response.getId()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Link linkById(Long id){
        return linkTo(methodOn(StudentController.class).findById(id)).withSelfRel();
    }

}
