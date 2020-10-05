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
        int resultado = 0, suma = 0;
        int iter = 24;
        double probabilidades[] = {1,2,3,4};
        
        
        
        for (int i = 0; i < iter; i++) {
            resultado = Dado.getInstance().quienEmpieza(probabilidades.length);
            probabilidades[resultado]++;
        }
        for (int i = 0; i < probabilidades.length; i++) {
            probabilidades[i] = probabilidades[i]/iter;
        }
        
        
        
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
    
        System.out.println(Dado.getInstance().salgoDeLaCarcel());
        System.out.println(Dado.getInstance().getUltimoResultado());
    }
}
