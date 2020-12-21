/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author roberro
 */
abstract class Sorpresa {
//Atributos de instancia
    protected String texto = "";
    protected int valor;
//Relaciones con las demas clases
    MazoSorpresas mazo;
    Tablero tablero;
    
    /**Hace nulas las referencias al mazo y al tablero y fija valor a -1
     * 
     */
    void init() {
        tablero = null;
        mazo = null;
        valor = -1;
    }
    /**
     * 
    */
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        return (actual >= 0 && actual < todos.size());
    }
    /**Constructor por defecto
     * 
     */
    public Sorpresa() {
        init();
    }
    /**Indica al diario que se aplica una sorpresa a un jugador
     * 
     */ 
    protected void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se esta aplicando la sorpresa " + this.toString() + " al jugador " + todos.get(actual).getNombre());
    }
    /** Llama al metodo que aplica la sorpresa adecuada segun el valor atributo
     * 
     * @param actual
     * @param todos 
     */
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
}
