package com.medicita.controller;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;
import com.medicita.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // http://localhost:8080/api/citas
    @PostMapping
    public CitaResponseDTO save(@RequestBody CitaRequestDTO citaRequestDTO) {
        return citaService.save(citaRequestDTO);
    }

    // http://localhost:8080/api/citas
    @GetMapping
    public List<CitaResponseDTO> findAll() {
        return citaService.findAll();
    }

    // http://localhost:8080/api/citas/1
    @GetMapping("/{id}")
    public CitaResponseDTO findById(@PathVariable Integer id) {
        return citaService.findById(id);
    }

    // http://localhost:8080/api/citas/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        citaService.deleteById(id);
    }

    // http://localhost:8080/api/citas/{id}
    @PutMapping("/{id}")
    public CitaResponseDTO update(@PathVariable Integer id, @RequestBody CitaRequestDTO citaRequestDTO) {
        return citaService.update(id, citaRequestDTO);
    }

    // Endpoints adicionales específicos

    // Obtener citas por paciente
    @GetMapping("/paciente/{pacienteId}")
    public List<CitaResponseDTO> getCitasByPaciente(@PathVariable Integer pacienteId) {
        return citaService.findCitasByPaciente(pacienteId);
    }

    // Actualizar estado de una cita (p.ej. CANCELADA)
    @PutMapping("/{id}/estado")
    public CitaResponseDTO updateEstado(@PathVariable Integer id, @RequestBody EstadoUpdateRequest req) {
        return citaService.updateEstadoCita(id, req.getEstado());
    }

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