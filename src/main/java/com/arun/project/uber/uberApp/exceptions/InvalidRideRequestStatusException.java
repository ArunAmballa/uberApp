package com.arun.project.uber.uberApp.exceptions;

public class InvalidRideRequestStatusException extends RuntimeException {
    public InvalidRideRequestStatusException(String message) {
        super(message);
    }
    public InvalidRideRequestStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
