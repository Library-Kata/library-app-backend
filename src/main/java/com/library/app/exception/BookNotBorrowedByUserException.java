package com.library.app.exception;

/**
 * Exception thrown when a user tries to return a book they didn't borrow.
 */
public class BookNotBorrowedByUserException extends RuntimeException {
    public BookNotBorrowedByUserException(String message) {
        super(message);
    }
}
