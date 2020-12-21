require_relative "titulo_propiedad"

module Civitas
  class CasillaCalle < Casilla
    attr_reader :titulo
    
    def initialize(titulo)
      @titulo = titulo
      super(@titulo.nombre)
    end
    
    def recibe_jugador(actual, todos)
      if(jugador_correcto(actual, todos))
        super
        jugador = todos[actual]
        if(!@titulo.tienePropietario())
          jugador.puedeComprarCasilla
        else
          @titulo.tramitarAlquiler(jugador)
        end
        todo[actual] = jugador
      end
    end
    
    def to_s
      return super + "\nPropiedad " + @titulo.toString() + "\n"
    end
  end
end
