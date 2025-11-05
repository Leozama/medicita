package com.medicita.service;

import com.medicita.entity.Paciente;
import com.medicita.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImplementation implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImplementation(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente findById(Integer id) {
        return pacienteRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Paciente update(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
}
