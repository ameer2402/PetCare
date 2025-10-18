package com.examly.springapp.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.UserAlreadyExistsException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Auth Controller class
 * Controller acts as an intermediary between the service logic and client
 * All HTTP request handling is done in the controller
 */
@RestController
@Tag(name = "Auth", description = "Auth controller used for register and login")
public class AuthController {

    private final UserService userService;

    // Constructor injection for UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /*
     * HTTP POST endpoint to register a new user
     * 
     * @RequestBody binds the HTTP request body to the User parameter
     * Throws UserAlreadyExistsException if the user already exists
     * Returns ResponseEntity with appropriate HTTP status and User object
     * }
     * 
     * /**
     * /**
     * Register a new user
     * 
     * @param user User object to be registered
     * 
     * @return ResponseEntity with the registered User object and appropriate status
     * 
     * @throws UserAlreadyExistsException if the user already exists
     */
    @Operation(summary = "Register a new user", description = "Endpoint to register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    @PostMapping("/api/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        User registeredUser = userService.registerUser(user);
        if (registeredUser == null) {
            // Returning 400 status if user is already registered
            throw new UserAlreadyExistsException("User Already Exists");
        }

        // Returning 200 status and user object if registration is successful
        return ResponseEntity.status(200).body(registeredUser);
    }

    /*
     * HTTP POST endpoint to log in a user
     * 
     * @RequestBody binds the HTTP request body to the User parameter
     * Throws UserNotFoundException if the user is not found
     * Returns ResponseEntity with appropriate HTTP status and LoginDTO object
     * }
     * 
     * /**
     * Login a user
     * 
     * @param user User object containing login details
     * 
     * @return ResponseEntity with the LoginDTO object and appropriate status
     * 
     * @throws UserNotFoundException if the user is not found
     */
    @Operation(summary = "Login a user", description = "Endpoint to login a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @PostMapping("/api/login")
    public ResponseEntity<LoginDTO> loginUser(@RequestBody User user) throws UserNotFoundException {
        LoginDTO login = userService.loginUser(user);
        if (login == null) {
            // Returning 400 status if login is unsuccessful
            throw new UserNotFoundException("User not found");
        }
        // Returning 200 status and login DTO if login is successful
        return ResponseEntity.status(200).body(login);
    }

    /*
     * HTTP GET endpoint to get all users
     * getAllUsers method is called from the service and returns a ResponseEntity
     * with a list of users
     * Returns ResponseEntity with appropriate HTTP status and list of users
     * 
     * /**
     * Get all users
     * 
     * @return ResponseEntity with the list of all users and appropriate status
     */
    @Operation(summary = "Get all users", description = "Endpoint to get all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "No users found")
    })
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            // Returning 400 status if no users are found
            return ResponseEntity.status(400).body(Collections.emptyList());
        }
        // Returning 200 status and the list of users if users are found
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}