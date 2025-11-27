package com.medicita.service;

import com.medicita.DTO.MedicoRequestDTO;
import com.medicita.DTO.MedicoResponseDTO;

import java.util.List;

public interface MedicoService {
    MedicoResponseDTO save(MedicoRequestDTO medicoRequestDTO);

    List<MedicoResponseDTO> findAll();

    MedicoResponseDTO findById(Integer id);

    void deleteById(Integer id);

    MedicoResponseDTO update(Integer id, MedicoRequestDTO medicoRequestDTO);
}