package com.examly.springapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global Exception Handler class
 * This is used to handle exceptions that are thrown in the service.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException.
     * 
     * @param e The UserNotFoundException thrown.
     * @return A ResponseEntity with HTTP status 404 and the exception message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userException(UserNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    /**
     * Handles UserAlreadyExistsException.
     * 
     * @param e The UserAlreadyExistsException thrown.
     * @return A ResponseEntity with HTTP status 400 and the exception message.
     */
    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<String> userEmailNotFoundException(UserEmailNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    // Handle UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    /**
     * Handles PetNotFoundException.
     * 
     * @param e The PetNotFoundException thrown.
     * @return A ResponseEntity with HTTP status 400 and the exception message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle UserAlreadyExistsException
    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<String> petNotFoundException(PetNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<String> AppointmentNotFoundException(AppointmentNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(AppointmentExceedException.class)
    public ResponseEntity<String> AppointmentExceedException(AppointmentExceedException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    // Handle FeedbackAlreadyExistsException
    @ExceptionHandler(FeedbackAlreadyExistException.class)
    public ResponseEntity<String> feedbackAlreadyExistException(FeedbackAlreadyExistException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}