package com.devfortech.crudservice.domain.repository;

import com.devfortech.crudservice.domain.entity.AddresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresRepository extends JpaRepository<AddresEntity,Long> {

    AddresEntity findByPostalCode(String cep);
}
