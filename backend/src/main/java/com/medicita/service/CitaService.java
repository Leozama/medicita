package com.medicita.service;

import com.medicita.entity.Cita;

import java.util.List;

/**
 * INTERFACE DE SERVICIO - CONTRATO DE LÓGICA DE NEGOCIO
 *
 * RESPONSABILIDAD:
 * - Definir los métodos de lógica de negocio disponibles
 * - Servir como contrato entre Controller y ServiceImpl
 * - Centralizar reglas de negocio
 *
 * REGLAS:
 * - Define QUÉ se puede hacer (no CÓMO)
 * - Nombres de métodos descriptivos del negocio
 * - No depende de tecnologías específicas
 */

public interface CitaService {
    Cita save(Cita cita);
    List<Cita> findAll();
    Cita findById(Integer id);
    void deleteById(Integer id);
    Cita update(Cita cita);
}