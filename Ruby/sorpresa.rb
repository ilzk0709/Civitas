# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'estados_juego'
module Civitas
  attr_accessor :valor 
  class Sorpresa
    
    def initialize( _texto = "", _valor = -1, _mazo = nil,_tablero = nil)
      @texto = _texto
      @valor = _valor
      @mazo = _mazo
      @tablero = _tablero
    end
    
    def jugador_correcto(actual, todos)
      actual >= 0 && todos.size > actual
    end
    
    def informe(actual, todos)
      Diario.instance.ocurre_evento("Se esta aplicando la sorpresa " + to_s() + " al jugador " + todos[actual].nombre)
    end
    
    private_class_method:new
  end
end