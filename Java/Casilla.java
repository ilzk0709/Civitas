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
public class Casilla {
    
    static private int carcel;
   //Atributos de instancia
    private String nombre = "";
    private float importe = 0;
   //Relaciones con las demas clases
    TipoCasilla tipo;
    TituloPropiedad tituloPropiedad;
    MazoSorpresas mazo;
    Sorpresa sorpresa;
    
    /**Metodo que inicia los argumentos
     * 
     */
    void init() {
        tituloPropiedad = null;
        sorpresa = null;
        mazo = null;
    }
    
    /**Constructor de casillas tipo descanso
     * 
     * @param _nombre 
     */
    Casilla(String _nombre) {
        init();
        tipo = TipoCasilla.DESCANSO;
        nombre = _nombre;
    }
     
    /**Constructor para las casillas de tipo calle
     * 
     * @param titulo 
     */
    Casilla(TituloPropiedad titulo) {
        init();
        nombre = titulo.getNombre();
        tipo = TipoCasilla.CALLE;
        tituloPropiedad = titulo;
        importe = titulo.getPrecioCompra();
    }
    
    /**Constructor para las casillas de tipo impuesto
     * 
     * @param cantidad
     * @param _nombre 
     */
    Casilla(float cantidad, String _nombre) {
        init();
        tipo = TipoCasilla.IMPUESTO;
        nombre = _nombre;
        importe = cantidad;
    }
    
    /**Constructor para las casillas de tipo juez
     * 
     * @param numCasillaCarcel
     * @param _nombre 
     */
    Casilla(int numCasillaCarcel, String _nombre) {
        init();
        tipo = TipoCasilla.JUEZ;
        carcel = numCasillaCarcel;
        nombre = _nombre;
    }
    
    /**Constructor para las casillas tipo sorpresa
     * 
     * @param mazo
     * @param _nombre 
     */
    Casilla(MazoSorpresas _mazo, String _nombre) {
        init();
        tipo = TipoCasilla.SORPRESA;
        mazo = _mazo;
        nombre = _nombre;
    }
    
    /** Consultor del nombre de la casilla
     * 
     * @return atributo nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**Consultor del Titulo Propiedad de la casilla
     * 
     * @return El objeto TituloPropiedad de la casilla
     */
    TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }
    
    /**Informa al diario de que ha caido un jugador en la casilla y da los datos de la misma
     * 
     * @param actual
     * @param todos 
     */
    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + 
                " ha caido en la casilla " + todos.get(actual).getNumCasillaActual() +
                "\n" + toString());
    }
    
    /**Comprueba que existe un jugador en todos en la posicion actual
     * 
     * @param actual
     * @param todos
     * @return 
     */
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (actual >= 0 && actual < todos.size());
    }
    /**Llama al metodo recibeJugador indicado para la casilla actual
     * 
     * @param actual indice del jugador actual en el vector
     * @param todos  vector con todos los jugadores
     */
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        switch (tipo) {
            case CALLE:
                recibeJugador_calle(actual, todos);
                break;
            case IMPUESTO:
                recibeJugador_impuesto(actual, todos);
                break;
            case JUEZ:
                recibeJugador_juez(actual, todos);
                break;
            case SORPRESA:
                recibeJugador_sorpresa(actual, todos);
                break;
            case DESCANSO:
                informe(actual, todos);
                break;
        }
    }
    
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Jugador jugador = todos.get(actual);
            if (!tituloPropiedad.tienePropietario()) {
                jugador.puedeComprarCasilla();
            } else {
                tituloPropiedad.tramitarAlquiler(jugador);
            }
            todos.set(actual, jugador);
        }
    }
    
    /**Hace que el jugador pague un impuesto
     * 
     * @param actual
     * @param todos 
     */
    private void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
    /**Encarcela al jugador
     * 
     * @param actual
     * @param todos 
     */
    private void recibeJugador_juez(int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
    /**Realiza las acciones necesarias a un jugador que cae en una casilla de tipo sorpresa
     * 
     * @param actual indice del jugador actual en el vector
     * @param todos vector con todos los jugadores
     */
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos)) {
            sorpresa = mazo.siguiente();
            informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
    
    @Override
    /**Sobreescritura del metodo toString
     * 
     * @return tipo de la casilla, nombre e importe
     */
    public String toString() {
        String devolver = "Tipo: " + tipo.toString() + "\nNombre: " + nombre + "\nImporte: " + importe;
        return devolver;
    }
}
