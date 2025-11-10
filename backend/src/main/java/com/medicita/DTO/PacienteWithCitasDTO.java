package com.medicita.DTO;

import java.util.List;

public class PacienteWithCitasDTO {
    private int id;
    private String firstName;
    private String secondName;
    private String email;
    private String telefono;
    private String fechaNacimiento;
    private List<CitaInfoDTO> citas;

    // Constructores, Getters y Setters
    public PacienteWithCitasDTO() {}

    // Getters y Setters...
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
    public List<CitaInfoDTO> getCitas() { return citas; }
    public void setCitas(List<CitaInfoDTO> citas) { this.citas = citas; }
}