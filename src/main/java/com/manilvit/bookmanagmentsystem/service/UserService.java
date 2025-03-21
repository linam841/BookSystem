package com.manilvit.bookmanagmentsystem.service;

import com.manilvit.bookmanagmentsystem.dto.UserMapper;
import com.manilvit.bookmanagmentsystem.dto.UserRegistrationDTO;
import com.manilvit.bookmanagmentsystem.model.Role;
import com.manilvit.bookmanagmentsystem.model.User;
import com.manilvit.bookmanagmentsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User newUser = userMapper.toUser(userRegistrationDTO);

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");

        newUser.setAuthorities(Set.of(userRole)); // Назначаем роль

        return userRepository.save(newUser);
    }
}