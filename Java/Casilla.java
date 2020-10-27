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
    
   //Atributos de la clase
    static private int carcel;
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
        tipo = TipoCasilla.CALLE;
        tituloPropiedad = titulo;
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
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla de caracteristicas " + toString());
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
    //Siguiente practica
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        
    }
    //Siguiente practica
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos){
        
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
    
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos){
        //siguiente practica
    }
    
    @Override
    /**Sobreescritura del metodo toString
     * 
     * @return tipo de la casilla, nombre e importe
     */
    public String toString() {
        String devolver = "La casilla es de tipo " + tipo.toString() + ", su nombre es " + nombre + " y su importe es " + importe;
        return devolver;
    }
}
