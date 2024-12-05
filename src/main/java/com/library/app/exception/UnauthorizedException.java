package com.library.app.exception;

/**
 * Exception thrown when a user is unauthorized to perform an action.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
