package com.jrespino.proyectoconecta4;

import android.util.Log;

import java.util.Random;
import java.util.regex.Pattern;

public class Game {
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int VACIO = 0;
    static final int MAQUINA = 1;
    static final int JUGADOR = 2;
    static final String MAQ_GANADOR = "1111";
    static final String JUG_GANADOR = "2222";
    static final String PATRONGANADOR_A = "222";
    private int turno;
    private char estado;
    public int tablero[][];

    public Game(int jugador){
        tablero = new int[NFILAS][NCOLUMNAS];
        for (int i = 0; i< NFILAS; i++){
            for (int j = 0; j < NCOLUMNAS; j++){
                tablero[i][j] = VACIO;
            }
        }
        turno = jugador;
    }

    public int getTurno() {
        return turno;
    }

    public static int getNFILAS() {
        return NFILAS;
    }

    public static int getNCOLUMNAS() {
        return NCOLUMNAS;
    }

    public static int getVACIO() {
        return VACIO;
    }

    public static int getMAQUINA() {
        return MAQUINA;
    }

    public static int getJUGADOR() {
        return JUGADOR;
    }

    public static String getMaqGanador() {
        return MAQ_GANADOR;
    }

    public static String getJugGanador() {
        return JUG_GANADOR;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public boolean colocarFicha(Coordenada coordenada){
        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();
        if(tablero[fila][columna] == VACIO){
            tablero[fila][columna] = turno;
            return true;
        }
        return false;
    }

    public void cambiarTurno() {
        if(turno == JUGADOR)
            setTurno(MAQUINA);
        else
            setTurno(JUGADOR);
    }

    public int filSelect(int columna) {
        int i = NFILAS - 1;
        int fil = -1;
        boolean lsel = false;
        while (i >= 0 && !lsel){
            if(tablero[i][columna] == VACIO){
                fil = i;
                lsel = true;
            }
            i--;
        }
        return fil;
    }

    public String recorrerFila(int fila){
        String cadena = "";
        for(int i = 0; i < NCOLUMNAS; i++){
            cadena += tablero[fila][i];
        }
        return cadena;
    }

    public String recorrerColumna(int columna){
        String cadena = "";
        for(int i = 0; i < NFILAS; i++){
            cadena += tablero[i][columna];
        }
        return cadena;
    }

    public String recorrerDiagonal1(Coordenada coordenada){
        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();
        String cadena = "";
        for (int i = fila, j= columna; i < NFILAS && j < NCOLUMNAS; i++, j++)
            cadena += tablero[i][j];
        for (int i = fila-1, j= columna - 1; j >= 0 && i >=0; i--, j--)
            cadena = tablero[i][j] + cadena;

        return cadena;
    }
    public String recorrerDiagonal2(Coordenada coordenada){
        String cadena = "";
        int f = 0;
        int c =0;
        for (f = 0;f<NFILAS;f++){
            for (c =0;c<NCOLUMNAS;c++){
                if(f+c ==coordenada.getFila() + coordenada.getColumna())
                    cadena += Integer.toString(tablero[f][c]);
            }
        }
        return cadena;
    }

    public int maquinaRespondeMovimientoA(int columna) {
        String fila;
        int col;
        for (int i = 0; i < NFILAS; i++) {
            fila = "";
            for (int j = 0; j < NCOLUMNAS; j++) {
                fila += tablero[i][j];
                col = j;

                if (fila.contains(PATRONGANADOR_A) && col != (NCOLUMNAS - 1) && tablero[i][col + 1] == VACIO)
                    return col + 1;
                if (fila.contains(PATRONGANADOR_A) && (col - 3) >= 0 && tablero[i][col - 3] == VACIO)
                    return col - 3;

            }
        }
        return columna;
    }

    public boolean isVacio(Coordenada coordenada){
        if(coordenada.getFila() == -1)
            return false;
        return tablero[coordenada.getFila()][coordenada.getColumna()] == VACIO;
    }

    public String tableroToString(){
        String str = "";
        for (int i = 0; i<NFILAS; i++){
            for (int j = 0; j<NCOLUMNAS; j++)
                str += tablero[i][j];
        }
        return str;
    }

    public void stringToTablero(String str){
        int contador = 0;
        for(int i = 0; i<NFILAS;i++){
            for (int j = 0; j < NCOLUMNAS; j++) {
                tablero[i][j] = Integer.parseInt(String.valueOf(str.charAt(contador)));
                contador++;
            }
        }
    }

    public int columnaAleatoria(){
        String fila;
        String cadenaColumnas = "";
        for (int i = 0; i < NCOLUMNAS; i++) {
            fila = "";
            for (int j = 0; j < NFILAS; j++) {
                fila += tablero[j][i];
            }
            if (fila.contains("0"))
                cadenaColumnas += i;
        }
        Log.d("Columnas disponibles",cadenaColumnas);
        long posicion = Math.round(Math.random() * ((cadenaColumnas.length()-1) - 0));
        return Integer.parseInt(String.valueOf(cadenaColumnas.charAt((int) posicion)));
    }
}
