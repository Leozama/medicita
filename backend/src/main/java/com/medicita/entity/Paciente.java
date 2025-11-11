package com.medicita.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String secondName;
    private String email;
    private String telefono;
    private String fechaNacimiento;
    // AGREGAR ESTOS CAMPOS NUEVOS:
    private String userName;
    private String password;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // EVITA LA RECURSIÓN
    private List<Cita> citas = new ArrayList<>();

    public Paciente() {
    }

    public Paciente(int id, String firstName, String secondName, String email, String telefono, String fechaNacimiento, String userName, String password, List<Cita> citas) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.userName = userName;
        this.password = password;
        this.citas = citas;
    }

    // Getters y Setters (mantén los mismos)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ELIMINA el método toString() o déjalo simple
    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", firstName='" + firstName + '\'' + '}';
    }
}