package com.library.app.service.impl;

import com.library.app.exception.*;
import com.library.app.model.dto.BookDTO;
import com.library.app.model.entity.*;
import com.library.app.repository.*;
import com.library.app.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of BookService.
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }
    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookEntity = BookEntity.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .available(true)
                .build();

        BookEntity savedBook = bookRepository.save(bookEntity);
        return mapToDTO(savedBook);
    }


    // Utility method to convert BookEntity to BookDTO
    private BookDTO mapToDTO(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .available(bookEntity.isAvailable())
                .build();
    }
}
