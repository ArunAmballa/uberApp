package com.arun.project.uber.uberApp.exceptions;

public class RideRequestException extends RuntimeException {
    public RideRequestException(String message) {
        super(message);
    }
    public RideRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
