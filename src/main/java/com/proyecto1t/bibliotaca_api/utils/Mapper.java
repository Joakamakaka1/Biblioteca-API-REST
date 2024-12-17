package com.proyecto1t.bibliotaca_api.utils;

import com.proyecto1t.bibliotaca_api.dto.*;
import com.proyecto1t.bibliotaca_api.model.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    // Author
    public AuthorDTO toAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getNationality()
        );
    }

    public Author toAuthorEntity(AuthorDTO authorDTO) {
        return new Author(
                authorDTO.getId(),
                authorDTO.getName(),
                authorDTO.getNationality()
        );
    }

    // Book
    public BookDTO toBookDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor().getId(),
                book.getCategory().getId()
        );
    }

    public Book toBookEntity(BookDTO bookDTO, Author author, Category category) {
        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getIsbn(),
                author,
                category,
                null, // Comments se manejarán de forma independiente
                null, // Loans se manejarán de forma independiente
                null  // Reservations se manejarán de forma independiente
        );
    }

    // Category
    public CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }

    public Category toCategoryEntity(CategoryDTO categoryDTO) {
        return new Category(
                categoryDTO.getId(),
                categoryDTO.getName()
        );
    }

    // Comment
    public CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(), // Convertimos User a su ID
                comment.getBook().getId()  // Convertimos Book a su ID
        );
    }

    public Comment toCommentEntity(CommentDTO commentDTO, User user, Book book) {
        return new Comment(
                commentDTO.getId(),
                commentDTO.getContent(),
                user,
                book
        );
    }

    // Loan
    public LoanDTO toLoanDTO(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getUser().getId(), // Convertimos User a su ID
                loan.getBook().getId()  // Convertimos Book a su ID
        );
    }

    public Loan toLoanEntity(LoanDTO loanDTO, User user, Book book) {
        return new Loan(
                loanDTO.getId(),
                loanDTO.getLoanDate(),
                loanDTO.getReturnDate(),
                user,
                book
        );
    }

    // Reservation
    public ReservationDTO toReservationDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus().name(), // Convertimos el Enum a String
                reservation.getUser().getId(), // Convertimos User a su ID
                reservation.getBook().getId()  // Convertimos Book a su ID
        );
    }

    public Reservation toReservationEntity(ReservationDTO reservationDTO, User user, Book book) {
        return new Reservation(
                reservationDTO.getId(),
                reservationDTO.getReservationDate(),
                Reservation.ReservationStatus.valueOf(reservationDTO.getStatus()), // Convertimos el String al Enum
                user,
                book
        );
    }

    // UserResponse
    public UserResponseDTO toUserResponseDTO(User user, String token) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().name(), // Convertimos el Enum a String
                token
        );
    }

    public User toUserEntity(UserResponseDTO userResponseDTO) {
        return new User(
                userResponseDTO.getId(),
                userResponseDTO.getFirstName(),
                userResponseDTO.getLastName(),
                userResponseDTO.getEmail(),
                null, // Password no se incluye en el DTO
                null, // Comments se manejarán de forma independiente
                null, // Loans se manejarán de forma independiente
                null, // Reservations se manejarán de forma independiente
                User.Role.valueOf(userResponseDTO.getRole()) // Convertimos el String al Enum
        );
    }
}
