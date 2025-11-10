package com.medicita.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;


@Entity
@Table(name = "Citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    private String horaAgendada;
    private String motivo;
    private String fecha;

    // NUEVA RELACIÓN: UNA CITA PUEDE TENER UN PAGO
    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pago pago;


    public Cita() {
    }

    public Cita(int id, Paciente paciente, Medico medico, String horaAgendada, String motivo, String fecha, Pago pago) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.horaAgendada = horaAgendada;
        this.motivo = motivo;
        this.fecha = fecha;
        this.pago = pago;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
