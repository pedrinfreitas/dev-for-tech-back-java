package com.devfortech.crudservice.rest.controller;

import com.devfortech.crudservice.rest.dto.ClassRequestDTO;
import com.devfortech.crudservice.rest.dto.ClassResponseDTO;
import com.devfortech.crudservice.service.ClassService;
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
@RequestMapping("/classe")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService service;

    private final PagedResourcesAssembler<ClassResponseDTO> assembler;

    @PostMapping
    public ClassResponseDTO create(@RequestBody ClassRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ClassResponseDTO> dto = service.findAll(pageable);
        dto.stream().forEach(v -> v.add(linkById(v.getId())));
        PagedModel<EntityModel<ClassResponseDTO>> pagedModel = assembler.toModel(dto);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ClassResponseDTO findById(@PathVariable Long id){
        ClassResponseDTO dto = service.findByID(id);
        dto.add(linkById(id));
        return dto;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private Link linkById(Long id){
        return linkTo(methodOn(ClassController.class).findById(id)).withSelfRel();
    }
}
