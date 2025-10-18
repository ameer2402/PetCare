package com.examly.springapp.exception;

/**
 * Custom exception class to handle cases where appointments exceed a certain
 * limit.
 */
public class AppointmentExceedException extends Exception {

    /**
     * Default constructor for AppointmentExceedException.
     * Calls the default constructor of the superclass (Exception).
     */
    public AppointmentExceedException() {
        super();
    }

    /**
     * Parameterized constructor for AppointmentExceedException.
     * Calls the parameterized constructor of the superclass (Exception) with the
     * provided message.
     * 
     * @param message A custom error message that explains the reason for the
     *                exception.
     */
    public AppointmentExceedException(String message) {
        super(message);
    }
}