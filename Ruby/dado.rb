#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require 'singleton'
module Civitas
  class Dado
    include Singleton
    srand()
    attr_accessor = :ultimo_resultado
    def initialize
      @@salida_carcel = 5
      @ultimo_resultado = 0
      @debug = false
    end
    
    def tirar
      tirada = 1
      if (@debug == false)
        tirada = rand(6)+1.to_i
      end
      @ultimo_resultado = tirada
      return tirada
    end
    
    def salgo_de_la_carcel
      sale = false
      tirada = tirar()
      if (tirada >= 5)
        sale = true
      end
      return sale
    end
    
    def quien_empieza(n)
      numjugador = (rand(n) + 1).to_i
    end
    
    def ultimo_resultado
      @ultimo_resultado
    end
    
    def set_debug(d)
      @debug = d
      if (@debug == true)
        Diario.instance.ocurre_evento("Se ha activado el debug")
      else 
        if (@debug == false)
          Diario.instance.ocurre_evento("Se ha desactivado el debug")
        end
      end
    end
    
  end
end