/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;
import civitas.*;
import java.util.ArrayList;
/**
 *
 * @author ilzk
 */
public class Test_P2 {
    public static void main (String args[]) {
        VistaTextual vista = new VistaTextual();
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Miguel");
        nombres.add("Roberro");
        nombres.add("Ca?as");
        nombres.add("Brian");
        CivitasJuego juego = new CivitasJuego(nombres);
        Dado.getInstance().setDebug(true);
        Controlador control = new Controlador(juego, vista);
        control.juega();
    }
}

