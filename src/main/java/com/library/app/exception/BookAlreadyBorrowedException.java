package com.library.app.exception;

/**
 * Exception thrown when a book has already been borrowed by the user.
 */
public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
