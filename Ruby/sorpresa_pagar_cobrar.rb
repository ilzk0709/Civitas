# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa'
module Civitas
  class Sorpresa_pagar_cobrar < Sorpresa
    attr_accessor :valor
    def initialize
      #Sorpresa.new()
      @valor = 10
    end

    def aplicar_a_jugador(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor)
      end
    end

    def to_s()
      "Pagar cobrar"
    end
    public_class_method:new
  end
end