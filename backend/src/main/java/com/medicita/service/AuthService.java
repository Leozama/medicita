package com.medicita.service;

import com.medicita.entity.Paciente;
import com.medicita.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final PacienteRepository pacienteRepository;

    public AuthService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente login(String userName, String password) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByUserName(userName);

        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            // Comparación simple de contraseñas (para demo universidad)
            if (paciente.getPassword().equals(password)) {
                return paciente; // Login exitoso
            }
        }
        throw new RuntimeException("Usuario o contraseña incorrectos");
    }
}