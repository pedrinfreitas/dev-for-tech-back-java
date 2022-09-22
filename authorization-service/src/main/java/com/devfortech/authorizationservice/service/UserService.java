package com.devfortech.authorizationservice.service;

import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import com.devfortech.authorizationservice.exception.ResourceNotFoundException;
import com.devfortech.authorizationservice.rest.dto.ChangeUserRequest;
import com.devfortech.authorizationservice.rest.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private User findByUserEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> { throw new UsernameNotFoundException("User not found");});
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByUserEmail(email);
    }

    @Transactional(readOnly = true)
    public UserProfile findUser(String email){
        User usuario = findByUserEmail(email);
        return new UserProfile(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

}
