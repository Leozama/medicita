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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de cita es requerido");
        }

        // Buscar la cita con médico y paciente
        Cita cita = citaRepository.findByIdWithMedicoAndPaciente(pagoRequestDTO.getCitaId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + pagoRequestDTO.getCitaId()));

        // Obtener costo de consulta; si no está configurado, usar valor por defecto
        Double costo = cita.getMedico().getCostoConsulta();
        if (costo == null) {
            // Usar monto por defecto para consultas si no está configurado
            costo = 150.0;
        }

        // Crear el pago (estado por defecto PENDIENTE, pero el request puede pedir PAGADO)
        Pago pago = new Pago();
        pago.setCita(cita);
        pago.setPaciente(cita.getPaciente());
        pago.setFechaCita(cita.getFecha());
        pago.setMonto(costo);

        String requestedEstado = pagoRequestDTO.getEstado();
        if (requestedEstado == null) {
            pago.setEstado("PENDIENTE");
        } else {
            // Normalizar y validar
            String est = requestedEstado.toUpperCase();
            if (!est.equals("PENDIENTE") && !est.equals("PAGADO") && !est.equals("CANCELADO")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido al crear pago. Debe ser: PENDIENTE, PAGADO o CANCELADO");
            }
            pago.setEstado(est);
        }

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
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado con ID: " + pagoId));

        // Validar que el estado sea válido
        if (!nuevoEstado.equals("PENDIENTE") && !nuevoEstado.equals("PAGADO") && !nuevoEstado.equals("CANCELADO")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido. Debe ser: PENDIENTE, PAGADO o CANCELADO");
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