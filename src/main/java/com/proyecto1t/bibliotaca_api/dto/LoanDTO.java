package com.proyecto1t.bibliotaca_api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    @NotNull(message = "Loan date cannot be null")
    @FutureOrPresent(message = "Loan date cannot be in the past")
    private LocalDate loanDate;

    @NotNull(message = "Return date cannot be null")
    @FutureOrPresent(message = "Return date cannot be in the past")
    private LocalDate returnDate;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
}
