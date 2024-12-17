package com.proyecto1t.bibliotaca_api.controller;

import com.proyecto1t.bibliotaca_api.dto.UserLoginDTO;
import com.proyecto1t.bibliotaca_api.dto.UserRegisterDTO;
import com.proyecto1t.bibliotaca_api.dto.UserResponseDTO;
import com.proyecto1t.bibliotaca_api.service.TokenService;
import com.proyecto1t.bibliotaca_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login") // -> http://localhost:8080/users/login
    public String login(@RequestBody UserLoginDTO clienteLoginDTO) {
        // Autenticamos al usuario con sus credenciales de inicio de sesión (Username y Password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clienteLoginDTO.getUsername(), clienteLoginDTO.getPassword())
        );
        return tokenService.generateToken(authentication); // Retornamos el token
    }

    @PostMapping("/register") // -> http://localhost:8080/users/register
    public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserRegisterDTO registeredUser = userService.registerUser(userRegisterDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping("/") // -> http://localhost:8080/users
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}") // -> http://localhost:8080/users/1
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String username) {
        UserResponseDTO user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{username}") // -> http://localhost:8080/users/1
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String username, @RequestBody UserRegisterDTO user) {
        UserResponseDTO updatedUser = userService.updateUser(username, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}") // -> http://localhost:8080/users/1
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
