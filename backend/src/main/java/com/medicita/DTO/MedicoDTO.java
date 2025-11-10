package com.medicita.DTO;

public class MedicoDTO {
    private Integer id;
    private String firstName;
    private String secondName;
    private String especialidad;
    private String horario;

    // Constructor vacío
    public MedicoDTO() {}

    // Constructor con parámetros
    public MedicoDTO(Integer id, String firstName, String secondName, String especialidad, String horario) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.especialidad = especialidad;
        this.horario = horario;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
