package com.proyecto1t.bibliotaca_api.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad request (400)";

    public BadRequestException(String message) {
        super(DESCRIPTION + ": " + message);
    }
}
