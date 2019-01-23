package com.jrespino.proyectoturismocordoba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private ArrayList<Lugar> arrayListLugares;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView = findViewById(R.id.myRecyclerView);
        arrayListLugares = new ArrayList<Lugar>(){{
            add(new Lugar("La mezquita",
                    R.drawable.mezquita,
                    "Mezquita-catedral de Córdoba, actualmente conocida como la Catedral de la Asunción de Nuestra Señora de forma eclesiástica o simplemente Mezquita de Córdoba de forma general, es un edificio de la ciudad de Córdoba, España. Se empezó a construir como mezquita en el año 786, con la apropiación por los conquistadores musulmanes de la basílica hispanorromana de San Vicente Mártir y la reutilización de parte de los materiales, quedando reservada al culto musulmán. El edificio resultante fue objeto de ampliaciones durante el Emirato de Córdoba y el Califato de Córdoba. Con 23 400 metros cuadrados, fue la segunda mezquita más grande del mundo en superficie, por detrás de la Mezquita de La Meca, siendo sólo alcanzada posteriormente por la Mezquita Azul (Estambul, 1588). Una de sus principales características es que su muro de la qibla no fue orientado hacia La Meca, sino 51º grados más hacia el sur, algo habitual en las mezquitas de al-Ándalus.\n" +
                            "En 1238, tras la Reconquista cristiana de la ciudad, se llevó a cabo su consagración como catedral de la diócesis con la Ordenación episcopal de su primer obispo, Lope de Fitero. El edificio alberga el cabildo catedralicio de la Diócesis de Córdoba, y por su carácter de templo católico y sede episcopal, está reservado al culto católico. En 1523, bajo la dirección de los arquitectos Hernán Ruiz, el Viejo y su hijo, se construyó su basílica cruciforme renacentista de estilo plateresco.\n" +
                            "Hoy todo el conjunto constituye el monumento más importante de Córdoba, y también de toda la arquitectura andalusí, junto con la Alhambra, así como el más emblemático del arte omeya hispanomusulmán. Declarada como Bien de interés cultural y Patrimonio Cultural de la Humanidad como parte del centro histórico de la ciudad, se incluyó por el público entre los 12 Tesoros de España en 20076\\u200B y fue premiada como el mejor sitio de interés turístico de Europa y sexto del mundo según un concurso de TripAdvisor. En 2016 tuvo 1,81 millones de visitas, lo que la convierte en uno de los monumentos más visitados de España.",
                    "Calle Cardenal Herrero, 1",
                    "L-S: 8:30-18:00 h. Domingo: 8:30-11:30h. y 15:00-18:00 h.",
                    "957 47 05 12 / 699 34 11 42",
                    "info@mezquitadecordoba.org",
                    "https://www.mezquitadecordoba.org",37.87891,-4.779396));
//            add(new Lugar("Medina Azahara",R.drawable.medina_azahara,
//                    "Medina Azahara, castellanización del nombre en árabe, مدينة الزهراء Madīnat al-Zahrā' (\"la ciudad brillante\"), fue una ciudad palatina o áulica que mandó edificar Abderramán III (Abd al-Rahman III, al-Nasir) a unos 8 km en las afueras de Córdoba en dirección oeste, más concretamente, en Sierra Morena.",
//                    ""));
//            add(new Lugar("Puente romano",
//                    R.drawable.puente_romano,
//                    "El puente romano de Córdoba está situado sobre el río Guadalquivir a su paso por Córdoba, y une el barrio del Campo de la Verdad con el Barrio de la Catedral. También conocido como \"el Puente Viejo\" fue el único puente con que contó la ciudad durante 20 siglos, hasta la construcción del puente de San Rafael, a mediados del siglo XX. El 9 de enero de 2008 se inauguró la mayor y discutida remodelación que el puente Romano ha tenido en su historia.",
//                    ""));
        }};
        myAdapter = new MyAdapter(arrayListLugares, this, R.layout.item_card, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lugar lugar, int position) {
                Intent intent = new Intent(MainActivity.this,MostrarLugar.class);
                intent.putExtra("lugar",lugar);
                startActivity(intent);
            }
        });
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }
}
