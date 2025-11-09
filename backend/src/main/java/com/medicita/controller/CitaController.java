package com.medicita.controller;

import com.medicita.DTO.CitaRequestDTO;
import com.medicita.DTO.CitaResponseDTO;
import com.medicita.entity.Cita;
import com.medicita.service.CitaService;
import com.medicita.service.CitaServiceImplementation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;
    private final CitaServiceImplementation citaServiceImplementation;

    public CitaController(CitaService citaService, CitaServiceImplementation citaServiceImplementation) {
        this.citaService = citaService;
        this.citaServiceImplementation = citaServiceImplementation;
    }

    // http://localhost:8080/api/citas
    @PostMapping
    public Cita save(@RequestBody Cita cita){
        return citaService.save(cita);
    }

    // http://localhost:8080/api/citas
    @GetMapping
    public List<Cita> findAll(){
        return citaService.findAll();
    }

    // http://localhost:8080/api/citas/1
    @GetMapping("/{id}")
    public Cita findById(@PathVariable Integer id){
        return citaService.findById(id);
    }

    // http://localhost:8080/api/citas/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        citaService.deleteById(id);
    }

    // http://localhost:8080/api/citas
    @PutMapping
    public Cita update(@RequestBody Cita cita){
        Cita citaDb = citaService.findById(cita.getId());
        citaDb.setMedico(cita.getMedico());
        citaDb.setHoraAgendada(cita.getHoraAgendada());
        citaDb.setMotivo(cita.getMotivo());
        citaDb.setFecha(cita.getFecha());
        return citaService.update(citaDb);
    }

    // NUEVO: Crear cita usando DTO
    @PostMapping("/dto")
    public Cita createCitaWithDTO(@RequestBody CitaRequestDTO citaRequestDTO) {
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
    public Cita updateCitaWithDTO(@PathVariable Integer id, @RequestBody CitaRequestDTO citaRequestDTO) {
        return citaServiceImplementation.updateCitaFromDTO(id, citaRequestDTO);
    }
}