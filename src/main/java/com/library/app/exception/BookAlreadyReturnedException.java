package com.library.app.exception;

/**
 * Exception thrown when a book has already been returned.
 */
public class BookAlreadyReturnedException extends RuntimeException {
    public BookAlreadyReturnedException(String message) {
        super(message);
    }
}
