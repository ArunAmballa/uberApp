package com.arun.project.uber.uberApp.exceptions;

public class InvalidRideOtpException extends RuntimeException {
    public InvalidRideOtpException(String message) {
        super(message);
    }
    public InvalidRideOtpException(String message, Throwable cause) {
        super(message, cause);
    }
}
