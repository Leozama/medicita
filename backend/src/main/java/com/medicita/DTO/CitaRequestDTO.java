package com.medicita.DTO;

import com.medicita.entity.Medico;
import jakarta.persistence.*;

public class CitaRequestDTO {

    public Integer medicoId;
    private String horaAgendada;
    private String motivo;
    private String fecha;

    public CitaRequestDTO() {
    }

    public CitaRequestDTO(Integer medicoId, String horaAgendada, String motivo, String fecha) {
        this.medicoId = medicoId;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
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
