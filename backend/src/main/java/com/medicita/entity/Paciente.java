package com.medicita.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

/**
 * ENTIDAD - MODELO DE DATOS
 *
 * RESPONSABILIDAD:
 * - Representar la tabla de la base de datos
 * - Definir la estructura de los datos
 * - Contener validaciones de datos
 * - Mapear objetos Java ↔ tablas SQL
 *
 * REGLAS:
 * - Solo datos, NO lógica de negocio compleja
 * - Anotaciones JPA para mapeo
 * - Anotaciones de validación
 * - Getters/Setters obligatorios para JPA
 */

@Entity
@Table(name = "Paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String secondName;
    private int age;
    private int CI;

    public Paciente() {
    }

    public Paciente(int id, String firstName, String secondName, int age, int CI) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.CI = CI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCI() {
        return CI;
    }

    public void setCI(int CI) {
        this.CI = CI;
    }


}
