package com.serbladev.appfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        //Esto es un static final porque es una constante que nunca va a cambiar.
    public static final String CLAVE_NOMBRE = "NOMBRE";
    EditText etNombre, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aquí estamos relacionando la parte de código con la parte visual. Le decimos que nuestra variable etNombre
        //enganche con la vista del layout llamada etNombre
        etNombre = findViewById(R.id.etNombre);
        etPassword = findViewById(R.id.etPassword);
    }

        // "Linking methods" sólo usarlos con botones. Evitamos así utilizar los onClickListener.
    public void onEntrar(View view) {
            //Aquí obtenemos lo que se ha escrito en los EditTexts
        String nom = etNombre.getText().toString();
        String pas = etPassword.getText().toString();

            //Si coinciden con los requisitos, pasamos a la segunda actividad
        if (nom.equals("Pepe") && pas.equals("123")) {

                // Aquí estamos creando el intent que vamos a necesitar para ir a otra actividad
                // Hay que imaginar un intent como un burro que nos lleva y nos trae, y sus putExtra son las alforjas adicionales que puede llevar
                // con datos e información entre pantallas.
            Intent i = new Intent(MainActivity.this, ListaActivity.class);
            i.putExtra(CLAVE_NOMBRE , nom);

                //Aquí estaría arrancando el intent y pasándonos a la siguiente actividad junto con la información asociada en sus putExtra
            startActivity(i);

            //Si no coinciden, muestra un aviso de datos incorrectos.
        } else {
            Toast.makeText(this,"Los datos no son correctos", Toast.LENGTH_LONG).show();
        }

    }


}