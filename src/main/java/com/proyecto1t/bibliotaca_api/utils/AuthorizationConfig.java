package com.proyecto1t.bibliotaca_api.utils;

import com.proyecto1t.bibliotaca_api.exceptions.InternalErrorException;
import com.proyecto1t.bibliotaca_api.model.Comment;
import com.proyecto1t.bibliotaca_api.model.Loan;
import com.proyecto1t.bibliotaca_api.model.Reservation;
import com.proyecto1t.bibliotaca_api.model.User;
import com.proyecto1t.bibliotaca_api.repository.CommentRepository;
import com.proyecto1t.bibliotaca_api.repository.LoanRepository;
import com.proyecto1t.bibliotaca_api.repository.ReservationRepository;
import com.proyecto1t.bibliotaca_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfig {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StringToLong stringToLong;

    // Comprueba si el usuario actual tiene el rol de administrador
    // o si el nombre de usuario del recurso coincide con el del usuario autenticado

    /*
    Documentacion: https://www.baeldung.com/spring-security-authorizationmanager
     */
    public AuthorizationManager<RequestAuthorizationContext> getUsuarioByUsernameManager() {
        return (authentication, object) -> {
            Authentication auth = authentication.get(); // Obtiene el contexto de autenticación actual.

            // Comprueba si el usuario tiene el rol de administrador.
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                return new AuthorizationDecision(true); // Si el usuario es administrador, se permite acceso automáticamente.
            }

            // Extrae el nombre de usuario desde el URI de la solicitud.
            String path = object.getRequest().getRequestURI();
            String username = path.replaceAll("/users/", "");

            User user = null;
            try {
                user = userRepository.findByUsername(username).orElse(null);
            } catch (Exception e) {
                throw new InternalErrorException("Error inesperado: " + e.getMessage());
            }

            if (user == null) {
                return new AuthorizationDecision(false);
            }

            // Si el nombre de usuario del recurso coincide con el del usuario autenticado, se permite acceso.
            if (user.getUsername().equals(auth.getName())) {
                return new AuthorizationDecision(true);
            }

            return new AuthorizationDecision(false);
        };
    }

    public AuthorizationManager<RequestAuthorizationContext> getLoansByIdManager() {
        return (authentication, object) -> {
            Authentication auth = authentication.get();

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                return new AuthorizationDecision(true);
            }

            // Extrae el ID del préstamo desde el URI de la solicitud.
            String path = object.getRequest().getRequestURI();
            String loanId = path.replaceAll("/loans/", "");
            Long id = stringToLong.method(loanId);

            if(id == null) {
                return new AuthorizationDecision(false);
            }

            Loan loan = null;
            try {
                loan = loanRepository.findById(id).orElse(null);
            } catch (Exception e) {
                throw new InternalErrorException("Error inesperado: " + e.getMessage());
            }

            if (loan == null) {
                return new AuthorizationDecision(false);
            }

            // Obtiene al usuario autenticado desde el contexto.
            String username = auth.getName();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return new AuthorizationDecision(false);
            }

            // Comprueba si el préstamo pertenece al usuario autenticado.
            if(!loan.getUser().getId().equals(user.getId())) {
                return new AuthorizationDecision(false);
            }

            return new AuthorizationDecision(true); // Si se cumplen todas las condiciones, se permite acceso.
        };
    }

    public AuthorizationManager<RequestAuthorizationContext> getReservationsByIdManager() {
        return (authentication, object) -> {
            Authentication auth = authentication.get();

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                return new AuthorizationDecision(true);
            }

            String path = object.getRequest().getRequestURI();
            String reservationId = path.replaceAll("/reservations/", "");
            Long id = stringToLong.method(reservationId);

            if(id == null) {
                return new AuthorizationDecision(false);
            }

            Reservation reservation = null;
            try {
                reservation = reservationRepository.findById(id).orElse(null);
            } catch (Exception e) {
                throw new InternalErrorException("Error inesperado: " + e.getMessage());
            }

            if (reservation == null) {
                return new AuthorizationDecision(false);
            }

            String username = auth.getName();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return new AuthorizationDecision(false);
            }

            if(!reservation.getUser().getId().equals(user.getId())) {
                return new AuthorizationDecision(false);
            }

            return new AuthorizationDecision(true);
        };
    }

    public AuthorizationManager<RequestAuthorizationContext> getComentariosByIdManager() {
        return (authentication, object) -> {
            Authentication auth = authentication.get();

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                return new AuthorizationDecision(true);
            }

            String path = object.getRequest().getRequestURI();
            String commentId = path.replaceAll("/comentarios/", "");
            Long id = stringToLong.method(commentId);

            if(id == null) {
                return new AuthorizationDecision(false);
            }

            Comment comment = null;
            try {
                comment = commentRepository.findById(id).orElse(null);
            } catch (Exception e) {
                throw new InternalErrorException("Error inesperado: " + e.getMessage());
            }

            if (comment == null) {
                return new AuthorizationDecision(false);
            }

            String username = auth.getName();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return new AuthorizationDecision(false);
            }

            if(!comment.getUser().getId().equals(user.getId())) {
                return new AuthorizationDecision(false);
            }

            return new AuthorizationDecision(true);
        };
    }
}

