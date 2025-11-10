package com.medicita.service;

import com.medicita.DTO.CitaDTO;
import com.medicita.DTO.MedicoDTO;
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

    @Override
    public List<Cita> obtenerPorMedico(Integer medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }

    @Override
    public List<CitaDTO> obtenerPorMedicoDTO(Integer medicoId) {
        return citaRepository.findByMedicoId(medicoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // METODO DE CONVERSIÓN ACTUALIZADO (con PacienteDTO y MedicoDTO)
    private CitaDTO convertToDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());

        // Crear PacienteDTO
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(cita.getPaciente().getId());
        pacienteDTO.setFirstName(cita.getPaciente().getFirstName());
        pacienteDTO.setSecondName(cita.getPaciente().getSecondName());
        pacienteDTO.setAge(cita.getPaciente().getAge());
        pacienteDTO.setCi(cita.getPaciente().getCI());
        dto.setPaciente(pacienteDTO);

        // NUEVO: Crear MedicoDTO
        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setId(cita.getMedico().getId());
        medicoDTO.setFirstName(cita.getMedico().getFirstName());
        medicoDTO.setSecondName(cita.getMedico().getSecondName());
        medicoDTO.setEspecialidad(cita.getMedico().getEspecialidad());
        medicoDTO.setHorario(cita.getMedico().getHorario());
        dto.setMedico(medicoDTO);

        dto.setHoraAgendada(cita.getHoraAgendada());
        dto.setMotivo(cita.getMotivo());
        dto.setFecha(cita.getFecha());

        return dto;
    }
}
