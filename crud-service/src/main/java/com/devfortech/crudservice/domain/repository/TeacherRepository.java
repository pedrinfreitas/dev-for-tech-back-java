package com.devfortech.crudservice.domain.repository;

import com.devfortech.crudservice.domain.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity,Long> {
}
