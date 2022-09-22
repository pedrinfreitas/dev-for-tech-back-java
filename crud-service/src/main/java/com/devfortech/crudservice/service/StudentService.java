package com.devfortech.crudservice.service;

import com.devfortech.crudservice.domain.entity.AddresEntity;
import com.devfortech.crudservice.domain.entity.StudentEntity;
import com.devfortech.crudservice.domain.repository.AddresRepository;
import com.devfortech.crudservice.domain.repository.PessoaRepository;
import com.devfortech.crudservice.domain.repository.StudentRepository;
import com.devfortech.crudservice.exception.DatabaseException;
import com.devfortech.crudservice.exception.ResourceExistsException;
import com.devfortech.crudservice.exception.ResourceNotFoundException;
import com.devfortech.crudservice.rest.dto.StudentDTO;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PessoaRepository pessoaRepository;
    private final AddresRepository addresRepository;


    @Transactional
    public StudentDTO create(StudentDTO dto) {
        if (pessoaRepository.existsByEmailAddress(dto.getPessoa().getEmailAddress())){
            throw new ResourceExistsException("Ja existe um Aluno com este endereço de email");
        }

        StudentEntity entity = new StudentEntity(dto);;
        entity.getPessoa().setAddress(addresRepository.save(new AddresEntity(dto.getPessoa().getAddres())));
        entity.setPessoa(pessoaRepository.save(entity.getPessoa()));
        return convertToDTO(studentRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable){
        Page<StudentEntity> entity = studentRepository.findAll(pageable);
        return entity.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public StudentDTO findByID(Long id){
        StudentEntity entity = checkById(id);
        return convertToDTO(entity);
    }

    @Transactional
    public void deleteByID(Long id){
        try {
            studentRepository.delete(checkById(id));
        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private StudentEntity checkById(Long id){
        return  studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno nao encontrado com o ID: " + id));
    }

    private StudentDTO convertToDTO(StudentEntity entity){
        return new StudentDTO(entity);
    }
}
