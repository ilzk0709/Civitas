# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "casilla"
require_relative "dado"
require_relative "diario"
require_relative "estados_juego"
require_relative "mazo_sorpresas"
require_relative "sorpresa"
require_relative "tablero"

module Civitas
  class Test_P1
    
    def initialize
    end
    
    def main
      @probabilidades = [0.0, 0.0, 0.0, 0.0]
      @resultados = [0,0,0,0,0,0]
      @pruebas = 100
      @board = Tablero.new(10)
      @casillas = 20
      
      #Que jugador empieza
        for i in (0..@pruebas)
          resultado = Dado.instance.quien_empieza(@probabilidades.length)
          @probabilidades[resultado-1] +=1
        end

        puts "Resultados: " + @probabilidades.to_s()
      
      #Probabilidades de que empiece cada jugador
        for i in (0..@probabilidades.length - 1)
          @probabilidades[i] = @probabilidades[i]/@pruebas
        end

        puts "Probabilidades: " + @probabilidades.to_s()
        
      #Prueba del modo debug
        Dado.instance.set_debug(true)
        
        for i in (1..@pruebas)
          resultado = Dado.instance.tirar
          @resultados[resultado-1] += 1
          if(resultado != 1)
            @funciona = false
          end
        end

        puts "Resultados(d): " + @resultados.to_s()
        
        @resultados[0] = 0
      #Prueba de desactivar el debug
        Dado.instance.set_debug(false)
        @suma = 0
          for i in (1..@pruebas)
            resultado = Dado.instance.tirar
            @resultados[resultado-1] += 1
            @suma = @suma + resultado
          end
            if (@suma == @pruebas)
              @funciona = false
            end

        puts "Resultados: " + @resultados.to_s()

      #Prueba de salgo_de_la_carcel y ultimo_resultado
        if (Dado.instance.salgo_de_la_carcel)
          puts "Salgo de la carcel"
        else if (Dado.instance.salgo_de_la_carcel == false)
          puts "No salgo de la carcel"
          end
        end
        puts "Ultimo resultado: " + Dado.instance.ultimo_resultado.to_s
      #Prueba a crear mazos y aniadir y quitar sorpresas
        
        @mazo = MazoSorpresas.new(true)
        @s1 = Sorpresa.new
        @s2 = Sorpresa.new
        @mazo.al_mazo(@s1)
        @mazo.al_mazo(@s2)
        @mazo.inhabilitar_carta_especial(@s2)
        @mazo.habilitar_carta_especial(@s2)
        
        puts "DIARIO"

        while (Diario.instance.eventos_pendientes)
          puts Diario.instance.leer_evento
        end

        puts "FIN DIARIO"
      #Llenar tablero
        for i in (0..@casillas)
          @board.aniade_casilla(Casilla.new)
        end

        if (@board.get_casilla(@casillas) != nil)
          puts "En el tablero hay " + @casillas.to_s + " casillas"
        end
      #Comprobar error de num_casilla_carcel > tablero
        @board.vaciar

      for i in (0..5)
        @board.aniade_casilla(Casilla.new)
      end
      @posicion = @board.nueva_posicion(0, Dado.instance.tirar)
      if (@posicion == -1)
        puts "Tablero incorrecto"
      end
    end
  end
  
  Test_P1.new.main
  
end



