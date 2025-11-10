package com.medicita.service;

import com.medicita.DTO.PagoDTO;
import com.medicita.entity.Pago;
import com.medicita.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PagoServiceImplementation implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImplementation(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public Pago save(Pago pago) {
        return  pagoRepository.save(pago);
    }

    @Override
    public List<Pago> findAll() {
        return  pagoRepository.findAll();
    }

    @Override
    public Pago findById(Integer id) {
        return  pagoRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        pagoRepository.deleteById(id);

    }

    @Override
    public Pago update(Pago pago) {
        return   pagoRepository.save(pago);
    }

    // NUEVOS MÉTODOS CON DTO
    @Override
    public PagoDTO saveDTO(Pago pago) {
        Pago savedPago = pagoRepository.save(pago);
        return convertToDTO(savedPago);
    }

    @Override
    public List<PagoDTO> findAllDTO() {
        return pagoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoDTO findByIdDTO(Integer id) {
        Pago pago = pagoRepository.findById(id).get();
        return convertToDTO(pago);
    }

    @Override
    public PagoDTO updateDTO(Pago pago) {
        Pago updatedPago = pagoRepository.save(pago);
        return convertToDTO(updatedPago);
    }

    @Override
    public List<PagoDTO> findByPacienteIdDTO(Integer pacienteId) {
        return pagoRepository.findByPacienteId(pacienteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // MÉTODO DE CONVERSIÓN
    private PagoDTO convertToDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setPacienteId(pago.getPaciente().getId());
        dto.setPacienteNombre(pago.getPaciente().getFirstName() + " " + pago.getPaciente().getSecondName());
        dto.setCitaId(pago.getCita().getId());
        dto.setCitaMotivo(pago.getCita().getMotivo());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstado(pago.getEstado());
        return dto;
    }
}
