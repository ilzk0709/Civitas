package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {

    private float importe;
    private TituloPropiedad titulo;

    public CasillaCalle(TituloPropiedad tit) {
        super(tit.getNombre());
        titulo = tit;
        importe = tit.getPrecioCompra();
    }

    public TituloPropiedad getTituloPropiedad() {
        return titulo;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPropiedad: " + titulo.toString();
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            Jugador jugador = todos.get(actual);
            if (!titulo.tienePropietario()) {
                jugador.puedeComprarCasilla();
            } else {
                titulo.tramitarAlquiler(jugador);
            }
        }
    }

}
