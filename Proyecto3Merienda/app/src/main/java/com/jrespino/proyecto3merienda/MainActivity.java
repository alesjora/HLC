package com.jrespino.proyecto3merienda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    Button buttonComer;
    TextView textViewEstado;
    ImageView imageViewComer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();

        buttonComer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get().load(R.drawable.despuesdecomer).fit().into(imageViewComer);
                textViewEstado.setText("Estoy lleno!");
            }
        });
    }

    private void bindUI() {
        buttonComer = findViewById(R.id.buttonComer);
        textViewEstado = findViewById(R.id.textViewEstado);
        imageViewComer = findViewById(R.id.imageViewComer);
    }
}
