package com.arun.project.uber.uberApp.exceptions;

public class InvalidRideStatusException extends RuntimeException {
    public InvalidRideStatusException(String message) {
        super(message);
    }
    public InvalidRideStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
