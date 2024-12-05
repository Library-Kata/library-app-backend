package com.library.app.controller;

import com.library.app.exception.UnauthorizedException;
import com.library.app.model.dto.BorrowingDTO;
import com.library.app.service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
@Tag(name = "Borrowings", description = "Endpoints for managing book borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    @PostMapping("/borrow/{bookId}")
    @Operation(summary = "Borrow a book", description = "Borrow a book by providing its ID.")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        borrowingService.borrowBook(username, bookId);
        return ResponseEntity.ok("Book borrowed successfully.");
    }

    @PostMapping("/return/{borrowingId}")
    @Operation(summary = "Return a borrowed book", description = "Return a borrowed book by providing its borrowing record ID.")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            borrowingService.returnBook(username, borrowingId);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all borrowed books", description = "Retrieve all books borrowed by the current user.")
    public ResponseEntity<List<BorrowingDTO>> getAllBorrowedBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("You must be logged in to access this resource.");
        }
        String username = authentication.getName();
        List<BorrowingDTO> books = borrowingService.getAllBorrowedBooks(username);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/current")
    @Operation(summary = "Get currently borrowed books", description = "Retrieve the list of currently borrowed books by the user.")
    public ResponseEntity<List<BorrowingDTO>> getCurrentlyBorrowedBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<BorrowingDTO> books = borrowingService.getCurrentlyBorrowedBooks(username);
        return ResponseEntity.ok(books);
    }
}
