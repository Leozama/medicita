package com.medicita.controller;

import com.medicita.DTO.CitaDTO;
import com.medicita.entity.Cita;
import com.medicita.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8080/api/citas
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController( CitaService citaService) {
        this.citaService = citaService;
    }

    // http://localhost:8080/api/citas
    @PostMapping
    public CitaDTO save(@RequestBody Cita cita){
        return citaService.saveDTO(cita);
    }

    // http://localhost:8080/api/citas
    @GetMapping
    public List<CitaDTO> findAll(){
        return citaService.findAllDTO();
    }

    // http://localhost:8080/api/citas/1
    @GetMapping("/{id}")
    public CitaDTO findById(@PathVariable Integer id){
        return citaService.findByIdDTO(id);
    }

    // http://localhost:8080/api/citas/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        citaService.deleteById(id);
    }

    // http://localhost:8080/api/citas
    @PutMapping
    public CitaDTO update(@RequestBody Cita cita){
        Cita citaDb = citaService.findById(cita.getId());
        citaDb.setPaciente(cita.getPaciente());
        citaDb.setHoraAgendada(cita.getHoraAgendada());
        citaDb.setMotivo(cita.getMotivo());
        citaDb.setFecha(cita.getFecha());
        return citaService.updateDTO(citaDb);
    }

    // http://localhost:8080/api/citas/paciente/1
    @GetMapping("/paciente/{pacienteId}")
    public List<CitaDTO> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return citaService.obtenerPorPacienteDTO(pacienteId);
    }

    // http://localhost:8080/api/citas/medico/1
    @GetMapping("/medico/{medicoId}")
    public List<CitaDTO> obtenerPorMedico(@PathVariable Integer medicoId) {
        return citaService.obtenerPorMedicoDTO(medicoId);
    }
}