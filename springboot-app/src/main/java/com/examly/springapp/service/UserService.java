package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.exception.UserAlreadyExistsException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;

/**
 * Service interface for handling user-related operations.
 */
public interface UserService {

    /**
     * Registers a new user.
     * 
     * @param user The user to register
     * @return The registered user
     * @throws UserAlreadyExistsException if a user with the same email already
     *                                    exists
     */
    User registerUser(User user) throws UserAlreadyExistsException;

    /**
     * Authenticates a user and generates a JWT token.
     * 
     * @param user The user to authenticate
     * @return A LoginDTO containing the JWT token
     * @throws UserNotFoundException if the user is not found or the password is
     *                               incorrect
     */
    LoginDTO loginUser(User user) throws UserNotFoundException;

    /**
     * Retrieves all users.
     * 
     * @return A list of all users
     */
    List<User> getAllUsers();
}
