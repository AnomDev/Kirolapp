package com.serbladev.appfitness.actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.serbladev.appfitness.R;
import com.serbladev.appfitness.adaptadores.MiRecyclerAdapterParaElRecyclerViewDeEjercicios;
import com.serbladev.appfitness.databinding.ActivityListaBinding;
import com.serbladev.appfitness.modelo.EjercicioSQLITE;
import com.serbladev.appfitness.pojo.Ejercicio;
import com.serbladev.appfitness.utils.MisPreferencias;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    //Esto coge la clase con todas las vistas de su layout. Sirve para no tener que utilizar el findViewById (igual que en Kotlin no se emplea).
    ActivityListaBinding vistas;
    DividerItemDecoration itemDecor;
    String nombre = "";
    EjercicioSQLITE bbdd;
    //Aquí creamos el ArrayList de datos de tipo Ejercicio que vamos a utilizar para rellenar el RecyclerView
    ArrayList<Ejercicio> arrayListDeEjercicios = new ArrayList<>();
    MiRecyclerAdapterParaElRecyclerViewDeEjercicios miAdaptador;
    //Para poder usar el contexto de mi ListaActivity fuera de ella usamos esto<:
    Activity yomismo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yomismo = this;

        //Con esto construimos el objeto "vistas".
        vistas = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(vistas.getRoot());

        // Aquí estamos cogiendo el intent creado en MainActivity con el nombre y lo estamos trayendo a ListaActivity
        Intent elintentquemetraigodelaotraactividad = getIntent();
        nombre = elintentquemetraigodelaotraactividad.getStringExtra("nombredelusuario");

        // Aquí estamos pintando los datos del string obtenido del intent en el textView.
        vistas.tvTitleListActivity.setText("Ejercicio de " + nombre);
        //Gracias al Binding de arriba, nos ahorramos tener que poner "TextView tvEjercicio = findViewById(R.id.tvEjercicio).setText("Ejercicio de "+nombre);"


         bbdd = new EjercicioSQLITE(this,"BaseDatosEjercicios", null, 1);

        //Aquí estamos obteniendo todos los elementos de un ejercicio que ya está creado gracias al método leerEjercicios de la clase EjerciciosSQLITE
        arrayListDeEjercicios = bbdd.leerEjercicios();


        //Aquí estamos creando un nuevo Ejercicio con los parámetros que pide el constructor de Ejercicio y que será un item del RecyclerView
     /*   Ejercicio e = new Ejercicio("1", "12-10-1990", "Corriendo", 12.3, true);
        datos.add(e);
        */
        //Aquí ya tenemos el Ejercicio creadod y estamos añadiendo nuevos items al RecyclerView
       /* datos.add(new Ejercicio("2", "13-10-1990", "Corriendo", 12.3, true));
        datos.add(new Ejercicio("3", "15-10-1990", "Andando", 17, false));
        datos.add(new Ejercicio("4", "23-11-1990", "Bicicleta", 37.9, true));
        datos.add(new Ejercicio("5", "31-12-1990", "Andando", 15.5, false));
        datos.add(new Ejercicio("6", "12-01-1991", "Corriendo", 6.3, true));
        datos.add(new Ejercicio("7", "21-01-1991", "Corriendo", 9, false));
        datos.add(new Ejercicio("8", "23-01-1991", "Corriendo", 12.2, false));
        datos.add(new Ejercicio("9", "04-02-1991", "Andando", 25.2, true));*/

         /* //Aquí creamos el adaptador para el ArrayList y le metemos los datos generados arriba además de utilizar un tipo de adaptador propio de android para pintar en listas
        //Podríamos definir el adaptador como "el señor pintor" que pinta los datos que tú le das y en la forma que tu le digas.
        ArrayAdapter<String>  adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        vistas.lvLista.setAdapter(adaptador);*/


        // Aquí leemos las preferencias que tenemos guardadas al haber cerrado la app
        // Podemos evitar guardarlo en una variable porque el método leerPreferencias lo hemos hecho static.
        int itemSeleccionadaLeidaDePreferencias =MisPreferencias.leerPreferenciasDeLaAplicacion(yomismo);

        // construyo MI ADAPTADOR pasandole el array de ejercicios qye ha de pintar
        miAdaptador = new MiRecyclerAdapterParaElRecyclerViewDeEjercicios(arrayListDeEjercicios);
        // le digo al adaptador qué fila debe estar seleccionada (si se guardo en preferencias)
        miAdaptador.setItemSeleccionado(itemSeleccionadaLeidaDePreferencias);
        // y finalmente le pongo el adaptador al RecyclerView
        vistas.rvWorkoutList.setAdapter(miAdaptador);


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
    public void onNavigation(View view) {

        if (view.getId() == R.id.btAddWorkout) {
            Intent ialta = new Intent(ListaActivity.this, AltaActivity.class);
            ialta.putExtra("nombredelusuariodenuevo", nombre);
            startActivityForResult(ialta, 34353); //PASO 1: se añade ForResult para indicarle un intent de ida y vuelta. Se le añade un código de ID de la clase que pide iniciar la nueva actividad.

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
        if (requestCode == 34353) {  // si el intent original fue hacia alta
            if (resultCode == RESULT_OK) {
           //  String elejercicioENJSON = data.getStringExtra("elEjercicioRecienCreado");

                //Aquí el Gson funciona a la inversa y transforma un JSON en un objeto, para que sepa cómo es el objeto hay que indicarle ".class"
            //    Gson gson = new Gson();
              //  (())   Ejercicio ejercicioCreadoDesdeJson = gson.fromJson(elejercicioENJSON, Ejercicio.class);
            //    datos.add(ejercicioCreadoDesdeJson);
                EjercicioSQLITE bbdd = new EjercicioSQLITE(this,"BaseDatosEjercicios", null, 1);
                arrayListDeEjercicios = bbdd.leerEjercicios();
                //recyadapter.notifyDataSetChanged(); //Aqui avisamos al adapter que tiene datos nuevos y que refresque el recyclerView.
                miAdaptador = new MiRecyclerAdapterParaElRecyclerViewDeEjercicios(arrayListDeEjercicios);
                vistas.rvWorkoutList.setAdapter(miAdaptador);
            }
        }
        // No es necesario un "else". Si el "if" no se cumple, simplemente lo ignora y sigue su camino.

    }

    //EL MENÚ LATERAL SE CREARÍA USANDO LOS DOS MÉTODOS DE: ONCREATEOPTIONSMENU Y ONOPTIONSITEMSSELECTED
    // onCreateOptionsMenu se invoca cuando se construye la actividad y se crea el menu. @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // Aqui se “infla” el layoput qye se desee en el menu de esta actividad
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    // onOptionsItemSelected se invoca cuando se selecciona alguna de las opciones del menu // recibe como parametro un objeto MenuItem (el pulsado)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Con el objeto MenuItem y su metodo getItemId() podemos saber el id del view del menu pulsa
        int id = item.getItemId();
        switch (id) {
            case (R.id.opcion_borrar):
                Toast.makeText(getApplicationContext(), "Borrando....", Toast.LENGTH_SHORT).show();

                int posicionactualelegida = miAdaptador.getItemSeleccionado();
                //  Ejercicio  e = arrayListDeEjercicios.get(posicionactualelegida);

                //El metodo remove, además de borrar el ejercicio te lo devuelve "por si lo quieres usar" (como por ejemplo para poder borrarlo tmb en la BBDD)
                Ejercicio e  = arrayListDeEjercicios.remove(posicionactualelegida);
                bbdd.borrarEjercicio(e);

                // vuelvo a poner que no hay ningun elemento seleccionado en el listview /adapter
                miAdaptador.setItemSeleccionado(-1);
                miAdaptador.notifyDataSetChanged();
                break;

            case (R.id.opcion_preferencias):
                Toast.makeText(getApplicationContext(), "Pulsado Prefeencias ", Toast.LENGTH_SHORT).show() ;
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // esta vacio por que eso es lo que por ahora queremos que haga el boton atras, exactamente NADA
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ad_closeapp).setCancelable(false).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                MisPreferencias.guardarPreferenciasDeLaAplicacion(yomismo, miAdaptador.getItemSeleccionado());
                ListaActivity.this.finish();

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}