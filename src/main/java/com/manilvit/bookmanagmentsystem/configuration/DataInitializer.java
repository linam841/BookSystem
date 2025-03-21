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


@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Если пользователи уже существуют, ничего не делаем
        if (userRepository.count() > 0) {
            return;
        }

        // Создаем объекты ролей
        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");


        // Создаем администратора с обеими ролями
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminPassword"));
        admin.setFirstname("Admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        admin.setAuthorities(adminRoles);

        // Создаем обычного пользователя с ролью пользователя
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("userPassword"));
        user.setFirstname("User");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setAuthorities(userRoles);

        // Сохраняем пользователей. Теперь роли уже есть в базе данных, и они будут привязаны к пользователю.
        userRepository.save(admin);
        userRepository.save(user);
    }
}