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

    //Metodo nuevo para crear cita desde DTO
    public Cita createCitaFromDTO(CitaRequestDTO citaRequestDTO) {
        // Buscar el médico por ID
        Medico medico = medicoRepository.findById(citaRequestDTO.getMedico().getId()).get();


        // Crear la entidad Cita
        Cita cita = new Cita();
        cita.setNombreMedico(citaRequestDTO.getNombreMedico());
        cita.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        cita.setMotivo(citaRequestDTO.getMotivo());
        cita.setFecha(citaRequestDTO.getFecha());

        return citaRepository.save(cita);
    }

    // Metodo para actualizar con DTO
    public Cita updateCitaFromDTO(Integer id, CitaRequestDTO citaRequestDTO) {
        // Verificar que la cita existe
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        // Buscar el médico por ID
        Medico medico = medicoRepository.findById(citaRequestDTO.getMedico().getId()).get();

        // Actualizar la cita
        citaExistente.setNombreMedico(citaRequestDTO.getNombreMedico());
        citaExistente.setHoraAgendada(citaRequestDTO.getHoraAgendada());
        citaExistente.setMotivo(citaRequestDTO.getMotivo());
        citaExistente.setFecha(citaRequestDTO.getFecha());

        return citaRepository.save(citaExistente);
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
}
