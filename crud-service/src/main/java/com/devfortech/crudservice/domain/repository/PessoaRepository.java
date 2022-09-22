package com.devfortech.crudservice.domain.repository;

import com.devfortech.crudservice.domain.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity,Long> {

    boolean existsByEmailAddress(String email);

}
