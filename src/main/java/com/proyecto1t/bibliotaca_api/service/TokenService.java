package com.proyecto1t.bibliotaca_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;

    /**
     * Generate token string.
     *
     * @param authentication the authentication
     * @return the string
     */
    public String generateToken(Authentication authentication) { // Se usa en el metodo login -> http://localhost:8080/cliente/login
        // Obtenemos la fecha actual
        Instant now = Instant.now();

        // Obtenemos los roles del usuario autenticado
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .collect(Collectors.toList());

        // Generamos el token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();

        // Retornamos el token generado por el encoder de JWT
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
