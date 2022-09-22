package com.devfortech.authorizationservice.domain.repository;

import com.devfortech.authorizationservice.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByDescription(String description);
}
