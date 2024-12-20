package com.proyecto1t.bibliotaca_api.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class MethodArgumentNotValidException extends RuntimeException{
    private static final String DESCRIPTION = "Method argument not valid";

    private List<String> validationErrors; // Lista de errores

    // Constructor que acepta la lista de errores
    public MethodArgumentNotValidException(List<String> validationErrors) {
        super(DESCRIPTION + ": " + String.join(", ", validationErrors));
        this.validationErrors = validationErrors;
    }
}
