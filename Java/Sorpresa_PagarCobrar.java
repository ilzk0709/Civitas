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
public class Sorpresa_PagarCobrar extends Sorpresa {
    
    public Sorpresa_PagarCobrar(){
        valor = 10;
    }
    
    @Override
    /**Aplica la sorpresa Pagar cobrar al jugador actual
     * 
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
    
    /**Sobreescribe el metodo toString
     * 
     */
    public String toString(){
        return "Pagar cobrar";
    }
}
