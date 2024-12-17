package com.proyecto1t.bibliotaca_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private Long userId;
    private Long bookId;
}
