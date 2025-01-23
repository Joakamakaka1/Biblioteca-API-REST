package com.proyecto1t.bibliotaca_api.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Resource not found (404)";

    public NotFoundException(String message) {
        super(DESCRIPTION + ": " + message);
    }
}
