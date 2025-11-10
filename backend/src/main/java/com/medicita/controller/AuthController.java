package com.medicita.controller;

import com.medicita.entity.Paciente;
import com.medicita.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Paciente login(@RequestBody LoginRequest request) {
        return authService.login(request.getUserName(), request.getPassword());
    }

    // Clase interna simple para el request
    public static class LoginRequest {
        private String userName;
        private String password;

        // getters y setters
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}