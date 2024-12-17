package com.proyecto1t.bibliotaca_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMsg {
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
