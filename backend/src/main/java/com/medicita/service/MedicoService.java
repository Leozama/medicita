package com.medicita.service;

import com.medicita.entity.Medico;

import java.util.List;



public interface MedicoService {
    Medico save(Medico medico);
    List<Medico> findAll();
    Medico findById(Integer id);
    void deleteById(Integer id);
    Medico update(Medico medico);
}