package com.devfortech.crudservice.service;

import com.devfortech.crudservice.domain.entity.ClassEntity;
import com.devfortech.crudservice.domain.entity.GradeEntity;
import com.devfortech.crudservice.domain.entity.StudentEntity;
import com.devfortech.crudservice.domain.repository.ClassRepository;
import com.devfortech.crudservice.domain.repository.GradeRepository;
import com.devfortech.crudservice.domain.repository.StudentRepository;
import com.devfortech.crudservice.domain.repository.TeacherRepository;
import com.devfortech.crudservice.exception.DatabaseException;
import com.devfortech.crudservice.exception.ResourceNotFoundException;
import com.devfortech.crudservice.rest.dto.ClassRequestDTO;
import com.devfortech.crudservice.rest.dto.ClassResponseDTO;
import com.devfortech.crudservice.rest.dto.GradeDTO;
import com.devfortech.crudservice.rest.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final GradeRepository gradeRepository;


    @Transactional
    public ClassResponseDTO create(ClassRequestDTO dto) {
        return mapEntityToResponseDTO(classRepository.save(mapRequestToEntity(dto)));
    }

    @Transactional(readOnly = true)
    public Page<ClassResponseDTO> findAll(Pageable pageable){
        Page<ClassEntity> entity = classRepository.findAll(pageable);
        return entity.map(this::mapEntityToResponseDTO);
    }

    @Transactional(readOnly = true)
    public ClassResponseDTO findByID(Long id){
        ClassEntity entity = checkById(id);
        return mapEntityToResponseDTO(entity);
    }
    @Transactional
    public ClassResponseDTO updateByID(Long id, ClassRequestDTO dto){
        ClassEntity entity = checkById(id);
        BeanUtils.copyProperties(mapRequestToEntity(dto), entity);
        entity.setId(id);
        classRepository.save(entity);
        return mapEntityToResponseDTO(entity);
    }

    @Transactional
    public void deleteByID(Long id){
        try {
            classRepository.delete(checkById(id));
        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private ClassEntity checkById(Long id){
        return  classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe nao econtrada com o id: " + id));
    }


    private ClassEntity mapRequestToEntity(ClassRequestDTO dto){
        ClassEntity classe = new ClassEntity();
        Set<GradeEntity> grades = new HashSet<>();
        Set<StudentEntity> alunos = new HashSet<>();

        dto.getGrades().forEach(x -> grades.add(gradeRepository.findByDescricao(x)
                        .orElseThrow(() -> new ResourceNotFoundException("Grade "+ x +" nao cadastrada"))));

        dto.getStudents().forEach(x -> alunos.add(studentRepository.findById(x)
                        .orElseThrow(() -> new ResourceNotFoundException("Aluno nao encontrado com o id: " + x))));

        classe.setGrades(grades);
        classe.setStudents(alunos);
        classe.setTeacher(teacherRepository.findById(dto.getTeacher())
                        .orElseThrow(() -> new ResourceNotFoundException("Professor nao encontrado com o id: " + dto.getTeacher())));

        return classe;
    }


    private ClassResponseDTO mapEntityToResponseDTO(ClassEntity entity){
        ClassResponseDTO classe = new ClassResponseDTO();
        Set<GradeDTO> grades = new HashSet<>();
        HashMap<Long, String> alunos = new HashMap<>();

        entity.getStudents().forEach(x -> alunos.put(x.getId(), x.getPessoa().getName()));

        entity.getGrades().forEach(x -> grades.add(new GradeDTO(gradeRepository.findByDescricao(x.getDescricao())
                .orElseThrow(() -> new ResourceNotFoundException("Grade "+ x +" nao cadastrada")))));

        classe.setId(entity.getId());
        classe.setGrades(grades);
        classe.setStudents(alunos);
        classe.setTeacher(new TeacherDTO(entity.getTeacher()));

        return classe;
    }

}
