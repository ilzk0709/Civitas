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
public class Sorpresa {
//Atributos de instancia
    private String texto = "";
    private int valor;
//Relaciones con las demas clases
    MazoSorpresas mazo;
    TipoSorpresa tipo;
    Tablero tablero;
    
    /**Hace nulas las referencias al mazo y al tablero y fija valor a -1
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
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        return (actual >= 0 && actual < todos.size());
    }
    /**Constructor por defecto
     * 
     */
    public Sorpresa(TipoSorpresa _tipo) {
        init();
        tipo = _tipo;
        if (tipo == TipoSorpresa.PAGARCOBRAR){
            //Falta ver cuando estos valores son negativos
            valor = 10;
        } else if (tipo == TipoSorpresa.PORCASAHOTEL){
            valor = 3;
        }
    }
    
    /**Constructor de sorpresa que crea la sorpresa que envia a la carcel
     * 
     * @param tipo el tipo de la sorpresa a crear
     * @param tablero el tablero del que se obtiene el numCasillaCarcel
     */
    
    public Sorpresa(TipoSorpresa _tipo, Tablero _tablero) {
        if (_tipo == TipoSorpresa.IRCARCEL) {
            init();
            tipo = _tipo;
            texto = "El/La jugador/a va a la carcel";
            tablero = _tablero;
            int numCarcel = tablero.getCarcel();
        }
        else
            System.err.println("Constructor inadecuado");
    }
    
    /**Constructor que crea la sorpresa que envia al jugador a otra casilla
     * 
     * @param tipo
     * @param tablero 
     * @param numCasilla 
     */
    public Sorpresa(TipoSorpresa _tipo, Tablero _tablero, int numCasilla) {
        if (_tipo == TipoSorpresa.IRCASILLA) {
            tipo = _tipo;
            valor = numCasilla;
            texto = "El/La jugador/a va a la casilla " + numCasilla; 
        }else
            System.err.println("Constructor inadecuado");
    }
    /**Constructor para la sorpresa que evita la carcel
     * 
     * @param tipo
     * @param mazo 
     */
    public Sorpresa(TipoSorpresa _tipo, MazoSorpresas _mazo) {
        if (_tipo == TipoSorpresa.SALIRCARCEL) {
            tipo = _tipo;
            texto = "El/La jugador/a puede salir de la carcel si cae en ella";
            init();
        }else
            System.err.println("Constructor inadecuado");
    }
    /**Indica al diario que se aplica una sorpresa a un jugador
     * 
     */ 
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se esta aplicando la sorpresa " + this.toString() + " al jugador " + todos.get(actual).getNombre());
    }
    /** Llama al metodo que aplica la sorpresa adecuada segun el valor atributo
     * 
     * @param actual
     * @param todos 
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
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
    
    /**Aplica la sorpresa Ir a casilla al jugador actual
     * 
     */
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos) {
        int casillaActual = 0, tirada = 0;
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            casillaActual = todos.get(actual).getNumCasillaActual();
            tirada = tablero.nuevaPosicion(casillaActual, valor);
            tablero.calcularTirada(casillaActual, valor);
            todos.get(actual).moverACasilla(tirada);
            tablero.getCasilla(tirada).recibeJugador();
        }
    }
    
    /**Aplica la sorpresa Ir a carcel al jugador actual
     * 
     */
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    /**Aplica la sorpresa Pagar cobrar al jugador actual
     * 
     */
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
    /**Aplica la sorpresa Por casa hotel al jugador actual
     * 
     */
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            for (int i = 0; i < todos.get(actual).cantidadCasasHoteles(); i++) {
                todos.get(actual).modificarSaldo(valor);   
            }
        }
    }
    /**Aplica la sorpresa Por jugador al jugador actual
     * 
     */
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)) {
            informe(actual,todos);
            Sorpresa quitar = new Sorpresa(TipoSorpresa.PAGARCOBRAR);
            
            Sorpresa dar = new Sorpresa(TipoSorpresa.PAGARCOBRAR);
            for (int i = 0; i < todos.size(); i++) {
                todos.get(actual).modificarSaldo(valor);
            }
        }
    }
    /**Aplica la sorpresa Salir carcel al jugador actual
     * 
     */
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            boolean tienen = false;
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).tieneSalvoconducto())
                    tienen = true;
            }
            if (!tienen)
                todos.get(actual).obtenerSalvoconducto(this);
            salirDelMazo();
        }
    }
    /**Inhabilita la carta del mazo si es del tipo SALIRCARCEL
     * 
     */
    void salirDelMazo() {
        if(tipo == TipoSorpresa.SALIRCARCEL){
            mazo.inhabilitarCartaEspecial(this);
        }
    }
    /**Habilita la carta en el mazo si es del tipo SALIRCARCEL
     * 
     */
    void usada() {
        if(tipo == TipoSorpresa.SALIRCARCEL){
            mazo.habilitarCartaEspecial(this);
        }
    }
    
    /**Sobreescribe el metodo toString
     * 
     */
    public String toString(){
        String devolver = "";
        switch (tipo){
            case IRCARCEL:
                devolver = "Ir carcel";
            case IRCASILLA:
                devolver = "Ir casilla";
            case PAGARCOBRAR:
                devolver = "Pagar cobrar";
            case PORCASAHOTEL:
                devolver = "Por casa hotel";
            case PORJUGADOR:
                devolver = "Por jugador";
            case SALIRCARCEL:
                devolver = "Salir carcel";
        }
        return devolver;
    }
}
