package civitas;

public class JugadorEspeculador extends Jugador {

    private static int FactorEspeculador = 2;
    private int fianza;

    public JugadorEspeculador(Jugador jug, int _fianza) {
        super(jug);
        fianza = _fianza;

        for (int i = 0; i < super.getPropiedades().size(); i++) {
            super.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }

    @Override
    protected boolean debeSerEncarcelado() {
        boolean b = super.debeSerEncarcelado();

        if (b) {
            b = super.puedoGastar(fianza);
        }

        return b;
    }

    @Override
    int getCasasMax() {
        return FactorEspeculador * super.getCasasMax();
    }

    @Override
    int getHotelesMax() {
        return FactorEspeculador * super.getHotelesMax();
    }

    @Override
    boolean pagaImpuesto(float cantidad) {
        return super.pagaImpuesto(cantidad / 2);
    }

    @Override
    public String toString() {
        return "Jugador Especulador \n" + super.toString() + "\n";
    }

}
