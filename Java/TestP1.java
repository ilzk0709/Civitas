/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author roberro
 */
public class TestP1 {
    
    public void quienProb(int iter, double[] probabilidades) {
        
    }
    
    public static void main (String args[]) {
        
        boolean funciona = true;
        int resultado = 0, suma = 0, carcel = 7, casillas = 20, posicion = 0;
        int iter = 100;
        double probabilidades[] = {0,0,0,0};
        Sorpresa s = new Sorpresa(), s1 = new Sorpresa();
        MazoSorpresas mazo = new MazoSorpresas();
        Tablero tablero = new Tablero(carcel);
        
        
        for (int i = 0; i < iter; i++) {
            resultado = Dado.getInstance().quienEmpieza(probabilidades.length);
            //Los valores van del 0 al 3 porque son los índices del vector
            //de probabilidades
            probabilidades[resultado]++;
        }
        
        System.out.println("Veces: " + probabilidades.toString());
        
        for (int i = 0; i < probabilidades.length; i++) {
            probabilidades[i] = probabilidades[i]/iter;
        }
        
        System.out.println("Probabilidades: " + probabilidades.toString());
        
        Dado.getInstance().setDebug(true);
        for (int i = 0; i < iter; i++) {
            resultado = Dado.getInstance().tirar();
            //El modo debug implica que siempre sale 1, si sale otro numero, falla
            if (resultado != 1)
                funciona = false;
        }
        //Para comprobar si funciona desactivar el debug, ponemos resultado a 0
        Dado.getInstance().setDebug(false);
        resultado = 0;
        for (int i = 0; i < iter; i++) {
            //Vamos a sumar todos los resultados de las tiradas, si son distintos
            //de 1 la suma deberia ser mayor que el numero de pruebas. Aunque 
            // este metodo fallaria el caso en el que salga todo 1 por casualidad
            resultado = Dado.getInstance().tirar();
            suma += resultado;
        }
        if (suma == iter)
                funciona = false;
        
    //*******************Funciona************************
    
        if (Dado.getInstance().salgoDeLaCarcel())
            System.out.println("Sales de la cárcel");
        else if (!Dado.getInstance().salgoDeLaCarcel())
            System.out.println("No sales de la cárcel");
        else
            System.err.println("Dado.salgoDeLaCarcel no funciona");
        
        System.out.println("El último resltado ha sido " +
                Dado.getInstance().getUltimoResultado());
        
        mazo.alMazo(s);
        mazo.alMazo(s1);
        mazo.siguiente();
        mazo.inhabilitarCartaEspecial(s1);
        mazo.habilitarCartaEspecial(s1);
        while (Diario.getInstance().eventosPendientes())
            System.out.println(Diario.getInstance().leerEvento());
        
        for (int i = 0; i < casillas; i++) {
            tablero.aniadeCasilla(new Casilla());
        }
        
        tablero.vaciar();
        
        for (int i = 0; i < carcel - 2; i++) {
            tablero.aniadeCasilla(new Casilla());
        }
        
        posicion = tablero.nuevaPosicion(posicion, Dado.getInstance().tirar());
        if (posicion == -1)
            System.err.println("Tablero incorrecto");
    }
}
