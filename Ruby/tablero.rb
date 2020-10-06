# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class Tablero
    attr_accesor = :numCasillaCarcel, :porSalida
    def initialize(numCasillaCarcel)
      if(numCasillaCarcel > 0)
        @numCasillaCarcel = numCasillaCarcel
      else
        @numCasillaCarcel = 1
      @casillas = Array.new()
      @porSalida = 0
      @tieneJuez = false
      end
    end

    def correcto
      @casillas.size < @numCasillaCarcel
    end
    
    def correcto(numCasilla)
      @casillas.size < @numCasillaCarcel && numCasilla > 0 && numCasilla < @casillas.size
    end
  end
end
