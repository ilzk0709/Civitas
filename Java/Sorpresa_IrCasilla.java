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
public class Sorpresa_IrCasilla extends Sorpresa {
    
    /**Constructor que crea la sorpresa que envia al jugador a otra casilla
     * 
     * @param tipo
     * @param tablero 
     * @param numCasilla 
     */
    public Sorpresa_IrCasilla(Tablero _tablero, int numCasilla) {
            valor = numCasilla;
            tablero = _tablero;
            texto = "El jugador va a la casilla " + numCasilla;
    }
    
    @Override
    
    /**Aplica la sorpresa Ir a casilla al jugador actual
     * 
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        int casillaActual, tirada;
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            casillaActual = todos.get(actual).getNumCasillaActual();
            tirada = tablero.nuevaPosicion(casillaActual, valor);
            tablero.calcularTirada(casillaActual, valor);
            todos.get(actual).moverACasilla(tirada);
            tablero.getCasilla(tirada).recibeJugador(actual,todos);
        }
    }
    
    /**Sobreescribe el metodo toString
     * 
     */
    public String toString(){
        return "Ir casilla";
    }
}
