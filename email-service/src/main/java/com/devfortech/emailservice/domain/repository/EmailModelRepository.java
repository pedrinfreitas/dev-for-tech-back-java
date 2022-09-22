package com.devfortech.emailservice.domain.repository;

import com.devfortech.emailservice.domain.entity.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailModelRepository extends JpaRepository<EmailModel, Long> {
}
