package com.rockettsttudio.library.repository;

import com.rockettsttudio.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByTitle(String title);
    boolean existsByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenreContainingIgnoreCase(String genre);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByLanguageContainingIgnoreCase(String language);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByPagesBetween(int minPages, int maxPages);
}
