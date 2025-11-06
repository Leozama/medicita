package com.medicita.service;

import com.medicita.entity.Paciente;

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

public interface PacienteService {
    Paciente save(Paciente paciente);
    List<Paciente> findAll();
    Paciente findById(Integer id);
    void deleteById(Integer id);
    Paciente update(Paciente paciente);
}
