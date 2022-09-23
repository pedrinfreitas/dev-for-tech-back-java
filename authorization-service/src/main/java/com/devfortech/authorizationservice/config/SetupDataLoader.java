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

        if (userRepository.existsByEmail("admin@devfortech.com")){
            return;
        }

        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("STUDENT");
        createRoleIfNotFound("TEACHER");

        Set<Role> adminRole = new HashSet<>();
        adminRole.add(roleRepository.findByDescription("ADMIN"));

        Set<Role> studentRole = new HashSet<>();
        studentRole.add(roleRepository.findByDescription("STUDENT"));

        Set<Role> teacherRole = new HashSet<>();
        teacherRole.add(roleRepository.findByDescription("TEACHER"));


        User admin = new User();
        admin.setNome("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@devfortech.com");
        admin.setRoles(adminRole);
        admin.setEnabled(true);
        admin.setCredentialsNonExpired(true);
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        userRepository.save(admin);
        User student = new User();
        student.setNome("student");
        student.setPassword(passwordEncoder.encode("student"));
        student.setEmail("student@devfortech.com");
        student.setRoles(studentRole);
        student.setEnabled(true);
        student.setCredentialsNonExpired(true);
        student.setAccountNonExpired(true);
        student.setAccountNonLocked(true);
        userRepository.save(student);

        User teacher = new User();
        teacher.setNome("teacher");
        teacher.setPassword(passwordEncoder.encode("teacher"));
        teacher.setEmail("teacher@devfortech.com");
        teacher.setRoles(teacherRole);
        teacher.setEnabled(true);
        teacher.setCredentialsNonExpired(true);
        teacher.setAccountNonExpired(true);
        teacher.setAccountNonLocked(true);
        userRepository.save(teacher);
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
