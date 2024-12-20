package com.proyecto1t.bibliotaca_api.controller;

import com.proyecto1t.bibliotaca_api.dto.ReservationDTO;
import com.proyecto1t.bibliotaca_api.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/") // -> http://localhost:8080/reservation/
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}") // -> http://localhost:8080/reservation/1
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable String id) {
        ReservationDTO reservation = reservationService.findById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping("/") // -> http://localhost:8080/reservation/
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservation) {
        ReservationDTO createdReservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // -> http://localhost:8080/reservation/1
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable String id, @Valid @RequestBody ReservationDTO reservation) {
        ReservationDTO updateReservation = reservationService.updateReservation(id, reservation);
        return new ResponseEntity<>(updateReservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // -> http://localhost:8080/reservation/1
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
