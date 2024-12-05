package com.library.app.service;

import com.library.app.model.dto.BookDTO;
import com.library.app.model.dto.BorrowingDTO;

import java.util.List;

public interface BorrowingService {
    void borrowBook(String username, Long bookId);
    void returnBook(String username, Long borrowingId);
    List<BorrowingDTO> getAllBorrowedBooks(String username);
    List<BorrowingDTO> getCurrentlyBorrowedBooks(String username);
}
