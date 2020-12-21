require_relative 'estados_juego'
module Civitas
  
  attr_reader :nombre  
  
  class Casilla
    
    @@carcel = 10
    private
    def initialize(_nombre = "")
      @nombre = _nombre
    end
    
    def informe(actual, todos)
      Diario.instance.ocurre_evento("El jugador " + todos[actual].to_s + 
          " ha caido en la casilla" + todos[actual].numCasillaActual.to_s + "\n" + to_s)
    end
    
    def jugador_correcto(actual, todos)
      actual >= 0 && actual < todos.size()
    end
    
    public
    def recibe_jugador(actual, todos)
      informe(actual, todos);
    end
    
    def to_s
      "Casilla de nombre " + @nombre.to_s
    end
  end
end