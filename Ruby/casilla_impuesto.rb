# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaImpuesto < Casilla
    def initialize(impor, nombre)
      @importe = impor
      super(nombre)
    end
    
    def recibe_jugador(actual, todos)
      if(jugador_correcto(actual,todos))
        super
        todos[actual].pagaImpuesto(@importe)
      end
    end
    
    def to_s
      return super + "\nImpuesto de: " + @importe.to_s + "\n"
    end
  end
end
