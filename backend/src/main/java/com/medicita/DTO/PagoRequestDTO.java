package com.medicita.DTO;

public class PagoRequestDTO {
    private Integer citaId;

    public PagoRequestDTO() {
    }

    public PagoRequestDTO(Integer citaId) {
        this.citaId = citaId;
    }

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
    }
}