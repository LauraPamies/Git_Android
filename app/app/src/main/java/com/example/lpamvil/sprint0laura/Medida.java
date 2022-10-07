package com.example.lpamvil.sprint0laura;

public class Medida {

    //Atributos de la clase
    private int valor;
    private String fecha;
    private long latitud;
    private long longitud;


    //Getters y Setters de los atributos
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    //Constructor vacío
    public Medida() {
    }


    //Constructor con los atributos
    public Medida(int valor, String fecha, long latitud, long longitud) {
        this.valor = valor;
        this.fecha = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
