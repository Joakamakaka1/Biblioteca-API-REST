package com.proyecto1t.bibliotaca_api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    @NotNull(message = "Reservation date cannot be null")
    @FutureOrPresent(message = "Reservation date cannot be in the past")
    private LocalDate reservationDate;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
}
