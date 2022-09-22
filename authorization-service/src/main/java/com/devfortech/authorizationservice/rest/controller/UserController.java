package com.devfortech.authorizationservice.rest.controller;

import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import com.devfortech.authorizationservice.exception.ResourceNotFoundException;
import com.devfortech.authorizationservice.rest.dto.ChangeUserRequest;
import com.devfortech.authorizationservice.rest.dto.SignUpRequest;
import com.devfortech.authorizationservice.rest.dto.UserProfile;
import com.devfortech.authorizationservice.service.AuthService;
import com.devfortech.authorizationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public UserProfile getUserProfile(@RequestParam(value = "email") String email) {
        return userService.findUser(email);
    }

    @PutMapping
    public ResponseEntity<UserProfile> updateUser(@RequestParam(value = "email") String email, @RequestBody ChangeUserRequest dto) {
        return new ResponseEntity<>(authService.updateUser(email, dto), OK);
    }

}
