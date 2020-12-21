module Civitas
  class MazoSorpresas
    srand()
    def initialize(*d)
      @sorpresas = []
      @barajada = false
      if (d.size != 0)
        @debug = d
        if (@debug == true)
          Diario.instance.ocurre_evento("Se ha creado el mazo de sorpresas")
        end
      else
        @debug = false
      end
      @usadas = 0
      @cartas_especiales = []
      @ultima_sorpresa
    end
    
    def al_mazo(s)
      if (@barajada == false)
        @sorpresas << s
      end
    end
    
    def siguiente
      sorpresa = Sorpresa.new()
      if (@barajada == false || @usadas == @sorpresas.size)
        if (@debug == false)
          for i in (0..@sorpresas.size)
            ind = (rand() * sorpresas.size).to_i
            sorpresatemp = @sorpresas[i]
            @sorpresas[i] = sorpresas[ind]
            @sorpresas[ind] = sorpresatemp
          end
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
        Diario.instance.ocurre_evento("Se ha quitado la carta " + sorpresa.to_s + " del mazo de sorpresas")
      end
    end
    
    def habilitar_carta_especial(sorpresa)
      while (@cartas_especiales.rindex(sorpresa) != nil)
        @cartas_especiales.delete(sorpresa)
        @sorpresas << sorpresa
        Diario.instance.ocurre_evento("Se ha aniadido la carta " + sorpresa.to_s + " al mazo de sorpresas")
      end
    end
  end  
end