package com.proyecto1t.bibliotaca_api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;

    @NotEmpty(message = "Author name cannot be empty")
    private String name;

    private String nationality;
}
