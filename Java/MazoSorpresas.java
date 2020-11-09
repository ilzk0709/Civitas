/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
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
    Sorpresa sorpresa = new Sorpresa();
    Random random = new Random();
    int ind = 0;
    if (!barajada || usadas == sorpresas.size()) 
        if (!debug) {
            for (int i = 0; i < sorpresas.size(); i++) {
                ind = random.nextInt(sorpresas.size());
                sorpresas.set(i, sorpresas.get(ind));
            }
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
            if (sorpresas.remove(sorpresa));
                cartasEspeciales.add(sorpresa);
                Diario.getInstance().ocurreEvento("Se ha aniadido " + sorpresa +
                        "al mazo de cartas especiales");
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
