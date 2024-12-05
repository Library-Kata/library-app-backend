package com.library.app.service;

import com.library.app.model.dto.BookDTO;

import java.util.List;

/**
 * Service interface for book-related operations.
 */
public interface BookService {
    List<BookDTO> getAllBooks();

    BookDTO addBook(BookDTO bookDTO);
}
