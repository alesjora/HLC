package com.jrespino.proyectoconecta4;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class JuegoOnline extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog alertDialog;
    private int idPartida;
    private int turnoJugador;
    private final String IP_SERVIDOR = "192.168.115.118";
    private TimerTask doThis;
    private Timer gameTimer;
    private int delay = 1000;   // delay for 5 sec.
    private int period = 1000;  // repeat every sec.
    private ImageButton imageButtonFicha;
    private final int ids[][] = {
            {R.id.f0c0, R.id.f0c1, R.id.f0c2, R.id.f0c3, R.id.f0c4, R.id.f0c5, R.id.f0c6},
            {R.id.f1c0, R.id.f1c1, R.id.f1c2, R.id.f1c3, R.id.f1c4, R.id.f1c5, R.id.f1c6},
            {R.id.f2c0, R.id.f2c1, R.id.f2c2, R.id.f2c3, R.id.f2c4, R.id.f2c5, R.id.f2c6},
            {R.id.f3c0, R.id.f3c1, R.id.f3c2, R.id.f3c3, R.id.f3c4, R.id.f3c5, R.id.f3c6},
            {R.id.f4c0, R.id.f4c1, R.id.f4c2, R.id.f4c3, R.id.f4c4, R.id.f4c5, R.id.f4c6},
            {R.id.f5c0, R.id.f5c1, R.id.f5c2, R.id.f5c3, R.id.f5c4, R.id.f5c5, R.id.f5c6},
    };
    private Game game;
    private String nombreJugador1;
    private String nombreJugador2;
    private TextView textViewJugadorActual;
    private Coordenada ultimaCoordenada = null;
    private boolean puedoPonerFicha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_online);
        textViewJugadorActual = findViewById(R.id.textViewNombreJugador);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idPartida = bundle.getInt("idPartida");
            turnoJugador = bundle.getInt("turnoJugador");
        }
        crearDialogoEspera();
        comprobarEstadoInicialPartida();
        gameTimer = new Timer();
        doThis = new TimerTask() {
            public void run() {
                comprobarEstadoInicialPartida();
            }
        };
        gameTimer.scheduleAtFixedRate(doThis, delay, period);

        game = new Game(Game.JUGADOR);

        for (int i = 0; i < Game.NFILAS; i++) {
            for (int j = 0; j < Game.NCOLUMNAS; j++) {
                imageButtonFicha = findViewById(ids[i][j]);
                imageButtonFicha.setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (!puedoPonerFicha || game.getEstado() == 'F')
            return;
        int id = view.getId();
        Coordenada coordenada = coorJuego(id);
        if (game.isVacio(coordenada)) {
            game.colocarFicha(coordenada);
            dibujarFicha(coordenada, game.getTurno());
            enviarFichaYTurno(coordenada);
            ultimaCoordenada = coordenada;
            if(comprobarGanador(coordenada)){
                game.setEstado('F');
                terminarPartida();
            }
        } else {
            Toast.makeText(this, "No caben más fichas en esta columna.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean comprobarGanador(Coordenada coordenada) {
        Log.d("comprobandoFinal","Entra 2");
        if (game.recorrerColumna(coordenada.getColumna()).contains(Game.MAQ_GANADOR)
                || game.recorrerFila(coordenada.getFila()).contains(Game.MAQ_GANADOR)
                || game.recorrerDiagonal1(coordenada).contains(Game.MAQ_GANADOR)
                || game.recorrerDiagonal2(coordenada).contains(Game.MAQ_GANADOR)) {
            Log.d("comprobandoFinal","Entra 3");
            if(turnoJugador == 1){
                setEstadoPartida(3);
                mostrarDialogoGanador("Has ganado!!");
            }
            else{
                setEstadoPartida(4);
                mostrarDialogoGanador("Has perdido!!");
            }
            return true;
        } else if (game.recorrerColumna(coordenada.getColumna()).contains(Game.JUG_GANADOR)
                || game.recorrerFila(coordenada.getFila()).contains(Game.JUG_GANADOR)
                || game.recorrerDiagonal1(coordenada).contains(Game.JUG_GANADOR)
                || game.recorrerDiagonal2(coordenada).contains(Game.JUG_GANADOR)) {
            Log.d("comprobandoFinal","Entra 4");
            if(turnoJugador == 2){
                setEstadoPartida(4);
                mostrarDialogoGanador("Has ganado!!");
            }
            else{
                setEstadoPartida(3);
                mostrarDialogoGanador("Has perdido!!");
            }
            return true;
        }
        Log.d("comprobandoFinal","Entra 5");
        return false;
    }

    private void setEstadoPartida(int estado) {
        String url = "http://" + IP_SERVIDOR + "/conecta4/gestion.php?setEstado=1&idPartida=" + idPartida + "&estado="+estado;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JuegoOnline.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void mostrarDialogoGanador(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setTitle("Partida finalizada");
        builder.setNegativeButton("Volver a la lista de partidas", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                onBackPressed();
            }
        });
        builder.create().show();
    }

    private void dibujarFicha(Coordenada coordenada, int turno) {
        int id = ids[coordenada.getFila()][coordenada.getColumna()];
        ImageButton imageButton = findViewById(id);
        if (turno == 1) {
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ficha_jugador));
        } else {
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ficha_maquina));
        }
    }

    private Coordenada coorJuego(int id) {
        int fila = 0;
        int columna = 0;
        for (int i = 0; i < Game.NFILAS; i++) {
            for (int j = 0; j < Game.NCOLUMNAS; j++) {
                if (ids[i][j] == id) {
                    columna = j;
                }
            }
        }
        fila = game.filSelect(columna);
        return new Coordenada(fila, columna);
    }

    private void crearDialogoEspera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Espera...");
        builder.setMessage("Espera hasta que el rival esté disponible...");
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void comprobarEstadoInicialPartida() {
        String url = "http://" + IP_SERVIDOR + "/conecta4/gestion.php?comprobarEstadoPartida=1&idPartida=" + idPartida;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Integer.valueOf(response) == 1) {
                            doThis.cancel();
                            gameTimer.cancel();
                            alertDialog.dismiss();
                            obtenerNombresJugadores();
                            empezarPartida();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JuegoOnline.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void obtenerNombresJugadores() {
        String url = "http://" + IP_SERVIDOR + "/conecta4/gestion.php?nombreJugadores=1&idPartida=" + idPartida;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject datos = response.getJSONObject(0);
                            nombreJugador1 = datos.getString("jugador1");
                            nombreJugador2 = datos.getString("jugador2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JuegoOnline.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void empezarPartida() {
        gameTimer = new Timer();
        doThis = new TimerTask() {
            public void run() {
                obtenerUltimaFichaPulsada();
                obtenerEstadoActual();
                obtenerTurnoActual();
                if(ultimaCoordenada != null){
                    Log.d("comprobandoFinal","Entra 1");
                    if(comprobarGanador(ultimaCoordenada))
                        terminarPartida();

                }
            }
        };
        gameTimer.scheduleAtFixedRate(doThis, delay, period);
    }

    private void obtenerEstadoActual() {
        String url = "http://" + IP_SERVIDOR + "/conecta4/gestion.php?getEstado=1&idPartida=" + idPartida;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int estado = Integer.valueOf(response);
                        if (estado == 3 && turnoJugador == 1) {
                            mostrarDialogoGanador("Has ganado!!");
                            terminarPartida();
                        } else if(estado == 3 && turnoJugador == 2){
                            mostrarDialogoGanador("Has perdido!!");
                            terminarPartida();
                        } else if(estado == 4 && turnoJugador == 1){
                            mostrarDialogoGanador("Has perdido!!");
                            terminarPartida();
                        } else if(estado == 4 && turnoJugador == 2){
                            mostrarDialogoGanador("Has ganado!!");
                            terminarPartida();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JuegoOnline.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void terminarPartida() {
        doThis.cancel();
        gameTimer.cancel();
        game.setEstado('F');
    }

    private void obtenerUltimaFichaPulsada() {
        String url = "http://"+ IP_SERVIDOR +"/conecta4/gestion.php?getUltimaCasillaPulsada=1&idPartida="+idPartida;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject datos = response.getJSONObject(0);
                            if(datos.length() != 0){
                                int jugador = datos.getInt("jugador");
                                int fila = datos.getInt("x");
                                int columna = datos.getInt("y");
                                //Log.d("fila","Fila: "+fila+" Columna: "+columna+" Jugador: "+jugador);
                                if(ultimaCoordenada == null){
                                    //Log.d("fila2","Fila: "+fila+" Columna: "+columna+" Jugador: "+jugador);
                                    ultimaCoordenada = new Coordenada(fila,columna);
                                    game.colocarFicha(ultimaCoordenada);
                                    dibujarFicha(ultimaCoordenada,jugador);
                                }

                                else if(ultimaCoordenada.getFila() != fila || ultimaCoordenada.getColumna() != columna){
                                    //Log.d("fila3","Fila: "+fila+" Columna: "+columna+" Jugador: "+jugador);
                                    ultimaCoordenada = new Coordenada(fila,columna);
                                    game.colocarFicha(ultimaCoordenada);
                                    dibujarFicha(ultimaCoordenada,jugador);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JuegoOnline.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void obtenerTurnoActual() {
        String url = "http://" + IP_SERVIDOR + "/conecta4/gestion.php?getTurno=1&idPartida=" + idPartida;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int turno = Integer.valueOf(response);
                        //Log.d("turno",response);
                        if (game.getTurno() != turno) {
                            //Log.d("turno2","entra");
                            game.setTurno(turno);
                            cambiarPonerFicha(turno);
                            cambiarNombreJugador(turno);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JuegoOnline.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void cambiarPonerFicha(int turno) {
        puedoPonerFicha = (turno == turnoJugador) ? true : false;
    }

    private void cambiarNombreJugador(int turno) {
        if(turno == 1)
            textViewJugadorActual.setText("Turno de "+nombreJugador1);
        else
            textViewJugadorActual.setText("Turno de "+nombreJugador2);

    }

    private void enviarFichaYTurno(Coordenada coordenada) {
        int turno = (game.getTurno() == 1)? 2 : 1;
        puedoPonerFicha = false;
        String url = "http://" + IP_SERVIDOR + "/conecta4/gestion.php?nuevoMovimiento=1&idPartida=" + idPartida + "&turno="+turno+"&fila="+coordenada.getFila()+"&columna="+coordenada.getColumna()+"&jugador="+game.getTurno();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JuegoOnline.this, "Error al enviar ficha", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

