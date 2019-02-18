package com.jrespino.proyectoconecta4;

public class Coordenada {
    private int fila;
    private int columna;

    public Coordenada(int fila, int columna) {
        setFila(fila);
        setColumna(columna);
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
