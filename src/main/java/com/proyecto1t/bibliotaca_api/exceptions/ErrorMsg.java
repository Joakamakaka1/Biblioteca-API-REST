package com.proyecto1t.bibliotaca_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMsg {
    private LocalDateTime timestamp;
    private int status;
    private List<String> errors;
    private String path;
}
