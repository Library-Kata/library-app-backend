package com.library.app.config;

import com.library.app.model.UserRole;
import com.library.app.model.entity.UserEntity;
import com.library.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Initializes default data for the application.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create a default admin user if not exists
        if (!userRepository.existsById("admin")) {
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(UserRole.ROLE_SUPERADMIN))
                    .build();
            userRepository.save(admin);
        }
    }
}
