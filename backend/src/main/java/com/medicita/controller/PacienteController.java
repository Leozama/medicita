package com.medicita.controller;

import com.medicita.DTO.PacienteRequestDTO;
import com.medicita.DTO.PacienteResponseDTO;
import com.medicita.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "http://localhost:4200")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // http://localhost:8080/api/pacientes
    @PostMapping
    public PacienteResponseDTO save(@RequestBody PacienteRequestDTO pacienteRequestDTO) {
        return pacienteService.save(pacienteRequestDTO);
    }

    // http://localhost:8080/api/pacientes
    @GetMapping
    public List<PacienteResponseDTO> findAll() {
        return pacienteService.findAll();
    }

    // http://localhost:8080/api/pacientes/1
    @GetMapping("/{id}")
    public PacienteResponseDTO findById(@PathVariable Integer id) {
        return pacienteService.findById(id);
    }

    // http://localhost:8080/api/pacientes/1
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        pacienteService.deleteById(id);
    }

    // http://localhost:8080/api/pacientes/{id}
    @PutMapping("/{id}")
    public PacienteResponseDTO update(@PathVariable Integer id, @RequestBody PacienteRequestDTO pacienteRequestDTO) {
        return pacienteService.update(id, pacienteRequestDTO);
    }
}