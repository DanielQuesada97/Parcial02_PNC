package com.rockettsttudio.library.service;

import com.rockettsttudio.library.dto.BookResponse;
import com.rockettsttudio.library.dto.CreateBookRequest;
import com.rockettsttudio.library.dto.UpdateBookRequest;
import com.rockettsttudio.library.entity.Book;
import com.rockettsttudio.library.exception.ResourceNotFoundException;
import com.rockettsttudio.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public BookResponse createBook(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("A book with this ISBN already exists");
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setLanguage(request.getLanguage());
        book.setPages(request.getPages());
        book.setGenre(request.getGenre());
        
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public BookResponse updateBook(UUID id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getLanguage() != null) {
            book.setLanguage(request.getLanguage());
        }

        Book updatedBook = bookRepository.save(book);
        return mapToResponse(updatedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponse(book);
    }

    @Transactional(readOnly = true)
    public BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
        return mapToResponse(book);
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchByGenre(String genre) {
        return bookRepository.findByGenreContainingIgnoreCase(genre).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchByLanguage(String language) {
        return bookRepository.findByLanguageContainingIgnoreCase(language).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchByPageRange(int minPages, int maxPages) {
        return bookRepository.findByPagesBetween(minPages, maxPages).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BookResponse mapToResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setPublicationYear(book.getPublicationYear());
        response.setLanguage(book.getLanguage());
        response.setPages(book.getPages());
        response.setGenre(book.getGenre());
        return response;
    }
}
