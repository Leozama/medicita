package com.medicita.DTO;

public class CitaDTO {

    private Integer id;
    private PacienteDTO paciente;
    private MedicoDTO medico;
    private String horaAgendada;
    private String motivo;
    private String fecha;

    // Constructor vacío
    public CitaDTO() {}

    // Constructor con parámetros
    public CitaDTO(Integer id, PacienteDTO paciente, MedicoDTO medico, String horaAgendada, String motivo, String fecha) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
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

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

}
