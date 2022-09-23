package com.devfortech.crudservice.service;

import com.devfortech.crudservice.domain.entity.GradeEntity;
import com.devfortech.crudservice.domain.repository.GradeRepository;
import com.devfortech.crudservice.exception.DatabaseException;
import com.devfortech.crudservice.exception.ResourceExistsException;
import com.devfortech.crudservice.exception.ResourceNotFoundException;
import com.devfortech.crudservice.rest.dto.GradeDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    @Transactional
    public GradeDTO create(GradeDTO dto) {
        if (gradeRepository.existsByDescricao(dto.getDescricao())){
            throw new ResourceExistsException("Já existe grade com esta descrição");
        }
        return new GradeDTO(gradeRepository.save(new GradeEntity(dto)));
    }

    @Transactional(readOnly = true)
    public Page<GradeDTO> findAll(Pageable pageable){
        Page<GradeEntity> entity = gradeRepository.findAll(pageable);
        return entity.map(this::convertToDTO);
    }

    @Transactional
    public GradeDTO updateByID(Long id, GradeDTO dto){
        GradeEntity entity = checkById(id);
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        gradeRepository.save(entity);
        return new GradeDTO(entity);
    }

    @Transactional
    public void deleteByID(Long id){
        try {
            gradeRepository.delete(checkById(id));
        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private GradeEntity checkById(Long id){
        return  gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade nao encontrada com o ID: " + id));
    }

    private GradeDTO convertToDTO(GradeEntity entity){
        return new GradeDTO(entity);
    }
}
