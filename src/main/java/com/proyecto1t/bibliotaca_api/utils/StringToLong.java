package com.proyecto1t.bibliotaca_api.utils;

import org.springframework.stereotype.Component;

/**
 * The type String to long.
 */
@Component
public class StringToLong { // Clase que convierte un String a Long
    /**
     * Method long.
     *
     * @param id the id
     * @return the long
     */
    public Long method(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El id debe ser un número");
        }
    }
}
