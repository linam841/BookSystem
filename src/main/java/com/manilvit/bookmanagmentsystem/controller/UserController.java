package com.manilvit.bookmanagmentsystem.controller;

import com.manilvit.bookmanagmentsystem.dto.UserRegistrationDTO;
import com.manilvit.bookmanagmentsystem.model.User;
import com.manilvit.bookmanagmentsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related operations such as registration.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Registers a new user.
     *
     * @param userRegistrationDTO the user registration data transfer object
     * @return ResponseEntity containing the created user and HTTP status 201 (Created)
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User newUser = userService.registerUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
