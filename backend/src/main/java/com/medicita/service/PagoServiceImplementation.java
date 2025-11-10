package com.medicita.service;

import com.medicita.DTO.PagoRequestDTO;
import com.medicita.DTO.PagoResponseDTO;
import com.medicita.entity.Cita;
import com.medicita.entity.Pago;
import com.medicita.repository.CitaRepository;
import com.medicita.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PagoServiceImplementation implements PagoService {

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;

    public PagoServiceImplementation(PagoRepository pagoRepository, CitaRepository citaRepository) {
        this.pagoRepository = pagoRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public PagoResponseDTO generarPagoPendiente(PagoRequestDTO pagoRequestDTO) {
        // Validar que el ID de cita no sea nulo
        if (pagoRequestDTO.getCitaId() == null) {
            throw new RuntimeException("ID de cita es requerido");
        }

        // Buscar la cita con médico y paciente
        Cita cita = citaRepository.findByIdWithMedicoAndPaciente(pagoRequestDTO.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + pagoRequestDTO.getCitaId()));

        // Validar que el médico tenga costo de consulta configurado
        if (cita.getMedico().getCostoConsulta() == null) {
            throw new RuntimeException("El médico no tiene configurado el costo de consulta");
        }

        // Crear el pago pendiente
        Pago pago = new Pago();
        pago.setCita(cita);
        pago.setPaciente(cita.getPaciente());
        pago.setFechaCita(cita.getFecha());
        pago.setMonto(cita.getMedico().getCostoConsulta());
        pago.setEstado("PENDIENTE");

        Pago pagoGuardado = pagoRepository.save(pago);

        return convertirPagoAResponseDTO(pagoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> findPagosByPaciente(Integer pacienteId) {
        List<Pago> pagos = pagoRepository.findByPacienteIdWithDetails(pacienteId);
        return pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponseDTO findByIdAsDTO(Integer id) {
        Pago pago = pagoRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        return convertirPagoAResponseDTO(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> findAllAsDTO() {
        List<Pago> pagos = pagoRepository.findAllWithDetails();
        return pagos.stream()
                .map(this::convertirPagoAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoResponseDTO actualizarEstadoPago(Integer pagoId, String nuevoEstado) {
        Pago pago = pagoRepository.findByIdWithDetails(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + pagoId));

        // Validar que el estado sea válido
        if (!nuevoEstado.equals("PENDIENTE") && !nuevoEstado.equals("PAGADO") && !nuevoEstado.equals("CANCELADO")) {
            throw new RuntimeException("Estado inválido. Debe ser: PENDIENTE, PAGADO o CANCELADO");
        }

        pago.setEstado(nuevoEstado);
        Pago pagoActualizado = pagoRepository.save(pago);

        return convertirPagoAResponseDTO(pagoActualizado);
    }

    @Override
    public void deleteById(Integer id) {
        pagoRepository.deleteById(id);
    }

    // MÉTODO AUXILIAR PARA CONVERTIR PAGO A RESPONSE DTO
    private PagoResponseDTO convertirPagoAResponseDTO(Pago pago) {
        PagoResponseDTO dto = new PagoResponseDTO();

        dto.setPagoId(pago.getId());
        dto.setCitaId(pago.getCita().getId());
        dto.setPacienteId(pago.getPaciente().getId());
        dto.setFechaCita(pago.getFechaCita());
        dto.setMonto(pago.getMonto());
        dto.setEstado(pago.getEstado());

        // Datos del paciente
        String nombreCompletoPaciente = pago.getPaciente().getFirstName() + " " +
                pago.getPaciente().getSecondName();
        dto.setNombrePaciente(nombreCompletoPaciente.trim());

        // Datos del médico
        String nombreCompletoMedico = pago.getCita().getMedico().getFirstName() + " " +
                pago.getCita().getMedico().getSecondName();
        dto.setNombreMedico(nombreCompletoMedico.trim());
        dto.setEspecialidad(pago.getCita().getMedico().getEspecialidad());

        return dto;
    }
}