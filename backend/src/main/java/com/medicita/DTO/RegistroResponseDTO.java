package com.medicita.DTO;

public class RegistroResponseDTO {
    private Integer userId;
    private String mensaje;

    public RegistroResponseDTO(Integer userId, String mensaje) {
        this.userId = userId;
        this.mensaje = mensaje;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
