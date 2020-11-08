package com.serbladev.appfitness.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.serbladev.appfitness.pojo.Ejercicio;

import java.util.ArrayList;

public class EjercicioSQLITE extends SQLiteOpenHelper {

    SQLiteDatabase mibbdd;

    public EjercicioSQLITE(@Nullable Context context, @Nullable String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void connect(){
        mibbdd = getWritableDatabase();
    }

    public void disconnect(){
        mibbdd.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE TABLA_EJERCICIOS (id INTEGER PRIMARY KEY," +
                "nombre TEXT," +
                "fecha TEXT," +
                "tipoactividad TEXT," +
                "distancia DOUBLE," +
                "nocturno INTEGER)";

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertarEjercicio(Ejercicio ejercicionuevo) {

        ContentValues valores = new ContentValues();
        valores.put("nombre" , ejercicionuevo.getNombre() );
        valores.put("fecha", ejercicionuevo.getFecha());
        valores.put("tipoactividad", ejercicionuevo.getTipoActividad());
        valores.put("distancia", ejercicionuevo.getDistancia());
        if( ejercicionuevo.isNocturno() ){
            valores.put("nocturno", 1 );
        }else{
            valores.put("nocturno", 0 );
        }
        connect();
        mibbdd.insert("TABLA_EJERCICIOS", null, valores);
        disconnect();
    }

    public void borrarEjercicio(Ejercicio ejercicioQueBorro) {
        connect();
        mibbdd.delete("TABLA_EJERCICIOS", "ID = "+ejercicioQueBorro.getId(), null);
        disconnect();
    }



    public ArrayList<Ejercicio> leerEjercicios() {
        ArrayList<Ejercicio> datos = new ArrayList<>();
        connect();
        String[] campos = {"id", "nombre", "fecha", "tipoactividad", "distancia", "nocturno"};
        Cursor listaDeFilas = mibbdd.query("TABLA_EJERCICIOS",campos,null,null,null,null, null);
        while (listaDeFilas.moveToNext()){
               int elid =  listaDeFilas.getInt(0);
               String elnombr = listaDeFilas.getString(1);
               String lafecha = listaDeFilas.getString(2);
               String eltipo = listaDeFilas.getString(3);
               double ladistancia = listaDeFilas.getDouble(4);
               int elnocturno = listaDeFilas.getInt(5);
               boolean elnoc ;
               if(elnocturno==1){
                   elnoc = true;
               }else{
                   elnoc = false;
               }
              // elnoc =  (elnocturno==1) ? true: false;
               Ejercicio eeee = new Ejercicio(elid,elnombr,lafecha,eltipo,ladistancia,elnoc);
                datos.add(eeee);
        }

        disconnect();
        return datos;
    }






    }
