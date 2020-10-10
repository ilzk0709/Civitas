# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class MazoSorpresas
    diario = Diario.new()
    srand()
    def initialize
       @sorpresas = []
       @barajada = false
       @debug = false
       @usadas = 0
       @cartas_especiales = []
       @ultima_sorpresa
    end
    
    def initialize(d)
      initialize()
      @debug = d
      if (@debug == true)
        diario.ocurre_evento("Se ha creado el mazo de sorpresas")
      end
    end
    
    def al_mazo(s)
      if (@barajada == false)
        sorpresa = Sorpresa.new()
        @sorpresas << sorpresa
      end
    end
    
    def siguiente
      sorpresa = Sorpresa.new()
      ind = 0
      if (@barajada == false || @usadas == @sorpresas.size)
        if (@debug == false)
          #for
          @usadas = 0
          @barajada = true
        end
      end
      @usadas = usadas + 1
      sorpresa = @sorpresas.shift
      @sorpresas.delete_at(0)
      @sorpresas << sorpresa
      return sorpresa
    end
    
    def inhabilitar_carta_especial(sorpresa)
      while (@sorpresas.rindex(sorpresa) != nil)
        @sorpresas.delete(sorpresa)
        @cartas_especiales << sorpresa
        diario.ocurre_evento("Se ha quitado la carta " + sorpresa + 
            " del mazo de sorpresas")
      end
    end
    
    def habilitar_carta_especial(sorpresa)
      while (@cartas_especiales.rindex(sorpresa) != nil)
        @cartas_especiales.delete(sorpresa)
        @sorpresas << sorpresa
        diario.ocurre_evento("Se ha aniadido la carta " + sorpresa + 
            " al mazo de sorpresas")
      end
    end
  end  
end

