# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa'
module Civitas
  class Sorpresa_por_jugador < Sorpresa
    attr
    def initialize
      @valor = 10
    end

    def aplicar_a_jugador(actual, todos)
      if (jugador_correcto(actual, todos))
          informe(actual,todos)
          quitar = Sorpresa_pagar_cobrar.new()
          quitar.valor = 0-@valor
          for i in 1..todos.size
            if (i != actual)
              quitar.aplicar_a_jugador(i, todos)
            end
          end
          dar = Sorpresa_pagar_cobrar.new()
          dar.valor = todos.size * @valor
          dar.aplicar_a_jugador(actual, todos)
      end
    end

    def to_s()
      "Por jugador"
    end
    public_class_method:new
  end
end