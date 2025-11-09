package com.medicita.service;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.entity.Cita;
import com.medicita.entity.Medico;
import com.medicita.repository.CitaRepository;
import com.medicita.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
