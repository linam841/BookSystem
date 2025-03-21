package com.manilvit.bookmanagmentsystem.configuration;

import com.manilvit.bookmanagmentsystem.model.Role;
import com.manilvit.bookmanagmentsystem.model.User;
import com.manilvit.bookmanagmentsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * This class initializes default users and roles in the database
 * when the application starts, if no users are found.
 */
@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for DataInitializer.
     *
     * @param userRepository  repository for managing user data
     * @param passwordEncoder password encoder for securing passwords
     */
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Runs the data initialization logic.
     *
     * @param args command-line arguments
     */
    @Override
    @Transactional
    public void run(String... args) {
        // If users already exist, do nothing
        if (userRepository.count() > 0) {
            return;
        }

        // Create role objects
        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");

        // Create an admin user with both roles
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminPassword"));
        admin.setFirstname("Admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        admin.setAuthorities(adminRoles);

        // Create a standard user with the user role
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("userPassword"));
        user.setFirstname("User");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setAuthorities(userRoles);

        // Save users in the database
        userRepository.save(admin);
        userRepository.save(user);
    }
}