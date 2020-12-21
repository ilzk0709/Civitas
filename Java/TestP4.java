package civitas;


public class TestP4{

    public static void main(String args[]) {
        Jugador jugador = new Jugador("Manolo");
        TituloPropiedad titulo = new TituloPropiedad("Finca Manoli", 400, 2, 12000, 15000, 300);
        jugador.getPropiedades().add(titulo);
        
        System.out.println("---Jugador antes de convertir:");
        System.out.println(jugador.toString());
        
        JugadorEspeculador especulador = new JugadorEspeculador(jugador, 500);
        System.out.println("\n---Jugador después de convertirse:");
        System.out.println(especulador.toString());
    }
}
