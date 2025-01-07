package com.arun.project.uber.uberApp.advices;


import com.arun.project.uber.uberApp.exceptions.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflictException(RuntimeConflictException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleRideNotFoundException(RideNotFoundException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(DriverMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleDriverMismatchException(DriverMismatchException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(DriverNotAvailableException.class)
    public ResponseEntity<ApiResponse<?>> handleDriverNotAvailableException(DriverNotAvailableException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(InvalidRideOtpException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidRideOtpException(InvalidRideOtpException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(InvalidRideRequestStatusException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidRideRequestStatusException(InvalidRideRequestStatusException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(InvalidRideStatusException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidRideStatusException(InvalidRideStatusException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(RideProcessingException.class)
    public ResponseEntity<ApiResponse<?>> handleRideProcessingException(RideProcessingException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(RideRequestNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleRideRequestNotFoundException(RideRequestNotFoundException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationException(MethodArgumentNotValidException e) {

        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = ApiError
                .builder()
                .message("Invalid Input Fields")
                .subErrors(errors)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return helper(apiError);
    }



//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException e) {
//
//        ApiError apiError = ApiError
//                .builder()
//                .message(e.getMessage())
//                .status(HttpStatus.UNAUTHORIZED)
//                .build();
//        return helper(apiError);
//    }
//
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException e) {
//        ApiError build = ApiError
//                .builder()
//                .message(e.getMessage())
//                .status(HttpStatus.UNAUTHORIZED)
//                .build();
//        return helper(build);
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .build();
        return helper(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception e){

        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return helper(apiError);
    }

    private ResponseEntity<ApiResponse<?>> helper(ApiError apiError){
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}

