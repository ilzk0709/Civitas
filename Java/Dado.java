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
public class Dado {
    static final private Dado instance = new Dado();
    static final private int SalidaCarcel = 5;
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    private Dado(){
        random = new Random();
        ultimoResultado = 0;
        debug = false;
    }
    
    static public Dado getInstance() {
        return instance;
    }
    
    public int tirar() {
        int tirada = 1;
        if (!debug)
            tirada = random.nextInt(6) + 1;
        ultimoResultado = tirada;
        return tirada;
    }
    
    public boolean salgoDeLaCarcel() {
        boolean sale = false;
        int tirada = tirar();
        if (tirada >= 5)
            sale = true;
        return sale;
    }
    
    public int quienEmpieza(int n) {
        int numjugador = random.nextInt(n);
        return numjugador;
    }
    
    public void setDebug(boolean d) {
        debug = d;
        if (d == true)
            Diario.getInstance().ocurreEvento("Se ha activado el modo debug");
        else if (d == false)
            Diario.getInstance().ocurreEvento("Se ha desactivado el modo debug");
    }
    
    public int getUltimoResultado() {
        return ultimoResultado;
    }
}
