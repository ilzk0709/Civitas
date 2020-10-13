module Civitas
  class MazoSorpresas
    
    def initialize(d = false)
      @debug = d
      @ultimaSorpresa = Sorpresa.new
      init()
      if(@debug)
        Diario.instance.ocurre_evento("Debug mode: " + @debug.to_s)
      end
    end
    
    def alMazo(s)
      
      if(!@barajada)
        @sorpresas.push(s)
      end
    end
    
    def siguiente()
      if((!@barajada || @usadas == @sorpresas.length) && !@debug)
        @sorpresas.shuffle
        @barajada = true
        @usadas = 0
        @usadas += 1
        temp = @sorpresas[0]
        @sorpresas.delete_at(0)
        @sorpresas.push(temp)
        @ultimaSorpresa = temp
      end
      
      return @ultimaSorpresa
    end
    
    def inhabilitarCartaEspecial(sorpresa)
      if(@sorpresas.include?(sorpresa))
        @sorpresas.delete(sorpresa)
        @cartasEspeciales.push(sorpresa)
        
        Diario.instance.ocurre_evento("Carta aniadida a cartas especiales")
      end
    end
    
    def habilitarCartaEspecial(sorpresa)
      if(@cartasEspeciales.include?(sorpresa))
        @cartasEspeciales.delete(sorpresa)
        @sorpresas.push(sorpresa)
        Diario.instance.ocurre_evento("Carta aniadida a sorpresas")
      end
    end
    
    private
    def init()
      @sorpresas = []
      @cartasEspeciales = []
      @barajada = false
      @usadas = 0
    end
  end
end
