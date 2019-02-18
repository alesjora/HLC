package com.jrespino.proyectoconecta4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_games_online extends AppCompatActivity {
    private ListView myListView;
    private ArrayList myArrayList = new ArrayList();
    private Adapter myAdapter;
    private FloatingActionButton fab;
    private SharedPreferences prefs;
    private final String IP_SERVIDOR = "192.168.115.118";
    private String nombreJugador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_online);
        myListView = findViewById(R.id.myListView);
        fab = findViewById(R.id.fabNuevaPartida);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearDialogoNuevaPartida();
            }
        });
        obtenerListaPartida();
        myAdapter = new Adapter(myArrayList,R.layout.list_item,this);
        myListView.setAdapter(myAdapter);
        prefs = getSharedPreferences("preferenciasUsuario", Context.MODE_PRIVATE);
        nombreJugador = prefs.getString("nick",null);
//        Timer gameTimer = new Timer();
//        TimerTask doThis;
//        int delay = 3000;   // delay for 5 sec.
//        int period = 3000;  // repeat every sec.
//        doThis = new TimerTask() {
//            public void run() {
//                //myArrayList.clear();
//                obtenerListaPartida();
//                //myAdapter.notifyDataSetChanged();
//            }
//        };
//        gameTimer.scheduleAtFixedRate(doThis, delay, period);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter.notifyDataSetChanged();
//            }
//        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Partida partida = (Partida) myAdapter.getItem(i);
                cambiarEstadoPartida(partida.getId());
                //empezarPartida(,2);
            }
        });
    }

    private void cambiarEstadoPartida(final int idPartida) {
        RequestQueue queue = MySingleton.getInstance(this).getRequestQueue();
        String url = "http://"+IP_SERVIDOR+"/conecta4/gestion.php?empezarPartida=1&idPartida="+idPartida+"&jugador2="+nombreJugador;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        empezarPartida(idPartida,2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_games_online.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void crearDialogoNuevaPartida() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear partida");
        builder.setMessage("Introduce una descripción para la partida");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog,null);
        final EditText editTextValor = view.findViewById(R.id.editTextValor);
        editTextValor.setHint("Descripción");
        builder.setView(view);
        builder.setPositiveButton("Crear partida", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                crearPartida(editTextValor.getText().toString().replace(" ","+"), nombreJugador);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void crearPartida(String descripcion, String jugador1) {
        RequestQueue queue = MySingleton.getInstance(this).getRequestQueue();
        String url = "http://"+IP_SERVIDOR+"/conecta4/gestion.php?nuevaPartida=1&descripcion="+descripcion+"&jugador1="+jugador1;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        empezarPartida(Integer.parseInt(response),1);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_games_online.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void empezarPartida(int idPartida, int turnoJugador) {
        Intent intent = new Intent(Activity_games_online.this,JuegoOnline.class);
        intent.putExtra("idPartida", idPartida);
        intent.putExtra("turnoJugador", turnoJugador);
        startActivity(intent);
    }

    private void obtenerListaPartida() {
        String url = "http://"+IP_SERVIDOR+"/conecta4/gestion.php?partidas=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject partida = response.getJSONObject(i);

                                // Get the current student (json object) data
                                myArrayList.add(new Partida(partida.getInt("id"),
                                        partida.getString("descripcion"),
                                        partida.getString("jugador1")));
                                myAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_games_online.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_online, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_recargar:
                myArrayList.clear();
                obtenerListaPartida();
                myAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
