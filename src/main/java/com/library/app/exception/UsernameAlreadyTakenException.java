package com.library.app.exception;

/**
 * Exception thrown when a username is already taken.
 */
public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
