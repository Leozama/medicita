package com.medicita.service;

import com.medicita.entity.Medico;
import com.medicita.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN DE SERVICIO - LÓGICA DE NEGOCIO
 *
 * RESPONSABILIDAD:
 * - Implementar las reglas de negocio
 * - Coordinar operaciones entre diferentes componentes
 * - Manejar transacciones y validaciones complejas
 * - Transformar datos entre capas
 *
 * REGLAS:
 * - Contiene la lógica central de la aplicación
 * - Puede usar múltiples repositories
 * - Maneja excepciones de negocio
 * - NO debe contener lógica HTTP o de base de datos directa
 */

@Service
public class MedicoServiceImplementation implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImplementation(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico findById(Integer id) {
        return medicoRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        medicoRepository.deleteById(id);
    }

    @Override
    public Medico update(Medico medico) {
        return medicoRepository.save(medico);
    }
}
