package com.medicita.service;

import com.medicita.entity.Pago;
import com.medicita.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PagoServiceImplementation implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImplementation(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public Pago save(Pago pago) {
        return null;
    }

    @Override
    public List<Pago> findAll() {
        return List.of();
    }

    @Override
    public Pago findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Pago update(Pago pago) {
        return null;
    }
}
