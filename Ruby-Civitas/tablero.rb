#encoding: UTF-8

module Civitas
  class Tablero
    
    attr_accessor :numCasillaCarcel
    
    def initialize(n)
      
      if(n >= 1)
        @numCasillaCarcel = n
      else
        @numCasillaCarcel = 1
      end
      
      @casillas = []
      @casillas.push(Casilla.new("Salida"))
      @porSalida = 0
      @tieneJuez = false
    end
    
    def añadeCasilla(casilla)
      if(@casillas.length == @numCasillaCarcel)
        @casillas.push(Casilla.new("Cárcel"))
      end
      
      @casillas.push(Casilla.new(casilla.nombre))
      
      if(@casillas.length == @numCasillaCarcel)
        @casillas.push(Casilla.new("Cárcel"))
      end
    end
    
    def añadeJuez()
      if(!@tieneJuez)
        @casillas.push(Casilla.new("Juez"))
        @tieneJuez = true
      end
    end
    
    def porSalida()
      salida = @porSalida
       
      if(@porSalida > 0)
        @porSalida -= 1
      end
      
      return salida
    end
    
    def getCasilla(numCasilla)
      if(correcto(numCasilla))
        return @casillas[numCasilla]
      else
        return nil
      end
    end
    
    def nuevaPosicion(actual, tirada)
      posicion = -1
      
      if(correcto())
        posicion = (actual + tirada) % 20;
      end
      if(posicion != actual + tirada)
        @porSalida += 1
      end
      
      return posicion
    end
    
    def calcularTirada(origen, destino)
      tirada = destino - origen
      
      if(tirada < 0)
        tirada += 20
      end
      
      return tirada
    end

    private   
    def correcto(numCasilla = -1)
      
      correct = false
      _correct = false
      
      if(@casillas.size > @numCasillaCarcel) && (@tieneJuez)
        correct = true
      end
      
      if(numCasilla != -1)
        if(correct && (@casillas.size > numCasilla))
          _correct = true
        end
      else
        _correct = correct
      end
      
      
      return _correct;
    end    
  end
end
