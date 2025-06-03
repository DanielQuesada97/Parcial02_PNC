package com.rockettsttudio.library.repository;

import com.rockettsttudio.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByTitle(String title);
}
