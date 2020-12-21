# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa'
module Civitas
  class Sorpresa_por_casa_hotel < Sorpresa
    def initialize
      @valor = 10
    end

    def aplicar_a_jugador(actual,todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        for i in 1..todos[actual].cantidadCasasHoteles()
          todos[actual].modificarSaldo(@valor)
        end
      end
    end

    def to_s()
      "Por casa hotel"
    end

    public_class_method:new
  end
end