package com.library.app.service.impl;

import com.library.app.exception.*;
import com.library.app.mapper.BorrowingMapper;
import com.library.app.model.dto.BookDTO;
import com.library.app.model.dto.BorrowingDTO;
import com.library.app.model.dto.UserDTO;
import com.library.app.model.entity.*;
import com.library.app.repository.*;
import com.library.app.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of BorrowingService.
 */
@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;

    @Override
    public void borrowBook(String username, Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found."));

        if (!bookEntity.isAvailable()) {
            throw new BookNotAvailableException("The book '" + bookEntity.getTitle() + "' is not available for borrowing.");
        }

        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the user has already borrowed this book
        boolean alreadyBorrowed = borrowingRepository.existsByUserUsernameAndBookIdAndReturnedDateIsNull(username, bookId);
        if (alreadyBorrowed) {
            throw new BookAlreadyBorrowedException("You have already borrowed this book.");
        }

        bookEntity.setAvailable(false);
        bookRepository.save(bookEntity);

        BorrowingEntity borrowingEntity = BorrowingEntity.builder()
                .user(userEntity)
                .book(bookEntity)
                .borrowDate(LocalDateTime.now())
                .build();

        borrowingRepository.save(borrowingEntity);
    }

    @Override
    public void returnBook(String username, Long borrowingId) {
        BorrowingEntity borrowingEntity = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new BorrowingNotFoundException("Borrowing not found."));

        // Check if the borrowing belongs to the user
        if (!borrowingEntity.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("You are not authorized to return this borrowing.");
        }

        // Check if the book has already been returned
        if (borrowingEntity.getReturnedDate() != null) {
            throw new BookAlreadyReturnedException("The book '" + borrowingEntity.getBook().getTitle() + "' has already been returned.");
        }

        // Update the book to make it available
        BookEntity bookEntity = borrowingEntity.getBook();
        bookEntity.setAvailable(true);
        bookRepository.save(bookEntity);

        // Update the borrowing with the return date
        borrowingEntity.setReturnedDate(LocalDateTime.now());
        borrowingRepository.save(borrowingEntity);
    }

    @Override
    public List<BorrowingDTO> getAllBorrowedBooks(String username) {
        return borrowingRepository.findByUserUsername(username).stream()
                .map(BorrowingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getCurrentlyBorrowedBooks(String username) {
        return borrowingRepository.findByUserUsernameAndReturnedDateIsNull(username).stream()
                .map(BorrowingMapper::toDto)
                .collect(Collectors.toList());
    }

}
