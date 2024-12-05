package com.library.app.exception;

/**
 * Exception thrown when a book is not available for borrowing.
 */
public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
