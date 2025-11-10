package com.medicita.repository;

import com.medicita.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago,Integer> {
    // NUEVO: Buscar pagos por paciente
    List<Pago> findByPacienteId(Integer pacienteId);
}
