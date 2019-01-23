package com.jrespino.proyectoturismocordoba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MostrarLugar extends AppCompatActivity {
    private Lugar lugar;
    private TextView nombre;
    private ImageView imagen;
    private TextView descripcion;
    private TextView localizacion;
    private TextView horario;
    private TextView telefono;
    private TextView email;
    private TextView web;
    private ImageButton imageButtonMapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_lugar);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            lugar = (Lugar) bundle.getSerializable("lugar");
        }
        bindUI();
        Picasso.get().load(lugar.getImagen()).fit().into(imagen);
        nombre.setText(lugar.getNombre());
        localizacion.append(lugar.getLocalizacion());
        horario.append(lugar.getHorario());
        telefono.append(lugar.getTelefono());
        email.append(lugar.getEmail());
        web.append(lugar.getWeb());
        descripcion.setText(lugar.getDescripcionLarga());
        imageButtonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MostrarLugar.this,MapsActivity.class);
                intent.putExtra("latitud",lugar.getLatidud());
                intent.putExtra("longitud",lugar.getLongitud());
                intent.putExtra("nombre",lugar.getNombre());
                startActivity(intent);
            }
        });
    }

    private void bindUI() {
        imagen = findViewById(R.id.imageViewLugar);
        nombre = findViewById(R.id.textViewNombre);
        descripcion = findViewById(R.id.textViewDescripcion);
        localizacion = findViewById(R.id.textViewLocalizacion);
        horario = findViewById(R.id.textViewHorario);
        telefono = findViewById(R.id.textViewTelefono);
        email = findViewById(R.id.textViewEmail);
        web = findViewById(R.id.textViewWeb);
        imageButtonMapa = findViewById(R.id.imageButtonMapa);
    }
}
