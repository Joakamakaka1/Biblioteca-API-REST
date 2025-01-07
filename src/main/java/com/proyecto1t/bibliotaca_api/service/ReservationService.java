package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.ReservationDTO;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Reservation;
import com.proyecto1t.bibliotaca_api.model.User;
import com.proyecto1t.bibliotaca_api.repository.BookRepository;
import com.proyecto1t.bibliotaca_api.repository.ReservationRepository;
import com.proyecto1t.bibliotaca_api.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

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
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Reservation not found");
        }
        Reservation reservation = reservationRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        return mapper.toReservationDTO(reservation);
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        if (reservationDTO == null) {
            throw new NotFoundException("Reservation not found");
        }

        User userId = userRepository.findById(reservationDTO.getUserId().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book bookId = bookRepository.findById(reservationDTO.getBookId().getId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Reservation reservation = mapper.toReservationEntity(reservationDTO, userId, bookId);
        reservation = reservationRepository.save(reservation);
        return mapper.toReservationDTO(reservation);
    }

    public ReservationDTO updateReservation(String id, ReservationDTO reservationDTO) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Reservation not found");
        }

        User user = userRepository.findById(reservationDTO.getUserId().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findById(reservationDTO.getBookId().getId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Reservation existingReservation = reservationRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        existingReservation.setReservationDate(reservationDTO.getReservationDate());
        existingReservation.setStatus(Reservation.ReservationStatus.valueOf(reservationDTO.getStatus())); // convertir a enum
        existingReservation.setUser(user);
        existingReservation.setBook(book);

        Reservation reservation = reservationRepository.save(existingReservation);
        return mapper.toReservationDTO(reservation);
    }

    public void deleteReservation(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Reservation not found");
        }

        Reservation reservation = reservationRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        reservationRepository.deleteById(reservation.getId());
    }
}
