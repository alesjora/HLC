package com.jrespino.proyectomarcadordebaloncesto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPartida extends AppCompatActivity {

    private Button buttonSumar1Local;
    private Button buttonSumar2Local;
    private Button buttonSumar3Local;
    private Button buttonSumar1Visitante;
    private Button buttonSumar2Visitante;
    private Button buttonSumar3Visitante;
    private Button buttonReiniciar;
    private TextView textViewResultadoLocal;
    private TextView textViewResultadoVisitante;
    private TextView textViewEquipoLocal;
    private TextView textViewEquipoVisitante;
    private ImageView imageViewSumarPuntoLocal;
    private ImageView imageViewRestarPuntoLocal;
    private ImageView imageViewSumarPuntoVisitante;
    private ImageView imageViewRestarPuntoVisitante;
    private final int UN_PUNTO = 1;
    private final int DOS_PUNTOS = 2;
    private final int TRES_PUNTOS = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        asignarNombresEquipos();
        buttonSumar1Local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoLocal,UN_PUNTO);
            }
        });
        buttonSumar2Local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoLocal,DOS_PUNTOS);
            }
        });
        buttonSumar3Local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoLocal,TRES_PUNTOS);
            }
        });
        buttonSumar1Visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoVisitante,UN_PUNTO);
            }
        });
        buttonSumar2Visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoVisitante,DOS_PUNTOS);
            }
        });
        buttonSumar3Visitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoVisitante,TRES_PUNTOS);
            }
        });
        imageViewSumarPuntoLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoLocal,UN_PUNTO);
            }
        });
        imageViewRestarPuntoLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restarPunto(textViewResultadoLocal);
            }
        });
        imageViewSumarPuntoVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarPuntos(textViewResultadoVisitante,UN_PUNTO);
            }
        });
        imageViewRestarPuntoVisitante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restarPunto(textViewResultadoVisitante);
            }
        });
        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarValores();
            }
        });

    }

    private void reiniciarValores(){
        textViewResultadoLocal.setText("0");
        textViewResultadoVisitante.setText("0");
    }

    private void asignarNombresEquipos() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            textViewEquipoLocal.setText(bundle.getString("local"));
            textViewEquipoVisitante.setText(bundle.getString("visitante"));
        }
    }

    private void bindUI() {
        buttonSumar1Local = findViewById(R.id.buttonSumar1Local);
        buttonSumar2Local = findViewById(R.id.buttonSumar2Local);
        buttonSumar3Local = findViewById(R.id.buttonSumar3Local);
        buttonSumar1Visitante = findViewById(R.id.buttonSumar1Visitante);
        buttonSumar2Visitante = findViewById(R.id.buttonSumar2Visitante);
        buttonSumar3Visitante = findViewById(R.id.buttonSumar3Visitante);
        textViewResultadoLocal = findViewById(R.id.textViewResultadoLocal);
        textViewResultadoVisitante = findViewById(R.id.textViewResultadoVisitante);
        textViewEquipoLocal = findViewById(R.id.textViewEquipoLocal);
        textViewEquipoVisitante = findViewById(R.id.textViewEquipoVisitante);
        imageViewSumarPuntoLocal = findViewById(R.id.imageViewSumarPuntoLocal);
        imageViewRestarPuntoLocal = findViewById(R.id.imageViewRestarPuntoLocal);
        imageViewSumarPuntoVisitante = findViewById(R.id.imageViewSumarPuntoVisitante);
        imageViewRestarPuntoVisitante = findViewById(R.id.imageViewRestarPuntoVisitante);
        buttonReiniciar = findViewById(R.id.buttonReiniciar);
    }

    private void sumarPuntos(TextView textViewResultado, int puntos){
        int resultado = Integer.parseInt(textViewResultado.getText().toString()) + puntos;
        if(resultado < 0)
            return;
        textViewResultado.setText(String.valueOf(resultado));
    }
    private void restarPunto(TextView textViewResultado){
        int resultado = Integer.parseInt(textViewResultado.getText().toString()) - UN_PUNTO;
        if(resultado < 0)
            return;
        textViewResultado.setText(String.valueOf(resultado));
    }
}
