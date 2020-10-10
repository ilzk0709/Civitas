# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class Test_P1
    @dado = Dado.new
    @diario = Diario.new
    @probabilidades = [0,0,0,0]
    @pruebas = 100
    @board = Tablero.new(10)
    @casillas = 20
      for i in (0..@pruebas)
        resultado = @dado.quien_empieza(@probabilidades.length)
        @probabilidades[resultado] = @probabilidades[resultado] + 1
      end
      
      put @probabilidades.to_s()
      
      for i in (0..@probabilidades.length)
        @probabilidades[i] = @probabilidades[i]/@pruebas
      end
      
      put @probabilidades.to_s()
      
      @dado.set_debug(true)
        for i in (0..@pruebas)
          @resultado = @dado.quien_empieza(@probabilidades.length)
          if(@resultado != 1)
            @funciona = false
          end
        end
        
      @dado.set_debug(false)
        @suma = 0
        for i in (0..@pruebas)
          @resultado = @dado.quien_empieza(@probabilidades.length)
          @suma = @suma + @resultado
        end
          if (@suma == @pruebas)
            @funciona = false
          end
          
    if (@dado.salgo_de_la_carcel)
      put "Salgo de la carcel"
    else if (@dado.salgo_de_la_carcel == false)
      put "No salgo"
      end
    end
  
    put @dado.ultimo_resultado
    
    @mazo = MazoSorpresas.new
    @s1 = Sorpresa.new
    @s2 = Sorpresa.new
    @mazo.al_mazo(s1)
    @mazo.al_mazo(s2)
    @mazo.inhabilitar_carta_especial(s2)
    @mazo.habilitar_carta_especial(s2)
    while (@diario.eventos_pendientes)
      put @diario.leer_evento
    end
    
    for i in (0..@casillas)
      @board << Casilla.new
    end
    @board.vaciar
    for i in (0..5)
      @board << Casilla.new
    end
    @posicion = @board.nueva_posicion(0, @dado.tirar)
    if (@posicion == -1)
      put "Tablero incorrecto"
    end
  end
end

