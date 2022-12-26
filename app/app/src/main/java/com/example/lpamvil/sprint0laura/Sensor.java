package com.example.lpamvil.sprint0laura;

public class Sensor {
    private String tipo;
    private String nombre;
    private String idSensor;
    private String idUsuario;

    public Sensor(String tipo, String nombre, String idSensor, String idUsuario) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.idSensor = idSensor;
        this.idUsuario = idUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(String idSensor) {
        this.idSensor = idSensor;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
