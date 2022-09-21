package com.devfortech.pequenosgenios.repositories;

import com.devfortech.pequenosgenios.entities.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository <ClassEntity,Long>{
}
