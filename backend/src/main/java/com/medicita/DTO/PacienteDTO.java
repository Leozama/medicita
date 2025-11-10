package com.medicita.DTO;

public class PacienteDTO {
    private Integer id;
    private String firstName;
    private String secondName;
    private int age;
    private int ci;

    // Constructor vacío
    public PacienteDTO() {}

    // Constructor con parámetros
    public PacienteDTO(Integer id, String firstName, String secondName, int age, int ci) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.ci = ci;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }
}
