package com.devfortech.authorizationservice.service;

import com.devfortech.authorizationservice.config.jwt.JwtTokenProvider;
import com.devfortech.authorizationservice.domain.entity.Role;
import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.RoleRepository;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import com.devfortech.authorizationservice.exception.SignupException;
import com.devfortech.authorizationservice.rest.dto.ChangeUserRequest;
import com.devfortech.authorizationservice.rest.dto.LoginRequest;
import com.devfortech.authorizationservice.rest.dto.SignUpRequest;
import com.devfortech.authorizationservice.rest.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return  tokenProvider.createToken(getUser(email));
    }


    public void signup(SignUpRequest req){
        if(userRepository.existsByEmail(req.getEmail())) {
            throw new SignupException("Email ja esta em uso!");
        }

        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByDescription(req.getRole()));

        User usuario = new User(req);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        usuario.setEnabled(true);
        usuario.setRoles(role);

        userRepository.save(usuario);
    }

    @Transactional
    public UserProfile updateUser(String email, ChangeUserRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getOldPassword()));

        User usuario = getUser(email);
        usuario.setNome(request.getName());

        if (request.getNewEmail() != null && !request.getNewEmail().equals(""))
            usuario.setEmail(request.getNewEmail());

        if (request.getNewPassword() != null && !request.getNewPassword().equals(""))
            usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(usuario);

        return new UserProfile(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }


    private User getUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {  throw new UsernameNotFoundException("User not found");  });
    }


}
