package com.rockettsttudio.library.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private String language;
    private int pages;
    private String genre;
}
