package com.spring.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Author cannot be empty")
    private String author;

    @Size(min = 4, max = 4, message = "Publication year must be 4 digits")
    private String publicationYear;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotEmpty(message = "ISBN cannot be empty")
    private String isbn;
}