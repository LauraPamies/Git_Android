package com.example.lpamvil.sprint0laura;

public class Usuario {
    String nombre,mail,usuario,contrasena;
    int telefono;

    public Usuario(String nombre, String mail, String usuario, String contrasena, int telefono) {
        this.nombre = nombre;
        this.mail = mail;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public Usuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        mail = mail;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        contrasena = contrasena;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
