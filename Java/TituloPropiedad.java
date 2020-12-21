package civitas;

/**
 * @brief TituloPropiedad
 *
 * El título de propiedad indica información referente al precio de compra, el
 * precio de edificación, factor de revalorización de la venta, precio base del
 * alquiler, precio base de hipoteca y nombre de la calle asociada a dicha
 * casilla. El título de propiedad de una casilla es el objeto de las compras y
 * ventas, de forma que podemos decir que un jugador es “propietario de una
 * casilla” cuando es propietario de su título de propiedad.
 *
 * @author Miguel Garcia Lopez
 * @date Octubre 2020
 */
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

    /**
     * @brief Constructor de la clase con seis parametros
     * @param nomb Nombre de la propiedas
     * @param prec_alquiler Precio del alquiler
     * @param fac_reval Factor de revalorizacion
     * @param prec_hipo Precio de la hipoteca
     * @param prec_compra Precio de compra
     * @param prec_edif Precio de edificacion
     */
    public TituloPropiedad(String nomb, float prec_alquiler, float fac_reval, float prec_hipo, float prec_compra, float prec_edif) {
        nombre = nomb;
        alquilerBase = prec_alquiler;
        factorRevalorizacion = fac_reval;
        hipotecaBase = prec_hipo;
        precioCompra = prec_compra;
        precioEdificar = prec_edif;
        propietario = null;
        numCasas = 0;
        numHoteles = 0;
        hipotecado = false;
    }

    /**
     * @brief Cambia al jugador actual por el pasado por parametro
     * @param jug Jugador actualizado
     */
    void actualizaPropietarioPorConversion(Jugador jug) {
        propietario = jug;
    }

    boolean cancelarHipoteca(Jugador jug) {
        boolean result = false;

        if (getHipotecado()) {
            if (esEsteElPropietario(jug)) {
                propietario.paga(getImporteCancelarHipoteca());
                hipotecado = false;
                result = true;
            }
        }
        return result;
    }

    boolean hipotecar(Jugador jug) {
        boolean salida = false;

        if (!getHipotecado() && esEsteElPropietario(jug)) {
            propietario.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        
        return salida;
    }

    boolean comprar(Jugador jug) {
        boolean result = false;

        if (!tienePropietario()) {
            propietario = jug;
            result = true;
            propietario.paga(getPrecioCompra());
        }

        return result;
    }

    boolean construirCasa(Jugador jug) {
        boolean result = false;
        
        if(esEsteElPropietario(jug)){
            propietario.paga(getPrecioEdificar());
            numCasas++;
            result = true;
        }
        
        return result;
    }

    boolean construirHotel(Jugador jug) {
        boolean result = false;

        if (esEsteElPropietario(jug)) {
            propietario.paga(getPrecioEdificar());
            numHoteles++;
            result = true;
        }

        return result;
    }

    /**
     * @brief Devuelve el valor de la hipoteca base
     * @return hipoteca base de la propiedad
     */
    private float getImporteHipoteca() {
        return hipotecaBase;
    }

    /**
     * @brief Devuelve el nombre de la propiedad
     * @return nombre de la propiedad
     */
    String getNombre() {
        return nombre;
    }

    /**
     * @brief Devuelve el numero de casas de la propiedad
     * @return numeo de casas
     */
    int getNumCasas() {
        return numCasas;
    }

    /**
     * @brief Devuelve el numero de hoteles de la propiedad
     * @return numero de hoteles
     */
    int getNumHoteles() {
        return numHoteles;
    }

    /**
     * @brief Devuelve el coste de compra de la propiedad
     * @return precio de la propiedad
     */
    float getPrecioCompra() {
        return precioCompra;
    }

    /**
     * @brief Devuelve el coste por edificar la propiedad
     * @return precio de edificacion
     */
    float getPrecioEdificar() {
        return precioEdificar;
    }

    /**
     * @brief Devuelve la instancia del propietario
     * @return propietario del titular
     */
    Jugador getPropietario() {
        return propietario;
    }

    /**
     * @brief Devuelve true si la propiedad esta hipotecada y false si no
     * @return valor de hipotecado
     */
    public boolean getHipotecado() {
        return hipotecado;
    }

    /**
     * brief Comprueba si el jug es el propietario del titular
     *
     * @param jug Jugador a comprobar
     * @return true si es propietario, false si no lo es
     */
    private boolean esEsteElPropietario(Jugador jug) {
        boolean esPropietario = false;

        if (getPropietario() == jug) {
            esPropietario = true;
        }

        return esPropietario;
    }

    /**
     * @brief Devuelve el precio del alquiler de la propiedad
     * @return precio del alquiler
     */
    private float getPrecioAlquiler() {
        float precio_alquiler = 0;

        if (!getHipotecado() && !propietarioEncarcelado()) {
            precio_alquiler = alquilerBase * (1 + (getNumCasas() * 0.5f) + (getNumHoteles() * 2.5f));
        }

        return precio_alquiler;
    }

    /**
     * @brief Devuelve el importe a pagar para cancelar una hipoteca
     * @return importe de cancelacion de hipoteca
     */
    float getImporteCancelarHipoteca() {
        float CantidadRecibida = getImporteHipoteca() * (1 + (getNumCasas() * 0.5f) + (getNumHoteles() * 2.5f));
        return CantidadRecibida * factorInteresesHipoteca;
    }

    /**
     * @brief Comprueba si el propietario esta encarcelado o no
     * @return true si esta encarcelado, false si no
     */
    private boolean propietarioEncarcelado() {
        boolean encarcelado = false;

        if (tienePropietario() && propietario.isEncarcelado()) {
            encarcelado = true;
        }

        return encarcelado;

    }

    /**
     * @brief Si la propiedad tiene propietario y el jugador pasado por
     * parametro no lo es se tramita el pago del alquiler a ese jugador y se
     * ingresa la misma cantidad de saldo en el propietario
     * @param jug Jugador que paga el alquiler si no es propietario
     */
    void tramitarAlquiler(Jugador jug) {
        if (tienePropietario() && !esEsteElPropietario(jug)) {
            jug.pagaAlquiler(getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler());
        }
    }

    /**
     * @brief Comprueba si la propiedad tiene dueño
     * @return true si tiene dueño, false si no
     */
    boolean tienePropietario() {
        boolean tiene_prop = false;

        if (propietario != null) {
            tiene_prop = true;
        }

        return tiene_prop;
    }

    /**
     * @brief Retorna numero de edificaciones en la propiedad
     * @return numero de casas + numero de hoteles
     */
    int cantidadCasasHoteles() {
        return getNumCasas() + getNumHoteles();
    }

    /**
     * @brief Retorna precio de venta de la propiedad
     * @return precio de venta
     */
    private float getPrecioVenta() {
        return getPrecioCompra() + getPrecioEdificar() * factorRevalorizacion;
    }

    /**
     * @brief si el jugador pasado como parametro es el propietario del título y
     * el numero de casas construidas es mayor o igual que el parametro n, se
     * decrementa el contador de casas construidas en n unidades
     * @param n Numero de casas a derruir
     * @param jug Instancia de jugador
     * @return true si se han derruido casa, false si no
     */
    boolean derruirCasas(int n, Jugador jug) {
        boolean derruir = false;
        if (esEsteElPropietario(jug) && getNumCasas() >= n) {
            numCasas -= n;
            derruir = true;
        }

        return derruir;
    }

    /**
     * @brief si el jugador pasado como parametro es el propietario del titulo,
     * y este no esta hipotecado, entonces se da al propietario el precio de
     * venta, se desvincula el propietario de la propiedad, y se eliminan las
     * casas y hoteles
     * @param jug Instancia de jugador
     * @return si se vende true, si no false
     */
    boolean vender(Jugador jug) {
        boolean vendido = false;

        if (esEsteElPropietario(jug) && !getHipotecado()) {
            propietario.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            vendido = true;
        }

        return vendido;
    }

    /**
     * @brief Informa del estado completo de la propiedad
     * @return estado de la propiedad
     */
    @Override
    public String toString() {
        return "Nombre Propiedad: " + getNombre() + "\n"
                + "Precio Alquiler: " + getPrecioAlquiler() + "\n"
                + "Precio Compra: " + getPrecioCompra() + "\n"
                + "Precio Edificacion: " + getPrecioEdificar() + "\n"
                + "Precio Venta: " + getPrecioVenta() + "\n"
                + "Propietario: " + getPropietario().getNombre() + "\n"
                + "Hipoteca: " + getImporteHipoteca() + "\n"
                + "Hipotecada: " + getHipotecado() + "\n"
                + "Numero Casas: " + getNumCasas() + "\n"
                + "Numero Hoteles: " + getNumHoteles();
    }
}
