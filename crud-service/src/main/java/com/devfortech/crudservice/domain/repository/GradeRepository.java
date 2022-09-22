package com.devfortech.crudservice.domain.repository;

import com.devfortech.crudservice.domain.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<GradeEntity,Long> {

    boolean existsByDescricao(String descricao);

    Optional<GradeEntity> findByDescricao(String descricao);
}
