package com.medicita.service;

import com.medicita.DTO.PacienteRequestDTO;
import com.medicita.DTO.PacienteResponseDTO;
import com.medicita.entity.Paciente;
import com.medicita.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImplementation implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImplementation(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteResponseDTO save(PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = new Paciente();
        paciente.setFirstName(pacienteRequestDTO.getFirstName());
        paciente.setSecondName(pacienteRequestDTO.getSecondName());
        paciente.setEmail(pacienteRequestDTO.getEmail());
        paciente.setTelefono(pacienteRequestDTO.getTelefono());
        paciente.setFechaNacimiento(pacienteRequestDTO.getFechaNacimiento());
        paciente.setUserName(pacienteRequestDTO.getUserName());
        paciente.setPassword(pacienteRequestDTO.getPassword());

        Paciente savedPaciente = pacienteRepository.save(paciente);
        return convertirAResponseDTO(savedPaciente);
    }

    @Override
    public List<PacienteResponseDTO> findAll() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponseDTO findById(Integer id) {
        Paciente paciente = pacienteRepository.findByIdWithCitas(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
        return convertirAResponseDTO(paciente);
    }

    @Override
    public void deleteById(Integer id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public PacienteResponseDTO update(Integer id, PacienteRequestDTO pacienteRequestDTO) {
        Paciente pacienteDb = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));

        pacienteDb.setFirstName(pacienteRequestDTO.getFirstName());
        pacienteDb.setSecondName(pacienteRequestDTO.getSecondName());
        pacienteDb.setEmail(pacienteRequestDTO.getEmail());
        pacienteDb.setTelefono(pacienteRequestDTO.getTelefono());
        pacienteDb.setFechaNacimiento(pacienteRequestDTO.getFechaNacimiento());
        pacienteDb.setUserName(pacienteRequestDTO.getUserName());
        // No actualizamos password aquí por seguridad, o se podría añadir si es
        // requerido

        Paciente updatedPaciente = pacienteRepository.save(pacienteDb);
        return convertirAResponseDTO(updatedPaciente);
    }

    private PacienteResponseDTO convertirAResponseDTO(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getFirstName() + " " + paciente.getSecondName(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento(),
                paciente.getUserName());
    }
}