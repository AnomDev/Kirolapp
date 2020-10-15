package com.serbladev.appfitness;

public class Ejercicio {

    //Los atributos siempre privados, ergo han de tener hgettes y setter
    private String nombre;
    private String fecha;
    private String tipoActividad;
    private double distancia;
    private boolean nocturno;

    //Constructores
    public Ejercicio(String nombre, String fecha, String tipoActividad, double distancia, boolean nocturno) {
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
}
