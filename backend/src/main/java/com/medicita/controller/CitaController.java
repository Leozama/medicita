package com.medicita.controller;

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
}