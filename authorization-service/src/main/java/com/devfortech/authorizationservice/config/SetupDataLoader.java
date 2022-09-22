package com.devfortech.authorizationservice.config;

import com.devfortech.authorizationservice.domain.entity.Role;
import com.devfortech.authorizationservice.domain.entity.User;
import com.devfortech.authorizationservice.domain.repository.RoleRepository;
import com.devfortech.authorizationservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (userRepository.existsByEmail("test@test.com")){
            return;
        }

        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleRepository.findByDescription("ADMIN"));

        Set<Role> userRole = new HashSet<>();
        userRole.add(roleRepository.findByDescription("USER"));

        User admin = new User();
        admin.setNome("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("test@test.com");
        admin.setRoles(adminRoles);
        admin.setEnabled(true);
        admin.setCredentialsNonExpired(true);
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        userRepository.save(admin);

        User user = new User();
        user.setNome("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("test2@test.com");
        user.setRoles(userRole);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }

    @Transactional
    void createRoleIfNotFound(String name) {

        Role role = roleRepository.findByDescription(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }

}
