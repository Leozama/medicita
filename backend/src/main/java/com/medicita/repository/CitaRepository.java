package com.medicita.repository;

import com.medicita.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query("SELECT c FROM Cita c JOIN FETCH c.medico")
    List<Cita> findAllWithMedico();

    @Query("SELECT c FROM Cita c JOIN FETCH c.medico WHERE c.id = :id")
    Optional<Cita> findByIdWithMedico(Integer id);
}