package com.serbladev.appfitness.adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.serbladev.appfitness.R;
import com.serbladev.appfitness.actividades.ListaActivity;
import com.serbladev.appfitness.modelo.EjercicioSQLITE;
import com.serbladev.appfitness.pojo.Ejercicio;

import java.util.ArrayList;


public class MiRecyclerAdapterParaElRecyclerViewDeEjercicios extends RecyclerView.Adapter<MiRecyclerAdapterParaElRecyclerViewDeEjercicios.MiHolder> {
    private ArrayList<Ejercicio> listaEjercicios;
    private int itemSeleccionado= -1;
    private MiRecyclerAdapterParaElRecyclerViewDeEjercicios miContextoCustomizado;
    private ListaActivity elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV;


    //Aquí le pasamos el arrayList de los elementos de la lista
    public MiRecyclerAdapterParaElRecyclerViewDeEjercicios(ArrayList<Ejercicio> listaEjercicios, ListaActivity context) {
        this.listaEjercicios = listaEjercicios;
        this.elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV = context;
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

        //Aquí vamos a cambiar el color de fondo a un item del RV en función de si lo tenemos seleccionado o no.
        if(position== itemSeleccionado){
            itemActualQueEstoyPintando.elLayoutEntero.setBackgroundColor(ContextCompat.getColor(elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV, R.color.fondoLayout));
        }else{
            itemActualQueEstoyPintando.elLayoutEntero.setBackgroundColor(Color.argb((float) 0.1,0,1,2));
        }


        itemActualQueEstoyPintando.elLayoutEntero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              itemSeleccionado = position;
              miContextoCustomizado.notifyDataSetChanged();   // refresca TODA LA LISTA
            }
        });

        itemActualQueEstoyPintando.elLayoutEntero.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuLongClick();
                return false;  // Este return (si fuera true) haría que pasara al metodo OnCLick además de hacer el OnLongClick
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public int getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(int itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public void menuLongClick(){
        //TODO: No está cogiendome el contexto, he intentado crearme una variable global que me defina el contexto de la actividad donde está el RV y su adapter
        //TODO: (ListaActivity) pero pese a no darme fallo, me devuelve un null como una casa y me crashea la aplicación.
        //TODO: También he intentado pasarle en el constructor del adapter un contexto como argumento (por algo que leí en StackOF, pero me ha valido verga :(

        final CharSequence[] items = {"Cambiar a nocturno", "Borrar ejercicio", "No hacer nada"};
        AlertDialog.Builder builder = new AlertDialog.Builder(elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV);
        builder.setTitle("Elija una acción");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                    case 0:
                        if(itemSeleccionado != -1){

                            // cambio en el arraylist
                            Ejercicio ejercicio = listaEjercicios.get(itemSeleccionado);
                            ejercicio.setNocturno( ! ejercicio.isNocturno());
                            //Del array listaEjercicios cogemos el item que tenemos marcado y lo invertimos a como estuviera (con el ! )
                            // cambio en la BBDD
                            elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV.bbdd.cambiarNocturno(ejercicio);
/*                      Otra forma de hacerlo:
                        listaEjercicios.get(itemSeleccionado).setNocturno( ! listaEjercicios.get(itemSeleccionado).isNocturno());
                        elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV.bbdd.cambiarNocturno(listaEjercicios.get(itemSeleccionado));*/
                        }else{
                            Toast.makeText(elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV, "Accion no disponible hasta que no seleccione un registro", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if(itemSeleccionado != -1){
                        elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV.borrarPaseo();
                        }else{
                            Toast.makeText(elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV, "Accion no disponible hasta que no seleccione un registro", Toast.LENGTH_SHORT).show();
                        }
                        break;


                }
                String elegido = items[item]+"";
                Toast.makeText(elContextoDeLaActividadEnLaQueSeEncuentraElAdapterDeMiRV, items[item], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

