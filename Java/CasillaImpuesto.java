package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla {

    private float importe;

    public CasillaImpuesto(float impor, String nombre) {
        super(nombre);
        importe = impor;
    }

    @Override
    public String toString() {
        return super.toString() + "\nImpuesto de: " + importe + "\n";
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
}
