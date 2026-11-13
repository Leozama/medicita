package com.medicita.controller;

import com.medicita.DTO.MedicoRequestDTO;
import com.medicita.DTO.MedicoResponseDTO;
import com.medicita.service.MedicoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.medicita.repository.MedicoRepository;
import com.medicita.entity.Medico;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping("api/medicos")
@CrossOrigin(origins = {"http://localhost:4200", "https://medicita-1.onrender.com"})
public class MedicoController {

    private final MedicoService medicoService;

    @Autowired
    private MedicoRepository medicoRepository;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    // http://localhost:8080/api/medicos
    @PostMapping
    public MedicoResponseDTO save(@RequestBody MedicoRequestDTO medicoRequestDTO) {
        return medicoService.save(medicoRequestDTO);
    }

    // http://localhost:8080/api/medicos
    @GetMapping
    public List<MedicoResponseDTO> findAll() {
        return medicoService.findAll();
    }

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    // VULNERABILIDAD SQL INJECTION: Endpoint de búsqueda vulnerable por GET con concatenación SQL
    @GetMapping("/buscar")
    public List<java.util.Map<String, Object>> buscarInseguro(@RequestParam("esp") String esp) {
        // Consulta SQL nativa concatenada directamente (Vulnerable a SQLi)
        String sql = "SELECT * FROM medicos WHERE especialidad = '" + esp + "'";
        return jdbcTemplate.queryForList(sql);
    }

    // http://localhost:8080/api/medicos/1
    @GetMapping("/{id}")
    public MedicoResponseDTO findById(@PathVariable Integer id) {
        return medicoService.findById(id);
    }

    // http://localhost:8080/api/medicos/1
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        medicoService.deleteById(id);
    }

    // http://localhost:8080/api/medicos/{id}
    @PutMapping("/{id}")
    public MedicoResponseDTO update(@PathVariable Integer id, @RequestBody MedicoRequestDTO medicoRequestDTO) {
        return medicoService.update(id, medicoRequestDTO);
    }
}