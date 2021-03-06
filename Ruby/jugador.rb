#encoding:utf-8

require_relative "titulo_propiedad"

module Civitas
  class Jugador
    
    attr_accessor :nombre, :CASAS_MAX, :HOTELES_MAX, :CASAS_POR_HOTEL, :numCasillaActual, :PRECIO_POR_LIBERTAL,  :PASO_POR_SALIDA, :propiedades, :puedeComprar, :saldo, :encarcelado
    
    @@CASAS_MAX = 4
    @@HOTELES_MAX = 4
    @@SALDO_INICIAL = 7500
    @@CASAS_POR_HOTEL = 4
    @@PASO_POR_SALIDA = 1000
    @@PRECIO_POR_LIBERTAD = 200
    
    def puede_comprar
      return @puedeComprar
    end
    
    def tiene_algo_que_gestionar
      tieneAlgoQueGestionar
    end
    
    def initialize(nomb, jug = nil)
      if(jug == nil)
        @nombre = nomb
        @propiedades = []
        @encarcelado = false
        @numCasillaActual = 0
        @puedeComprar = true
        @saldo = @@SALDO_INICIAL
        @salvoconducto = nil
      else
        @propiedades = jug.propiedades[0..jug.propiedades.size-1]
        @nombre = jug.nombre
        @encarcelado = jug.encarcelado
        @numCasillaActual = jug.numCasillaActual
        @puedeComprar = jug.puedeComprar
        @saldo = jug.saldo
      end
    end
    
    def cantidadCasasHoteles()
      return @propiedades.size;
    end
    
    def <=>(jug)
      return @saldo <=> jug.saldo
    end
    
    def debeSerEncarcelado()
      encarcelar = false
      
      if(!@encarcelado)
        if(tieneSalvoconducto())
          perderSalvoconducto()
          encarcelar = false
          Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + "se libra de carcel")
        else
          encarcelar = true
        end
      end
      
      return encarcelar
    end
    
    def enBancarrota()
      bancarrota = false
      
      if(@saldo == 0)
        bancarrota = true
      end
      
      return bancarrota
    end
    
    def encarcelar(numCasillaCarcel)
      if(debeSerEncarcelado())
        moverACasilla(numCasillaCarcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + " va a la carcel")
      end
      
      return @encarcelado
    end
    
    def existeLaPropiedad(ip)
      existir = true
      
      if(@propiedades[ip] == nil)
        existir = false
      end
      
      return existir
    end
    
    def modificarSaldo(cantidad)
      @saldo += cantidad
      Diario.instance.ocurre_evento("Saldo incrementado en " + cantidad.to_s)
      return true
    end
    
    def moverACasilla(numCasilla)
      b = true
      
      if(@encarcelado)
        b = false
      else
        @numCasillaActual = numCasilla
        @puedeComprar = false
        Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + " se mueve a " + @numCasillaActual.to_s)
      end
      
      return b
    end
    
    def obtenerSalvoconducto(s)
      b = true
      
      if(@encarcelado)
        b = false
      else
        @salvoconducto = s
      end
      
      return b
    end
    
    def paga(cantidad)
      return modificarSaldo((-1)*cantidad.to_i)
    end
    
    def pagaAlquiler(cantidad)
      b = true
      
      if(@encarcelado)
        b = false
      else
        paga(cantidad)
      end
      
      return b
    end
    
    def pagaImpuesto(cantidad)
      b = true
      
      if(@encarcelado)
        b = false
      else
        paga(cantidad)
      end
      
      return b
    end
    
    def pasaPorSalida()
      modificarSaldo(@@PASO_POR_SALIDA)
      Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + " ha pasado por salida")
      return true
    end
    
    def perderSalvoconducto
      @salvoconducto.usada()
      @salvoconducto = nil
    end
    
    def puedeComprarCasilla()
      if(@encarcelado)
        @puedeComprar = false
      else
        @puedeComprar = true
      end
      
      return @puedeComprar
    end
    
    def salirCarcelPagando()
      b = false
      
      if(@saldo >= @@PRECIO_POR_LIBERTAD)
        b = true
        @saldo = @saldo - @@PRECIO_POR_LIBERTAD
      end
      
      return b
    end
     def salirCarcelTirando
      b = false
      tirada = Dado.instance.tirar
      if(tirada > 5)
        b = true
      end
      
      return b
    end
    def puedeEdificarCasa(propiedad)
      b = false
      
      if(propiedad.numCasas < @@CASAS_MAX)
        if(@saldo >= propiedad.precioEdificar)
          b = true
        end
      end
      
      return b
    end
    
    def puedeEdificarHotel(propiedad)
      b = false
      
      if(propiedad.numHoteles < @@HOTELES_MAX)
        if(propiedad.numCasas == @@CASAS_POR_HOTEL)
          if(@saldo >= propiedad.precioEdificar)
            b = true
          end
        end
      end
      
      return b
    end
    
    def puedoGastar(precio)
      b = false
      
      if(@encarcelado)
        b = false
      else
        if(precio <= @saldo)
          b = true
        else
          b = false
        end
      end
      
      return b
    end
    
    def recibe(cantidad)
      b = true
      
      if(@encarcelado)
        b = false
      else
        modificarSaldo(cantidad)
        Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + " recibe " + cantidad.to_s)
      end
      
      return b
    end
    
    def tieneAlgoQueGestionar()
      b = false
      
      if(@propiedades.size > 0)
        b = true
      end
      
      return b
    end
    
    def tieneSalvoconducto()
      b = false
      
      if(@salvoconducto != nil)
        b = true
      end
      
      return b
    end
    
    def vender(ip)
      b = false
      
      if(@encarcelado)
        b = false
      else
        if(existeLaPropiedad(ip))
          if(@propiedades[ip].vender(self))
            Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + " vende " + @propiedades[ip].nombre)
            @propiedades.delete_at(ip)
            b = true
          end
        end
      end
      
      return b
    end
    
    def cancelarHipoteca(ip)
      result = false
      
      if(@encarcelado)
        return result
      end
      
      if(existeLaPropiedad(ip))
        propiedad = TituloPropiedad.new
        propiedad = @propiedades[ip]
        cantidad = propiedad.getImporteCancelarHipoteca()
        puedoGastar = puedoGastar(cantidad)
        
        if(puedoGastar)
          result = propiedad.cancelarHipoteca(self)
        end
        
        if(result)
          Diario.instance.ocurre_evento("El jugador " + @nombre.to_s + " cancela hipotecada de " + @propiedades[ip].nombre.to_s)
        end
      end
      
      return result
    end
    
    def comprar(propiedad)
      result = false
      
      if(@encarcelado)
        return result
      end
      
      if(@puedeComprar)
        precio = propiedad.precioCompra
        
        if(puedoGastar(precio))
          result = propiedad.comprar(self)
        end
        
        if(result)
          @propiedades.push(propiedad)
          Diario.instance.ocurre_evento("El jugador " + @nombre.to_s + " compra propiedad " + propiedad.to_s())
        end
      end
      
      return result
    end
    
    def construirHotel(ip)
      result = false
      
      if(@encarcelado)
        return result
      end
      
      if(existeLaPropiedad(ip))
        propiedad = TituloPropiedad.new
        propiedad = @propiedades[ip]
        puedoEdificarHotel = puedeEdificarHotel(propiedad)
        precio = propiedad.precioEdificar
        
        if(puedoEdificarHotel)
          result = propiedad.construirHotel(self)
          propiedad.derruirCasas(@@CASAS_POR_HOTEL, self)
          Diario.instance.ocurre_evento("El jugador " + @nombre.to_s + " construye hotel en " + @propiedades[ip].nombre.to_s)
        end
      end
      
      return result
    end
    
    def hipotecar(ip)
      result = true
      
      if(@encarcelado)
        return result
      end
      
      if(existeLaPropiedad(ip))
        propiedad = TituloPropiedad.new()
        propiedad = @propiedades[ip]
        result = propiedad.hipotecar(self)
      end
      
      if(result)
        Diario.instance.ocurre_evento("El jugador " + @nombre.to_s + " hipoteca " + @propiedades[ip].nombre.to_s)
      end
      
      return result
    end
    
    def construirCasa(ip)
      result = false
      
      if(@encarcelado)
        return result
      end
      
      existe = existeLaPropiedad(ip)
      
      if(existe)
        propiedad = TituloPropiedad.new
        propiedad = @propiedades[ip]
        puedoEdificar = puedeEdificarCasa(propiedad)
        precio = propiedad.precioEdificar
        if(puedoEdificar)
          result = propiedad.construirCasa(self)
          Diario.instance.ocurre_evento("El jugador " + @nombre.to_s + " construye casa en " + @propiedades[ip].nombre.to_s)
        end
      end
      
      return result
    end
    
    def to_s
      return "Jugador: " + @nombre + "\n" + "Encarcelado?: " + @encarcelado.to_s + "\n" + "Saldo: " + @saldo.to_s + "\n" + "Casilla Actual: " + @numCasillaActual.to_s
    end
  end
end
