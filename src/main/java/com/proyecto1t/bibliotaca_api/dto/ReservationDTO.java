package com.proyecto1t.bibliotaca_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private LocalDate reservationDate;
    private String status;
    private Long userId;
    private Long bookId;
}
