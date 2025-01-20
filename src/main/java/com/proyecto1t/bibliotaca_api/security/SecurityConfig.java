package com.proyecto1t.bibliotaca_api.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.proyecto1t.bibliotaca_api.utils.AuthorizationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private RsaKeyProperties rsaKeyProperties;
    @Autowired
    private AuthorizationConfig authenticationManager;

    /**
     * Security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/users/register", "/users/login").permitAll()

                        // Rutas de autores
                        .requestMatchers(HttpMethod.GET, "/authors/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authors/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/authors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/authors/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authors/{id}").hasRole("ADMIN")

                        // Rutas de libros
                        .requestMatchers(HttpMethod.GET, "/books/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/books/{id}").hasRole("ADMIN")

                        // Rutas de categorías
                        .requestMatchers(HttpMethod.GET, "/categories/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")

                        // Rutas de usuarios
                        .requestMatchers(HttpMethod.GET, "/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{username}").access(authenticationManager.getUsuarioByUsernameManager())
                        .requestMatchers(HttpMethod.PUT, "/users/{username}").access(authenticationManager.getUsuarioByUsernameManager())
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}").access(authenticationManager.getUsuarioByUsernameManager())

                        // Rutas de préstamos
                        .requestMatchers(HttpMethod.GET, "/loans/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/loans/{id}").access(authenticationManager.getLoansByIdManager())
                        .requestMatchers(HttpMethod.POST, "/loans").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/loans/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/loans/{id}").hasRole("ADMIN")

                        // Rutas de reservas
                        .requestMatchers(HttpMethod.GET, "/reservations/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/reservations/{id}").access(authenticationManager.getReservationsByIdManager())
                        .requestMatchers(HttpMethod.POST, "/reservations").access(authenticationManager.getReservationsByIdManager())
                        .requestMatchers(HttpMethod.PUT, "/reservations/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reservations/{id}").hasRole("ADMIN")

                        // Rutas de comentarios
                        .requestMatchers(HttpMethod.GET, "/comments/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments/{id}").access(authenticationManager.getComentariosByIdManager())
                        .requestMatchers(HttpMethod.POST, "/comments").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/comments/{id}").access(authenticationManager.getComentariosByIdManager())
                        .requestMatchers(HttpMethod.DELETE, "/comments/{id}").access(authenticationManager.getComentariosByIdManager())

                        // Cualquier otra ruta
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() { // Método que configura la codificación de contraseñas
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { // Método que configura la autenticación
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Jwt decoder jwt decoder.
     *
     * @return the jwt decoder
     */
    @Bean
    public JwtDecoder jwtDecoder() { // Método que configura el decodificador de JWT
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
    }

    /**
     * Jwt encoder jwt encoder.
     *
     * @return the jwt encoder
     */
    @Bean
    public JwtEncoder jwtEncoder() { // Método que configura el codificador de JWT
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }
}
