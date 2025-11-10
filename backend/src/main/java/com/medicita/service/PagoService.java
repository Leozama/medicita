package com.medicita.service;

import com.medicita.DTO.PagoDTO;
import com.medicita.entity.Pago;

import java.util.List;

public interface PagoService {
    Pago save(Pago pago);
    List<Pago> findAll();
    Pago findById(Integer id);
    void deleteById(Integer id);
    Pago update(Pago pago);

    // NUEVOS MÉTODOS CON DTO
    PagoDTO saveDTO(Pago pago);
    List<PagoDTO> findAllDTO();
    PagoDTO findByIdDTO(Integer id);
    PagoDTO updateDTO(Pago pago);
    List<PagoDTO> findByPacienteIdDTO(Integer pacienteId);
}
