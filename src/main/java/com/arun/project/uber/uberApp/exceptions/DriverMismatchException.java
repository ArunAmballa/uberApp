package com.arun.project.uber.uberApp.exceptions;

public class DriverMismatchException extends RuntimeException {
    public DriverMismatchException(String message) {
        super(message);
    }
    public DriverMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
