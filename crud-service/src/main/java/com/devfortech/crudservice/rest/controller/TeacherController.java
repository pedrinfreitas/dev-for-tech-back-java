package com.devfortech.crudservice.rest.controller;

import com.devfortech.crudservice.rest.dto.TeacherDTO;
import com.devfortech.crudservice.service.TeacherService;
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

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService service;

    private final PagedResourcesAssembler<TeacherDTO> assembler;

    @PostMapping
    public TeacherDTO create(@Valid @RequestBody TeacherDTO dto) {
        TeacherDTO createDto = service.create(dto);
        createDto.add(linkById(createDto.getId()));
        return createDto;
    }

    @GetMapping(value = "{id}")
    public TeacherDTO findById(@PathVariable Long id){
        TeacherDTO dto = service.findByID(id);
        dto.add(linkById(id));
        return dto;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<TeacherDTO> dto = service.findAll(pageable);
        dto.stream().forEach(x -> x.add(linkById(x.getId())));
        PagedModel<EntityModel<TeacherDTO>> pagedModel = assembler.toModel(dto);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO dto, @PathVariable Long id){
        TeacherDTO newDto = service.updateByID(id, dto);
        return new ResponseEntity<>(newDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Link linkById(Long id){
        return linkTo(methodOn(TeacherController.class).findById(id)).withSelfRel();
    }

}
