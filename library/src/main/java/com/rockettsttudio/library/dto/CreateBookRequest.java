package com.rockettsttudio.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 3, max = 50, message = "Author name must be between 3 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    @NotBlank(message = "Publication year is required")
    @Size(min = 4, max = 4, message = "Publication year must be a 4-digit number")
    private int publicationYear;

    @Size(max = 20, message = "Language cannot exceed 20 characters")
    private String language;

    @NotBlank(message = "Page count is required")
    @Size(min = 1, max = 5, message = "Page count must be between 1 and 5 digits")
    private int pages;

    @NotBlank(message = "Genre is required")
    @Size(min = 3, max = 50, message = "Genre must be between 3 and 50 characters")
    private String genre;
}
