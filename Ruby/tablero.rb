#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class Tablero
    attr_accesor = :num_casilla_carcel, :por_salida
    def initialize(num_casilla_carcel)
      if(num_casilla_carcel > 0)
        @num_casilla_carcel = num_casilla_carcel
      else
        @num_casilla_carcel = 1
      end
      @casillas = Array.new()
      casilla = Casilla.new("Salida")
      @casillas << casilla
      @por_salida = 0
      @tiene_juez = false
    end

    def correcto(*num_casilla)
      if (num_casilla.size == 0)
        return (@casillas.size > @num_casilla_carcel)
      else
        return (@casillas.size > @num_casilla_carcel && num_casilla[0] > 0 && num_casilla[0] < @casillas.size)
      end
    end
    
    def get_por_salida
      numero = @por_salida
      if (@por_salida > 0)
        @por_salida = @por_salida - 1
      end
      return numero
    end
      
    def aniade_casilla(casilla)
      carcel = Casilla.new("Carcel")
      if (@casillas.size == @num_casilla_carcel)
        @casillas << carcel
      end
      @casillas << casilla
      if (@casillas.size == @num_casilla_carcel)
        @casillas << carcel
      end
    end
    
    def aniade_juez
      juez = CasillaJuez.new(@numCasillaCarcel, "Juez")
      if (@tiene_juez == false)
        @casillas << juez
        @tiene_juez = true
      end
    end
    
    def get_casilla(num_casilla)
      casilla = nil
      if (correcto(num_casilla))
        casilla = @casillas.slice(num_casilla)
      end
      return casilla
    end
    
    def nueva_posicion(actual, tirada)
      posicion = -1
      if (correcto)
        posicion = (actual + tirada) % @casillas.size()
        if (posicion != actual + tirada)
          @por_salida = por_salida + 1
        end
      end
      return posicion
    end
    
    def calcular_tirada(origen, destino)
      tirada = destino - origen
      if (tirada < 0)
        tirada = tirada + @casillas.size
      end
      return tirada
    end
    
    def vaciar
      @casillas = []
    end
  end
end