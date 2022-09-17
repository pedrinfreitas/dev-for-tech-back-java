package com.devfortech.authorizationservice.service;

import com.devfortech.authorizationservice.config.jwt.JwtTokenProvider;
import com.devfortech.authorizationservice.domain.entity.Role;
import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.RoleRepository;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import com.devfortech.authorizationservice.exception.SignupException;
import com.devfortech.authorizationservice.rest.dto.LoginRequest;
import com.devfortech.authorizationservice.rest.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String login(LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> {throw new UsernameNotFoundException("User name not found");});

        return  tokenProvider.createToken(user);
    }


    public void signup(SignUpRequest req){
        if(userRepository.existsByUsername(req.getUsername())) {
            throw new SignupException("Username ja esta em uso!");
        }
        if(userRepository.existsByEmail(req.getUsername())) {
            throw new SignupException("Username ja esta em uso!");
        }

        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByDescription("USER"));

        User usuario = new User(req);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        usuario.setEnabled(true);
        usuario.setRoles(role);

        userRepository.save(usuario);
    }

}
