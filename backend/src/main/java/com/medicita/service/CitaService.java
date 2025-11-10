package com.medicita.service;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;
import com.medicita.entity.Cita;

import java.util.List;

public interface CitaService {
    Cita save(Cita cita);
    List<Cita> findAll();
    Cita findById(Integer id);
    void deleteById(Integer id);
    Cita update(Cita cita);

    // MÉTODO MODIFICADO - ahora devuelve CitaResponseDTO
    CitaResponseDTO createCitaFromDTO(CitaRequestDTO citaRequestDTO);
}