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

/**
 * Service for managing user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Loads user details by username (email).
     *
     * @param username the username (email) of the user
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Registers a new user with a default "ROLE_USER" authority.
     *
     * @param userRegistrationDTO the user registration data transfer object
     * @return the saved user entity
     */
    @Transactional
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User newUser = userMapper.toUser(userRegistrationDTO);

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");

        newUser.setAuthorities(Set.of(userRole));

        return userRepository.save(newUser);
    }
}
