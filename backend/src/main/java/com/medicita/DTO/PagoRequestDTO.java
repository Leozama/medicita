package com.medicita.DTO;

public class PagoRequestDTO {
    private Integer citaId;
    private String estado;

    public PagoRequestDTO() {
    }

    public PagoRequestDTO(Integer citaId) {
        this.citaId = citaId;
    }

    public PagoRequestDTO(Integer citaId, String estado) {
        this.citaId = citaId;
        this.estado = estado;
    }

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}