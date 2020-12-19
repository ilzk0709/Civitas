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
public class Sorpresa_IrCarcel extends Sorpresa {
    
    /**Constructor de sorpresa que crea la sorpresa que envia a la carcel
     * 
     * @param _tipo el tipo de la sorpresa a crear
     * @param _tablero el tablero del que se obtiene el numCasillaCarcel
     */
    
    public Sorpresa_IrCarcel(Tablero _tablero) {
            texto = "El jugador va a la carcel";
            tablero = _tablero;
            valor = tablero.getCarcel();
    }
    
    @Override
    /**Aplica la sorpresa Ir a carcel al jugador actual
     * 
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    /**Sobreescribe el metodo toString
     * 
     */
    public String toString(){
        return "Ir carcel";
    }
}
