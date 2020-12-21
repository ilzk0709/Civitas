# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaJuez < Casilla
    def initialize (carcel, nombre)
      @carcel = carcel
      super(nombre)
    end
    
    def recibe_jugador(actual,todos)
      if(jugador_correcto(actual,todos))
        super
        todos[actual].encarcelar(@carcel)
      end
    end
    
    def to_s
      return super + "\nJugador enviado a la cárcel\n"
    end
  end
end
