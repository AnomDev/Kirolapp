package com.serbladev.appfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent iSplash = new Intent(this, MainActivity.class);
        startActivity(iSplash);
        finish(); // Esto evita que se pueda  regresar a esta Activity
    }
}