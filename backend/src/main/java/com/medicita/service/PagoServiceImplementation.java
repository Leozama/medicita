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
        return  pagoRepository.save(pago);
    }

    @Override
    public List<Pago> findAll() {
        return  pagoRepository.findAll();
    }

    @Override
    public Pago findById(Integer id) {
        return  pagoRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        pagoRepository.deleteById(id);

    }

    @Override
    public Pago update(Pago pago) {
        return   pagoRepository.save(pago);
    }
}
