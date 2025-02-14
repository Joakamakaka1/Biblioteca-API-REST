package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.UserRegisterDTO;
import com.proyecto1t.bibliotaca_api.dto.UserResponseDTO;
import com.proyecto1t.bibliotaca_api.exceptions.BadRequestException;
import com.proyecto1t.bibliotaca_api.exceptions.DuplicateException;
import com.proyecto1t.bibliotaca_api.exceptions.MethodArgumentNotValidException;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.repository.UserRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Mapper mapper;

    // Autenticación de usuarios en la base de datos por username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.proyecto1t.bibliotaca_api.model.User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario not found"));

        UserDetails userDetails = User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toString().split(","))
                .build();

        return userDetails;
    }

    public UserRegisterDTO registerUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
            throw new DuplicateException("Email already exists");
        }

        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new DuplicateException("Username already exists");
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        if(!userRegisterDTO.getRole().equals("USER") && !userRegisterDTO.getRole().equals("ADMIN")) {
            throw new BadRequestException("Role must be USER or ADMIN");
        }

        com.proyecto1t.bibliotaca_api.model.User user = new com.proyecto1t.bibliotaca_api.model.User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRoles(userRegisterDTO.getRole().equals("ADMIN") ? com.proyecto1t.bibliotaca_api.model.User.Roles.ADMIN : com.proyecto1t.bibliotaca_api.model.User.Roles.USER);

        userRepository.save(user);
        return userRegisterDTO;
    }

    public UserResponseDTO findByUsername(String username) {
        com.proyecto1t.bibliotaca_api.model.User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return mapper.toUserResponseDTO(user);
    }

    public List<UserResponseDTO> findAll() {
        List<com.proyecto1t.bibliotaca_api.model.User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new NotFoundException("Users not found");
        }

        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        users.forEach(user -> userResponseDTOs.add(mapper.toUserResponseDTO(user)));
        return userResponseDTOs;
    }

    public UserResponseDTO updateUser(String username, UserRegisterDTO userRegisterDTO) {
        if(userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
            throw new DuplicateException("Email already exists");
        }

        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new DuplicateException("Username already exists");
        }

        if(username.isEmpty() || username.isBlank()) {
            throw new NotFoundException("User not found");
        }

        if(!userRegisterDTO.getRole().equals("USER") && !userRegisterDTO.getRole().equals("ADMIN")) {
            throw new BadRequestException("Role must be USER or ADMIN");
        }

        com.proyecto1t.bibliotaca_api.model.User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRoles(userRegisterDTO.getRole().equals("ADMIN") ? com.proyecto1t.bibliotaca_api.model.User.Roles.ADMIN : com.proyecto1t.bibliotaca_api.model.User.Roles.USER);

        userRepository.save(user);
        return mapper.toUserResponseDTO(user);
    }

    public UserResponseDTO deleteUser(String username) {
        if(username.isEmpty() || username.isBlank()) {
            throw new NotFoundException("User not found");
        }

        com.proyecto1t.bibliotaca_api.model.User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
        return mapper.toUserResponseDTO(user);
    }
}
