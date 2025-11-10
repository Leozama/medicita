package com.medicita.controller;

import com.medicita.entity.Paciente;
import com.medicita.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLADOR REST - CAPA DE PRESENTACIÓN
 *
 * RESPONSABILIDAD:
 * - Manejar las peticiones HTTP (GET, POST, PUT, DELETE)
 * - Convertir JSON ↔ Objetos Java
 * - Devolver respuestas HTTP apropiadas
 * - Validar datos de entrada
 *
 * REGLAS:
 * - Solo debe contener lógica relacionada con HTTP
 * - NO debe contener lógica de negocio
 * - Delega toda la lógica al Service
 *
 * URL BASE: http://localhost:8080/api/pacientes
 */

@RestController
//http://localhost:8080/api/pacientes
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //http://localhost:8080/api/pacientes
    @PostMapping
    public Paciente save(@RequestBody Paciente paciente){
        return pacienteService.save(paciente);
    }

    //http://localhost:8080/api/pacientes
    @GetMapping
    public List<Paciente> findAll(){
        return pacienteService.findAll();
    }

    //http://localhost:8080/api/pacientes/1
    @GetMapping("/{id}")
    public Paciente findById(@PathVariable Integer id){
        return pacienteService.findById(id);
    }

    //http://localhost:8080/api/pacientes/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        pacienteService.deleteById(id);
    }

    //http://localhost:8080/api/pacientes
    @PutMapping
    public Paciente update(@RequestBody Paciente paciente){
        Paciente pacienteDb = pacienteService.findById(paciente.getId());
        pacienteDb.setFirstName(paciente.getFirstName());
        pacienteDb.setSecondName(paciente.getSecondName());
        pacienteDb.setEmail(paciente.getEmail());
        pacienteDb.setTelefono(paciente.getTelefono());
        pacienteDb.setFechaNacimiento(paciente.getFechaNacimiento());
        return pacienteService.update(pacienteDb);
    }
}