package com.proyecto1t.bibliotaca_api.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class MethodArgumentNotValidException extends RuntimeException{
    private List<String> validationErrors; // Errores de validación

    // Constructor que acepta la lista de errores
    public MethodArgumentNotValidException(List<String> validationErrors) {
        super(String.join(", ", validationErrors));
        this.validationErrors = validationErrors;
    }
}
