/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author roberro
 */
public class MazoSorpresas {
    
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    private void init() {
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }
    
    public MazoSorpresas(boolean d) {
        debug = d;
        init();
        if (debug == true)
            Diario.getInstance().ocurreEvento("Se ha creado el mazo de sorpresas");
    }
    
    public MazoSorpresas() {
        init();
        debug = false;
    }
    
    public void alMazo(Sorpresa s) {
        if (!barajada) {
            sorpresas.add(s);
        }
    }
    
    public Sorpresa siguiente() {
    Sorpresa sorpresa = new Sorpresa_PagarCobrar();                             //No se que tipo poner
    if (!barajada || usadas == sorpresas.size()) 
        if (!debug) {
            Collections.shuffle(sorpresas);
            usadas = 0;
            barajada = true;
        }
    usadas++;
    sorpresa = sorpresas.get(0);
    ultimaSorpresa = sorpresas.get(0);
    sorpresas.remove(0);
    sorpresas.add(sorpresa);
    return sorpresa;   
    }
    
    public void inhabilitarCartaEspecial(Sorpresa sorpresa) {
        while (sorpresas.contains(sorpresa)) {
                int indice = sorpresas.lastIndexOf(sorpresa);
                sorpresas.remove(indice);
                cartasEspeciales.add(sorpresa);
                Diario.getInstance().ocurreEvento("Se ha aniadido " + sorpresa +
                        "[" + indice + "] al mazo de cartas especiales");
        }
    }
    
    public void habilitarCartaEspecial(Sorpresa sorpresa) {
        if (cartasEspeciales.remove(sorpresa)) {
            sorpresas.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha aniadido " + sorpresa +
"                        al mazo de sorpresas");
        }
    }
}
