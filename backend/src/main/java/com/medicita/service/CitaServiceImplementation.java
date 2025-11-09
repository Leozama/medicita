package com.medicita.service;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;
import com.medicita.entity.Cita;
import com.medicita.entity.Medico;
import com.medicita.repository.CitaRepository;
import com.medicita.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImplementation implements CitaService {

    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;

    public CitaServiceImplementation(CitaRepository citaRepository, MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Cita save(Cita cita) {
        // Validar que el médico existe
        if (cita.getMedico() == null || cita.getMedico().getId() == 0) {
            throw new RuntimeException("Médico es requerido");
        }

        // Cargar el médico completo desde la base de datos
        Medico medico = medicoRepository.findById(cita.getMedico().getId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + cita.getMedico().getId()));

        cita.setMedico(medico);
        return citaRepository.save(cita);
    }

    // Método para crear cita desde DTO
    public Cita createCitaFromDTO(CitaRequestDTO citaRequestDTO) {
        // Buscar el médico
        Medico medico = medicoRepository.findById(citaRequestDTO.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + citaRequestDTO.getMedicoId()));

        // Crear la cita
        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        cita.setMotivo(citaRequestDTO.getMotivo());
        cita.setFecha(citaRequestDTO.getFecha());

        return citaRepository.save(cita);
    }

    // Método para actualizar con DTO
    public Cita updateCitaFromDTO(Integer id, CitaRequestDTO citaRequestDTO) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        Medico medico = medicoRepository.findById(citaRequestDTO.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + citaRequestDTO.getMedicoId()));

        citaExistente.setMedico(medico);
        citaExistente.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        citaExistente.setMotivo(citaRequestDTO.getMotivo());
        citaExistente.setFecha(citaRequestDTO.getFecha());

        return citaRepository.save(citaExistente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> findAll() {
        return citaRepository.findAllWithMedico();
    }

    @Override
    @Transactional(readOnly = true)
    public Cita findById(Integer id) {
        return citaRepository.findByIdWithMedico(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        citaRepository.deleteById(id);
    }

    @Override
    public Cita update(Cita cita) {
        // Verificar que la cita existe
        Cita citaExistente = citaRepository.findByIdWithMedico(cita.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + cita.getId()));

        // Si se está actualizando el médico, cargarlo completo
        if (cita.getMedico() != null) {
            Medico medico = medicoRepository.findById(cita.getMedico().getId())
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + cita.getMedico().getId()));
            citaExistente.setMedico(medico);
        }

        citaExistente.setHoraAgendada(cita.getHoraAgendada());
        citaExistente.setMotivo(cita.getMotivo());
        citaExistente.setFecha(cita.getFecha());

        return citaRepository.save(citaExistente);
    }

    // NUEVO: Método para obtener todas las citas como DTO
    public List<CitaResponseDTO> findAllAsDTO() {
        List<Cita> citas = citaRepository.findAllWithMedico();
        return citas.stream().map(cita -> {
            CitaResponseDTO dto = new CitaResponseDTO();
            dto.setId(cita.getId());
            dto.setHoraAgendada(cita.getHoraAgendada());
            dto.setMotivo(cita.getMotivo());
            dto.setFecha(cita.getFecha());

            if (cita.getMedico() != null) {
                dto.setNombreMedico(cita.getMedico().getFirstName() + " " + cita.getMedico().getSecondName());
                dto.setEspecialidad(cita.getMedico().getEspecialidad());
            } else {
                dto.setNombreMedico("Médico no asignado");
                dto.setEspecialidad("No especificada");
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // NUEVO: Método para obtener una cita por ID como DTO
    public CitaResponseDTO findByIdAsDTO(Integer id) {
        Cita cita = citaRepository.findByIdWithMedico(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setId(cita.getId());
        dto.setHoraAgendada(cita.getHoraAgendada());
        dto.setMotivo(cita.getMotivo());
        dto.setFecha(cita.getFecha());

        if (cita.getMedico() != null) {
            dto.setNombreMedico(cita.getMedico().getFirstName() + " " + cita.getMedico().getSecondName());
            dto.setEspecialidad(cita.getMedico().getEspecialidad());
        } else {
            dto.setNombreMedico("Médico no asignado");
            dto.setEspecialidad("No especificada");
        }

        return dto;
    }
}