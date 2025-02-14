package com.proyecto1t.bibliotaca_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    public enum Roles {
        ADMIN, USER
    }
}
