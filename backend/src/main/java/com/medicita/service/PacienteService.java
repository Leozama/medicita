package com.medicita.service;

import com.medicita.DTO.PacienteRequestDTO;
import com.medicita.DTO.PacienteResponseDTO;
import com.medicita.entity.Paciente;

import java.util.List;

/**
 * INTERFACE DE SERVICIO - CONTRATO DE LÓGICA DE NEGOCIO
 *
 * RESPONSABILIDAD:
 * - Definir los métodos de lógica de negocio disponibles
 * - Servir como contrato entre Controller y ServiceImpl
 * - Centralizar reglas de negocio
 *
 * REGLAS:
 * package com.medicita.service;
 * 
 * import com.medicita.entity.Paciente;
 * import com.medicita.dto.PacienteRequestDTO;
 * import com.medicita.dto.PacienteResponseDTO;
 * 
 * import java.util.List;
 * 
 * /**
 * INTERFACE DE SERVICIO - CONTRATO DE LÓGICA DE NEGOCIO
 *
 * RESPONSABILIDAD:
 * - Definir los métodos de lógica de negocio disponibles
 * - Servir como contrato entre Controller y ServiceImpl
 * - Centralizar reglas de negocio
 *
 * REGLAS:
 * - Define QUÉ se puede hacer (no CÓMO)
 * - Nombres de métodos descriptivos del negocio
 * - No depende de tecnologías específicas
 */

public interface PacienteService {
    PacienteResponseDTO save(PacienteRequestDTO pacienteRequestDTO);

    List<PacienteResponseDTO> findAll();

    PacienteResponseDTO findById(Integer id);

    void deleteById(Integer id);

    PacienteResponseDTO update(Integer id, PacienteRequestDTO pacienteRequestDTO);
}
