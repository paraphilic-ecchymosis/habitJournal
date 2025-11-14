package com.crookedcoder.habitjournal.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler using Java 21 pattern matching in switch expressions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle all exceptions using pattern matching (Java 21 feature)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        LocalDateTime timestamp = LocalDateTime.now();

        // Use Java 21 pattern matching in switch for exception handling
        return switch (ex) {
            case ResourceNotFoundException e -> buildResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                e.getMessage(),
                timestamp,
                path
            );

            case DuplicateResourceException e -> buildResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                e.getMessage(),
                timestamp,
                path
            );

            case UnauthorizedException e -> buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                e.getMessage(),
                timestamp,
                path
            );

            case BadRequestException e -> buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                e.getMessage(),
                timestamp,
                path
            );

            case MethodArgumentNotValidException e -> {
                List<String> details = e.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();

                ErrorResponse errorResponse = new ErrorResponse(
                    timestamp,
                    HttpStatus.BAD_REQUEST.value(),
                    "Validation Failed",
                    "Input validation failed",
                    path,
                    details
                );

                yield new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            // Default case for unexpected exceptions
            default -> buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred: " + ex.getMessage(),
                timestamp,
                path
            );
        };
    }

    /**
     * Helper method to build error responses
     */
    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String error,
            String message,
            LocalDateTime timestamp,
            String path) {

        ErrorResponse errorResponse = new ErrorResponse(
            timestamp,
            status.value(),
            error,
            message,
            path
        );

        return new ResponseEntity<>(errorResponse, status);
    }
}
