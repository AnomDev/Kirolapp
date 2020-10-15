package com.serbladev.appfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MiHolder>{
    ArrayList<Ejercicio> listaEjercicios;

    //Aquí le pasamos el arrayList de los elementos de la lista
    public RecyclerAdapter(ArrayList<Ejercicio> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;
    }
    //Aqui cre
    public static class MiHolder extends  RecyclerView.ViewHolder{
        TextView tvFecha, tvActividad, tvDistancia;
        public MiHolder( View layoutDelItem) {
            super(layoutDelItem);
            tvFecha = layoutDelItem.findViewById(R.id.tvFecha);
            tvActividad = layoutDelItem.findViewById(R.id.tvActividad);
            tvDistancia = layoutDelItem.findViewById(R.id.tvDistancia);
        }
    }

    // este metodo coge el layput de la fila y con el construye los elementos dentro del R3ecyclerView
    @Override
    public MiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // construyo el layoput de la fila y lo HINCHO para crearlo
        View filahinchada  =LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item, parent,false);
        MiHolder miholder = new MiHolder(filahinchada);
        return miholder;
    }

    // este metodfose le llama para que pinte cada fila del recycler
    // recibe el holder mio (donde ya tengo creados los objetos textview) y la posicion de la file que pinto
    @Override
    public void onBindViewHolder(@NonNull MiHolder holder, int position) {

        Ejercicio ej = listaEjercicios.get(position);
        holder.tvFecha.setText(ej.getFecha());
        holder.tvActividad.setText(ej.getTipoActividad());
        holder.tvDistancia.setText(ej.getDistancia()+"");
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

}
