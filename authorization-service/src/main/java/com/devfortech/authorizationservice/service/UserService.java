package com.devfortech.authorizationservice.service;

import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public User findByUserName(String username){
        var user = repository.findByUsername(username)
                .orElseThrow(() -> { throw new UsernameNotFoundException("Username "  + username + " not found");});
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUserName(username);
    }
}
