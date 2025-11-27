package com.medicita.controller;

import com.medicita.DTO.LoginRequestDTO;
import com.medicita.DTO.LoginResponseDTO;
import com.medicita.DTO.PacienteRequestDTO;
import com.medicita.DTO.PacienteResponseDTO;
import com.medicita.DTO.RegistroRequestDTO;
import com.medicita.DTO.RegistroResponseDTO;
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
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        PacienteResponseDTO paciente = authService.login(request.getUserName(), request.getPassword());

        return new LoginResponseDTO(
                paciente.getId(),
                paciente.getNombreCompleto(),
                "PACIENTE", // O puedes agregar un campo 'rol' en Paciente
                "Login exitoso");
    }

    @PostMapping("/login-admin")
    public LoginResponseDTO loginAdmin(@RequestBody LoginRequestDTO request) {
        PacienteResponseDTO usuario = authService.login(request.getUserName(), request.getPassword());

        // Verificación simple de admin
        if (!usuario.getUserName().equals("admin")) { // o cualquier criterio simple
            throw new RuntimeException("Acceso denegado: no tiene permisos de administrador");
        }

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getNombreCompleto(),
                "ADMIN",
                "Login admin exitoso");
    }

    @PostMapping("/registro")
    public RegistroResponseDTO registro(@RequestBody RegistroRequestDTO request) {
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
        return new RegistroResponseDTO(saved.getId(), "Registro exitoso");
    }
}