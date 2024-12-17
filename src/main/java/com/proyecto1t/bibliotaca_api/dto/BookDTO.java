package com.proyecto1t.bibliotaca_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String title;
    private String isbn;
    private Long authorId;
    private Long categoryId;
}
