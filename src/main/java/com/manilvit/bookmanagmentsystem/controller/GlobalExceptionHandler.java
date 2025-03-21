package com.manilvit.bookmanagmentsystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to manage application-wide exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityNotFoundException and returns a 404 Not Found response.
     *
     * @param e the exception
     * @return ResponseEntity with error message and HTTP status 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DataIntegrityViolationException and returns a 400 Bad Request response.
     *
     * @param e the exception
     * @return ResponseEntity with error message and HTTP status 400
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Data integrity violation: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles RuntimeException and returns a 500 Internal Server Error response.
     *
     * @param e the exception
     * @return ResponseEntity with error message and HTTP status 500
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>("Failed to process request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all other exceptions and returns a 500 Internal Server Error response.
     *
     * @param e the exception
     * @return ResponseEntity with error message and HTTP status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}