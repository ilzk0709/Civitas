package civitas;

/**
 *
 * @author ilzk
 */
public class OperacionInmobiliaria {
    
    private int numPropiedad;
    private GestionesInmobiliarias gestion;
    
    public GestionesInmobiliarias getGestion() {
        return gestion;
    }
    
    public int getNumPropiedad() {
        return numPropiedad;
    }
    
    public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip) {
        gestion =gest;
        numPropiedad = ip;
    }
}