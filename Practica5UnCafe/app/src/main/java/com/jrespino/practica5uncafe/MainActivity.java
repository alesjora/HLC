package com.jrespino.practica5uncafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private TextView textViewCantidad;
    private CheckBox checkBoxCrema;
    private CheckBox checkBoxChocolate;
    private Button buttonMas;
    private Button buttonMenos;
    private Button buttonFinalizarPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        buttonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementarCantidad();
            }
        });
        buttonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementarCantidad();
            }
        });
        buttonFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarPedido();
            }
        });
    }

    private void finalizarPedido() {
        Toast.makeText(this, obtenerPedido(), Toast.LENGTH_SHORT).show();
    }

    private String obtenerPedido() {
        String nombre = editTextNombre.getText().toString();
        String conCrema = (checkBoxCrema.isChecked())?"Sí":"No";
        String conChocolate = (checkBoxChocolate.isChecked())?"Sí":"No";
        int cantidad = obtenerCantidad();
        return "Nombre: "+nombre+"\nCrema: "+conCrema+"\nChocolate: "+conChocolate+"\nCantidad: "+cantidad;
    }

    private void bindUI() {
        editTextNombre = findViewById(R.id.editTextNombre);
        textViewCantidad = findViewById(R.id.textViewCantidad);
        checkBoxCrema = findViewById(R.id.checkBoxCrema);
        checkBoxChocolate = findViewById(R.id.checkBoxChocolate);
        buttonMas = findViewById(R.id.buttonMas);
        buttonMenos = findViewById(R.id.buttonMenos);
        buttonFinalizarPedido = findViewById(R.id.buttonFinalizarPedido);
    }
    private void incrementarCantidad(){
        asignarCantidad(obtenerCantidad()+1);
    }

    private void decrementarCantidad(){
        asignarCantidad(obtenerCantidad()-1);
    }

    private int obtenerCantidad(){
        return Integer.parseInt(textViewCantidad.getText().toString());
    }
    private void asignarCantidad(int cantidad){
        if(cantidad < 0)
            return;
        textViewCantidad.setText(String.valueOf(cantidad));
    }
}
