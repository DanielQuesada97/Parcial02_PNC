package com.rockettsttudio.library.controller;

import com.rockettsttudio.library.dto.BookResponse;
import com.rockettsttudio.library.dto.CreateBookRequest;
import com.rockettsttudio.library.dto.UpdateBookRequest;
import com.rockettsttudio.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_USER')")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_USER')")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<BookResponse>> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer minPages,
            @RequestParam(required = false) Integer maxPages) {
        
        if (author != null) {
            return ResponseEntity.ok(bookService.searchByAuthor(author));
        }
        if (language != null) {
            return ResponseEntity.ok(bookService.searchByLanguage(language));
        }
        if (genre != null) {
            return ResponseEntity.ok(bookService.searchByGenre(genre));
        }
        if (minPages != null && maxPages != null) {
            return ResponseEntity.ok(bookService.searchByPageRange(minPages, maxPages));
        }
        
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_USER')")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
