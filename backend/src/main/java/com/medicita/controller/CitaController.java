package com.medicita.controller;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;
import com.medicita.entity.Cita;
import com.medicita.service.CitaServiceImplementation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {

    private final CitaServiceImplementation citaServiceImplementation;

    public CitaController(CitaServiceImplementation citaServiceImplementation) {
        this.citaServiceImplementation = citaServiceImplementation;
    }

    // http://localhost:8080/api/citas
    @PostMapping
    public Cita save(@RequestBody Cita cita){
        return citaServiceImplementation.save(cita);
    }

    // http://localhost:8080/api/citas
    @GetMapping
    public List<Cita> findAll(){
        return citaServiceImplementation.findAll();
    }

    // http://localhost:8080/api/citas/1
    @GetMapping("/{id}")
    public Cita findById(@PathVariable Integer id){
        return citaServiceImplementation.findById(id);
    }

    // http://localhost:8080/api/citas/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        citaServiceImplementation.deleteById(id);
    }

    // http://localhost:8080/api/citas
    @PutMapping
    public Cita update(@RequestBody Cita cita){
        return citaServiceImplementation.update(cita);
    }

    // NUEVO: Crear cita usando DTO
    @PostMapping("/dto")
    public CitaResponseDTO createCitaWithDTO(@RequestBody CitaRequestDTO citaRequestDTO) {
        return citaServiceImplementation.createCitaFromDTO(citaRequestDTO);
    }

    // NUEVO: Obtener todas las citas como DTO
    @GetMapping("/dto")
    public List<CitaResponseDTO> findAllAsDTO() {
        return citaServiceImplementation.findAllAsDTO();
    }

    // NUEVO: Obtener cita por ID como DTO
    @GetMapping("/dto/{id}")
    public CitaResponseDTO findByIdAsDTO(@PathVariable Integer id) {
        return citaServiceImplementation.findByIdAsDTO(id);
    }

    // NUEVO: Actualizar cita usando DTO
    @PutMapping("/dto/{id}")
    public CitaResponseDTO updateCitaWithDTO(@PathVariable Integer id, @RequestBody CitaRequestDTO citaRequestDTO) {
        Cita citaActualizada = citaServiceImplementation.updateCitaFromDTO(id, citaRequestDTO);
        return citaServiceImplementation.convertirCitaAResponseDTO(citaActualizada);
    }

    // NUEVO: Obtener citas por paciente
    @GetMapping("/paciente/{pacienteId}")
    public List<CitaResponseDTO> getCitasByPaciente(@PathVariable Integer pacienteId) {
        return citaServiceImplementation.findCitasByPaciente(pacienteId);
    }
}