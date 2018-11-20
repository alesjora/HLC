package com.jrespino.proyectomarcadordebaloncesto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityPrincipal extends AppCompatActivity {

    private EditText editTextEquipoLocal;
    private EditText editTextEquipoVisitante;
    private Button buttonEmpezarPartido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        editTextEquipoLocal = findViewById(R.id.editTextEquipoLocal);
        editTextEquipoVisitante = findViewById(R.id.editTextEquipoVisitante);
        buttonEmpezarPartido = findViewById(R.id.buttonEmpezarPartido);

        buttonEmpezarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPrincipal.this, ActivityPartida.class);
                intent.putExtra("local",editTextEquipoLocal.getText().toString());
                intent.putExtra("visitante", editTextEquipoVisitante.getText().toString());
                startActivity(intent);
            }
        });
    }
}
