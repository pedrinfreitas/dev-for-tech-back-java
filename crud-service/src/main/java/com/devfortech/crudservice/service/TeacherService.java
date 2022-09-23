package com.devfortech.crudservice.service;

import com.devfortech.crudservice.domain.entity.AddresEntity;
import com.devfortech.crudservice.domain.entity.PessoaEntity;
import com.devfortech.crudservice.domain.entity.TeacherEntity;
import com.devfortech.crudservice.domain.repository.AddresRepository;
import com.devfortech.crudservice.domain.repository.PessoaRepository;
import com.devfortech.crudservice.domain.repository.TeacherRepository;
import com.devfortech.crudservice.exception.DatabaseException;
import com.devfortech.crudservice.exception.ResourceExistsException;
import com.devfortech.crudservice.exception.ResourceNotFoundException;
import com.devfortech.crudservice.rest.dto.TeacherDTO;
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
public class TeacherService {


    private final TeacherRepository teacherRepository;
    private final PessoaRepository pessoaRepository;
    private final AuthSendMessage authSendMessage;
    private final AddresRepository addresRepository;

    @Transactional
    public TeacherDTO create(TeacherDTO dto) {
        if (pessoaRepository.existsByEmailAddress(dto.getPessoa().getEmailAddress())){
            throw new ResourceExistsException("Ja existe alguem com este endereço de email");
        }

        TeacherEntity entity = new TeacherEntity(dto);
        entity.getPessoa().setAddress(addresRepository.save(new AddresEntity(dto.getPessoa().getAddres())));
        entity.setPessoa(pessoaRepository.save(entity.getPessoa()));

        TeacherDTO newDto = convertToDTO(teacherRepository.save(entity));

        if (newDto.isCreateUser())
            authSendMessage.sendMessageCreateUser(newDto);

        return newDto;
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
    public TeacherDTO updateByID(Long id, TeacherDTO dto){
        TeacherEntity entity = checkById(id);
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
        teacherRepository.save(entity);

        if (entity.isCreateUser())
            authSendMessage.sendMessageUpdateUser(entity.getPessoa(), oldEmail);
        return new TeacherDTO(entity);
    }

    @Transactional
    public void deleteByID(Long id){
        try {
            TeacherEntity entity = checkById(id);
            teacherRepository.delete(entity);

            if (entity.isCreateUser())
                authSendMessage.sendMessageDeleteUser(entity.getPessoa().getEmailAddress());
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
