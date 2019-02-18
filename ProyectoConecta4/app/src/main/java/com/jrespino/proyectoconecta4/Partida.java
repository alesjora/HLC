package com.jrespino.proyectoconecta4;

public class Partida {
    private int id;
    private String descripcion;
    private String nombreCreador;

    public Partida(int id, String descripcion, String nombreCreador) {
        setId(id);
        setDescripcion(descripcion);
        setNombreCreador(nombreCreador);
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
