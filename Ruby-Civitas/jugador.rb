# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Jugador
    
    attr_accessor :nombre, :CASAS_MAX, :HOTELES_MAX, :CASAS_POR_HOTEL, :numCasillaActual, :PRECIO_POR_LIBERTAL, 
      :PASO_POR_SALIDA, :propiedades, :puedeComprar, :saldo, :encarcelado
    
    CASAS_MAX = 4
    HOTELES_MAX = 4
    SALDO_INICIAL = 7500
    CASAS_POR_HOTEL = 4
    PASO_POR_SALIDA = 1000
    PRECIO_POR_LIBERTAD = 200
    
    def initialize(nomb, jug = nil)
      if(jug != nil)
        @nombre = nomb
        @propiedades = []
        @encarcelado = false
        @numCasillaActual = 0
        @puedeComprar = true
        @saldo = SaldoInicial
        @salvoconducto = Sorpresa.new  
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
        if(!tieneSalvoconducto())
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
        moverAcasilla(numCasillaCarcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + "va a la carcel")
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
        Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + "se mueve a " + @numCasillaActual.to_s)
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
      return modificarSaldo(cantidad * -1)
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
      modificarSaldo(PASO_POR_SALIDA)
      Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + "ha pasado por salida")
      return true
    end
    
    def perderSalvoConducto
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
    
    def puedeSalirCarcelPagando()
      b = false
      
      if(@saldo >= PRECIO_POR_LIBERTAD)
        b = true
      end
      
      return b
    end
    
    def puedeEdificarCasa(propiedad)
      b = false
      
      if(propiedad.numCasas < CASAS_MAX)
        if(@saldo >= propiedad.precioEdificar)
          b = true
        end
      end
      
      return b
    end
    
    def puedeEdificarHotel(propiedad)
      b = false
      
      if(propiedad.numHoteles < HOTELES_MAX)
        if(propiedad.numCasas == CASAS_POR_HOTEL)
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
        Diario.instance.ocurre_evento("Jugador " + @nombre.to_s + " recibe " + cantida.to_s)
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
          if(@propiedades[ip].vender(this))
            @propiedades.delete_at(ip)
            Diario.instance.ocurre_evento("Jugador " + @nombre + " vende " + @propiedades[ip].nombre)
            b = true
          end
        end
      end
      
      return b
    end
    
    def toString()
      return "Jugador: " + @nombre + "\n"
      + "Encarcelado?: " + @encarcelado + "\n"
      + "Saldo: " + @saldo + "\n"
      + "Casilla Actual: " + @numCasillaActual
    end
  end
end
