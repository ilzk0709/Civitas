package civitas;

import java.util.Random;

public class Dado {

    private Random random;
    private int ultimoResultado;
    private boolean debug;
    static final private Dado instance = new Dado();
    final private int SalidaCarcel = 5;
    
    private Dado() {
        ultimoResultado = -1;
        debug = false;
        random = new Random();
    }

    static public Dado getInstance() {
        return instance;
    }

    int tirar() {
        if (!debug) {
            ultimoResultado = random.nextInt(6) + 1;
            return ultimoResultado;
        } 
        else
            return 1;
    }
    
    boolean salgoDeLaCarcel(){
        boolean salir = false;
        
        if(tirar() >= SalidaCarcel)
            salir = true;
        return salir;    
    }
    
    int quienEmpieza(int n) {
        int jug = random.nextInt(n);
        
        return jug;
    }
    
    public void setDebug(boolean d) {
        debug = d;
        Diario.getInstance().ocurreEvento("Debug mode: " + debug);
    }
    
    int getUltimoResultado() {
        return ultimoResultado;
    }

}
