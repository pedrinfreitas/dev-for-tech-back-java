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
            pessoaRepository.deleteById(id);
            addresRepository.deleteById(id);
        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
    @Transactional
    public StudentDTO update(Long id,StudentDTO dto) {
        StudentEntity entity = checkById(id);

        entity.setFees(dto.getFees());

        entity.getPessoa().setId(id);
        entity.getPessoa().setName(dto.getPessoa().getName());
        entity.getPessoa().setPhoneNumber(dto.getPessoa().getPhoneNumber());
        entity.getPessoa().setEmailAddress(dto.getPessoa().getEmailAddress());

        entity.getPessoa().getAddress().setId(id);
        entity.getPessoa().getAddress().setStreet((dto.getPessoa().getAddres().getStreet()));
        entity.getPessoa().getAddress().setCity((dto.getPessoa().getAddres().getCity()));
        entity.getPessoa().getAddress().setCountry((dto.getPessoa().getAddres().getCountry()));
        entity.getPessoa().getAddress().setPostalCode((dto.getPessoa().getAddres().getPostalCode()));
        entity.getPessoa().getAddress().setState((dto.getPessoa().getAddres().getState()));

        return convertToDTO(studentRepository.save(entity));
    }

    private StudentEntity checkById(Long id){
        return  studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno nao encontrado com o ID: " + id));
    }

    private StudentDTO convertToDTO(StudentEntity entity){
        return new StudentDTO(entity);
    }
}
