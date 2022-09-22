package com.devfortech.crudservice.service;

import com.devfortech.crudservice.domain.entity.AddresEntity;
import com.devfortech.crudservice.domain.entity.TeacherEntity;
import com.devfortech.crudservice.domain.repository.AddresRepository;
import com.devfortech.crudservice.domain.repository.PessoaRepository;
import com.devfortech.crudservice.domain.repository.TeacherRepository;
import com.devfortech.crudservice.exception.DatabaseException;
import com.devfortech.crudservice.exception.ResourceExistsException;
import com.devfortech.crudservice.exception.ResourceNotFoundException;
import com.devfortech.crudservice.rest.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TeacherService {


    private final TeacherRepository teacherRepository;
    private final PessoaRepository pessoaRepository;

    private final AddresRepository addresRepository;

    @Transactional
    public TeacherDTO create(TeacherDTO dto) {
        if (pessoaRepository.existsByEmailAddress(dto.getPessoa().getEmailAddress())){
            throw new ResourceExistsException("Ja existe um professor com este endereço de email");
        }

        TeacherEntity entity = new TeacherEntity(dto);
        entity.getPessoa().setAddress(addresRepository.save(new AddresEntity(dto.getPessoa().getAddres())));
        entity.setPessoa(pessoaRepository.save(entity.getPessoa()));
        return convertToDTO(teacherRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<TeacherDTO> findAll(Pageable pageable){
        Page<TeacherEntity> entity = teacherRepository.findAll(pageable);
        return entity.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public TeacherDTO findByID(Long id){
        TeacherEntity entity = checkById(id);
        return convertToDTO(entity);
    }

    @Transactional
    public void deleteByID(Long id){
        try {
            teacherRepository.delete(checkById(id));
        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }


    private TeacherEntity checkById(Long id){
        return  teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor nao encontrado com o ID: " + id));
    }

    private TeacherDTO convertToDTO(TeacherEntity entity){
        return new TeacherDTO(entity);
    }

}
