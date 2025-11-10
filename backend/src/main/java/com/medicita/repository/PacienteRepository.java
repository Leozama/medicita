package com.medicita.repository;

import com.medicita.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * REPOSITORY - CAPA DE ACCESO A DATOS
 *
 * RESPONSABILIDAD:
 * - Comunicación con la base de datos
 * - Operaciones CRUD (Create, Read, Update, Delete)
 * - Consultas personalizadas
 * - Abstracción de la tecnología de persistencia
 *
 * MAGIA DE SPRING DATA JPA:
 * - Los métodos se implementan AUTOMÁTICAMENTE
 * - No necesitas escribir SQL
 * - Los nombres de métodos generan queries
 *
 * REGLAS:
 * - Solo operaciones de base de datos
 * - No debe contener lógica de negocio
 * - Interface, NO clase
 */

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    @Query("SELECT p FROM Paciente p LEFT JOIN FETCH p.citas WHERE p.id = :id")
    Optional<Paciente> findByIdWithCitas(Integer id);
}
