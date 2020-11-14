/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

/**
 *
 * @author ilzk
 */
public class CivitasJuego {
    
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private GestorEstados gestor;
    private EstadosJuego estado;
    private MazoSorpresas mazo;
    private Tablero tablero;
    
    /** Avanza Jugador
     * 
     */
    private void avanzaJugador() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorsalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorsalida(jugadorActual);
        jugadores.set(indiceJugadorActual, jugadorActual);
    }
    /**
     * Aplica el metodo cancelarHipoteca de Jugador al jugador actual en la propiedad de indice ip
     * @param ip indice de la propiedad de la cual cancelar la hipoteca
     * @return result, true si se ha aplicado el cambio
     */
    public boolean cancelarHipoteca(int ip) {
        boolean result = jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
        return result;
    }
    /**Constructor de la clase CivitasJuego
     * EstadosJuego
     */
    public CivitasJuego() {
        jugadores = new ArrayList<>();
        gestor = new GestorEstados();
        gestor.estadoInicial();
        mazo = new MazoSorpresas();
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        tablero = new Tablero(13);
    }
    
    public CivitasJuego(ArrayList<String> nombres) {
        jugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            jugadores.add(new Jugador(nombres.get(i)));
        }
        jugadores = new ArrayList<>();
        gestor = new GestorEstados();
        gestor.estadoInicial();
        mazo = new MazoSorpresas();
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        tablero = new Tablero(13);
    }
    /**Comprueba si el jugador actual puede comprar el titulo de la casilla en la que esta
     * 
     * @return res true si el jugador actual puede comprar el titulo
     */
    public boolean comprar() {
        boolean res = false;
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        res = jugadorActual.comprar(titulo);
        return res;
    }
    /**
     * Aplica el metodo construirCasa de jugador al jugador actual en la propiedad del indice ip
     * @param ip indice de la propiedad en la cual se va a construir una casa
     * @return result, true si se aplica el cambio
     */
    public boolean construirCasa(int ip) {
        boolean result = jugadores.get(indiceJugadorActual).construirCasa(ip);
        return result;
    }
    /**
     * Aplica el metodo construirHotel de jugador al jugador actual en la propiedad del indice ip
     * @param ip indice de la propiedad en la cual se va a construir un hotel
     * @return result, true si se aplica el cambio
     */
    public boolean construirHotel(int ip) {
        boolean result = jugadores.get(indiceJugadorActual).construirHotel(ip);
        return result;
    }
    /** Cuenta las veces que un jugador ha pasado por la salida y le paga por
     * las veces que ha pasado en el turno actual
     * 
     * @param jugadorActual 
     */
    private void contabilizarPasosPorsalida(Jugador jugadorActual) {
        while (tablero.getPorSalida() > 0) {
            jugadorActual.pasaPorSalida();
        }
    }
    /** Determina si el juego finaliza porque al menos un jugador esta en bancarrota y reordena el vector para obtener el ranking
     * 
     * @return true si termina el juego, false si sigue
     */
    public boolean finalDelJuego() {
        boolean fin = false;
        for (int i = 0; i < jugadores.size() && !fin; i++) {
            if (jugadores.get(i).getSaldo()== 0) {
                fin = true;
                jugadores = ranking();
            }
        }
        return fin;
    }
    
    /** Devuelve el objeto de la casilla donde esta el jugador actual
     * 
     * @return Casilla del tablero donde esta el jugador actual
     */
    
    public Casilla getCasillaActual() {
        int indice = jugadores.get(indiceJugadorActual).getNumCasillaActual();
        return tablero.getCasilla(indice);
    }
    
    /** Devuelve el objeto del jugador actual
     * 
     * @return Jugador en la posicion indiceJugadorActual de jugadores
     */
    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }
    /**Aplica el metodo hipotecar de jugador al jugador acutal en la propiedad del indice ip
     * 
     * @param ip indice de la propiedad que se va a hipotecar
     * @return result, true si se aplica el cambio
     */
    public boolean hipotecar(int ip) {
        boolean result = jugadores.get(indiceJugadorActual).hipotecar(ip);
        return result;
    }
    /**Muestra los datos del jugador actual a traves del toString()
     * 
     * @return La informacion "raw" del jugador actual
     */
    public String infoJugadorTexto() {
        return jugadores.get(indiceJugadorActual).toString();
    }
    /**Inicializa el mazo creado en el constructor
     * 
     * @param tablero 
     */
    private void inicializarMazoSorpresas(Tablero tablero) {
        Sorpresa ircarcel = new Sorpresa(TipoSorpresa.IRCARCEL, tablero); 
        mazo.alMazo(ircarcel);
        mazo.habilitarCartaEspecial(ircarcel);
        Sorpresa ircasilla = new Sorpresa(TipoSorpresa.IRCASILLA, tablero); 
        mazo.alMazo(ircasilla);
        mazo.habilitarCartaEspecial(ircasilla);
        Sorpresa pagarcobrar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, tablero); 
        mazo.alMazo(pagarcobrar);
        mazo.habilitarCartaEspecial(pagarcobrar);
        Sorpresa porcasahotel = new Sorpresa(TipoSorpresa.PORCASAHOTEL, tablero); 
        mazo.alMazo(porcasahotel);
        mazo.habilitarCartaEspecial(porcasahotel);
        Sorpresa porjugador = new Sorpresa(TipoSorpresa.PORJUGADOR, tablero); 
        mazo.alMazo(porjugador);
        mazo.habilitarCartaEspecial(porjugador);
        Sorpresa salircarcel = new Sorpresa(TipoSorpresa.SALIRCARCEL, tablero); 
        mazo.alMazo(salircarcel);
        mazo.habilitarCartaEspecial(salircarcel);
    }
    /** Inicializa el tablero y aniade las casillas
     * 
     * 
     */
    private void inicializarTablero(MazoSorpresas _mazo) {
        tablero = new Tablero(10);
        for (int i = 0; i < 20; i++) {
            tablero.aniadeCasilla(new Casilla("casilla"));
        }
    }
    /** Pasa al siguiente jugador
     * 
     */
    private void pasarTurno() {
        indiceJugadorActual++;
        if (indiceJugadorActual == jugadores.size())
            indiceJugadorActual = 0;
    }
    /** Ordena los jugadores segun su saldo
     * 
     * @return El vector ordenado de jugadores segun su saldo
     */
    private ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ranking = new ArrayList<>();
        int comprobante = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            comprobante = jugadores.get(i).compareTo(jugadores.get(i+1));
            if (comprobante == 1)
                ranking.add(jugadores.get(i));
            else
                ranking.add(jugadores.get(i+1));
        }
        return ranking;
    }
    /** Llama al metodo salirCarcelPagando de jugador
     * 
     * @return result true si se aplica el cambio
     */
    public boolean salirCarcelPagando() {
        boolean result = jugadores.get(indiceJugadorActual).salirCarcelPagando();
        return result;
    }
    /** Llama al metodo salirCarcelTirando de jugador
     * 
     * @return result true si se aplica el cambio
     */
    public boolean salirCarcelTirando() {
        boolean result = jugadores.get(indiceJugadorActual).salirCarcelTirando();
        return result;
    }
    /**Realiza la accion necesaria en caso de que la operacion en curso sea pasar turno o avanzar
     * 
     * @return operacion el objeto con la operacion realizada
     */
    public OperacionesJuego siguientePaso() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestor.operacionesPermitidas(jugadorActual, estado);
        if (operacion == OperacionesJuego.PASAR_TURNO) {
            pasarTurno();
            siguientePasoCompletado(operacion);
        } else if (operacion == OperacionesJuego.AVANZAR) {
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        return operacion;
    }
    /** Actualiza el estado del juego despues de avanzar
     * 
     * @param operacion 
     */
    public void siguientePasoCompletado(OperacionesJuego operacion) {
        gestor.siguienteEstado(jugadores.get(indiceJugadorActual), EstadosJuego.DESPUES_AVANZAR, operacion);
    }
    /**Aplica el metodo vender al jugador actual en la propiedad del indice ip
     * 
     * @param ip indice de la propiedad que se va a vender
     * @return  result, true si se aplica el cambio
     */
    public boolean vender (int ip) {
        boolean result = jugadores.get(indiceJugadorActual).vender(ip);
        return result;
    }
    
    
}
