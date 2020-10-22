package civitas;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador> {

    final protected int CasasMax = 4;
    final protected int CasasPorHotel = 4;
    protected boolean encarcelado;
    final protected int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    final protected float PasoPorSalida = 1000;
    final protected float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    final private float SaldoInicial = 7500;
    private ArrayList<TituloPropiedad> propiedades;
    private Sorpresa salvoconducto;

    public Jugador(String nomb) {
        nombre = nomb;
    }

    protected Jugador(Jugador jug) {
        propiedades = new ArrayList<>(jug.propiedades);
    }

    int cantidadCasasHoteles() {
        return propiedades.size();
    }

    @Override
    public int compareTo(Jugador jug) {
        int comp = 0;

        if (getSaldo() > jug.getSaldo()) {
            comp = 1;
        } else if (getSaldo() < jug.getSaldo()) {
            comp = -1;
        }

        return comp;
    }

    boolean comprar(TituloPropiedad propiedad) {
        throw new UnsupportedOperationException("No implementado");
    }

    boolean construirCasa(int ip) {
        throw new UnsupportedOperationException("No implementado");
    }

    boolean construirHotel(int ip) {
        throw new UnsupportedOperationException("No implementado");
    }

    protected boolean debeSerEncarcelado() {
        boolean encarcelar = false;

        if (!isEncarcelado()) {
            if (!tieneSalvoconducto()) {
                perderSalvoconducto();
                encarcelar = false;
                Diario.getInstance().ocurreEvento("Jugador " + nombre + "se libra de carcel");
            } else {
                encarcelar = true;
            }
        }
        return encarcelar;
    }

    boolean enBancarrota() {
        boolean bancarrota = false;

        if (saldo == 0) {
            bancarrota = true;
        }

        return bancarrota;
    }

    boolean encarcelar(int numCasillaCarcel) {
        if (debeSerEncarcelado()) {
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("Jugador " + nombre + "va a la carcel");
        }

        return encarcelado;
    }

    boolean existeLaPropiedad(int ip) {
        boolean existir = true;

        if (propiedades.get(ip) == null) {
            existir = false;
        }

        return existir;
    }

    int getCasasMax() {
        return CasasMax;
    }

    int getHotelesMax() {
        return HotelesMax;
    }

    int getCasasPorHotel() {
        return CasasPorHotel;
    }

    protected String getNombre() {
        return nombre;
    }

    int getNumCasillaActual() {
        return numCasillaActual;
    }

    float getPrecioLibertad() {
        return PrecioLibertad;
    }

    float getPremioPorSalida() {
        return PasoPorSalida;
    }

    ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    boolean getPuedeComprar() {
        return puedeComprar;
    }

    protected float getSaldo() {
        return saldo;
    }

    public boolean isEncarcelado() {
        return encarcelado;
    }

    boolean modificarSaldo(float cantidad) {
        saldo += cantidad;
        Diario.getInstance().ocurreEvento("Saldo incrementado en " + cantidad);
        return true;
    }

    boolean moverACasilla(int numCasilla) {
        boolean b = true;

        if (isEncarcelado()) 
            b = false;
        else {
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("Jugador " + nombre + "se mueve a " + numCasillaActual);
        }

        return b;
    }
    
    boolean obtenerSalvoconducto(Sorpresa s) {
        boolean b = true;
        
        if(isEncarcelado())
            b = false;
        else
            salvoconducto = s;
        
        return b;
    }
    
    boolean paga(float cantidad) {
        return modificarSaldo(cantidad * -1);
    }
    
    boolean pagaAlquiler(float cantidad) {
        boolean b = true;
        
        if(isEncarcelado())
            b = false;
        else
            paga(cantidad);
        
        return b;
    }
    
    boolean pagaImpuesto(float cantidad) {
        boolean b = true;
        
        if(isEncarcelado())
            b = false;
        else
            paga(cantidad);
        
        return b;
    }
    
    boolean pasaPorSalid() {
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " ha pasado por salida");
        return true;
    }
    
    private void perderSalvoconducto() {
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    boolean puedeComprarCasilla() {
        if(isEncarcelado())
            puedeComprar = false;
        else
            puedeComprar = true;
        
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando() {
        boolean b = false;
        
        if(getSaldo() >= getPrecioLibertad())
            b = true;
        
        return b;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        boolean b = false;
        
        if(propiedad.getNumCasas() < getCasasMax())
            if (getSaldo() >= propiedad.getPrecioEdificar()) {
                b = true;
            }
        
        return b;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean b = false;
        
        if(propiedad.getNumHoteles() < getHotelesMax())
            if(propiedad.getNumCasas() == getCasasPorHotel())
                if (getSaldo() >= propiedad.getPrecioEdificar()) {
                    b = true;
                }
        
        return b;
    }
    
    private boolean puedoGastar(float precio) {
        boolean b = true;
        
        if(isEncarcelado())
            b = false;
        else if(precio >= getSaldo())
            b = true;
        else
            b = false;
        
        return b;
    }
    
    boolean recibe(float cantidad) {
        boolean b = true;
        
        if(isEncarcelado())
            b = false;
        else{
            modificarSaldo(cantidad);
            Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " recibe " + cantidad);
        }
        
        return b;
    }
    
    boolean tieneAlgoQueGestionar() {
        boolean b = false;
        
        if(propiedades.size() > 0)
            b = true;
        
        return b;
    }
    
    boolean tieneSalvoconducto() {
        boolean b = false;
        
        if(salvoconducto != null)
            b = true;
        
        return b;
    }
    
    boolean vender(int ip) {
        boolean b = true;
        
        if(isEncarcelado())
            b = false;
        else{
            
            if(existeLaPropiedad(ip))
                if(propiedades.get(ip).vender(this)){
                    propiedades.remove(ip);
                    Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " vende " + propiedades.get(ip).getNombre());
                    b = true;
                }
        }
        
        return b;
    }
}

