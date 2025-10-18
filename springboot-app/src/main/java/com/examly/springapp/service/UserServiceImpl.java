package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.exception.UserAlreadyExistsException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

/**
 * Implementation of the UserService interface, providing methods to handle
 * user registration, authentication, and retrieval operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtutils;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor to initialize UserServiceImpl with necessary dependencies.
     *
     * @param userRepo           Repository for user data access operations
     * @param passwordEncoder    Service for encoding passwords
     * @param authManager        Manager for handling authentication
     * @param jwtutils           Utility class for handling JWT token operations
     * @param userDetailsService Service for loading user-specific data
     */
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder,
            AuthenticationManager authManager, JwtUtils jwtutils,
            UserDetailsService userDetailsService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtutils = jwtutils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Registers a new user in the system. The user's password is encoded before
     * saving to the repository. Throws an exception if a user with the same
     * email already exists.
     *
     * @param user The user to register
     * @return The registered user
     * @throws UserAlreadyExistsException if a user with the same email already
     *                                    exists
     */
    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        Optional<User> newUser = userRepo.findByEmail(user.getEmail());
        if (!newUser.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }
        throw new UserAlreadyExistsException("User Already exists");
    }

    /**
     * Authenticates a user by verifying their email and password. If the
     * authentication is successful, a JWT token is generated and returned.
     *
     * @param user The user to authenticate
     * @return A LoginDTO containing the JWT token
     * @throws UserNotFoundException if the user is not found or the password is
     *                               incorrect
     */
    @Override
    public LoginDTO loginUser(User user) throws UserNotFoundException {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail()); // Retrieve UserDetails
            User userEntity = userRepo.findByEmail(user.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            int userId = userEntity.getUserId(); // Assuming User entity has an Id field
            String email = userEntity.getEmail();
            String jwtToken = jwtutils.generateToken(userDetails); // Pass userId and email to generateToken

            LoginDTO authUser = new LoginDTO();
            authUser.setJwtToken(jwtToken);
            return authUser;
        }
        throw new UserNotFoundException("Invalid User Name or Password");
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return A list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}