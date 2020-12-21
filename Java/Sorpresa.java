package civitas;

import java.util.ArrayList;

/**
 *
 * @author roberro
 */
public class Sorpresa {
//Atributos de instancia

    private String texto = "";
    private int valor;
//Relaciones con las demas clases
    MazoSorpresas mazo;
    TipoSorpresa tipo;
    Tablero tablero;

    /**
     * Hace nulas las referencias al mazo y al tablero y fija valor a -1
     *
     */
    void init() {
        tablero = null;
        mazo = null;
        valor = -1;
    }

    /**
     *
     */
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (actual >= 0 && actual < todos.size());
    }

    /**
     * Constructor por defecto
     *
     */
    public Sorpresa(TipoSorpresa _tipo) {
        init();
        tipo = _tipo;
        if (tipo == TipoSorpresa.PAGARCOBRAR) {
            valor = 10;
        } else if (tipo == TipoSorpresa.PORCASAHOTEL) {
            valor = 10;
        } else if (tipo == TipoSorpresa.PORJUGADOR) {
            valor = 10;
        }
    }

    /**
     * Constructor de sorpresa que crea la sorpresa que envia a la carcel
     *
     * @param _tipo el tipo de la sorpresa a crear
     * @param _tablero el tablero del que se obtiene el numCasillaCarcel
     */
    public Sorpresa(TipoSorpresa _tipo, Tablero _tablero) {
        if (_tipo == TipoSorpresa.IRCARCEL) {
            init();
            tipo = _tipo;
            texto = "El jugador va a la carcel";
            tablero = _tablero;
            valor = tablero.getCarcel();
        } else {
            System.err.println("Constructor inadecuado");
        }
    }

    /**
     * Constructor que crea la sorpresa que envia al jugador a otra casilla
     *
     * @param tipo
     * @param tablero
     * @param numCasilla
     */
    public Sorpresa(TipoSorpresa _tipo, Tablero _tablero, int numCasilla) {
        if (_tipo == TipoSorpresa.IRCASILLA) {
            init();
            tipo = _tipo;
            valor = numCasilla;
            tablero = _tablero;
            texto = "El jugador va a la casilla " + numCasilla;
        } else {
            System.err.println("Constructor inadecuado");
        }
    }

    /**
     * Constructor para la sorpresa que evita la carcel
     *
     * @param tipo
     * @param mazo
     */
    public Sorpresa(TipoSorpresa _tipo, MazoSorpresas _mazo) {
        if (_tipo == TipoSorpresa.SALIRCARCEL) {
            init();
            tipo = _tipo;
            texto = "El jugador puede salir de la carcel si cae en ella";
            mazo = _mazo;
        } else {
            System.err.println("Constructor inadecuado");
        }
    }

    /**
     * Indica al diario que se aplica una sorpresa a un jugador
     *
     */
    private void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se esta aplicando la sorpresa " + this.toString() + " al jugador " + todos.get(actual).getNombre());
    }

    /**
     * Llama al metodo que aplica la sorpresa adecuada segun el valor atributo
     *
     * @param actual
     * @param todos
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        switch (tipo) {
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual, todos);
                break;
            case IRCARCEL:
                aplicarAJugador_irCarcel(actual, todos);
                break;
            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual, todos);
                break;
            case PORCASAHOTEL:
                aplicarAJugador_porCasaHotel(actual, todos);
                break;
            case PORJUGADOR:
                aplicarAJugador_porJugador(actual, todos);
                break;
            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual, todos);
                break;
        }
    }

    /**
     * Aplica la sorpresa Ir a casilla al jugador actual
     *
     */
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos) {
        int casillaActual = 0, tirada = 0;
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            casillaActual = todos.get(actual).getNumCasillaActual();
            tirada = tablero.nuevaPosicion(casillaActual, valor);
            tablero.calcularTirada(casillaActual, valor);
            todos.get(actual).moverACasilla(tirada);
            tablero.getCasilla(tirada).recibeJugador(actual, todos);
        }
    }

    /**
     * Aplica la sorpresa Ir a carcel al jugador actual
     *
     */
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    /**
     * Aplica la sorpresa Pagar cobrar al jugador actual
     *
     */
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }

    /**
     * Aplica la sorpresa Por casa hotel al jugador actual
     *
     */
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            for (int i = 0; i < todos.get(actual).cantidadCasasHoteles(); i++) {
                todos.get(actual).modificarSaldo(valor);
            }
        }
    }

    /**
     * Aplica la sorpresa Por jugador al jugador actual
     *
     */
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Sorpresa quitar = new Sorpresa(TipoSorpresa.PAGARCOBRAR);
            quitar.valor = -valor;
            for (int i = 0; i < todos.size(); i++) {
                if (i != actual) {
                    quitar.aplicarAJugador(i, todos);
                }
            }
            Sorpresa dar = new Sorpresa(TipoSorpresa.PAGARCOBRAR);
            dar.valor = valor * todos.size();
            dar.aplicarAJugador(actual, todos);
        }
    }

    /**
     * Aplica la sorpresa Salir carcel al jugador actual
     *
     */
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            boolean tienen = false;
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).tieneSalvoconducto()) {
                    tienen = true;
                }
            }
            if (!tienen) {
                todos.get(actual).obtenerSalvoconducto(this);
            }
            salirDelMazo();
        }
    }

    /**
     * Inhabilita la carta del mazo si es del tipo SALIRCARCEL
     *
     */
    void salirDelMazo() {
        if (tipo == TipoSorpresa.SALIRCARCEL) {
            mazo.inhabilitarCartaEspecial(this);
        }
    }

    /**
     * Habilita la carta en el mazo si es del tipo SALIRCARCEL
     *
     */
    void usada() {
        if (tipo == TipoSorpresa.SALIRCARCEL) {
            mazo.habilitarCartaEspecial(this);
        }
    }

    /**
     * Sobreescribe el metodo toString
     *
     */
    public String toString() {
        String devolver = "";
        switch (tipo) {
            case IRCARCEL:
                devolver = "Ir carcel";
                break;
            case IRCASILLA:
                devolver = "Ir casilla";
                break;
            case PAGARCOBRAR:
                devolver = "Pagar cobrar";
                break;
            case PORCASAHOTEL:
                devolver = "Por casa hotel";
                break;
            case PORJUGADOR:
                devolver = "Por jugador";
                break;
            case SALIRCARCEL:
                devolver = "Salir carcel";
                break;
        }
        return devolver;
    }
}
