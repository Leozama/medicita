package com.medicita.repository;

import com.medicita.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    @Query("SELECT p FROM Pago p JOIN FETCH p.cita c JOIN FETCH c.medico JOIN FETCH p.paciente WHERE p.paciente.id = :pacienteId")
    List<Pago> findByPacienteIdWithDetails(Integer pacienteId);

    @Query("SELECT p FROM Pago p JOIN FETCH p.cita c JOIN FETCH c.medico JOIN FETCH p.paciente WHERE p.id = :id")
    Optional<Pago> findByIdWithDetails(Integer id);

    @Query("SELECT p FROM Pago p JOIN FETCH p.cita c JOIN FETCH c.medico JOIN FETCH p.paciente")
    List<Pago> findAllWithDetails();
}