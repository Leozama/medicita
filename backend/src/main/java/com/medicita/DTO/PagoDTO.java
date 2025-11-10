package com.medicita.DTO;

public class PagoDTO {
    private Integer id;
    private Integer pacienteId;
    private String pacienteNombre;
    private Integer citaId;
    private String citaMotivo;
    private String monto;
    private String metodoPago;
    private String estado;

    // Constructor vacío
    public PagoDTO() {}

    // Constructor con parámetros
    public PagoDTO(Integer id, Integer pacienteId, String pacienteNombre, Integer citaId,
                   String citaMotivo, String monto, String metodoPago, String estado) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.pacienteNombre = pacienteNombre;
        this.citaId = citaId;
        this.citaMotivo = citaMotivo;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getPacienteId() { return pacienteId; }
    public void setPacienteId(Integer pacienteId) { this.pacienteId = pacienteId; }

    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }

    public Integer getCitaId() { return citaId; }
    public void setCitaId(Integer citaId) { this.citaId = citaId; }

    public String getCitaMotivo() { return citaMotivo; }
    public void setCitaMotivo(String citaMotivo) { this.citaMotivo = citaMotivo; }

    public String getMonto() { return monto; }
    public void setMonto(String monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
