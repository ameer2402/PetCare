package com.examly.springapp.exception;

/**
 * Custom exception class to handle cases where an appointment is not found.
 */
public class AppointmentNotFoundException extends Exception {

    /**
     * Default constructor for AppointmentNotFoundException.
     * Calls the default constructor of the superclass (Exception).
     */
    public AppointmentNotFoundException() {
        super();
    }

    /**
     * Parameterized constructor for AppointmentNotFoundException.
     * Calls the parameterized constructor of the superclass (Exception) with the
     * provided message.
     * 
     * @param message A custom error message that explains the reason for the
     *                exception.
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
