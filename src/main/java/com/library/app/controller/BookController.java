package com.library.app.controller;

import com.library.app.mapper.BookMapper;
import com.library.app.model.request.BookRequest;
import com.library.app.model.response.BookResponse;
import com.library.app.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve the list of all books.")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        var books = bookService.getAllBooks().stream()
                .map(BookMapper::toResponse)
                .toList();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    @Operation(summary = "Add a new book", description = "Add a new book to the library.")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        var savedBook = bookService.addBook(BookMapper.toDto(bookRequest));
        return ResponseEntity.ok(BookMapper.toResponse(savedBook));
    }
}
