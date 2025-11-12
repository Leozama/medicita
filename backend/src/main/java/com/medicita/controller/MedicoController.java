package com.medicita.controller;

import com.medicita.entity.Medico;
import com.medicita.service.MedicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
//http://localhost:8080/api/medicos
@RequestMapping("api/medicos")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    //http://localhost:8080/api/medicos
    @PostMapping
    public Medico save(@RequestBody Medico medico){
        return  medicoService.save(medico);
    }

    //http://localhost:8080/api/medicos
    @GetMapping
    public List<Medico> findAll(){
        return medicoService.findAll();
    }

    //http://localhost:8080/api/medicos/1
    @GetMapping("/{id}")
    public Medico findById(@PathVariable Integer id){
        return medicoService.findById(id);
    }

    //http://localhost:8080/api/medicos/1
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        medicoService.deleteById(id);
    }

    //http://localhost:8080/api/medicos
    @PutMapping
    public Medico update(@RequestBody Medico medico){
        Medico medicoDb = medicoService.findById(medico.getId());
        medicoDb.setFirstName(medico.getFirstName());
        medicoDb.setSecondName(medico.getSecondName());
        medicoDb.setEspecialidad(medico.getEspecialidad());
        medicoDb.setHorario(medico.getHorario());
        return medicoService.update(medicoDb);
    }
}