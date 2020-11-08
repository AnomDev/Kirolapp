package com.serbladev.appfitness.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.serbladev.appfitness.R;
import com.serbladev.appfitness.databinding.ActivityAltaBinding;
import com.serbladev.appfitness.modelo.EjercicioSQLITE;
import com.serbladev.appfitness.pojo.Ejercicio;

public class AltaActivity extends AppCompatActivity {

    ActivityAltaBinding vistas;
    String nombreNuevamente = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_alta);

        vistas = ActivityAltaBinding.inflate(getLayoutInflater());
        setContentView(vistas.getRoot());

        Intent elintentquemetraigodelalistayvoyadevolverle = getIntent();
        nombreNuevamente = elintentquemetraigodelalistayvoyadevolverle.getStringExtra("nombredelusuariodenuevo");

        // Para construir el spinner, necesitamos seguir estos pasos (que son los mismos que cuando se crea un listView)
        // 1.- Creo los datos que va a contener
        String [] datosSpinner = {"Senderismo", "Running", "Natación", "Ciclismo"};
        // 2.- Creo un adaptador estandar y le paso los datos ya creados󠁿󠁿󠁿
        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, datosSpinner);
        // 3.- Engancho el adaptador en la lista (spinner)
        vistas.spWorkoutTypes.setAdapter(adaptadorSpinner);


        // Estas lineas son la concrección del código de abajo junto a la clase MiListener.
        vistas.btSave.setOnClickListener(new View.OnClickListener() {

            //Aquí vamos a obtener los datos que se han introducido en la pantalla de AltaActivity
            @Override
            public void onClick(View v) {
                String date = vistas.tietDate.getText().toString();
                String distance = vistas.tietDistance.getText().toString();
                String workoutType = (String) vistas.spWorkoutTypes.getSelectedItem(); //Aquí hay que hacer un casting porque le pido un String y él me está devolviendo un objeto.
                boolean nightWorkout = vistas.swNightWorkout.isChecked();

                //Aquí creamos un nuevo ejercicio y le pasamos lo datos que hemos obtenido
                Ejercicio newWorkout = new Ejercicio(0, nombreNuevamente, date, workoutType, Double.parseDouble(distance), nightWorkout);
                EjercicioSQLITE bbdd = new EjercicioSQLITE(getApplicationContext(),"BaseDatosEjercicios", null, 1);
                bbdd.insertarEjercicio(newWorkout);

                //Aquí estamos haciendo que el objeto newWorkout pase a ser un JSON (String) para que lo admina el putExtra del intent que lo devuelve a la lista. Es necesario implementar la libreria GSON en el Gradle.
              //  Gson objgson = new Gson();
             //  String ejercicioEnFormatoJSON = objgson.toJson(newWorkout);

           //     elintentquemetraigodelalistayvoyadevolverle.putExtra("elEjercicioRecienCreado", ejercicioEnFormatoJSON); //PASO 2: Cojo el mismo intent que me he traido y le añado el objeto en forma de JSON creado para devolverlo
                setResult( RESULT_OK, elintentquemetraigodelalistayvoyadevolverle); //PASO 3: setResult tiene un código de ID de quien devuelve el intent y su intent que devuelve.
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        // esta vacio por que eso es lo que por ahora queremos que haga el boton atras, exactamente NADA
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ad_exitwithoutsaving).setCancelable(false).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AltaActivity.this.finish();
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

//Esto es lo mismo que hacer la concrección del setOnClickListener.

//Esto dentro de onCreate
 /*MiListener mk = new MiListener();
        vistas.btSave.setOnClickListener(mk);*/

//Esto fuera de onCreate
/*class MiListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }
}*/
