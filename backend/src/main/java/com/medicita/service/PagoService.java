package com.medicita.service;

import com.medicita.DTO.PagoRequestDTO;
import com.medicita.DTO.PagoResponseDTO;

import java.util.List;

public interface PagoService {
    PagoResponseDTO generarPagoPendiente(PagoRequestDTO pagoRequestDTO);
    List<PagoResponseDTO> findPagosByPaciente(Integer pacienteId);
    PagoResponseDTO findByIdAsDTO(Integer id);
    List<PagoResponseDTO> findAllAsDTO();
    PagoResponseDTO actualizarEstadoPago(Integer pagoId, String nuevoEstado);
    void deleteById(Integer id);
}