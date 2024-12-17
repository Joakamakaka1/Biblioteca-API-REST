package com.proyecto1t.bibliotaca_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
}
