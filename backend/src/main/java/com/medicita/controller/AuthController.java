package com.medicita.controller;

import com.medicita.entity.Paciente;
import com.medicita.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Paciente paciente = authService.login(request.getUserName(), request.getPassword());

        return new LoginResponse(
                paciente.getId(),
                paciente.getFirstName() + " " + paciente.getSecondName(),
                "PACIENTE", // O puedes agregar un campo 'rol' en Paciente
                "Login exitoso"
        );
    }

    @PostMapping("/login-admin")
    public LoginResponse loginAdmin(@RequestBody LoginRequest request) {
        Paciente usuario = authService.login(request.getUserName(), request.getPassword());

        // Verificación simple de admin
        if (!usuario.getUserName().equals("admin")) { // o cualquier criterio simple
            throw new RuntimeException("Acceso denegado: no tiene permisos de administrador");
        }

        return new LoginResponse(
                usuario.getId(),
                usuario.getFirstName() + " " + usuario.getSecondName(),
                "ADMIN",
                "Login admin exitoso"
        );
    }

    // CLASES INTERNAS PARA REQUEST/RESPONSE
    public static class LoginRequest {
        private String userName;
        private String password;

        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private Integer userId;
        private String nombreCompleto;
        private String rol;
        private String mensaje;

        public LoginResponse(Integer userId, String nombreCompleto, String rol, String mensaje) {
            this.userId = userId;
            this.nombreCompleto = nombreCompleto;
            this.rol = rol;
            this.mensaje = mensaje;
        }

        // GETTERS
        public Integer getUserId() { return userId; }
        public String getNombreCompleto() { return nombreCompleto; }
        public String getRol() { return rol; }
        public String getMensaje() { return mensaje; }
    }
}