package com.medicita.service;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;
import com.medicita.entity.Cita;
import com.medicita.entity.Medico;
import com.medicita.entity.Paciente;
import com.medicita.repository.CitaRepository;
import com.medicita.repository.MedicoRepository;
import com.medicita.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImplementation implements CitaService {

    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public CitaServiceImplementation(CitaRepository citaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Cita save(Cita cita) {
        // Validar que el médico y paciente existen
        if (cita.getMedico() == null || cita.getMedico().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Médico es requerido");
        }
        if (cita.getPaciente() == null || cita.getPaciente().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente es requerido");
        }

        // Cargar el médico y paciente completos desde la base de datos
        Medico medico = medicoRepository.findById(cita.getMedico().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado con ID: " + cita.getMedico().getId()));

    Paciente paciente = pacienteRepository.findById(cita.getPaciente().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado con ID: " + cita.getPaciente().getId()));

        cita.setMedico(medico);
        cita.setPaciente(paciente);
        return citaRepository.save(cita);
    }

    // Metodo para crear cita desde DTO - MODIFICADO: ahora devuelve CitaResponseDTO
    @Override
    public CitaResponseDTO createCitaFromDTO(CitaRequestDTO citaRequestDTO) {
        // Validar que los IDs no sean nulos
        if (citaRequestDTO.getMedicoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de médico es requerido");
        }
        if (citaRequestDTO.getPacienteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de paciente es requerido");
        }

        // Buscar el médico en la base de datos
        Medico medico = medicoRepository.findById(citaRequestDTO.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado con ID: " + citaRequestDTO.getMedicoId()));

        // Buscar el paciente en la base de datos
        Paciente paciente = pacienteRepository.findById(citaRequestDTO.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado con ID: " + citaRequestDTO.getPacienteId()));

        // Crear y guardar la cita
        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        cita.setMotivo(citaRequestDTO.getMotivo());
        cita.setFecha(citaRequestDTO.getFecha());

        // Verificar si ya existe una cita activa para el mismo médico, fecha y hora
        java.util.Optional<Cita> existente = citaRepository.findActiveByMedicoAndFechaAndHora(
            citaRequestDTO.getMedicoId(), citaRequestDTO.getFecha(), citaRequestDTO.getHoraAgendada());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una cita para este médico en la misma fecha y hora");
        }

        Cita citaGuardada = citaRepository.save(cita);

        // Convertir a DTO y retornar
        return convertirCitaAResponseDTO(citaGuardada);
    }

    // Metodo para actualizar con DTO
    public Cita updateCitaFromDTO(Integer id, CitaRequestDTO citaRequestDTO) {
    Cita citaExistente = citaRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));

        Medico medico = medicoRepository.findById(citaRequestDTO.getMedicoId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado con ID: " + citaRequestDTO.getMedicoId()));

    Paciente paciente = pacienteRepository.findById(citaRequestDTO.getPacienteId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado con ID: " + citaRequestDTO.getPacienteId()));

        citaExistente.setMedico(medico);
        citaExistente.setPaciente(paciente);
        citaExistente.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        citaExistente.setMotivo(citaRequestDTO.getMotivo());
        citaExistente.setFecha(citaRequestDTO.getFecha());

        return citaRepository.save(citaExistente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> findAll() {
        return citaRepository.findAllWithMedicoAndPaciente();
    }

    @Override
    @Transactional(readOnly = true)
    public Cita findById(Integer id) {
    return citaRepository.findByIdWithMedicoAndPaciente(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        citaRepository.deleteById(id);
    }

    @Override
    public Cita update(Cita cita) {
        // Verificar que la cita existe
        Cita citaExistente = citaRepository.findByIdWithMedicoAndPaciente(cita.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + cita.getId()));

        // Si se está actualizando el médico, cargarlo completo
        if (cita.getMedico() != null) {
            Medico medico = medicoRepository.findById(cita.getMedico().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado con ID: " + cita.getMedico().getId()));
            citaExistente.setMedico(medico);
        }

        // Si se está actualizando el paciente, cargarlo completo
        if (cita.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(cita.getPaciente().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado con ID: " + cita.getPaciente().getId()));
            citaExistente.setPaciente(paciente);
        }

        citaExistente.setHoraAgendada(cita.getHoraAgendada());
        citaExistente.setMotivo(cita.getMotivo());
        citaExistente.setFecha(cita.getFecha());

        return citaRepository.save(citaExistente);
    }

    // Metodo para obtener todas las citas como DTO
    public List<CitaResponseDTO> findAllAsDTO() {
        List<Cita> citas = citaRepository.findAllWithMedicoAndPaciente();
        return citas.stream().map(this::convertirCitaAResponseDTO).collect(Collectors.toList());
    }

    // Método para obtener una cita por ID como DTO
    public CitaResponseDTO findByIdAsDTO(Integer id) {
    Cita cita = citaRepository.findByIdWithMedicoAndPaciente(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
        return convertirCitaAResponseDTO(cita);
    }

    // Método para actualizar el estado de una cita
    public CitaResponseDTO updateEstadoCita(Integer id, String estado) {
    Cita cita = citaRepository.findByIdWithMedicoAndPaciente(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
    cita.setEstado(estado);
    Cita saved = citaRepository.save(cita);
    return convertirCitaAResponseDTO(saved);
    }

    // Método para obtener citas por paciente
    public List<CitaResponseDTO> findCitasByPaciente(Integer pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteIdWithMedico(pacienteId);
        return citas.stream().map(this::convertirCitaAResponseDTO).collect(Collectors.toList());
    }

    // MeTODO AUXILIAR PARA CONVERTIR CITA A RESPONSE DTO
    public CitaResponseDTO convertirCitaAResponseDTO(Cita cita) {
        CitaResponseDTO dto = new CitaResponseDTO();

        // Datos básicos de la cita
        dto.setId(cita.getId());
        dto.setHoraAgendada(cita.getHoraAgendada());
        dto.setMotivo(cita.getMotivo());
        dto.setFecha(cita.getFecha());

        // Datos del médico
        if (cita.getMedico() != null) {
            String nombreCompletoMedico = cita.getMedico().getFirstName() + " " + cita.getMedico().getSecondName();
            dto.setNombreMedico(nombreCompletoMedico.trim());
            dto.setEspecialidad(cita.getMedico().getEspecialidad());
        } else {
            dto.setNombreMedico("Médico no asignado");
            dto.setEspecialidad("No especificada");
        }

        // Datos del paciente
        if (cita.getPaciente() != null) {
            String nombreCompletoPaciente = cita.getPaciente().getFirstName() + " " + cita.getPaciente().getSecondName();
            dto.setNombrePaciente(nombreCompletoPaciente.trim());
            dto.setEmailPaciente(cita.getPaciente().getEmail());
        } else {
            dto.setNombrePaciente("Paciente no asignado");
            dto.setEmailPaciente("No especificado");
        }

        // Estado
        dto.setEstado(cita.getEstado() != null ? cita.getEstado() : "ACTIVA");

        return dto;
    }
}