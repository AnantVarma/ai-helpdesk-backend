package com.anant.helpdesk.admin.service;

import com.anant.helpdesk.admin.dto.CreateAgentRequest;
import com.anant.helpdesk.common.enums.UserRole;
import com.anant.helpdesk.user.entity.User;
import com.anant.helpdesk.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAgent(CreateAgentRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User agent = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.AGENT)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(agent);
        System.out.println("service");

    }
}
