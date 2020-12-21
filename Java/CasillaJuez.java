package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla {

    private static int carcel;

    public CasillaJuez(int _carcel, String nombre) {
        super(nombre);
        carcel = _carcel;
    }

    @Override
    public String toString() {
        return super.toString() + "\nJugador enviado a la cárcel\n";
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
}
