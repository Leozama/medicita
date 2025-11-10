package com.medicita.controller;

import com.medicita.DTO.PagoRequestDTO;
import com.medicita.DTO.PagoResponseDTO;
import com.medicita.service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Generar un pago pendiente a partir de una cita
    // POST http://localhost:8080/api/pagos
    // Body: { "citaId": 1 }
    @PostMapping
    public PagoResponseDTO generarPagoPendiente(@RequestBody PagoRequestDTO pagoRequestDTO) {
        return pagoService.generarPagoPendiente(pagoRequestDTO);
    }

    // Obtener todos los pagos
    // GET http://localhost:8080/api/pagos
    @GetMapping
    public List<PagoResponseDTO> findAll() {
        return pagoService.findAllAsDTO();
    }

    // Obtener un pago por ID
    // GET http://localhost:8080/api/pagos/1
    @GetMapping("/{id}")
    public PagoResponseDTO findById(@PathVariable Integer id) {
        return pagoService.findByIdAsDTO(id);
    }

    // Obtener pagos de un paciente especÃ­fico
    // GET http://localhost:8080/api/pagos/paciente/1
    @GetMapping("/paciente/{pacienteId}")
    public List<PagoResponseDTO> getPagosByPaciente(@PathVariable Integer pacienteId) {
        return pagoService.findPagosByPaciente(pacienteId);
    }

    // Actualizar el estado de un pago
    // PUT http://localhost:8080/api/pagos/1/estado
    // Body: { "estado": "PAGADO" }
    @PutMapping("/{id}/estado")
    public PagoResponseDTO actualizarEstado(@PathVariable Integer id, @RequestBody EstadoUpdateRequest request) {
        return pagoService.actualizarEstadoPago(id, request.getEstado());
    }

    // Eliminar un pago
    // DELETE http://localhost:8080/api/pagos/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        pagoService.deleteById(id);
    }

    // Clase auxiliar para recibir el nuevo estado
    public static class EstadoUpdateRequest {
        private String estado;

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}