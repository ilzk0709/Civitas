/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.TituloPropiedad;
import civitas.Jugador;
/**
 *
 * @author ilzk
 */
public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    Controlador(CivitasJuego _juego, VistaTextual _vista) {
        juego = _juego;
        vista = _vista;
    }
    
    void juega() {
        
        vista.setCivitasJuego(juego);
        
        while (!juego.finalDelJuego()) {
            vista.actualizarVista();
            vista.pausa();
            if (juego.siguientePaso() != OperacionesJuego.PASAR_TURNO) {
                while (Diario.getInstance().eventosPendientes()) {
                    Diario.getInstance().leerEvento();
                }
            }
            if (!juego.finalDelJuego()) {
                OperacionesJuego operacionj = juego.siguientePaso();
                switch (operacionj) {
                    case COMPRAR:
                        if (vista.comprar() == Respuestas.SI){
                            juego.comprar();
                            juego.siguientePasoCompletado(OperacionesJuego.COMPRAR);
                        }
                        break;
                    case GESTIONAR:
                        vista.gestionar();
                        GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[vista.getGestion()];
                        int propiedad = vista.getPropiedad();
                        OperacionInmobiliaria operacion = new OperacionInmobiliaria(gestion, propiedad);
                        switch (gestion) {
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(propiedad);
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(propiedad);
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(propiedad);
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(propiedad);
                                break;
                            case VENDER:
                                juego.vender(propiedad);
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(operacionj);
                                break;
                        }
                        break;
                    case SALIR_CARCEL:
                        switch (vista.salirCarcel()) {
                            case PAGANDO:
                                juego.salirCarcelPagando();
                                break;
                            case TIRANDO:
                                juego.salirCarcelTirando();
                        }
                        juego.siguientePasoCompletado(operacionj);
                        break;
                }
                
            }
        }
        //Necesita retocar
        for (int i = 0; i < 4; i++) {
            System.err.println(juego.getJugadorActual().toString());
            juego.siguientePasoCompletado(OperacionesJuego.PASAR_TURNO);
        }
    }
}
