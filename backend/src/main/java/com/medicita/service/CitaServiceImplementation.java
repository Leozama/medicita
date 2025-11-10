package com.medicita.service;

import com.medicita.DTO.CitaDTO;
import com.medicita.DTO.PacienteDTO;
import com.medicita.entity.Cita;
import com.medicita.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImplementation implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImplementation(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> findAll() {
        return  citaRepository.findAll();
    }

    @Override
    public Cita findById(Integer id) {
        return  citaRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        citaRepository.deleteById(id);

    }

    @Override
    public Cita update(Cita cita) {
        return  citaRepository.save(cita);
    }

    @Override
    public List<Cita> obtenerPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    // NUEVOS MÉTODOS CON DTO (actualizados)
    @Override
    public CitaDTO saveDTO(Cita cita) {
        Cita savedCita = citaRepository.save(cita);
        return convertToDTO(savedCita);
    }

    @Override
    public List<CitaDTO> findAllDTO() {
        return citaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CitaDTO findByIdDTO(Integer id) {
        Cita cita = citaRepository.findById(id).get();
        return convertToDTO(cita);
    }

    @Override
    public CitaDTO updateDTO(Cita cita) {
        Cita updatedCita = citaRepository.save(cita);
        return convertToDTO(updatedCita);
    }

    @Override
    public List<CitaDTO> obtenerPorPacienteDTO(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // METODO DE CONVERSIÓN ACTUALIZADO (con PacienteDTO)
    private CitaDTO convertToDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());

        // Crear PacienteDTO con todos los datos del paciente
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(cita.getPaciente().getId());
        pacienteDTO.setFirstName(cita.getPaciente().getFirstName());
        pacienteDTO.setSecondName(cita.getPaciente().getSecondName());
        pacienteDTO.setAge(cita.getPaciente().getAge());
        pacienteDTO.setCi(cita.getPaciente().getCI());

        dto.setPaciente(pacienteDTO);
        dto.setHoraAgendada(cita.getHoraAgendada());
        dto.setMotivo(cita.getMotivo());
        dto.setFecha(cita.getFecha());

        return dto;
    }
}
