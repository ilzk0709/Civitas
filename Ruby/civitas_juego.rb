# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
@@CARCEL = 10


module Civitas
  srand()
  class Civitas_juego
    def initialize (_indice_jugador_actual = 0, _jugadores = [], _gestor = Gestor_estados.new, _estado = Estados_juego::INICIO_TURNO, _mazo = MazoSorpresas.new)
      @indice_jugador_actual = _indice_jugador_actual
      @jugadores = _jugadores
      @gestor = _gestor
      @estado = _estado
      @mazo = _mazo
      @tablero
    end
    
    private
    def avanza_jugador
      jugador_actual = @jugadores[@indice_jugador_actual]
      posicion_actual = jugador_actual.numCasillaActual
      tirada = Dado.instance.tirar()
      posicion_nueva = tablero.nueva_posicion(posicion_actual, tirada)
      casilla = tablero.get_casilla(posicion_nueva)
      
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.moverACasilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)
      @jugadores[@indice_jugador_actual] = jugador_actual
    end
    
    def cancelar_hipoteca(ip)
      @jugadores[@indice_jugador_actual].cancelarHipoteca(ip)
    end
    
    def comprar()
      jugador_actual = @jugadores[@indice_jugador_actual]
      num_casilla_actual = jugador_actual.numCasillaActual
      casilla = @tablero.get_casilla(num_casilla)
      titulo = casilla.titulo_propiedad
      jugador_actual.comprar(titulo)
    end
    
    def construir_casa(ip)
      @jugadores[@indice_jugador_actual].construirCasa(ip)
    end
    
    def construir_hotel(ip)
      @jugadores[@indice_jugador_actual].construirHotel(ip)
    end
    
    private
    def contabilizar_pasos_por_salida(jactual)
      while @tablero.get_por_salida > 0
        jactual.pasaPorSalida
      end
    end
    
    def final_del_juego
      for i in @jugadores.size
        @jugadores[i].saldo == 0
      end
    end
    
    def get_casilla_actual
      tablero.get_casilla(@jugadores[@indice_jugador_actual].numCasillaActual)
    end
    
    def get_jugador_actual
      @jugadores[@indice_jugador_actual]
    end
    
    def hipotecar(ip)
      @jugadores[@indice_jugador_actual].hipotecar(ip)
    end
    
    def info_jugador_texto
      @jugadores[@indice_jugador_actual].toString
    end
    
    private
    def inicializar_tablero(_mazo)
      @tablero = Tablero.new(@@CARCEL)
      tablero.aniade_casilla(Casilla.new("", _importe, nil, _sorpresa, _titulo_propiedad, _tipo))
    end
    
    private
    def pasar_turno
      @indice_jugador_actual += 1
      if (@indice_jugador_actual == @jugadores.size)
        @indice_jugador_actual = 0
      end
    end
    
    def ranking
      ranking = []
      comprobante = 0
      num_inicial = @jugadores.size
      es_maximo = true
      while(ranking.size < num_inicial)
        for i in @jugadores.size
          for j in @jugadores.size
            comprobante = @jugadores[i] <=> @jugadores[j]
            if (comprobante == -1 || @jugadores[i].saldo > 0)
              es_maximo = false
            end
          end
          if (es_maximo)
            ranking << @jugadores[i]
            @jugadores[i].saldo = -1
          end
        end
      end
    end
    
    def salir_carcel_pagando
      @jugadores[@indice_jugador_actual].salirCarcelPagando
    end
    
    def salir_carcel_tirando
      @jugadores[@indice_jugador_actual].salirCarcelTirando
    end
    
    def siguiente_paso_completado(operacion)
      @estado = @gestor.siguiente_estado(@jugadores[@indice_jugador_actual], @estado, operacion)
    end
    
    def vender(ip)
      @jugadores[@indice_jugador_actual].vender(ip)
    end
  end
end
