package com.medicita.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;


@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    private String horaAgendada;
    private String motivo;
    private String fecha;


    public Cita() {
    }

    public Cita(int id, Medico medico, String horaAgendada, String motivo, String fecha) {
        this.id = id;
        this.medico = medico;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public String getNombreMedicoCompleto() {
        if (medico != null) {
            return medico.getFirstName() + " " + medico.getSecondName();
        }
        return null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}