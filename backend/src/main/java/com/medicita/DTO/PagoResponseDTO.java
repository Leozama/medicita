package com.medicita.DTO;

public class PagoResponseDTO {
    private int pagoId;
    private int citaId;
    private int pacienteId;
    private String nombrePaciente;
    private String nombreMedico;
    private String especialidad;
    private String fechaCita;
    private Double monto;
    private String estado;

    public PagoResponseDTO() {
    }

    public PagoResponseDTO(int pagoId, int citaId, int pacienteId, String nombrePaciente,
                           String nombreMedico, String especialidad, String fechaCita,
                           Double monto, String estado) {
        this.pagoId = pagoId;
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.nombrePaciente = nombrePaciente;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.fechaCita = fechaCita;
        this.monto = monto;
        this.estado = estado;
    }

    // Getters y Setters
    public int getPagoId() {
        return pagoId;
    }

    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    public int getCitaId() {
        return citaId;
    }

    public void setCitaId(int citaId) {
        this.citaId = citaId;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
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

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}