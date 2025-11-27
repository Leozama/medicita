package com.medicita.controller;

import com.medicita.DTO.PacienteRequestDTO;
import com.medicita.DTO.PacienteResponseDTO;
import com.medicita.entity.Paciente;
import com.medicita.service.AuthService;
import com.medicita.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final PacienteService pacienteService;

    public AuthController(AuthService authService, PacienteService pacienteService) {
        this.authService = authService;
        this.pacienteService = pacienteService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        PacienteResponseDTO paciente = authService.login(request.getUserName(), request.getPassword());

        return new LoginResponse(
                paciente.getId(),
                paciente.getNombreCompleto(),
                "PACIENTE", // O puedes agregar un campo 'rol' en Paciente
                "Login exitoso");
    }

    @PostMapping("/login-admin")
    public LoginResponse loginAdmin(@RequestBody LoginRequest request) {
        PacienteResponseDTO usuario = authService.login(request.getUserName(), request.getPassword());

        // Verificación simple de admin
        if (!usuario.getUserName().equals("admin")) { // o cualquier criterio simple
            throw new RuntimeException("Acceso denegado: no tiene permisos de administrador");
        }

        return new LoginResponse(
                usuario.getId(),
                usuario.getNombreCompleto(),
                "ADMIN",
                "Login admin exitoso");
    }

    // CLASES INTERNAS PARA REQUEST/RESPONSE
    public static class LoginRequest {
        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
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
        public Integer getUserId() {
            return userId;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public String getRol() {
            return rol;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

    // Registro request/response
    public static class RegistroRequest {
        private String nombreCompleto;
        private String email;
        private String telefono;
        private String fechaNacimiento;
        private String password;
        private String username;

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class RegistroResponse {
        private Integer userId;
        private String mensaje;

        public RegistroResponse(Integer userId, String mensaje) {
            this.userId = userId;
            this.mensaje = mensaje;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

    @PostMapping("/registro")
    public RegistroResponse registro(@RequestBody RegistroRequest request) {
        // Validaciones básicas
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username es requerido");
        }
        // Verificar existencia por username
        if (pacienteService != null) {
            try {
                // pacienteService uses repository which provides findByUserName
                // If user exists, return 409
                java.util.Optional<PacienteResponseDTO> existing = pacienteService.findAll().stream()
                        .filter(p -> request.getUsername().equals(p.getUserName()))
                        .findFirst();
                if (existing.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario ya existente");
                }
            } catch (Exception e) {
                // ignore, will handle save below
            }
        }

        // Separar nombre completo en firstName/secondName si viene
        String firstName = "";
        String secondName = "";
        if (request.getNombreCompleto() != null && !request.getNombreCompleto().isBlank()) {
            String[] parts = request.getNombreCompleto().trim().split("\\s+", 2);
            firstName = parts[0];
            if (parts.length > 1)
                secondName = parts[1];
        }

        PacienteRequestDTO pacienteDTO = new PacienteRequestDTO();
        pacienteDTO.setFirstName(firstName);
        pacienteDTO.setSecondName(secondName);
        pacienteDTO.setEmail(request.getEmail());
        pacienteDTO.setTelefono(request.getTelefono());
        pacienteDTO.setFechaNacimiento(request.getFechaNacimiento());
        pacienteDTO.setUserName(request.getUsername());
        pacienteDTO.setPassword(request.getPassword());

        PacienteResponseDTO saved = pacienteService.save(pacienteDTO);
        return new RegistroResponse(saved.getId(), "Registro exitoso");
    }
}