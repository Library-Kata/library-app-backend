package com.library.app.service.impl;

import com.library.app.exception.*;
import com.library.app.mapper.BookMapper;
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
                .map(BookMapper::toDto)
                .toList();
    }
    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        var bookEntity = BookMapper.toEntity(bookDTO);
        bookEntity.setAvailable(true);
        var savedBook = bookRepository.save(bookEntity);
        return BookMapper.toDto(savedBook);
    }
}
