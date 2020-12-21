package civitas;

import java.util.ArrayList;

/**
 * @brief Jugador
 *
 * La clase jugador indica información referente al saldo del mismo, su nombre,
 * sus propiedades, las casas maximas a tener en una casilla y por hotel, el
 * numero maximo de hoteles, saldo inicial, casilla en la que se encuentra, si
 * puede comprar, si esta en la carcel, cuanto cuesta salir de la carcel, si
 * tiene o no un salvoconducto para esta y el dinero recibido en cada paso por
 * salida. Un objeto de jugador es el que realiza las acciones en Civitas, quien
 * compra, edifica, va a la carcel etc...
 *
 * @author Miguel Garcia Lopez
 * @date Octubre 2020
 */
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

    /**
     * @brief Constructor de la clase por defecto, con el parametro "nombre"
     * @param nomb nomb Nombre del jugador inicializado
     */
    public Jugador(String nomb) {
        nombre = nomb;
        propiedades = new ArrayList<>();
        encarcelado = false;
        numCasillaActual = 0;
        puedeComprar = true;
        saldo = SaldoInicial;
        salvoconducto = null;
    }

    /**
     * @brief Constructor por copia
     * @param jug Jugador a copiar
     */
    protected Jugador(Jugador jug) {
        propiedades = new ArrayList<>(jug.propiedades);
        nombre = jug.getNombre();
        encarcelado = jug.isEncarcelado();
        numCasillaActual = jug.getNumCasillaActual();
        puedeComprar = jug.getPuedeComprar();
        saldo = jug.getSaldo();
    }

    /**
     * @brief Retorna el numero total de casas y hoteles que posee el jugador
     * @return cantidad = numCasas + numHoteles del jugador
     */
    int cantidadCasasHoteles() {
        int cantidad = 0;

        for (int i = 0; i < propiedades.size(); i++) {
            cantidad += (propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles());
        }

        return cantidad;
    }

    /**
     * @brief Compara los saldos de distintos jugadores
     * @param jug Jugador a comparar
     * @return 0 si son iguales, 1 si es mayor el primero, -1 si es mayor el
     * segundo
     */
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
        boolean result = false;

        if (isEncarcelado()) {
            return result;
        }
        if (getPuedeComprar()) {
            float precio = propiedad.getPrecioCompra();

            if (puedoGastar(precio)) {
                result = propiedad.comprar(this);

                if (result) {
                    getPropiedades().add(propiedad);
                    Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " compra la propiedad " + propiedad.toString());
                }

                puedeComprar = false;
            }
        }

        return result;
    }

    boolean construirCasa(int ip) {
        boolean result = false;

        if (isEncarcelado()) {
            return result;
        }

        boolean existe = existeLaPropiedad(ip);

        if (existe) {
            TituloPropiedad propiedad = getPropiedades().get(ip);
            boolean puedoEdificar = puedoEdificarCasa(propiedad);
            float precio = propiedad.getPrecioEdificar();

            if (puedoEdificarCasa(propiedad)) {
                result = propiedad.construirCasa(this);
                Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " construye casa en propiedad " + getPropiedades().get(ip).getNombre());
            }
        }
        return result;
    }

    boolean construirHotel(int ip) {
        boolean result = false;

        if (isEncarcelado()) {
            return result;
        }

        if (existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = getPropiedades().get(ip);
            boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
            float precio = propiedad.getPrecioEdificar();

            if (puedoEdificarHotel) {
                result = propiedad.construirHotel(this);
                propiedad.derruirCasas(getCasasPorHotel(), this);
                Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " construye hotel en propiedad " + getPropiedades().get(ip).getNombre());
            }
        }

        return result;
    }

    /**
     * 
     * @param ip
     * @return 
     */
    boolean hipotecar(int ip) {
        boolean result = false;

        if (isEncarcelado()) {
            return result;
        }

        if (existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = getPropiedades().get(ip);
            result = propiedad.hipotecar(this);
        }

        if (result) {
            Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " hipoteca " + getPropiedades().get(ip).getNombre());
        }

        return result;
    }

    /**
     * @brief Comprueba si un jugador cumple los requerimientos necesarios para
     * ser encarcelado
     * @return true si debe ser encarcelado, false de manera contraria
     */
    protected boolean debeSerEncarcelado() {
        boolean encarcelar = false;

        if (!isEncarcelado()) {
            if (tieneSalvoconducto()) {
                perderSalvoconducto();
                encarcelar = false;
                Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " se libra de carcel.");
            } else {
                encarcelar = true;
            }
        }
        return encarcelar;
    }

    /**
     * @brief El jugador cancela la hipoteca de su propiedad pasada por parametro
     * @param ip Identificador de la propiedad en cuestion
     * @return true si se cancela, false en caso contrario
     */
    boolean cancelarHipoteca(int ip) {
        boolean result = false;

        if (isEncarcelado()) {
            return result;
        }
        if (existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = getPropiedades().get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = puedoGastar(cantidad);

            if (puedoGastar) {
                result = propiedad.cancelarHipoteca(this);
            }
        }

        if (result) {
            Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " cancela la hipotecada de " + getPropiedades().get(ip).getNombre());
        }

        return result;
    }

    /**
     * @brief Comprueba si el jugador esta en bancarrota, es decir, si su saldo
     * es cero
     * @return true si esta en bancarrota, false de manera contraria
     */
    boolean enBancarrota() {
        boolean bancarrota = false;

        if (saldo == 0) {
            bancarrota = true;
        }

        return bancarrota;
    }

    /**
     * @brief Si el jugador debe ser encarcelado este metodo realiza la accion
     * de encarcelamiento
     * @param numCasillaCarcel Casilla donde se encuentra la carcel
     * @return true si ha sido encarcelado, false si no
     */
    boolean encarcelar(int numCasillaCarcel) {
        if (debeSerEncarcelado()) {
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " va a la carcel");
        }

        return encarcelado;
    }

    /**
     * @brief Comprueba si existe la propiedad buscada
     * @param ip Numero de la propiedad en el vector
     * @return true si existe, false si no
     */
    boolean existeLaPropiedad(int ip) {
        boolean existir = false;
        if (propiedades.size() > 0)
            if (propiedades.get(ip) != null) {
                existir = true;
            }
                

        return existir;
    }

    /**
     * @brief Retorna numero de casas maximas a tener por casilla
     * @return numero de casas maximas
     */
    int getCasasMax() {
        return CasasMax;
    }

    /**
     * @brief Retorna numero de hoteles maximos a tener por casilla
     * @return numero de hoteles maximos
     */
    int getHotelesMax() {
        return HotelesMax;
    }

    /**
     * @brief Retorna numero de casas maximas a tener por hotel
     * @return numero de casas maximas por hotel
     */
    int getCasasPorHotel() {
        return CasasPorHotel;
    }

    /**
     * @brief Retorna el nombre del jugador
     * @return nombre del jugador
     */
    protected String getNombre() {
        return nombre;
    }

    /**
     * @brief Devuelve la casilla actual en la que se encuentre el jugador
     * @return casilla actual del jugador
     */
    int getNumCasillaActual() {
        return numCasillaActual;
    }

    /**
     * @brief Devuelve el costo necesario para salir de la carcel
     * @return precio de salir de la carcel
     */
    float getPrecioLibertad() {
        return PrecioLibertad;
    }

    /**
     * @brief Retorna el premio por pasar por la casilla de salida
     * @return dinero ganado por pasar por salida
     */
    float getPremioPorSalida() {
        return PasoPorSalida;
    }

    /**
     * @brief Retorna la lista de propiedades del jugador en cuestion
     * @return array de propiedades
     */
    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    /**
     * @brief Devuelve si el jugador en cuestion puede o no comprar
     * @return true si puede comprar, false si no
     */
    boolean getPuedeComprar() {
        return puedeComprar;
    }

    /**
     * @brief Retorna el saldo del jugador
     * @return saldo del jugador
     */
    protected float getSaldo() {
        return saldo;
    }

    /**
     * @brief Devuelve si el jugador esta en la carcel
     * @return true si esta encarcelado, false si es al contrario
     */
    public boolean isEncarcelado() {
        return encarcelado;
    }

    /**
     * @brief Edita el saldo del jugador en funcion de la cantidad, que puede
     * ser negativa o positiva, en funcion de si debe o le dan dinero
     * @param cantidad Cantidad de saldo modificado
     * @return true
     */
    boolean modificarSaldo(float cantidad) {
        saldo += cantidad;
        Diario.getInstance().ocurreEvento("Saldo incrementado en " + cantidad);
        return true;
    }

    /**
     * @brief Mueve al jugador a la casilla especificada, a no ser que este en
     * la carcel
     * @param numCasilla Casilla a la que se mueve el jugador
     * @return true si se mueve, false por el contrario
     */
    boolean moverACasilla(int numCasilla) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " se mueve a " + getNumCasillaActual());
        }

        return b;
    }

    /**
     * @brief Se le otorga al jugador un salvoconducto, de tipo Sorpresa, a no
     * ser que este encarcelado
     * @param s Salvoconducto a otorgar
     * @return true si se le otorga, false si no
     */
    boolean obtenerSalvoconducto(Sorpresa s) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {
            salvoconducto = s;
        }

        return b;
    }

    /**
     * @brief Llama a modificarSaldo para retirar dinero del jugador por un pago
     * @param cantidad Cantidad a pagar
     * @return valor de modificarSaldo()
     */
    boolean paga(float cantidad) {
        return modificarSaldo(cantidad * -1);
    }

    /**
     * @brief Se llama a paga para retirar dinero del jugador por el alquiler
     * @param cantidad Cantidad a pagar
     * @return true si lo paga, false si no
     */
    boolean pagaAlquiler(float cantidad) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {
            paga(cantidad);
        }

        return b;
    }

    /**
     * @brief Se llama a paga para retirar dinero del jugador por un impuesto
     * @param cantidad Cantidad a pagar
     * @return true si lo paga, false si no
     */
    boolean pagaImpuesto(float cantidad) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {
            paga(cantidad);
        }

        return b;
    }

    /**
     * @brief Realiza la accion de pasar por salida, modificando el saldo con el
     * efectivo indicado por esa accion
     * @return true
     */
    boolean pasaPorSalida() {
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " ha pasado por salida");
        return true;
    }

    /**
     * @brief El jugador hace uso de su salvoconducto y lo pierde
     */
    private void perderSalvoconducto() {
        salvoconducto.usada();
        salvoconducto = null;
    }

    /**
     *  Retorna si se puede comprar una casilla
     * @return true si es posible, false si no
     */
    boolean puedeComprarCasilla() {
        puedeComprar = !isEncarcelado();

        return puedeComprar;
    }

    /**
     * @brief Comprueba si el jugador puede salir de la casa pagando
     * @return true si puede, false si no
     */
    private boolean puedeSalirCarcelPagando() {
        boolean b = false;

        if (getSaldo() >= getPrecioLibertad()) {
            b = true;
        }

        return b;
    }

    /**
     * @brief El jugador sale de la carcel pagando (si puede)
     * @return true si sale, false si no
     */
    boolean salirCarcelPagando() {
        boolean b = false;

        if (isEncarcelado() && puedeSalirCarcelPagando()) {
            paga(getPrecioLibertad());
            encarcelado = false;
            Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " paga salida de la carcel");
            b = true;
        }

        return b;
    }

    /**
     * @brief El jugador intenta salir de la carcel tirando
     * @return true si sale, false si no
     */
    boolean salirCarcelTirando() {
        boolean b = false;

        if (Dado.getInstance().salgoDeLaCarcel()) {
            encarcelado = false;
            Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " sale de la carcel tirando");
            b = true;
        }
        
        return b;
    }

    /**
     * @brief Comprueba si se puede edificar una casa
     * @param propiedad Propiedad sobre la que se desea edificar
     * @return true si es posible, false si no
     */
    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        boolean b = false;

        if (propiedad.getNumCasas() < getCasasMax()) {
            if (puedoGastar(propiedad.getPrecioEdificar())) {
                b = true;
            }
        }

        return b;
    }

    /**
     * @brief Comprueba si se puede edificar un hotel
     * @param propiedad Propiedad sobre la que se desea edificar
     * @return true si es posible, false si no
     */
    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean b = false;

        if (propiedad.getNumHoteles() < getHotelesMax()) {
            if (propiedad.getNumCasas() == getCasasPorHotel()) {
                if (puedoGastar(propiedad.getPrecioEdificar())) {
                    b = true;
                }
            }
        }

        return b;
    }

    /**
     * @brief Comprueba si es posible gastar cierta cantidad de saldo
     * @param precio Dinero a gastar
     * @return true si es posible, false si no
     */
    protected boolean puedoGastar(float precio) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {
            b = precio <= getSaldo();
        }

        return b;
    }

    /**
     * @brief El jugador recibe cierta cantidad de saldo
     * @param cantidad Dinero a recibir
     * @return true si lo recibe, false si no
     */
    boolean recibe(float cantidad) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {
            modificarSaldo(cantidad);
            Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " recibe " + cantidad);
        }

        return b;
    }

    /**
     * @brief Comprueba si el jugador tiene alguna propiedad la cual gestionar
     * @return true si tiene, false si no
     */
    boolean tieneAlgoQueGestionar() {
        boolean b = false;

        if (getPropiedades().size() > 0) {
            b = true;
        }

        return b;
    }

    /**
     * @brief Comprueba si el jugador tiene salvoconducto
     * @return true si lo posee, false si no
     */
    boolean tieneSalvoconducto() {
        boolean b = false;

        if (salvoconducto != null) {
            b = true;
        }

        return b;
    }

    /**
     * @brief Realiza la accion de vender una propiedad, a la cual se refiere
     * por el indice pasado por parametro
     * @param ip Indice de la propiedad a vender
     * @return true si es vendida, false si no
     */
    boolean vender(int ip) {
        boolean b = true;

        if (isEncarcelado()) {
            b = false;
        } else {


            if (existeLaPropiedad(ip)) {
                if (getPropiedades().get(ip).vender(this)) {
                    Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " vende " + getPropiedades().get(ip).getNombre());
                    getPropiedades().remove(ip);
                    b = true;
                }
            }
        }

        return b;
    }

    /**
     * @brief Informa del estado completo del jugador
     * @return estado del jugador
     */
    @Override
    public String toString() {
        return "Jugador: " + getNombre() + "\n"
                + "Encarcelado?: " + isEncarcelado() + "\n"
                + "Saldo: " + getSaldo() + "\n"
                + "Casilla Actual: " + getNumCasillaActual();

    }
}