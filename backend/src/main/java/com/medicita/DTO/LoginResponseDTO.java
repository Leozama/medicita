package com.medicita.DTO;

public class LoginResponseDTO {
    private Integer userId;
    private String nombreCompleto;
    private String rol;
    private String mensaje;

    public LoginResponseDTO(Integer userId, String nombreCompleto, String rol, String mensaje) {
        this.userId = userId;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.mensaje = mensaje;
    }

    // GETTERS
    public Integer getUserId() {
        return userId;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public String getMensaje() {
        return mensaje;
    }

    // SETTERS (Optional but good practice for DTOs)
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
