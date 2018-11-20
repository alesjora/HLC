package com.jrespino.proyectoconecta4;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Game game;
    private ImageButton imageButtonFicha;
    private final int ids[][] = {
            {R.id.f0c0,R.id.f0c1,R.id.f0c2,R.id.f0c3,R.id.f0c4,R.id.f0c5,R.id.f0c6},
            {R.id.f1c0,R.id.f1c1,R.id.f1c2,R.id.f1c3,R.id.f1c4,R.id.f1c5,R.id.f1c6},
            {R.id.f2c0,R.id.f2c1,R.id.f2c2,R.id.f2c3,R.id.f2c4,R.id.f2c5,R.id.f2c6},
            {R.id.f3c0,R.id.f3c1,R.id.f3c2,R.id.f3c3,R.id.f3c4,R.id.f3c5,R.id.f3c6},
            {R.id.f4c0,R.id.f4c1,R.id.f4c2,R.id.f4c3,R.id.f4c4,R.id.f4c5,R.id.f4c6},
            {R.id.f5c0,R.id.f5c1,R.id.f5c2,R.id.f5c3,R.id.f5c4,R.id.f5c5,R.id.f5c6},
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(Game.JUGADOR);

        for(int i = 0; i< Game.NFILAS;  i++){
            for (int j = 0; j < Game.NCOLUMNAS;  j++){
                imageButtonFicha  = findViewById(ids[i][j]);
                imageButtonFicha.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View view) {
        if(game.getEstado() != 'F') {
            int id = view.getId();
            Coordenada coordenada;
            ImageButton imageButton = (ImageButton) view;
            coordenada = coorJuego(id);
            if (game.isVacio(coordenada)) {
                game.colocarFicha(coordenada);
                dibujarFicha(coordenada, game.getTurno());
                if (comprobarGanador(coordenada))
                    game.setEstado('F');
                game.cambiarTurno();
//                if(game.getTurno() == Game.MAQUINA){
//                    //game.maquinaRespondeMovimientoA(5);
//                        game.colocarFicha(coordenada);
//                        dibujarFicha(coordenada, game.getTurno());
//                        if (comprobarGanador(coordenada))
//                            game.setEstado('F');
//                        game.cambiarTurno();
//                    }
//                }
            } else {
                Toast.makeText(this, "No caben más fichas en esta columna.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "La partida ya ha finalizado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deshabilitarBotones() {
        for(int i = 0; i< Game.NFILAS;  i++){
            for (int j = 0; j < Game.NCOLUMNAS;  j++){
                imageButtonFicha  = findViewById(ids[i][j]);
                imageButtonFicha.setEnabled(false);
            }
        }
    }

    private boolean comprobarGanador(Coordenada coordenada) {
        if(game.recorrerColumna(coordenada.getColumna()).contains(Game.MAQ_GANADOR)
                || game.recorrerFila(coordenada.getFila()).contains(Game.MAQ_GANADOR)
                || game.recorrerDiagonal1(coordenada).contains(Game.MAQ_GANADOR)
                || game.recorrerDiagonal2(coordenada).contains(Game.MAQ_GANADOR)){
            Toast.makeText(this, "Ha ganado la máquina", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(game.recorrerColumna(coordenada.getColumna()).contains(Game.JUG_GANADOR)
                || game.recorrerFila(coordenada.getFila()).contains(Game.JUG_GANADOR)
                || game.recorrerDiagonal1(coordenada).contains(Game.JUG_GANADOR)
                || game.recorrerDiagonal2(coordenada).contains(Game.JUG_GANADOR)){
            Toast.makeText(this, "Ha ganado el jugador", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void dibujarFicha(Coordenada coordenada, int turno) {
        int id = ids[coordenada.getFila()][coordenada.getColumna()];
        ImageButton imageButton = findViewById(id);
        if(turno == Game.JUGADOR){
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ficha_jugador));
        } else {
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ficha_maquina));
        }
    }

    private Coordenada coorJuego(int id) {
        int fila = 0;
        int columna = 0;
        for (int i = 0; i < Game.NFILAS; i++){
            for (int j = 0; j < Game.NCOLUMNAS; j++){
                if(ids[i][j] == id){
                    columna = j;
                }
            }
        }
        fila = game.filSelect(columna);
        return new Coordenada(fila,columna);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Music.play(this,getResources().getIdentifier("laserpack","raw",getPackageName()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Music.stop(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("TABLERO",game.tableroToString());
        outState.putInt("TURNO",game.getTurno());
        outState.putChar("ESTADO",game.getEstado());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        game.stringToTablero(savedInstanceState.getString("TABLERO"));
        game.setEstado(savedInstanceState.getChar("ESTADO"));
        game.setTurno(savedInstanceState.getInt("TURNO"));
        dibujarTablero();
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void dibujarTablero() {
        ImageButton imageButton;
        for(int i = 0, cont = 0; i<Game.NFILAS;i++){
            for (int j = 0; j < Game.NCOLUMNAS; j++) {
                int id = ids[i][j];
                imageButton = findViewById(id);
                if(game.tablero[i][j] == Game.JUGADOR)
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ficha_jugador));
                else if(game.tablero[i][j] == Game.MAQUINA)
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ficha_maquina));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_acercaDe:
                mostrarDialogoAcercaDe();
                return true;
            case R.id.option_mensaje:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Juega al conecta 4 máquina.");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Compartir"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoAcercaDe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de");
        builder.setMessage("Aplicación creada por José Rafael Álvarez Espino. Curso 2018/2019");
        builder.setPositiveButton("Cerrar",null);
        builder.create().show();
    }
}
