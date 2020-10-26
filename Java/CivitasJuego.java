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
    private MazoSorpresas mazo;
    private Tablero tablero;
    
    /** Avanza Jugator
     * @
     */
    private void avanzaJugador() {
        //Se hace en la siguiente practica
    }
    
    public boolean cancelarHipoteca(int ip) {
        //Depende del metodo del mismo nombre en jugador
        return false;
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
    
    public boolean comprar() {
        //Siguiente practica
        return false;
    }
    
    public boolean construirCasa(int ip) {
        //Depende del metodo en jugador que se llama igual
        return false;
    }
    
    public boolean construirHotel(int ip) {
        //Depende del metodo en jugador con el mismo nombre
        return false;
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
    /** Determina si el juego finaliza porque al menos un jugador esta en bancarrota
     * 
     * @return true si termina el juego, false si sigue
     */
    public boolean finalDelJuego() {
        boolean fin = false;
        for (int i = 0; i < jugadores.size() && !fin; i++) {
            if (jugadores.get(i).getSaldo()== 0)
                fin = true;
        }
        return false;
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
    
    public boolean hipotecar(int ip) {
        //Depende del metodo del mismo nombre en jugador
        return false;
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
            tablero.aniadeCasilla(new Casilla());
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
     * @return El vector ordenado de jugadores segun su sueldo
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
    
    public boolean salirCarcelPagando() {
        return false;
    }
    
    public boolean salirCarcelTirando() {
        return false;
    }
    
    public OperacionesJuego siguientePaso() {
        return OperacionesJuego.AVANZAR;
    }
    /** Actualiza el estado del juego despues de avanzar
     * 
     * @param operacion 
     */
    public void siguientePasoCompletado(OperacionesJuego operacion) {
        gestor.siguienteEstado(jugadores.get(indiceJugadorActual), EstadosJuego.DESPUES_AVANZAR, operacion);
    }
    
    public boolean vender (int ip) {
        
        return false;
    }
    
    
}
