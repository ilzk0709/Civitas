package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {
    private MazoSorpresas mazo;

    public CasillaSorpresa(MazoSorpresas _mazo, String nombre) {
        super(nombre);
        mazo = _mazo;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLa sorpresa que saldrá es:\n" + sorpresa.toString() + "\n";
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            sorpresa = mazo.siguiente();
            this.informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
