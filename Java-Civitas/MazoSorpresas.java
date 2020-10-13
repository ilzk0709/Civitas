package civitas;

import java.util.ArrayList;
import java.util.Collections;
        
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
        usadas = 0;
        barajada = false;
    }
    
    public MazoSorpresas() {
        init();
        debug = false;
    }
    
    public MazoSorpresas(boolean d) {
        debug = d;
        init(); 
        Diario.getInstance().ocurreEvento("Debug Mode:" + debug);
    }
    
    void alMazo(Sorpresa s) {
        if(!barajada)
            sorpresas.add(s);
    }
    
    Sorpresa siguiente() {
        if((!barajada || usadas == sorpresas.size()) && !debug){
            Collections.shuffle(sorpresas);
            barajada = true;
            usadas = 0;
            usadas++;
            Sorpresa temp = sorpresas.get(0);
            sorpresas.remove(0);
            sorpresas.add(temp);
            ultimaSorpresa = temp;
        }
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa) {
        if(sorpresas.contains(sorpresa)){
            sorpresas.remove(sorpresa);
            cartasEspeciales.add(sorpresa);
            Diario.getInstance().ocurreEvento(sorpresa + "Carta aniadida a cartas especiales");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa) {
        if(cartasEspeciales.contains(sorpresa)){
            cartasEspeciales.remove(sorpresa);
            sorpresas.add(sorpresa);
             Diario.getInstance().ocurreEvento(sorpresa + "Carta aniadida a sorpresas");
        }
    }
}
