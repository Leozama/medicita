package com.medicita.repository;

import com.medicita.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    // VULNERABILIDAD SQL Injection: Permitir inyección a través de parámetro concatenado usando concatenación de Java nativa
    @org.springframework.data.jpa.repository.Query(value = "SELECT * FROM medicos WHERE especialidad = '"+""+"'", nativeQuery = true)
    List<Medico> customQuery();
}
