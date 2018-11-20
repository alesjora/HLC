package com.jrespino.actividadintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView textViewContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewContenido = findViewById(R.id.textViewContenido);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            textViewContenido.setText(bundle.getString("cadena"));
        }
    }
}
