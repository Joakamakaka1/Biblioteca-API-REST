package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.UserResponseDTO;
import com.proyecto1t.bibliotaca_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    // Autenticación de usuarios en la base de datos por username
    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        com.proyecto1t.bibliotaca_api.model.User user = userRepository
                .findByFirstName(firstName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario not found"));

        UserDetails userDetails = User
                .builder()
                .username(user.getFirstName())
                .password(user.getPassword())
                .roles(user.getRole().toString().split(","))
                .build();

        return userDetails;
    }
}
