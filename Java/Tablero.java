package civitas;

import java.util.ArrayList;

public class Tablero {

    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;

    public Tablero(int n) {
        if (n >= 1) {
            numCasillaCarcel = n;
        } else {
            numCasillaCarcel = 1;
        }

        casillas = new ArrayList<>();
        Casilla obj = new Casilla("Salida");
        casillas.add(obj);

        porSalida = 0;
        tieneJuez = false;
    }

    private boolean correcto() {
        boolean correct = false;

        if (casillas.size() > numCasillaCarcel && tieneJuez) {
            correct = true;
        }

        return correct;
    }

    private boolean correcto(int numCasilla) {
        boolean correct = false;

        if (correcto() && casillas.size() > numCasilla) {
            correct = true;
        }

        return correct;
    }

    int getCarcel() {
        return numCasillaCarcel;
    }

    int getPorSalida() {
        int valorPorSalida = porSalida;

        if (porSalida > 0) {
            porSalida -= 1;
        }

        return valorPorSalida;
    }

    void añadeCasilla(Casilla casilla) {
        boolean añadir_cárcel = false;
        if (casillas.size() == numCasillaCarcel) {
            Casilla c = new Casilla("Cárcel");
            casillas.add(c);
            añadir_cárcel = true;
        }

        casillas.add(casilla);

        if (!añadir_cárcel && casillas.size() == numCasillaCarcel) {
            Casilla c = new Casilla("Cárcel");
            casillas.add(c);
        }
    }

    void añadeJuez() {
        if (!tieneJuez) {
            añadeCasilla(new CasillaJuez(numCasillaCarcel, "Juez"));
            tieneJuez = true;
        }
    }

    Casilla getCasilla(int numCasilla) {
        if (correcto(numCasilla)) {
            return casillas.get(numCasilla);
        } else {
            return null;
        }
    }

    int nuevaPosicion(int actual, int tirada) {
        int posicion = -1;

        if (correcto()) {
            posicion = (actual + tirada) % 20;
        }

        if (posicion != actual + tirada) {
            porSalida++;
        }

        return posicion;
    }

    int calcularTirada(int origen, int destino) {
        int tirada = destino - origen;

        if (tirada < 0) {
            tirada += 20;
        }

        return tirada;
    }
}
