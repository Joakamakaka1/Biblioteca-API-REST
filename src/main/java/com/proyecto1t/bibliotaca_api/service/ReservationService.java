package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.ReservationDTO;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Reservation;
import com.proyecto1t.bibliotaca_api.model.User;
import com.proyecto1t.bibliotaca_api.repository.ReservationRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import com.proyecto1t.bibliotaca_api.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private StringToLong stringToLong;

    public List<ReservationDTO> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new NotFoundException("Reservations not found");
        }

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        reservations.forEach(reservation -> reservationDTOs.add(mapper.toReservationDTO(reservation)));
        return reservationDTOs;
    }

    public ReservationDTO findById(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Reservation not found");
        }
        Reservation reservation = reservationRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        return mapper.toReservationDTO(reservation);
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO, User userId, Book bookId) {
        if (reservationDTO == null) {
            throw new NotFoundException("Reservation not found");
        }

        if (userId == null) {
            throw new NotFoundException("User not found");
        }

        if (bookId == null) {
            throw new NotFoundException("Book not found");
        }

        Reservation reservation = mapper.toReservationEntity(reservationDTO, userId, bookId);
        reservation = reservationRepository.save(reservation);
        return mapper.toReservationDTO(reservation);
    }

    public ReservationDTO updateReservation(String id, ReservationDTO reservationDTO, User userId, Book bookId) {
        if (reservationDTO == null) {
            throw new NotFoundException("Reservation not found");
        }

        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Reservation not found");
        }

        if (userId == null) {
            throw new NotFoundException("User not found");
        }

        if (bookId == null) {
            throw new NotFoundException("Book not found");
        }

        Reservation existingReservation = reservationRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        Reservation reservation = mapper.toReservationEntity(reservationDTO, userId, bookId);
        reservation.setId(existingReservation.getId());
        reservation = reservationRepository.save(reservation);
        return mapper.toReservationDTO(reservation);
    }

    public void deleteReservation(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Reservation not found");
        }

        Reservation reservation = reservationRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        reservationRepository.deleteById(reservation.getId());
    }
}
