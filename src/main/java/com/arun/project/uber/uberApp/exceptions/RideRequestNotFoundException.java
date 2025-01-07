package com.arun.project.uber.uberApp.exceptions;

public class RideRequestNotFoundException extends RuntimeException {
    public RideRequestNotFoundException(String message) {
        super(message);
    }
    public RideRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
