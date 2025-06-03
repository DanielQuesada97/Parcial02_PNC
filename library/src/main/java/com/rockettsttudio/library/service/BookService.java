package com.rockettsttudio.library.service;

import com.rockettsttudio.library.dto.BookResponse;
import com.rockettsttudio.library.dto.CreateBookRequest;
import com.rockettsttudio.library.entity.Book;
import com.rockettsttudio.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public BookResponse createBook(CreateBookRequest createBookRequest) {
        if(bookRepository.existsByTitle(createBookRequest.getTitle())) {
            throw new RuntimeException("Product with this name already exists");
        }

        Book book = new Book();
        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());
        book.setIsbn(createBookRequest.getIsbn());
        book.setPublicationYear(createBookRequest.getPublicationYear());
        book.setLanguage(createBookRequest.getLanguage());
        book.setPages(createBookRequest.getPages());
        book.setGenre(createBookRequest.getGenre());

        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(UUID id) {
    return bookRepository.findById(id)
            .map(this::mapToResponse)
            .orElseThrow( () -> new RuntimeException("Book not found with id: " + id));
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
