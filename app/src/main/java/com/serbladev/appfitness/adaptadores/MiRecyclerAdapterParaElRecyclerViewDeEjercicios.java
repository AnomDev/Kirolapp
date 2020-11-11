package com.serbladev.appfitness.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.serbladev.appfitness.R;
import com.serbladev.appfitness.pojo.Ejercicio;

import java.util.ArrayList;


public class MiRecyclerAdapterParaElRecyclerViewDeEjercicios extends RecyclerView.Adapter<MiRecyclerAdapterParaElRecyclerViewDeEjercicios.MiHolder> {
    ArrayList<Ejercicio> listaEjercicios;
    int itemSeleccionado= -1;
    MiRecyclerAdapterParaElRecyclerViewDeEjercicios miContextoCustomizado;
    //Aquí le pasamos el arrayList de los elementos de la lista
    public MiRecyclerAdapterParaElRecyclerViewDeEjercicios(ArrayList<Ejercicio> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;
        miContextoCustomizado = this;

    }

    //Aqui creamos el Holder customizado donde meteremos los elementos que van a ir dentro de cada item de la lista.
    public static class MiHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvActividad, tvDistancia;
        ConstraintLayout elLayoutEntero;

        public MiHolder(View layoutDelItem) {
            super(layoutDelItem);
            elLayoutEntero = (ConstraintLayout) layoutDelItem;
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

    // A este metodo se le llama para que pinte cada item del RecyclerView, SI NECESITA PINTARLO POR QUE DESAPARECIO
    // recibe el holder personal que hemos creado (que ya tiene creados los objetos TextView) además de la posicion de la file que se va a pintar.
    @Override
    public void onBindViewHolder(@NonNull MiHolder itemActualQueEstoyPintando, int position) {

        Ejercicio ej = listaEjercicios.get(position);
        itemActualQueEstoyPintando.tvFecha.setText(ej.getFecha());
        itemActualQueEstoyPintando.tvActividad.setText(ej.getTipoActividad());
        itemActualQueEstoyPintando.tvDistancia.setText(ej.getDistancia() + "");

        if(position== itemSeleccionado){
            itemActualQueEstoyPintando.elLayoutEntero.setBackgroundColor(Color.CYAN);
        }else{
            itemActualQueEstoyPintando.elLayoutEntero.setBackgroundColor(Color.GREEN);
        }


        itemActualQueEstoyPintando.elLayoutEntero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              itemSeleccionado = position;
              miContextoCustomizado.notifyDataSetChanged();   // refresca TODA LA LISTA
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

}
