package com.devfortech.emailservice.rest.controller;

import com.devfortech.emailservice.domain.entity.EmailModel;
import com.devfortech.emailservice.rest.dto.EmailDTO;
import com.devfortech.emailservice.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDTO dto) {
        EmailModel entity = new EmailModel();
        BeanUtils.copyProperties(dto, entity);
        emailService.sendEmail(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/emails/{emailId}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value="emailId") Long id){
        Optional<EmailModel> emailModelOptional = emailService.findById(id);
        return emailModelOptional.<ResponseEntity<Object>>map(
                emailModel -> ResponseEntity.status(HttpStatus.OK).body(emailModel))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found."));
    }
}
