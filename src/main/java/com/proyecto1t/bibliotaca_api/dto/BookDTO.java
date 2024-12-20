package com.proyecto1t.bibliotaca_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters long")
    @NotEmpty(message = "ISBN cannot be empty")
    private String isbn;

    @NotNull(message = "Author ID cannot be null")
    private Long authorId;

    @NotNull(message = "Author ID cannot be null")
    private Long categoryId;
}
