package civitas;

import java.util.ArrayList;

public class TituloPropiedad {
    
    private float alquilerBase;
    final private float factorInteresesHipoteca = 1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    public TituloPropiedad(String nomb, float prec_alquiler, float fac_reval, float prec_hipo, float prec_compra, float prec_edif) {
        nombre = nomb;
        alquilerBase = prec_alquiler;
        factorRevalorizacion = fac_reval;
        hipotecaBase = prec_hipo;
        precioCompra = prec_compra;
        precioEdificar = prec_edif;
        propietario = null;
    }
    
    void actualizaPropietarioPorConversion(Jugador jug) { 
        propietario = jug;
    }
    
    boolean cancelarHipoteca(Jugador jug) {
        throw new UnsupportedOperationException("No implementado");
    }
    
    boolean hipotecar(Jugador jug) {
        throw new UnsupportedOperationException("No implementado");
    }
    
    boolean comprar(Jugador jug) {
        throw new UnsupportedOperationException("No implementado");
    }
    
    boolean construirCasa(Jugador jug) {
        throw new UnsupportedOperationException("No implementado");
    }
    
    boolean construirHotel(Jugador jug) {
        throw new UnsupportedOperationException("No implementado");
    }
    
    private float getImporteHipoteca() {
        return hipotecaBase;
    }
    
    String getNombre() {
        return nombre;
    }
    
    int getNumCasas() {
        return numCasas;
    }
    
    int getNumHoteles() {
        return numHoteles;
    }
    
    float getPrecioCompra() {
        return precioCompra;
    }
    
    float getPrecioEdificar() {
        return precioEdificar;
    }
    
    Jugador getPropietario() {
        return propietario;
    }
    
    public boolean getHipotecado() {
        return hipotecado;
    }
    
    private boolean esEsteElPropietario(Jugador jug) {
        boolean esPropietario = false;
        
        if(getPropietario() == jug)
            esPropietario = true;
        
        return esPropietario;
    }
    
    private float getPrecioAlquiler() {
        float precio_alquiler = 0;
        
        if(!getHipotecado() && !propietarioEncarcelado())
            precio_alquiler = alquilerBase * (1 + (getNumCasas() * 0.5f) + (getNumHoteles() * 2.5f));
        
        return precio_alquiler;
    }
    
    float getImporteCancelarHipoteca() {
        float CantidadRecibida = getImporteHipoteca() * (1 + (getNumCasas() * 0.5f) + (getNumHoteles() * 2.5f));
        return CantidadRecibida * factorInteresesHipoteca;
    }
    
    private boolean propietarioEncarcelado() {
        boolean encarcelado = false;
        
        if(tienePropietario() && propietario.isEncarcelado())
            encarcelado = true;
        
        return encarcelado;
            
    }
    
    void tramitarAlquiler(Jugador jug) {
        float alquiler;
        if(tienePropietario() && !esEsteElPropietario(jug)) {
            alquiler = pagaAlquiler(jug);
            propietario.recibe(getPrecioAlquiler());
        }
    }
    
    boolean tienePropietario() {
        boolean tiene_prop = false;
        
        if(propietario.nombre != "\0")
            tiene_prop = true;
        
        return tiene_prop;
    } 
    
    int cantidadCasasHoteles() {
        return getNumCasas() + getNumHoteles();
    }
    
    private float getPrecioVenta() {
        return getPrecioCompra() + getPrecioEdificar() * factorRevalorizacion;
    }
    
    boolean derruirCasas(int n, Jugador jug) {
        boolean derruir = false;
        if(esEsteElPropietario(jug) && getNumCasas() >= n){
            numCasas -= n;
            derruir = true;
        }
        
        return derruir;
    }
    
    boolean vender(Jugador jug) {
        boolean vendido = false;
        
        if(esEsteElPropietario(jug) && !getHipotecado()){
            propietario.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            vendido = true;
        }
        
        return vendido;
    }
    
    @Override
    public String toString() {
        return "Nombre Propiedad: " + getNombre() + "\n"
                + "Precio Alquiler: " + getPrecioAlquiler() + "\n"
                + "Precio Compra: " + getPrecioCompra() + "\n"
                + "Precio Edificacion: " + getPrecioEdificar() + "\n"
                + "Precio Venta: " + getPrecioVenta() + "\n"
                + "Propietario: " +  getPropietario() + "\n"
                + "Hipoteca: " + getImporteHipoteca() + "\n"
                + "Hipotecada: " + getHipotecado() + "\n"
                + "Numero Casas: " + getNumCasas() + "\n"
                + "Numero Hoteles: " + getNumHoteles();
    }
}
