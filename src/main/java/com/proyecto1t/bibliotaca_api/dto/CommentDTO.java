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
public class CommentDTO {
    @NotEmpty(message = "Content cannot be empty")
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String content;

    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @NotNull(message = "Book ID cannot be empty")
    private Long bookId;
}
