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

    //Creamos una clase llamada EjercicioSQLite cuyos argumentos son un contexto, un nombre, un cursor y una versión)
    public EjercicioSQLITE(@Nullable Context context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Con este método abro la base de datos ya creada y recojo su "referencia" (variable) (
    public void connect() {
        mibbdd = getWritableDatabase();
    }

    // Con este, por contra, le decimos que queremos salir ya de la base de datos.
    public void disconnect() {
        mibbdd.close();
    }

    //Este método nos entrega la base de datos qeu se acaba de crear pues este metodo solo se llama una vez
    // y es cuando se intanta accedr a la bbdd por primera vez. (quiere acceed, no existe, la crea y nos la entrega en db)
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Aquí dentro hacemos un acceso a datos donde le metemos a nuestra BBDD db un string llamado sql en cuyo interior están
        // los campos (y sus tipos de datos ) que luego se rellenarán con la información obtenida al crear un nuevo Ejercicio.
        // Todo importante-> El orden y la nomenclatura tienen que ser EXACTAMENTE IGUALES a como está puesto en el pojo Ejercicio.

        // creamos un Sgrin que es un texto, que congiene una ORDEN en lenguaje SQL, en este caso, la orden de CREAR UN TABLA (NO LA BBDD; una TABLA)
        String calabaza = "CREATE TABLE TABLA_EJERCICIOS (id INTEGER PRIMARY KEY," +
                "nombre TEXT," +
                "fecha TEXT," +
                "tipoactividad TEXT," +
                "distancia DOUBLE," +
                "nocturno INTEGER)";

        //ahora le decimos a la bbdd que nos han dado que ejecute (exec) la orden que hay en el String (que crea la/s tablas)
        db.execSQL(calabaza);
    }

    // Este método sirve para cuando queremos actualizar con datos la base de datos.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Normalmente aqui se llega cuando cambio la version de la bbdd, normalmetne por que cambiamos LA ESTRUCTURA de la bbdd
        // (añadimos columnas , o tablas , o lo que sea)
        // El procedimiento normal en este metodo es :

        // 1.- Crear la base de datos nuevamente (vacia) con la nueva estructura
        // 2.- Copiar los datros que nos interesen de lo que teniamos ya enla nueva estrucura
        // 3.- borro la bbdd antigua
        // 4.- renombro la nueva como la antigua
    }

    // Este método sirve para crear un nuevo ejercicio con el que rellenar un nuevo campo de nuestra BBDD
    // Se pone insertarEjercicio(Ejercicio ejercicioNuevo) porque recibimos un objeto Ejercicio llamado ejercicioNuevo
    // que es el que vamos a guardar en la base de datos.

    public void insertarEjercicio(Ejercicio ejercicioNuevo) {
        // Aquí estamos generando un ContentValues llamada valores que coja los datos que se hayan introducido
        // a la hora de crear el nuevo ejercicio.
        ContentValues valores = new ContentValues();
        valores.put("nombre", ejercicioNuevo.getNombre());
        valores.put("fecha", ejercicioNuevo.getFecha());
        valores.put("tipoactividad", ejercicioNuevo.getTipoActividad());
        valores.put("distancia", ejercicioNuevo.getDistancia());
        //Esta es la manera de llevar a código un booleano
        if (ejercicioNuevo.isNocturno()) {
            valores.put("nocturno", 1);
        } else {
            valores.put("nocturno", 0);
        }
        connect();
        //Aquí le decimos que rellene la tabla de nombre "TABLA_EJERCICIOS" con el contentValues que acabamos de crear llamado valores. Lo del nullhack, ignorarlo
        mibbdd.insert("TABLA_EJERCICIOS", null, valores);
        disconnect();
    }

    public void borrarEjercicio(Ejercicio ejercicioQueBorro) {
        connect();
        //Aquí estamos creando el método que nos permita borrar uno de los ejercicios que existen sabiendo su ID
        mibbdd.delete("TABLA_EJERCICIOS", "ID = " + ejercicioQueBorro.getId(), null);
        disconnect();
    }

    public void cambiarNocturno(Ejercicio ejercicioCambiadoNocturno) {
        connect();
        //Aquí estamos creando el método que nos permita borrar uno de los ejercicios que existen sabiendo su ID

        String[] interrogantesdelwhere = {ejercicioCambiadoNocturno.getId()+""};
        ContentValues datosRegistroModificado = new ContentValues();
        datosRegistroModificado.put("nocturno", ejercicioCambiadoNocturno.isNocturno());

        mibbdd.update("TABLA_EJERCICIOS", datosRegistroModificado, "id=?", interrogantesdelwhere);

        disconnect();
    }

/*
UPDATE TABLA_EJERCICIOS
SET NOCTURNO = TRU
WHERE sitio = 'La plaza' AND fecha="23423424" AND distancio > 234;




 */

    // Hacemos una consulta a la base de datos para que nos de todas las filas de la tabla ejercicios
    // Como cada fila es "un ejercicio", y va a traernos varias filass (varios ejercicios), los meteremos todos en una
    // coleccion (un arraylist p.e.)
    // Como aquí no vamos a recibir un Ejercicio sino todos ellos, no le especificamos nada en lo que recibe el método (ArrayList<Ejercicio> leerEjercicios())
    public ArrayList<Ejercicio> leerEjercicios() {
        // creamos vacia la coleccion de ejercicios que queremos rellenar con lo que nos de la BBDD

        ArrayList<Ejercicio> arrayDeEjerciciosLeidosDeLaBBDD = new ArrayList<>();
        connect();
        // TELA: esto es un array de Strings que contiene los nombres de los campos de la tabla de los que hago la consulta
        // --> no es lo mismo decir 'SELECT nombre FROM TABLA_EJERCICIOS' que 'SELECT nombre,fecha,id,distancia FROM ejercicios'
        // este array tiene los campos que voy a pedir
        String[] nombresDeLosCampos = {"id", "nombre", "fecha", "tipoactividad", "distancia", "nocturno"};
        // ejecutamos en la bbdd mibbdd el metodo query (que significa 'consulta' -como una select-)
        // al metodo le pasamos:
        // - nobre de la tabla
        // - array con los nomnres de los campos
        // el metodo devuelve un opbjeto de Cursor, que es un grupo de filas de la tabla, exactamente las que he consultado
        // Aqui estamos cogiendo TODOS los registros, si quisieramos solo, pongamos, los de distancia > 10, entonces en el null del parametro selection
        // debemos poner un String como este : "distancia > 10"
        Cursor listaDeFilas = mibbdd.query("TABLA_EJERCICIOS", nombresDeLosCampos, null, null, null, null, null);


        // con un bucle recorro todas las filas del Cursor   (que era el conjunto de registros leidos)
        // en cada vuelra del bucle se procesa una fila (un ejhercicio)
        // DETALLE: cuando se crea
        boolean heEncontradoUnaFila = listaDeFilas.moveToNext(); // mueve a la sifguiente file SI HAY, si no hay, devuelve FALSE
        while (heEncontradoUnaFila == true) {
            // aqui estoy si estoy apuntando a alguna fila
            // Todo importante-> El orden y la nomenclatura tienen que ser EXACTAMENTE IGUALES a como está puesto en el array de campos (String[] nombresDeLosCampos).
            //
            int elid = listaDeFilas.getInt(0);
            String elnombr = listaDeFilas.getString(1);
            String lafecha = listaDeFilas.getString(2);
            String eltipo = listaDeFilas.getString(3);
            double ladistancia = listaDeFilas.getDouble(4);
            int elnocturno = listaDeFilas.getInt(5);

            boolean elnoc;
            if (elnocturno == 1) {
                elnoc = true;
            } else {
                elnoc = false;
            }
            // elnoc =  (elnocturno==1) ? true: false;   // esto es con un operador if

            // POR FIN tenemos leidos de la fila todos los dartos necesarios para crear un Ejercicio!
            // ... creamos un Ejercicio
            Ejercicio eeee = new Ejercicio(elid, elnombr, lafecha, eltipo, ladistancia, elnoc);
            // añadimos el ejericicio que acabamos de crear en el arrauy de ejercicios
            arrayDeEjerciciosLeidosDeLaBBDD.add(eeee);
            heEncontradoUnaFila = listaDeFilas.moveToNext();// pruebo a moverme nuevamente a la siguiente file SI HAY, si no hay, devuelve FALSE
        }

        disconnect();
        return arrayDeEjerciciosLeidosDeLaBBDD;
    }


}
