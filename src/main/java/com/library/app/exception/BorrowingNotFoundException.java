package com.library.app.exception;

/**
 * Exception thrown when a borrowing record is not found.
 */
public class BorrowingNotFoundException extends RuntimeException {
    public BorrowingNotFoundException(String message) {
        super(message);
    }
}
