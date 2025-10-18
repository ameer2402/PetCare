package com.examly.springapp.exception;

/**
 * Custom exception class to handle cases where a user's email is not found.
 */
public class UserEmailNotFoundException extends Exception {

    /**
     * Default constructor for UserEmailNotFoundException.
     * Calls the default constructor of the superclass (Exception).
     */
    public UserEmailNotFoundException() {
        super();
    }

    /**
     * Constructor that accepts a custom message for UserEmailNotFoundException.
     * Calls the parameterized constructor of the superclass (Exception) with the
     * provided message.
     * 
     * @param message A custom error message that explains the reason for the
     *                exception.
     */
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}