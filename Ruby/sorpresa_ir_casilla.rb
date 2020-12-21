# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa'
module Civitas
  class Sorpresa_ir_casilla < Sorpresa
    def initialize(_tablero, _num_casilla)
      @tablero = _tablero
      @valor = _num_casilla
    end

    def aplicar_a_jugador(actual, todos)
      casilla_actual = 0
      tirada = 0
      if(jugador_correcto(actual, todos))
          casilla_actual = todos[actual].numCasillaActual
          tirada = @tablero.nueva_posicion(casilla_actual, @valor)
          todos[actual].moverACasilla(tirada)
          @tablero.get_casilla(tirada).recibe_jugador(actual, todos)
      end
    end

    def to_s()
      "Ir casilla"
    end

    public_class_method:new
  end
end
