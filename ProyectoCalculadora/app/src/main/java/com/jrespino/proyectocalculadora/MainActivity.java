package com.jrespino.proyectocalculadora;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Button buttonPunto, buttonSumar, buttonRestar, buttonMultiplicar, buttonDividir, buttonBorrar;
    private FloatingActionButton fabIgual;
    private TextView textViewMuestra;
    private boolean sumar, restar, multiplicar, dividir, nuevoValor, realizarOperacion, primerNumero;
    private String operacionActual, operacionPosterior;
    private float valor1, valor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonPunto.setOnClickListener(this);
        buttonSumar.setOnClickListener(this);
        buttonRestar.setOnClickListener(this);
        buttonMultiplicar.setOnClickListener(this);
        buttonDividir.setOnClickListener(this);
        buttonBorrar.setOnClickListener(this);
        buttonBorrar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                textViewMuestra.setText("");
                resetearValores();
                return true;
            }
        });
        fabIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operacionActual == "" && operacionPosterior != "+" || operacionPosterior != "-" || operacionPosterior != "X" || operacionPosterior != "/")
                    asignarOperaciones(operacionPosterior);
                comprobarOperacion(textViewMuestra.getText().toString());
                nuevoValor = true;
            }
        });
    }

    private void bindUI() {
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonPunto = findViewById(R.id.buttonPunto);
        buttonSumar = findViewById(R.id.buttonSumar);
        buttonRestar = findViewById(R.id.buttonRestar);
        buttonMultiplicar = findViewById(R.id.buttonMultiplicar);
        buttonDividir = findViewById(R.id.buttonDividir);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        fabIgual = findViewById(R.id.fabIgual);
        textViewMuestra = findViewById(R.id.textViewMuestra);
        resetearValores();
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String valor = button.getText().toString();
        switch (view.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                if (textViewMuestra.getText().toString() == "0" && textViewMuestra.getText().toString().length() <= 1 || nuevoValor)
                    textViewMuestra.setText(valor);
                else
                    textViewMuestra.append(valor);
                nuevoValor = false;
                break;
            case R.id.buttonBorrar:
                if (!textViewMuestra.getText().toString().isEmpty())
                    textViewMuestra.setText(textViewMuestra.getText().subSequence(0, textViewMuestra.getText().length() - 1));
                break;
            case R.id.buttonPunto:
                if (textViewMuestra.getText().toString().indexOf(".") == -1)
                    textViewMuestra.append(".");
                break;
            case R.id.buttonSumar:
            case R.id.buttonRestar:
            case R.id.buttonMultiplicar:
            case R.id.buttonDividir:
                if (!primerNumero) {
                    valor1 = Float.parseFloat(textViewMuestra.getText().toString());
                    operacionPosterior = valor;
                    primerNumero = true;
                } else {
                    valor2 = Float.parseFloat(textViewMuestra.getText().toString());
                    realizarOperacion = true;
                    operacionActual = operacionPosterior;
                    operacionPosterior = valor;
                }
                asignarOperaciones(operacionActual);
                if (realizarOperacion && !nuevoValor)
                    comprobarOperacion(textViewMuestra.getText().toString());
                nuevoValor = true;
                break;
        }

    }

    private void comprobarOperacion(String valor) {
        if (sumar || restar || multiplicar || dividir) {
            valor2 = Float.parseFloat(valor);
            calcularOperacion();
        }
    }

    private void calcularOperacion() {
        if (sumar)
            asignarOperacion(valor1 + valor2);
        else if (restar)
            asignarOperacion(valor1 - valor2);
        else if (multiplicar)
            asignarOperacion(valor1 * valor2);
        else
            asignarOperacion(valor1 / valor2);

        valor1 = Float.parseFloat(textViewMuestra.getText().toString());
        valor2 = 0;
    }

    private void asignarOperacion(float valorDecimal) {
        if (convertir(valorDecimal)) {
            textViewMuestra.setText(String.valueOf(Math.round(valorDecimal)));
        } else
            textViewMuestra.setText(String.valueOf(valorDecimal));
    }

    private boolean convertir(float valor) {
        if (valor % 1 == 0)
            return true;
        return false;
    }

    private void resetearValores() {
        sumar = false;
        restar = false;
        multiplicar = false;
        dividir = false;
        nuevoValor = false;
        realizarOperacion = false;
        primerNumero = false;
        operacionActual = "";
        operacionPosterior = "";
        valor1 = 0;
        valor2 = 0;
    }

    private void asignarOperaciones(String operacionActual) {
        sumar = false;
        restar = false;
        multiplicar = false;
        dividir = false;
        switch (operacionActual) {
            case "+":
                sumar = true;
                break;
            case "-":
                restar = true;
                break;
            case "*":
                multiplicar = true;
                break;
            case "/":
                dividir = true;
                break;
        }
    }
}

