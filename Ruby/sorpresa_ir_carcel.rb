# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa'
module Civitas
  class Sorpresa_ir_carcel < Sorpresa
    def initialize(_tablero)
      #Sorpresa.new("Ir carcel", -1, nil, _tablero)
      @texto = "Ir carcel"
      @tablero = _tablero
    end

    def aplicar_a_jugador(actual, todos)
      if (jugador_correcto(actual, todos))
          informe(actual, todos)
          todos[actual].encarcelar(@tablero.num_casilla_carcel)
      end
    end

    def to_s()
      "Ir carcel"
    end

    public_class_method:new
  end
end