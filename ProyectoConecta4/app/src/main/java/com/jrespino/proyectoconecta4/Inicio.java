package com.jrespino.proyectoconecta4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Inicio extends AppCompatActivity {
    private Button buttonEmpezar;
    private Button buttonEmpezarOnline;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        buttonEmpezar = findViewById(R.id.buttonEmpezar);
        buttonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inicio.this,MainActivity.class));
            }
        });
        buttonEmpezarOnline = findViewById(R.id.buttonEmpezarOnline);
        buttonEmpezarOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!prefs.contains("nick"))
                    crearDialogoPartidaOnline();
                else
                    goToActivityGames();
            }
        });
        prefs =
                getSharedPreferences("preferenciasUsuario", Context.MODE_PRIVATE);
    }
    private void crearDialogoPartidaOnline() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Jugar online");
        builder.setMessage("Introduce un nick para jugar online");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog,null);
        final EditText editTextValor = view.findViewById(R.id.editTextValor);
        editTextValor.setHint("Nombre");
        builder.setView(view);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nick = editTextValor.getText().toString();
                if(nick.length() ==0)
                    return;
                guardarNombre(nick);
                goToActivityGames();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void goToActivityGames() {
        startActivity(new Intent(Inicio.this, Activity_games_online.class));
    }

    private void guardarNombre(String nick){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nick", nick);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_change_nick:
                cambiarNick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cambiarNick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nick online");
        builder.setMessage("Introduce un nick para jugar online");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog,null);
        final EditText editTextValor = view.findViewById(R.id.editTextValor);
        editTextValor.setHint("Nombre");
        if(prefs.contains("nick"))
            editTextValor.setText(prefs.getString("nick",null));
        builder.setView(view);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nick = editTextValor.getText().toString();
                if(nick.length() ==0)
                    return;
                guardarNombre(nick);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
