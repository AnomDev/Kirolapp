package com.serbladev.appfitness.pojo;

import java.io.Serializable;

public class Ejercicio implements Serializable {

    //Los atributos siempre privados, ergo han de tener hgettes y setter
    private int id;
    private String nombre;
    private String fecha;
    private String tipoActividad;
    private double distancia;
    private boolean nocturno;


    //Constructores


    public Ejercicio(int id, String nombre, String fecha, String tipoActividad, double distancia, boolean nocturno) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipoActividad = tipoActividad;
        this.distancia = distancia;
        this.nocturno = nocturno;
    }

    public Ejercicio() {
    }

    //Getters y setters

    public String getFecha() {
        return fecha;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public double getDistancia() {
        return distancia;
    }

    public boolean isNocturno() {
        return nocturno;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
