package com.serbladev.appfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MiHolder> {
    ArrayList<Ejercicio> listaEjercicios;

    //Aquí le pasamos el arrayList de los elementos de la lista
    public RecyclerAdapter(ArrayList<Ejercicio> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;

    }

    //Aqui creamos el Holder customizado donde meteremos los elementos que van a ir dentro de cada item de la lista.
    public static class MiHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvActividad, tvDistancia;

        public MiHolder(View layoutDelItem) {
            super(layoutDelItem);
            tvFecha = layoutDelItem.findViewById(R.id.tvFecha);
            tvActividad = layoutDelItem.findViewById(R.id.tvActividad);
            tvDistancia = layoutDelItem.findViewById(R.id.tvDistancia);
        }
    }

    // Este método coge el layout de la fila y construye los items dentro del RecyclerView
    @Override
    public MiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // construyo el layoput de la fila y lo HINCHO para crearlo
        View filahinchada = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item, parent, false);
        MiHolder miholder = new MiHolder(filahinchada);


        return miholder;
    }

    // A este metodo se le llama para que pinte cada fila del RecyclerView,
    // recibe el holder personal que hemos creado (que ya tiene creados los objetos TextView) además de la posicion de la file que se va a pintar.
    @Override
    public void onBindViewHolder(@NonNull MiHolder holder, int position) {

        Ejercicio ej = listaEjercicios.get(position);
        holder.tvFecha.setText(ej.getFecha());
        holder.tvActividad.setText(ej.getTipoActividad());
        holder.tvDistancia.setText(ej.getDistancia() + "");


    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

}
