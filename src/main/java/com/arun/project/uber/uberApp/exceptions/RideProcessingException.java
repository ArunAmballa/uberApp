package com.arun.project.uber.uberApp.exceptions;

public class RideProcessingException extends RuntimeException {
    public RideProcessingException(String message) {
        super(message);
    }
    public RideProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
