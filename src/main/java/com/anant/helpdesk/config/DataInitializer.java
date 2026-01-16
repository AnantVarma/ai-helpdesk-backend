package com.anant.helpdesk.config;

import com.anant.helpdesk.common.enums.UserRole;
import com.anant.helpdesk.user.entity.User;
import com.anant.helpdesk.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByEmail("av@gmail.com").isEmpty()) {

                User admin = User.builder()
                        .name("Anant Varma")
                        .email("av@gmail.com")
                        .password(passwordEncoder.encode("av123"))
                        .role(UserRole.ADMIN)
                        .createdAt(LocalDateTime.now())
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
