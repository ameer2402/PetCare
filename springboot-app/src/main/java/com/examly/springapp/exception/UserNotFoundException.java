package com.examly.springapp.exception;

/**
 * Custom exception class to handle cases where a user is not found.
 */
public class UserNotFoundException extends Exception {

    /**
     * Default constructor for UserNotFoundException.
     * Calls the default constructor of the superclass (Exception).
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructor that accepts a custom message for UserNotFoundException.
     * Calls the parameterized constructor of the superclass (Exception) with the
     * provided message.
     * 
     * @param message A custom error message that explains the reason for the
     *                exception.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}