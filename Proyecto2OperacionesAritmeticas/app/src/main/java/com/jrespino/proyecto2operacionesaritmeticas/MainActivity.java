package com.jrespino.proyecto2operacionesaritmeticas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumero1;
    EditText editTextNumero2;
    TextView textViewResultado;
    Button buttonSumar, buttonRestar, buttonMultiplicar, buttonDividir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNumero1 = findViewById(R.id.editTextNumero1);
        editTextNumero2 = findViewById(R.id.editTextNumero2);
        buttonSumar = findViewById(R.id.buttonSumar);
        buttonRestar = findViewById(R.id.buttonRestar);
        buttonMultiplicar = findViewById(R.id.buttonMultiplicar);
        buttonDividir = findViewById(R.id.buttonDividir);

        textViewResultado = findViewById(R.id.textViewResultado);

        buttonSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultado = getNumero1() + getNumero2();
                textViewResultado.setText(String.valueOf(resultado));
            }
        });
        buttonRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultado = getNumero1() - getNumero2();
                textViewResultado.setText(String.valueOf(resultado));
            }
        });
        buttonMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultado = getNumero1() * getNumero2();
                textViewResultado.setText(String.valueOf(resultado));
            }
        });
        buttonDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultado = getNumero1() / getNumero2();
                textViewResultado.setText(String.valueOf(resultado));
            }
        });
    }

    private int getNumero1() {
        return Integer.parseInt(editTextNumero1.getText().toString());
    }
    private int getNumero2() {
        return Integer.parseInt(editTextNumero2.getText().toString());
    }
}
