package com.rockettsttudio.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.Year;
import java.util.UUID;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank(message = "Title is required")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "Title cannot contain only numbers")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Column(unique = true)
    private String isbn;

    @Min(value = 1900, message = "Publication year must be at least 1900")
    @Max(value = 2024, message = "Publication year cannot be greater than current year")
    private int publicationYear;

    @NotBlank(message = "Language is required")
    private String language;

    @Min(value = 11, message = "Number of pages must be greater than 10")
    private int pages;

    @NotBlank(message = "Genre is required")
    private String genre;
}
