# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaSorpresa
    def initialize (mazo,nombre)
      @mazo = mazo
      super(nombre)
    end
    
    def recibe_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        @sorpresa = @mazo.siguiente() 
        super
        @sorpresa.aplicar_a_jugador(actual, todos)
      end
    end
    
    def to_s
      return super + "\nLa sorpresa que saldrá es:\n" + @sorpresa.to_s() + "\n"
    end
  end
end
