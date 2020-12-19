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
public class Sorpresa_PorJugador extends Sorpresa {
    
    public Sorpresa_PorJugador(){
        valor = 10;
    }
    
    @Override
    /**Aplica la sorpresa Por jugador al jugador actual
     * 
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            Sorpresa quitar = new Sorpresa_PagarCobrar();
            quitar.valor = -valor;
                for (int i = 0; i < todos.size(); i++) {
                    if (i != actual)
                        quitar.aplicarAJugador(i, todos);
                }
            Sorpresa dar = new Sorpresa_PagarCobrar();
            dar.valor = valor * todos.size();
            dar.aplicarAJugador(actual, todos);
        }
    }
    
    /**Sobreescribe el metodo toString
     * 
     */
    public String toString(){
        return "Por jugador";
    }
}
