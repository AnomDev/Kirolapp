package com.serbladev.appfitness.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.serbladev.appfitness.R;

public class MainActivity extends AppCompatActivity {

    //Esto es un static final porque es una constante que nunca va a cambiar.

    EditText etNombre, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aquí estamos relacionando la parte de código con la parte visual. Le decimos que nuestra variable etNombre
        //enganche con la vista del layout llamada etNombre
        etNombre = findViewById(R.id.tietName);
        etPassword = findViewById(R.id.tietPassword);
    }

    // "Linking methods" sólo usarlos con botones. Evitamos así utilizar los onClickListener.
    public void onEntrar(View view)  {
        //Aquí obtenemos lo que se ha escrito en los EditTexts
        String nom = etNombre.getText().toString();

        int pas = 0;

        try {
             pas = Integer.parseInt(etPassword.getText().toString()); //Aquí pasamos de String a entero.
        } catch (NumberFormatException eee) {
            Toast.makeText(this, "Sólo se permite la introducción de números", Toast.LENGTH_SHORT).show();
            return;
        }


        //Si coinciden con los requisitos, pasamos a la segunda actividad
        if (nom.equals("Pepe") && (pas == 123)) {

            // Aquí estamos creando el intent que vamos a necesitar para ir a otra actividad
            // Hay que imaginar un intent como un burro que nos lleva y nos trae, y sus putExtra son las alforjas adicionales que puede llevar
            // con datos e información entre pantallas.
            Intent i = new Intent(MainActivity.this, ListaActivity.class);
            i.putExtra("nombredelusuario", nom);

            //Aquí estaría arrancando el intent y pasándonos a la siguiente actividad junto con la información asociada en sus putExtra
            startActivity(i);
            finish();

            //Si no coinciden, muestra un aviso de datos incorrectos.
        } else {
            Toast.makeText(this, "Los datos no son correctos", Toast.LENGTH_LONG).show();
        }

    }


}