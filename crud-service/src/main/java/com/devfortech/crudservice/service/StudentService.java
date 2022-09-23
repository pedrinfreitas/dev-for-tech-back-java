package com.devfortech.crudservice.service;

import com.devfortech.crudservice.domain.entity.AddresEntity;
import com.devfortech.crudservice.domain.entity.PessoaEntity;
import com.devfortech.crudservice.domain.entity.StudentEntity;
import com.devfortech.crudservice.domain.repository.AddresRepository;
import com.devfortech.crudservice.domain.repository.ClassRepository;
import com.devfortech.crudservice.domain.repository.PessoaRepository;
import com.devfortech.crudservice.domain.repository.StudentRepository;
import com.devfortech.crudservice.exception.DatabaseException;
import com.devfortech.crudservice.exception.ResourceExistsException;
import com.devfortech.crudservice.exception.ResourceNotFoundException;
import com.devfortech.crudservice.rest.dto.StudentDTO;
import com.devfortech.crudservice.rest.message.AuthSendMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    private final ClassRepository classRepository;
    private final AddresRepository addresRepository;
    private final AuthSendMessage authSendMessage;


    @Transactional
    public StudentDTO create(StudentDTO dto) {
        if (pessoaRepository.existsByEmailAddress(dto.getPessoa().getEmailAddress())){
            throw new ResourceExistsException("Ja existe alguem com este endereço de email");
        }

        StudentEntity entity = new StudentEntity(dto);;

        AddresEntity addres = addresRepository.findByPostalCode(dto.getPessoa().getAddres().getPostalCode());

        entity.getPessoa().setAddress(addresRepository.save(
                addres != null ? addres : new AddresEntity(dto.getPessoa().getAddres())));

        entity.setPessoa(pessoaRepository.save(entity.getPessoa()));

        if (dto.getClasseID() != null)
            entity.setClasse(classRepository.findById(dto.getClasseID())
                .orElseThrow(() -> new ResourceNotFoundException("Classe", "Id", dto.getClasseID())));

        StudentDTO newDto = convertToDTO(studentRepository.save(entity));

        if (newDto.isCreateUser())
            authSendMessage.sendMessageCreateUser(newDto);

        return newDto;
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
    public StudentDTO update(Long id, StudentDTO dto){
        StudentEntity entity = checkById(id);

        PessoaEntity pessoaEntity = entity.getPessoa();
        AddresEntity addres = entity.getPessoa().getAddress();
        dto.getPessoa().setId(pessoaEntity.getId());
        dto.getPessoa().getAddres().setId(addres.getId());
        dto.setId(entity.getId());

        String oldEmail = entity.getPessoa().getEmailAddress();

        BeanUtils.copyProperties(dto.getPessoa(), pessoaEntity);
        BeanUtils.copyProperties(dto.getPessoa().getAddres(), addres);
        BeanUtils.copyProperties(dto, entity);

        pessoaRepository.save(pessoaEntity);
        addresRepository.save(addres);
        studentRepository.save(entity);

        if (entity.isCreateUser())
            authSendMessage.sendMessageUpdateUser(entity.getPessoa(), oldEmail);
        return convertToDTO(entity);
    }

    @Transactional
    public void deleteByID(Long id){
        try {
            StudentEntity entity = checkById(id);
            studentRepository.delete(entity);
            pessoaRepository.deleteById(id);
            addresRepository.deleteById(id);

            if (entity.isCreateUser())
                authSendMessage.sendMessageDeleteUser(entity.getPessoa().getEmailAddress());

        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private StudentEntity checkById(Long id){
        return  studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno nao encontrado com o ID: " + id));
    }

    private StudentDTO convertToDTO(StudentEntity entity){

        if (entity.getClasse() == null)
            return new StudentDTO(entity, null);
        else
            return new StudentDTO(entity, entity.getClasse().getId());
    }
}
