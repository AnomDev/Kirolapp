package com.serbladev.appfitness.actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.serbladev.appfitness.R;
import com.serbladev.appfitness.adaptadores.RecyclerAdapter;
import com.serbladev.appfitness.databinding.ActivityListaBinding;
import com.serbladev.appfitness.pojo.Ejercicio;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    //Esto coge la clase con todas las vistas de su layout. Sirve para no tener que utilizar el findViewById (igual que en Kotlin no se emplea).
    ActivityListaBinding vistas;
    DividerItemDecoration itemDecor;
    String nombre = "";

    //Aquí creamos el ArrayList de datos de tipo Ejercicio que vamos a utilizar para rellenar el RecyclerView
    ArrayList<Ejercicio> datos = new ArrayList<>();
    RecyclerAdapter recyadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Con esto construimos el objeto "vistas".
        vistas = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(vistas.getRoot());

        //Aquí estamos cogiendo el intent creado en MainActivity con el nombre y lo estamos trayendo a ListaActivity
        Intent elintentquemetraigodelaotraactividad = getIntent();
        nombre = elintentquemetraigodelaotraactividad.getStringExtra("nombredelpollo");

        //Aquí estamos pintando los datos del string obtenido del intent en el textView.
        vistas.tvTitleListActivity.setText("Ejercicio de " + nombre);
        //Gracias al Binding de arriba, nos ahorramos tener que poner "TextView tvEjercicio = findViewById(R.id.tvEjercicio).setText("Ejercicio de "+nombre);"

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


        recyadapter = new RecyclerAdapter(datos);
        vistas.rvWorkoutList.setAdapter(recyadapter);

        LinearLayoutManager linlaymanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vistas.rvWorkoutList.setLayoutManager(linlaymanager);

    }
   /* //Con este método nos vamos a AltaActivity
    public void onEntrarAlta (View view) {
        onNavigation(AltaActivity.class);
    }

    //Con este método nos volvemos a MainActivity(Login)
    public void onSalir(View view) {
        onNavigation(MainActivity.class);
    }

    public void onNavigation (Class clase){
        Intent i = new Intent(ListaActivity.this, clase);
        startActivity(i);
    }*/
   public void onNavigation (View view) {

       if(view.getId() == R.id.btAddWorkout){
           Intent ialta = new Intent(ListaActivity.this, AltaActivity.class);
           ialta.putExtra("nombredelpollonuevamente", nombre);
           startActivityForResult(ialta,34353); //PASO 1: se añade ForResult para indicarle un intent de ida y vuelta. Se le añade un código de ID de la clase que pide iniciar la nueva actividad.

       } else if (view.getId() == R.id.btExit) {
           Intent iback = new Intent(ListaActivity.this, MainActivity.class);
           startActivity(iback);
           finish();
       } else {
           //Toast
       }

   }

   //PASO 4: Cuando ya vengo de la actividad a la qye llame con startActivityForResult con mi resultado (intent) se dispara el método siguiente:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // por si acaso lo ponermos bonito aunque solo puede llegar aqui volviendo desde alta
        if(requestCode == 34353){  // si el intent original fue hacia alta
              if(resultCode == RESULT_OK){
                 String elejercicioENJSON =  data.getStringExtra("elEjercicioRecienCreado");

                 //Aquí el Gson funciona a la inversa y transforma un JSON en un objeto, para que sepa cómo es el objeto hay que indicarle ".class"
                 Gson gson = new Gson();
                 Ejercicio ejercicioCreadoDesdeJson = gson.fromJson(elejercicioENJSON, Ejercicio.class);
                 datos.add (ejercicioCreadoDesdeJson);
                 recyadapter.notifyDataSetChanged(); //Aqui avisamos al adapter que tiene datos nuevos y que refresque el recyclerView.

              }
        }
        // No es necesario un "else". Si el "if" no se cumple, simplemente lo ignora y sigue su camino.

    }

    @Override
    public void onBackPressed() {
        // esta vacio por que eso es lo que por ahora queremos que haga el boton atras, exactamente NADA
    }
}