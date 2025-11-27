package com.medicita.service;

import com.medicita.DTO.MedicoRequestDTO;
import com.medicita.DTO.MedicoResponseDTO;
import com.medicita.entity.Medico;
import com.medicita.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN DE SERVICIO - LÓGICA DE NEGOCIO
 *
 * RESPONSABILIDAD:
 * - Implementar las reglas de negocio
 * - Coordinar operaciones entre diferentes componentes
 * - Manejar transacciones y validaciones complejas
 * - Transformar datos entre capas
 *
 * REGLAS:
 * - Contiene la lógica central de la aplicación
 * - Puede usar múltiples repositories
 * - Maneja excepciones de negocio
 * - NO debe contener lógica HTTP o de base de datos directa
 */

@Service
public class MedicoServiceImplementation implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImplementation(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public MedicoResponseDTO save(MedicoRequestDTO medicoRequestDTO) {
        Medico medico = new Medico();
        medico.setFirstName(medicoRequestDTO.getFirstName());
        medico.setSecondName(medicoRequestDTO.getSecondName());
        medico.setEspecialidad(medicoRequestDTO.getEspecialidad());
        medico.setHorario(medicoRequestDTO.getHorario());
        medico.setCostoConsulta(medicoRequestDTO.getCostoConsulta());

        Medico savedMedico = medicoRepository.save(medico);
        return convertirAResponseDTO(savedMedico);
    }

    @Override
    public List<MedicoResponseDTO> findAll() {
        List<Medico> medicos = medicoRepository.findAll();
        return medicos.stream()
                .map(this::convertirAResponseDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public MedicoResponseDTO findById(Integer id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
        return convertirAResponseDTO(medico);
    }

    @Override
    public void deleteById(Integer id) {
        medicoRepository.deleteById(id);
    }

    @Override
    public MedicoResponseDTO update(Integer id, MedicoRequestDTO medicoRequestDTO) {
        Medico medicoDb = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));

        medicoDb.setFirstName(medicoRequestDTO.getFirstName());
        medicoDb.setSecondName(medicoRequestDTO.getSecondName());
        medicoDb.setEspecialidad(medicoRequestDTO.getEspecialidad());
        medicoDb.setHorario(medicoRequestDTO.getHorario());
        medicoDb.setCostoConsulta(medicoRequestDTO.getCostoConsulta());

        Medico updatedMedico = medicoRepository.save(medicoDb);
        return convertirAResponseDTO(updatedMedico);
    }

    private MedicoResponseDTO convertirAResponseDTO(Medico medico) {
        return new MedicoResponseDTO(
                medico.getId(),
                medico.getFirstName(),
                medico.getSecondName(),
                medico.getFirstName() + " " + medico.getSecondName(),
                medico.getEspecialidad(),
                medico.getHorario(),
                medico.getCostoConsulta());
    }
}
