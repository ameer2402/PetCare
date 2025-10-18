package com.examly.springapp.exception;

/**
 * Custom exception class to handle cases where a user already exists.
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Default constructor for UserAlreadyExistsException.
     * Calls the default constructor of the superclass (Exception).
     */
    public UserAlreadyExistsException() {
        super();
    }

    /**
     * Constructor that accepts a custom message for UserAlreadyExistsException.
     * Calls the parameterized constructor of the superclass (Exception) with the
     * provided message.
     * 
     * @param message A custom error message that explains the reason for the
     *                exception.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}