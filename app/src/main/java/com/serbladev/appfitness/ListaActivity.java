package com.serbladev.appfitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.serbladev.appfitness.databinding.ActivityListaBinding;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;

public class ListaActivity extends AppCompatActivity {

    //Esto coge la clase con todas las vistas de su layout. Sirve para no tener que utilizar el findViewById (igual que en Kotlin no se emplea).
    ActivityListaBinding vistas;
    DividerItemDecoration itemDecor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Con esto construimos el objeto "vistas".
        vistas = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(vistas.getRoot());

        //Aquí estamos cogiendo el intent creado en MainActivity con el nombre y lo estamos trayendo a ListaActivity
        Intent elintentquemetraigodelaotraactividad = getIntent();
        String nombre = elintentquemetraigodelaotraactividad.getStringExtra(MainActivity.CLAVE_NOMBRE);

        //Aquí estamos pintando los datos del string obtenido del intent en el textView.
        vistas.tvEjercicio.setText("Ejercicio de " + nombre);
        //Gracias al Binding de arriba, nos ahorramos tener que poner "TextView tvEjercicio = findViewById(R.id.tvEjercicio).setText("Ejercicio de "+nombre);"

        //Aquí creamos el ArrayList de datos de tipo Ejercicio que vamos a utilizar para rellenar el RecyclerView
        ArrayList<Ejercicio> datos = new ArrayList<>();
        //Aquí estamos creando un nuevo Ejercicio con los parámetros que pide el constructor de Ejercicio y que será un item del RecyclerView
        Ejercicio e = new Ejercicio("1", "12-10-1990", "Corriendo", 12.3, true);
        datos.add(e);
        //Aquí ya tenemos el Ejercicio creadod y estamos añadiendo nuevos items al RecyclerView
        datos.add(new Ejercicio("2", "13-10-1990", "Corriendo", 12.3, true));
        datos.add(new Ejercicio("3", "15-10-1990", "Andando", 17, false));
        datos.add(new Ejercicio("4", "23-11-1990", "Bicicleta", 37.9, true));
        datos.add(new Ejercicio("5", "31-12-1990", "Andando", 15.5, false));
        datos.add(new Ejercicio("6", "12-01-1991", "Corriendo", 6.3, true));
        datos.add(new Ejercicio("7", "21-01-1991", "Corriendo", 9, false));
        datos.add(new Ejercicio("8", "23-01-1991", "Corriendo", 12.2, false));
        datos.add(new Ejercicio("9", "04-02-1991", "Andando", 25.2, true));




         /* //Aquí creamos el adaptador para el ArrayList y le metemos los datos generados arriba además de utilizar un tipo de adaptador propio de android para pintar en listas
        //Podríamos definir el adaptador como "el señor pintor" que pinta los datos que tú le das y en la forma que tu le digas.
        ArrayAdapter<String>  adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        vistas.lvLista.setAdapter(adaptador);*/


        RecyclerAdapter ra = new RecyclerAdapter(datos);
        vistas.rvLista.setAdapter(ra);

        LinearLayoutManager linla = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vistas.rvLista.setLayoutManager(linla);


    }
}