/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author ilzk
 */
public class Casilla {

    //Atributos de la clase
    static private int carcel;
    private String nombre = "";
    private float importe = 0;
    //Relaciones con las demas clases
    TituloPropiedad tituloPropiedad;
    MazoSorpresas mazo;
    Sorpresa sorpresa;

    /**
     * Metodo que inicia los argumentos
     *
     */
    void init() {
        tituloPropiedad = null;
        sorpresa = null;
        mazo = null;
    }

    /**
     * Constructor de casillas tipo descanso
     *
     * @param _nombre
     */
    Casilla(String _nombre) {
        init();
        nombre = _nombre;
    }

    /**
     * Consultor del nombre de la casilla
     *
     * @return atributo nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Informa al diario de que ha caido un jugador en la casilla y da los datos
     * de la misma
     *
     * @param actual
     * @param todos
     */
    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla de caracteristicas " + toString());
    }

    /**
     * Comprueba que existe un jugador en todos en la posicion actual
     *
     * @param actual
     * @param todos
     * @return
     */
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (actual >= 0 && actual < todos.size());
    }

    /**
     * Llama al metodo recibe Jugador indicado para la casilla actual
     *
     * @param actual indice del jugador actual en el vector
     * @param todos vector con todos los jugadores
     */
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        informe(actual, todos);
    }

    @Override
    /**
     * Sobreescritura del metodo toString
     *
     * @return tipo de la casilla, nombre e importe
     */
    public String toString() {
        String devolver = "Casilla de nombre " + nombre;
        return devolver;
    }
}
