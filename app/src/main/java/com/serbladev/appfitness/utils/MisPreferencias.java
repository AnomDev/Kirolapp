package com.serbladev.appfitness.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MisPreferencias {

    public static int leerPreferenciasDeLaAplicacion(Activity actividad) {
        SharedPreferences prefs = actividad.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
// Si no existe el valor, se devuelve el segundo parametro
        //String correo = prefs.getString("mail", "por_defecto @gmail.com");
        int numero = prefs.getInt("pref_itemseleccionado", -1) ;
        return numero;
    }


    public static void guardarPreferenciasDeLaAplicacion(Activity actividad, int itemSeleccionado) {
        SharedPreferences prefs = actividad.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        // GUARDAR o AÑADIR NUEVOS valores a preferencias
        SharedPreferences.Editor editor = prefs.edit();
       // editor.putString("mail", "pruebas@gmail.com");
        editor.putInt("pref_itemseleccionado", itemSeleccionado);
        editor.commit();
    }

    public void borrarPreferenciasDeLaAplicacion(Activity actividad) {
        SharedPreferences prefs = actividad.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
// Asi eliminamos TODAS las preferencias del grupo prefs:
        editor.clear();
        editor.commit();
// y así eliminamos SOLO la preferencia titulo del grupo prefs:
        editor.remove("titulo");
        editor.commit();
    }

}
