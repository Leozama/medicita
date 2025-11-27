package com.medicita.service;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;

import java.util.List;

public interface CitaService {
    CitaResponseDTO save(CitaRequestDTO citaRequestDTO);

    List<CitaResponseDTO> findAll();

    CitaResponseDTO findById(Integer id);

    void deleteById(Integer id);

    CitaResponseDTO update(Integer id, CitaRequestDTO citaRequestDTO);

    // Métodos específicos adicionales que ya retornaban DTO o son útiles
    List<CitaResponseDTO> findCitasByPaciente(Integer pacienteId);

    CitaResponseDTO updateEstadoCita(Integer id, String estado);
}