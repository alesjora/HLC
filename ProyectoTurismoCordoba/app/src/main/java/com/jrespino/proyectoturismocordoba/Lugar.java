package com.jrespino.proyectoturismocordoba;

import java.io.Serializable;

public class Lugar implements Serializable {
    private String nombre;
    private int imagen;
    private String descripcionLarga;
    private String localizacion;
    private String horario;
    private String telefono;
    private String email;
    private String web;
    private double latidud;
    private double longitud;

    public Lugar(String nombre, int imagen, String descripcionLarga, String localizacion, String horario, String telefono, String email, String web, double latidud, double longitud) {
        setNombre(nombre);
        setImagen(imagen);
        setDescripcionLarga(descripcionLarga);
        setLocalizacion(localizacion);
        setHorario(horario);
        setTelefono(telefono);
        setEmail(email);
        setWeb(web);
        setLatidud(latidud);
        setLongitud(longitud);
    }

    public double getLatidud() {
        return latidud;
    }

    private void setLatidud(double latidud) {
        this.latidud = latidud;
    }

    public double getLongitud() {
        return longitud;
    }

    private void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    private void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getHorario() {
        return horario;
    }

    private void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTelefono() {
        return telefono;
    }

    private void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    private void setWeb(String web) {
        this.web = web;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    private void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    private void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
