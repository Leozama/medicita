package com.medicita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // RELACIÓN MUCHOS-A-UNO CON PACIENTE
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // RELACIÓN UNO-A-UNO CON CITA
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;
    private String fecha;
    private String monto;
    private String metodoPago;
    private String estado; // confirmar

    public Pago() {
    }

    public Pago(int id, Paciente paciente, Cita cita, String fecha, String monto, String metodoPago, String estado) {
        this.id = id;
        this.paciente = paciente;
        this.cita = cita;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
