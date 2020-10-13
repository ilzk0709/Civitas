package civitas;

public class TestP1 {

    public static void main(String[] args) {

        int n, cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0;

        //PRUEBA 1:
        for (int i = 0; i < 100; i++) {
            
            n = Dado.getInstance().quienEmpieza(4);

            switch (n) {
                case 0:
                    cont1++;
                    break;
                case 1:
                    cont2++;
                    break;
                case 2:
                    cont3++;
                    break;
                default:
                    cont4++;
                    break;
            }
        }

        System.out.println("Jug1: " + cont1);
        System.out.println("Jug2: " + cont2);
        System.out.println("Jug3: " + cont3);
        System.out.println("Jug4: " + cont4);
        
        System.out.println("\n---------\n");
        
        //PRUEBA 2:
        Dado.getInstance().setDebug(true);
        
        int x;
        
        for(int i = 0; i < 10; i++){
            x = Dado.getInstance().tirar();
            System.out.println(x);
        }
        
        System.out.println("\n---------\n");
        Dado.getInstance().setDebug(false);
        
        for(int i = 0; i < 10; i++){
            x = Dado.getInstance().tirar();
            System.out.println(x);
        }
        
        System.out.println("\n---------\n");
        
        //PRUEBA 3:
        int a;
        boolean b;
        
        a = Dado.getInstance().getUltimoResultado();
        b = Dado.getInstance().salgoDeLaCarcel();
        
        System.out.println("Tirada: " + a);
        System.out.println("Salir Carcel: " + b);
        
        System.out.println("\n---------\n");
        
        //PRUEBA 4:
         System.out.println(TipoCasilla.CALLE);
         System.out.println(EstadosJuego.DESPUES_COMPRAR);
         System.out.println(TipoSorpresa.PORCASAHOTEL);
         System.out.println(OperacionesJuego.GESTIONAR);
         
         System.out.println("\n---------\n");
         
         //PRUEBA 5:
         MazoSorpresas mazo = new MazoSorpresas();
         Sorpresa s = new Sorpresa(), h = new Sorpresa();
         
         mazo.alMazo(s);
         mazo.alMazo(h);
         mazo.siguiente();
         mazo.inhabilitarCartaEspecial(h);
         mazo.habilitarCartaEspecial(h);
         System.out.println(Diario.getInstance().leerEvento());
         
         //PRUEBA 6:
         System.out.println("\n---------\n");
         
         Tablero tablero = new Tablero(0);
         Casilla c = new Casilla("1"), c2 = new Casilla("2"), c3 = new Casilla("3");
         
         tablero.añadeCasilla(c);
         tablero.añadeCasilla(c2);
         tablero.añadeCasilla(c3);
         tablero.añadeJuez();
         
         System.out.println("TABLERO: " + tablero.getCasilla(5).getNombre());
         
         int tirada = Dado.getInstance().tirar();
         int posicion_actual = tablero.nuevaPosicion(0, tirada);
      
         System.out.println("Tirada: " + tirada);
         System.out.println("Calculo de tirada: " + tablero.calcularTirada(0, posicion_actual));

    }

}
