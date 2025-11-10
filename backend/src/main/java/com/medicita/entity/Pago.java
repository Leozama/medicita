package com.medicita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    private String fechaCita;
    private Double monto;
    private String estado; // "PENDIENTE", "PAGADO", "CANCELADO"

    public Pago() {
    }

    public Pago(int id, Cita cita, Paciente paciente, String fechaCita, Double monto, String estado) {
        this.id = id;
        this.cita = cita;
        this.paciente = paciente;
        this.fechaCita = fechaCita;
        this.monto = monto;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public String getFechaCita() { return fechaCita; }
    public void setFechaCita(String fechaCita) { this.fechaCita = fechaCita; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}