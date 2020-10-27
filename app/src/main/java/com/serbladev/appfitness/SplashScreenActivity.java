package com.serbladev.appfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.serbladev.appfitness.actividades.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Con esto se hace que se detenga el hilo actual el tiempo indicado en milisegundos.
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent iSplash = new Intent(this, MainActivity.class);
        startActivity(iSplash);
        finish(); // Esto evita que se pueda  regresar a esta Activity
    }
}