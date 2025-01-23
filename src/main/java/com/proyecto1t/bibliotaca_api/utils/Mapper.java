package com.proyecto1t.bibliotaca_api.utils;

import com.proyecto1t.bibliotaca_api.dto.*;
import com.proyecto1t.bibliotaca_api.model.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AuthorDTO toAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(author.getName());
        authorDTO.setNationality(author.getNationality());
        return authorDTO;
    }

    public Author toAuthorEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setNationality(authorDTO.getNationality());
        return author;
    }

    public BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthorId(toAuthorDTO(book.getAuthor()));
        bookDTO.setCategoryId(toCategoryDTO(book.getCategory()));
        return bookDTO;
    }

    public Book toBookEntity(BookDTO bookDTO, Author author, Category category) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(author);
        book.setCategory(category);
        return book;
    }

    public CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public Category toCategoryEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return category;
    }

    public CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserId(toUserResponseDTO(comment.getUser()));
        commentDTO.setBookId(toBookDTO(comment.getBook()));
        return commentDTO;
    }

    public Comment toCommentEntity(CommentDTO commentDTO, User user, Book book) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setUser(user);
        comment.setBook(book);
        return comment;
    }

    public LoanDTO toLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setReturnDate(loan.getReturnDate());
        loanDTO.setUserId(toUserResponseDTO(loan.getUser()));
        loanDTO.setBookId(toBookDTO(loan.getBook()));
        return loanDTO;
    }

    public Loan toLoanEntity(LoanDTO loanDTO, User user, Book book) {
        Loan loan = new Loan();
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setReturnDate(loanDTO.getReturnDate());
        loan.setUser(user);
        loan.setBook(book);
        return loan;
    }

    public ReservationDTO toReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(toUserResponseDTO(reservation.getUser()));
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setStatus(reservation.getStatus().toString());
        reservationDTO.setBookId(toBookDTO(reservation.getBook()));
        return reservationDTO;
    }

    public Reservation toReservationEntity(ReservationDTO reservationDTO, User user, Book book) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setStatus(Reservation.ReservationStatus.valueOf(reservationDTO.getStatus()));
        reservation.setUser(user);
        reservation.setBook(book);
        return reservation;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRoles().name());
        return userResponseDTO;
    }

    public User toUserEntity(UserResponseDTO userResponseDTO) {
        User user = new User();
        user.setUsername(userResponseDTO.getUsername());
        user.setEmail(userResponseDTO.getEmail());
        user.setRoles(userResponseDTO.getRole().equals("ADMIN") ? User.Roles.ADMIN : User.Roles.USER);
        return user;
    }
}
