# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa'
module Civitas
  class Sorpresa_salir_carcel < Sorpresa
    def initialize( _mazo)
      #Sorpresa.new("Salir carcel", -1, _mazo)
      @mazo = _mazo
    end

    def aplicar_a_jugador(actual, todos)
      if (jugador_correcto(actual,todos))
        informe(actual, todos)
        tienen = false
        for i in 1..todos.size-1
          if (todos[i].tieneSalvoconducto())
            tienen = true
          end
        end
        if (!tienen)
          todos[actual].obtenerSalvoconducto(self)
          salir_del_mazo()
        end
      end
    end

    def salir_del_mazo()
      if (@tipo == Tipo_sorpresa::SALIRCARCEL)
        @mazo.inhabilitar_carta_especial(self)
      end
    end

    def usada()
      if (@tipo == Tipo_sorpresa::SALIRCARCEL)
        @mazo.habilitar_carta_especial(self)
      end
    end

    def to_s()
      "Salir carcel"
    end

    public_class_method:new
  end
end