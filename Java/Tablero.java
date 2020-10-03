
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
/**
 *
 * @author roberro
 */
public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    public Tablero(int carcel) {
        numCasillaCarcel = carcel;
        casillas = new ArrayList<Casilla>();
        // Se puede aniadir directamente haciendo la declaracion en el argumento?
        casillas.add(new Casilla("Salida"));
        porSalida = 0;
        tieneJuez = false;
    }
    
    private boolean correcto() {
        return (casillas.size() > numCasillaCarcel);
    }
    
    private boolean correcto (int numCasilla) {
        boolean indiceValido = 0 <= numCasilla && numCasilla < casillas.size();
        return (this.correcto() && indiceValido);
    }
    
    public int getCarcel() {
        return numCasillaCarcel;
    }
    
    public int getPorSalida() {
        int numero = porSalida;
        if (porSalida > 0)
            porSalida--;
        return numero;
    }
    
    public void aniadeCasilla(Casilla casilla) {
        if (casillas.size() == numCasillaCarcel)
            casillas.add(new Casilla("Carcel"));
        casillas.add(casilla);
        if (casillas.size() == numCasillaCarcel)
            casillas.add(new Casilla("Carcel"));
    }
    
    public void aniadeJuez() {
        if (!tieneJuez) {
            casillas.add(new Casilla("JUEZ"));
            tieneJuez = true;
        }
    }
    
    public Casilla getCasilla(int numCasilla) {
        Casilla temporal = new Casilla();
        if (correcto(numCasilla))
            temporal = casillas.get(numCasilla);
        //Falta ver que es que devuelva null en caso de no ser numC valido
        return temporal;
    }
    
    public int nuevaPosicion(int actual, int tirada) {
        int posicion = -1;
        if (correcto()) {
            posicion = (actual + tirada) % casillas.size();
            if (posicion != actual + tirada)
                porSalida++;
        }
        return posicion;
    }
    
    public int calcularTirada (int origen, int destino) {
        int tirada = destino - origen;
        if (tirada < 0)
            tirada = tirada + casillas.size();
        return tirada;
    }
}
