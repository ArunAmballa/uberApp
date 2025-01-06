package com.arun.project.uber.uberApp.exceptions;

public class RuntimeConflictException extends RuntimeException {

    public RuntimeConflictException(String message) {
        super(message);
    }
    public RuntimeConflictException(String message, Throwable cause) {
        super(message, cause);
    }

}
