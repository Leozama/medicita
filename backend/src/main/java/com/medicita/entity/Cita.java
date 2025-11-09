package com.medicita.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;


@Entity
@Table(name = "Citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String medico;
    private String horaAgendada;
    private String motivo;
    private String fecha;



    public Cita() {
    }

    public Cita(int id, String medico, String horaAgendada, String motivo, String fecha) {
        this.id = id;
        this.medico = medico;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
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
}
