package com.examly.springapp.exception;

/**
 * Custom exception class to handle cases where a pet is not found.
 */
public class PetNotFoundException extends Exception {

    /**
     * Default constructor for PetNotFoundException.
     * Calls the default constructor of the superclass (Exception).
     */
    public PetNotFoundException() {
        super();
    }

    /**
     * Constructor that accepts a custom message for PetNotFoundException.
     * Calls the parameterized constructor of the superclass (Exception) with the
     * provided message.
     * 
     * @param message A custom error message that explains the reason for the
     *                exception.
     */
    public PetNotFoundException(String message) {
        super(message);
    }
}
