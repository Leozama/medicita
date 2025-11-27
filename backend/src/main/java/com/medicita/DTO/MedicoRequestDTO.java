package com.medicita.DTO;

public class MedicoRequestDTO {
    private String firstName;
    private String secondName;
    private String especialidad;
    private String horario;
    private Double costoConsulta;

    public MedicoRequestDTO() {
    }

    public MedicoRequestDTO(String firstName, String secondName, String especialidad, String horario,
            Double costoConsulta) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.especialidad = especialidad;
        this.horario = horario;
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
}
