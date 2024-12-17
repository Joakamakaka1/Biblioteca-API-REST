package com.proyecto1t.bibliotaca_api.exceptions;

public class InternalErrorException extends RuntimeException {
    private static final String DESCRIPTION = "Internal Error (500)";

    public InternalErrorException(String message) {
        super(DESCRIPTION + ": " + message);
    }
}
