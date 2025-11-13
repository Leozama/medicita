package com.medicita.repository;

import com.medicita.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query("SELECT c FROM Cita c JOIN FETCH c.medico JOIN FETCH c.paciente")
    List<Cita> findAllWithMedicoAndPaciente();

    @Query("SELECT c FROM Cita c JOIN FETCH c.medico JOIN FETCH c.paciente WHERE c.id = :id")
    Optional<Cita> findByIdWithMedicoAndPaciente(Integer id);

    @Query("SELECT c FROM Cita c JOIN FETCH c.medico WHERE c.paciente.id = :pacienteId")
    List<Cita> findByPacienteIdWithMedico(Integer pacienteId);

    @Query("SELECT c FROM Cita c WHERE c.medico.id = :medicoId AND c.fecha = :fecha AND c.horaAgendada = :hora AND (c.estado IS NULL OR c.estado <> 'CANCELADA')")
    java.util.Optional<Cita> findActiveByMedicoAndFechaAndHora(Integer medicoId, String fecha, String hora);
}