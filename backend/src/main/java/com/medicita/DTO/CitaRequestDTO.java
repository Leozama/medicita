package com.medicita.DTO;

import com.medicita.entity.Medico;
import jakarta.persistence.*;

public class CitaRequestDTO {

    public Medico medico;
    private String nombreMedico;
    private String horaAgendada;
    private String motivo;
    private String fecha;

    public CitaRequestDTO() {
    }

    public CitaRequestDTO(String nombreMedico, String horaAgendada, String motivo, String fecha) {
        this.nombreMedico = nombreMedico;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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

    public String getNombreMedico() {
        return nombreMedico;

    }

    public void setNombreMedico(String nombreMedico) {}
}
