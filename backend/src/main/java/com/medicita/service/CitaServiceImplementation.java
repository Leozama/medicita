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

    public CitaServiceImplementation(CitaRepository citaRepository, MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public CitaResponseDTO save(CitaRequestDTO citaRequestDTO) {
        // Validar que los IDs no sean nulos
        if (citaRequestDTO.getMedicoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de médico es requerido");
        }
        if (citaRequestDTO.getPacienteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de paciente es requerido");
        }

        // Buscar el médico en la base de datos
        Medico medico = medicoRepository.findById(citaRequestDTO.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Médico no encontrado con ID: " + citaRequestDTO.getMedicoId()));

        // Buscar el paciente en la base de datos
        Paciente paciente = pacienteRepository.findById(citaRequestDTO.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente no encontrado con ID: " + citaRequestDTO.getPacienteId()));

        // Crear y guardar la cita
        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        cita.setMotivo(citaRequestDTO.getMotivo());
        cita.setFecha(citaRequestDTO.getFecha());
        cita.setEstado("ACTIVA"); // Estado por defecto

        // Verificar si ya existe una cita activa para el mismo médico, fecha y hora
        java.util.Optional<Cita> existente = citaRepository.findActiveByMedicoAndFechaAndHora(
                citaRequestDTO.getMedicoId(), citaRequestDTO.getFecha(), citaRequestDTO.getHoraAgendada());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una cita para este médico en la misma fecha y hora");
        }

        Cita citaGuardada = citaRepository.save(cita);
        return convertirCitaAResponseDTO(citaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> findAll() {
        List<Cita> citas = citaRepository.findAllWithMedicoAndPaciente();
        return citas.stream().map(this::convertirCitaAResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO findById(Integer id) {
        Cita cita = citaRepository.findByIdWithMedicoAndPaciente(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
        return convertirCitaAResponseDTO(cita);
    }

    @Override
    public void deleteById(Integer id) {
        citaRepository.deleteById(id);
    }

    @Override
    public CitaResponseDTO update(Integer id, CitaRequestDTO citaRequestDTO) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));

        Medico medico = medicoRepository.findById(citaRequestDTO.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Médico no encontrado con ID: " + citaRequestDTO.getMedicoId()));

        Paciente paciente = pacienteRepository.findById(citaRequestDTO.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente no encontrado con ID: " + citaRequestDTO.getPacienteId()));

        citaExistente.setMedico(medico);
        citaExistente.setPaciente(paciente);
        citaExistente.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        citaExistente.setMotivo(citaRequestDTO.getMotivo());
        citaExistente.setFecha(citaRequestDTO.getFecha());

        Cita citaActualizada = citaRepository.save(citaExistente);
        return convertirCitaAResponseDTO(citaActualizada);
    }

    @Override
    public List<CitaResponseDTO> findCitasByPaciente(Integer pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteIdWithMedico(pacienteId);
        return citas.stream().map(this::convertirCitaAResponseDTO).collect(Collectors.toList());
    }

    @Override
    public CitaResponseDTO updateEstadoCita(Integer id, String estado) {
        Cita cita = citaRepository.findByIdWithMedicoAndPaciente(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
        cita.setEstado(estado);
        Cita saved = citaRepository.save(cita);
        return convertirCitaAResponseDTO(saved);
    }

    // MÉTODO AUXILIAR PARA CONVERTIR CITA A RESPONSE DTO
    private CitaResponseDTO convertirCitaAResponseDTO(Cita cita) {
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
            String nombreCompletoPaciente = cita.getPaciente().getFirstName() + " "
                    + cita.getPaciente().getSecondName();
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