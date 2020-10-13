module Civitas
  
  class Dado
    include Singleton
    
    attr_accessor :ultimoResultado
    
    def initialize
      @ultimoResultado = -1
      @debug = false
      @@SALIDACARCEL = 5
    end
    
    def tirar()
      if(!@debug)
        @ultimoResultado = Random.rand(1..6)
        return @ultimoResultado
      else
        return 1
      end
    end
    
    def salgoDeLaCarcel()
      salir = false
      
      if(tirar() >= @@SALIDACARCEL)
        salir = true
      end
      
      return salir
    end
    
    def quienEmpieza(n)
      jug = Random.rand(0..n-1)
      
      return jug
    end
    
    def setDebug(d)
      @debug = d
      Diario.instance.ocurre_evento("Debug mode: " + @debug.to_s)
    end
  end
end
