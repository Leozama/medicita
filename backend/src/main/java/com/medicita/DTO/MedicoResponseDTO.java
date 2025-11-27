package com.medicita.DTO;

public class MedicoResponseDTO {
    private int id;
    private String firstName;
    private String secondName;
    private String nombreCompleto;
    private String especialidad;
    private String horario;
    private Double costoConsulta;

    public MedicoResponseDTO() {
    }

    public MedicoResponseDTO(int id, String firstName, String secondName, String nombreCompleto, String especialidad,
            String horario, Double costoConsulta) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.horario = horario;
        this.costoConsulta = costoConsulta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(Double costoConsulta) {
        this.costoConsulta = costoConsulta;
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
}
