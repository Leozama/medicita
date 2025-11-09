package com.medicita.service;

import com.medicita.entity.Cita;
import com.medicita.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImplementation implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImplementation(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> findAll() {
        return  citaRepository.findAll();
    }

    @Override
    public Cita findById(Integer id) {
        return  citaRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        citaRepository.deleteById(id);

    }

    @Override
    public Cita update(Cita cita) {
        return  citaRepository.save(cita);
    }
}
