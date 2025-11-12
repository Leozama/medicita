package com.medicita.DTO;

public class CitaResponseDTO {
    private int id;
    private String nombreMedico;
    private String especialidad;
    private String nombrePaciente;
    private String emailPaciente;
    private String horaAgendada;
    private String motivo;
    private String estado;
    private String fecha;

    public CitaResponseDTO() {
    }

    public CitaResponseDTO(int id, String nombreMedico, String especialidad, String nombrePaciente, String emailPaciente, String horaAgendada, String motivo, String estado, String fecha) {
        this.id = id;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.nombrePaciente = nombrePaciente;
        this.emailPaciente = emailPaciente;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public String getHoraAgendada() {
        return horaAgendada;
    }

    public void setHoraAgendada(String horaAgendada) {
        this.horaAgendada = horaAgendada;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}