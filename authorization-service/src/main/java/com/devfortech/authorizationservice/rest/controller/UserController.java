package com.devfortech.authorizationservice.rest.controller;

import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import com.devfortech.authorizationservice.exception.ResourceNotFoundException;
import com.devfortech.authorizationservice.rest.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/checkUsernameAvailability")
    public Boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return isAvailable;
    }


    @GetMapping("/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username));

           UserProfile userProfile = new UserProfile(usuario.getId(), usuario.getUsername(), usuario.getEmail());

        return userProfile;
    }

}
