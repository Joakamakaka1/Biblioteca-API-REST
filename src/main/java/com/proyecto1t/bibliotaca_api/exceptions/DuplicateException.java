package com.proyecto1t.bibliotaca_api.exceptions;

public class DuplicateException extends RuntimeException {
    private static final String DESCRIPTION = "Duplicate (409)";

    public DuplicateException(String message) {
        super(DESCRIPTION + ": " + message);
    }
}
