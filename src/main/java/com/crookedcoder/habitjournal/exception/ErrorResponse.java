package com.crookedcoder.habitjournal.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Error response DTO using Java 21 record
 */
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    List<String> details
) {
    /**
     * Compact constructor without details
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, message, path, null);
    }
}
