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
public class Sorpresa_SalirCarcel extends Sorpresa{
    
    /**Constructor para la sorpresa que evita la carcel
     * 
     * @param _mazo mazo al que pertenece
     */
    public Sorpresa_SalirCarcel(MazoSorpresas _mazo) {
            texto = "El jugador puede salir de la carcel si cae en ella";
            mazo = _mazo;
    }
    
    /**Inhabilita la carta del mazo si es del tipo SALIRCARCEL
     * 
     */
    void salirDelMazo() {
            mazo.inhabilitarCartaEspecial(this);
    }
    /**Habilita la carta en el mazo si es del tipo SALIRCARCEL
     * 
     */
    void usada() {
            mazo.habilitarCartaEspecial(this);
    }
    
    @Override
    /**Aplica la sorpresa Salir carcel al jugador actual
     * 
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            boolean tienen = false;
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).tieneSalvoconducto())
                    tienen = true;
            }
            if (!tienen)
                todos.get(actual).obtenerSalvoconducto(this);
            salirDelMazo();
        }
    }
    
    /**Sobreescribe el metodo toString
     * 
     */
    public String toString(){
        return "Salir carcel";
    }
}
