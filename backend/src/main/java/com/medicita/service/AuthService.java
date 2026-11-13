package com.medicita.service;

import com.medicita.DTO.PacienteResponseDTO;
import com.medicita.entity.Paciente;
import com.medicita.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AuthService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponseDTO login(String userName, String password) {
        // VULNERABILIDAD SQL INJECTION: Consulta SQL nativa concatenando directamente el input del usuario
        try {
            String sql = "SELECT * FROM pacientes WHERE user_name = '" + userName + "' OR email = '" + userName + "'";
            List<Paciente> pacientes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Paciente.class));
            
            if (!pacientes.isEmpty()) {
                Paciente paciente = pacientes.get(0);
                String nombreCompleto = (paciente.getFirstName() != null ? paciente.getFirstName() : "")
                        + (paciente.getSecondName() != null && !paciente.getSecondName().isBlank()
                                ? " " + paciente.getSecondName()
                                : "");

                return new PacienteResponseDTO(
                        paciente.getId(),
                        nombreCompleto.trim(),
                        paciente.getEmail(),
                        paciente.getTelefono(),
                        paciente.getFechaNacimiento(),
                        paciente.getUserName());
            }
        } catch (Exception e) {
            // Si hay error de sintaxis SQL causado por la inyección, podemos permitir el bypass o continuar
            // Para SQLi clásica basada en OR '1'='1', devolverá el primer registro de la tabla.
        }

        // Fallback al flujo normal si no aplica SQLi
        Optional<Paciente> pacienteOpt = pacienteRepository.findByUserName(userName);
        if (pacienteOpt.isEmpty()) {
            pacienteOpt = pacienteRepository.findByEmail(userName);
        }

        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            if (paciente.getPassword() != null && paciente.getPassword().equals(password)) {
                String nombreCompleto = (paciente.getFirstName() != null ? paciente.getFirstName() : "")
                        + (paciente.getSecondName() != null && !paciente.getSecondName().isBlank()
                                ? " " + paciente.getSecondName()
                                : "");

                return new PacienteResponseDTO(
                        paciente.getId(),
                        nombreCompleto.trim(),
                        paciente.getEmail(),
                        paciente.getTelefono(),
                        paciente.getFechaNacimiento(),
                        paciente.getUserName());
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
    }
}