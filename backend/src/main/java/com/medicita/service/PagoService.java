package com.medicita.service;

import com.medicita.entity.Pago;

import java.util.List;

public interface PagoService {
    Pago save(Pago pago);
    List<Pago> findAll();
    Pago findById(Integer id);
    void deleteById(Integer id);
    Pago update(Pago pago);
}
