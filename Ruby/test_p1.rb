#encoding:utf-8

require_relative "diario"
require_relative "sorpresa"
require_relative "mazo_sorpresas"
require_relative "tablero"
require_relative "dado"
require_relative "casilla"
require_relative "estados_juego"

module Civitas
  
  class TestP1
    def main()
    
      #PRUEBA 1:
      
      cont1 = 0; cont2 = 0; cont3 = 0; cont4 = 0
      
      for i in 0..99
        
        n = Dado.instance.quienEmpieza(4)
        
        case n
        when 0
          cont1 += 1
        when 1
          cont2 += 1
        when 2
          cont3 += 1
        when 3
          cont4 += 1
        end
      end
      
      puts "Jug1: " + cont1.to_s
      puts "Jug2: " + cont2.to_s
      puts "Jug3: " + cont3.to_s
      puts "Jug4: " + cont4.to_s
      puts "\n--------------\n\n"
      
      #PRUBA 2:
      
      Dado.instance.setDebug(true)
      
      for i in 0..10
        x = Dado.instance.tirar
        puts x
      end
      
      puts "\n--------------\n\n"
      
      Dado.instance.setDebug(false)
      
      for i in 0..10
        x = Dado.instance.tirar
        puts x
      end
      
      puts "\n--------------\n\n"
      
      #PRUEBA 3:
      
      a = Dado.instance.ultimoResultado
      b = Dado.instance.salgoDeLaCarcel
      
      puts "Tirada: " + a.to_s
      puts "Salir Carcel: " + b.to_s
      puts "\n--------------\n\n"
      
      #PRUEBA 4:
      
      puts Tipo_casilla::CALLE
      puts Estados_juego::DESPUES_COMPRAR
      puts Tipo_sorpresa::PORCASAHOTEL
      puts Operaciones_juego::COMPRAR
      puts "\n--------------\n\n"
      
      #PRUEBA 5:
      
      mazo = MazoSorpresas.new()
      s = Sorpresa.new
      h = Sorpresa.new
      
      mazo.alMazo(s)
      mazo.alMazo(h)
      mazo.siguiente
      mazo.inhabilitarCartaEspecial(h)
      mazo.habilitarCartaEspecial(h)
      puts Diario.instance.leer_evento
      
      puts "\n--------------\n\n"
      
      #PRUEBA 6:
      
      tablero = Tablero.new(0)
      c1 = Casilla.new(1)
      c2 = Casilla.new(2)
      c3 = Casilla.new(3)
      
      tablero.añadeCasilla(c1)
      tablero.añadeCasilla(c2)
      tablero.añadeCasilla(c3)
      tablero.añadeJuez
      
      puts "TABLERO: " + tablero.getCasilla(5).nombre
     
      tirada = Dado.instance.tirar
      posicion_actual = tablero.nuevaPosicion(0, tirada)
      
      puts posicion_actual.to_s
      puts "Tirada: " + tirada.to_s
      puts "Calculo de tirada: " + tablero.calcularTirada(0, posicion_actual).to_s
      
    end
  end
  
  TestP1.new.main
end
