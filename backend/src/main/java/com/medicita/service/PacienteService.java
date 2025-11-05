package com.medicita.service;

import com.medicita.entity.Paciente;

import java.util.List;

public interface PacienteService {
    Paciente save(Paciente paciente);
    List<Paciente> findAll();
    Paciente findById(Integer id);
    void deleteById(Integer id);
    Paciente update(Paciente paciente);
}
