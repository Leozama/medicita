package com.medicita.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonIgnore // EVITA LA RECURSIÓN
    private Paciente paciente;

    private String horaAgendada;
    private String motivo;
    private String fecha;

    public Cita() {
    }

    public Cita(int id, Medico medico, Paciente paciente, String horaAgendada, String motivo, String fecha) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public String getHoraAgendada() { return horaAgendada; }
    public void setHoraAgendada(String horaAgendada) { this.horaAgendada = horaAgendada; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // ELIMINA el método toString() o déjalo simple
    @Override
    public String toString() {
        return "Cita{" + "id=" + id + ", motivo='" + motivo + '\'' + '}';
    }
}