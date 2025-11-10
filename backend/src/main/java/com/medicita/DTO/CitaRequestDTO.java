package com.medicita.DTO;

public class CitaRequestDTO {
    private Integer medicoId;
    private Integer pacienteId;
    private String horaAgendada;
    private String motivo;
    private String fecha;

    public CitaRequestDTO() {
    }

    public CitaRequestDTO(Integer medicoId, Integer pacienteId, String horaAgendada, String motivo, String fecha) {
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}