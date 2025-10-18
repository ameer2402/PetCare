package com.examly.springapp.exception;

public class FeedbackAlreadyExistException extends RuntimeException {
    public FeedbackAlreadyExistException(String message) {
        super(message);
    }

    public FeedbackAlreadyExistException() {
        super();
    }
}