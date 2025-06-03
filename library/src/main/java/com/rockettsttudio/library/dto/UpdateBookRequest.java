package com.rockettsttudio.library.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateBookRequest {
    @Pattern(regexp = "^(?!\\d+$).*$", message = "Title cannot contain only numbers")
    private String title;
    
    private String language;
} 