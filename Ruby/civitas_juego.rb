# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
@@CARCEL = 3
@@NUM_PROPIEDADES = 12
@@NUM_SORPRESAS = 3
@@CASILLAS_TABLERO = 20
#@@PRECIO_ALQUILER = 10
#@@FACTOR_REVALORACION = 10
#@@PRECIO_HIPOTECA = 10
#@@PRECIO_COMPRA = 7270
#@@PRECIO_EDIFICACION = 20
@@IMPUESTO = 20

require_relative 'casilla'
require_relative 'jugador'
require_relative 'gestor_estados'
require_relative 'mazo_sorpresas'
require_relative 'dado'
require_relative 'tablero'
require_relative 'sorpresa'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_ir_casilla'
require_relative 'sorpresa_salir_carcel'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'titulo_propiedad'#nosesihacefalta

module Civitas
  srand()
  class Civitas_juego
    
    attr_reader :jugador_actual , :casilla_actual
    
    def inicializar_mazo_sorpresas(_tablero)
      s = Sorpresa_salir_carcel.new(@mazo)
      @mazo.al_mazo(s)
      @mazo.habilitar_carta_especial(s)
      s = Sorpresa_pagar_cobrar.new()
      @mazo.al_mazo(s)
      @mazo.habilitar_carta_especial(s)
      s = Sorpresa_por_jugador.new()
      @mazo.al_mazo(s)
      @mazo.habilitar_carta_especial(s)
      s = Sorpresa_por_casa_hotel.new()
      @mazo.al_mazo(s)
      @mazo.habilitar_carta_especial(s)
      s = Sorpresa_ir_carcel.new(@tablero)
      @mazo.al_mazo(s)
      @mazo.habilitar_carta_especial(s)
      numcas = rand(@@CASILLAS_TABLERO)+1.to_i
      s = Sorpresa_ir_casilla.new(@tablero, numcas)
      @mazo.al_mazo(s)
      @mazo.habilitar_carta_especial(s)
    end
    private :inicializar_mazo_sorpresas
    
    def inicializar_tablero(_mazo)
      @tablero = Tablero.new(@@CARCEL)
      @tablero.aniade_casilla(Casilla.new_casilla_descanso("PARKING"))
      @tablero.aniade_casilla(Casilla.new_casilla_impuesto("IMPUESTO", @@IMPUESTO))
      for i in 0..0#@@NUM_PROPIEDADES
        @tablero.aniade_casilla(Casilla.new_casilla_calle(TituloPropiedad.new("CALLE " + i.to_s, @@PRECIO_ALQUILER, @@FACTOR_REVALORACION, @@PRECIO_HIPOTECA, @@PRECIO_COMPRA, @@PRECIO_EDIFICACION)))
      end
      for n in 1..@@NUM_SORPRESAS+@@NUM_PROPIEDADES
        @tablero.aniade_casilla(Casilla.new_casilla_sorpresa(@mazo, "SORPRESA"))
      end
      @tablero.aniade_juez
      inicializar_mazo_sorpresas(@tablero)
    end
    private :inicializar_tablero
    
    def initialize (_indice_jugador_actual = 1, _jugadores = [], _gestor = Gestor_estados.new, _estado = Estados_juego::INICIO_TURNO, _mazo = MazoSorpresas.new)
      @indice_jugador_actual = _indice_jugador_actual
      @jugadores = _jugadores
      @gestor = _gestor
      @estado = _estado
      @mazo = _mazo
      inicializar_tablero(@mazo)
    end
    
    def avanza_jugador
      jugador_actual = @jugadores[@indice_jugador_actual]
      posicion_actual = jugador_actual.numCasillaActual
      tirada = Dado.instance.tirar()
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla = @tablero.get_casilla(posicion_nueva)
      
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.moverACasilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)
      @jugadores[@indice_jugador_actual] = jugador_actual
    end
    private :avanza_jugador
    
    def cancelar_hipoteca(ip)
      @jugadores[@indice_jugador_actual].cancelarHipoteca(ip)
    end
    
    def comprar()
      jugador_actual = @jugadores[@indice_jugador_actual]
      num_casilla_actual = jugador_actual.numCasillaActual
      casilla = @tablero.get_casilla(num_casilla_actual)
      titulo = casilla.titulo_propiedad
      jugador_actual.comprar(titulo)
    end
    
    def construir_casa(ip)
      @jugadores[@indice_jugador_actual].construirCasa(ip)
    end
    
    def construir_hotel(ip)
      @jugadores[@indice_jugador_actual].construirHotel(ip)
    end
    
    def contabilizar_pasos_por_salida(jactual)
      while @tablero.get_por_salida > 0
        jactual.pasaPorSalida
      end
    end
    private :contabilizar_pasos_por_salida
    
    def final_del_juego
      fin = false
      for i in 1..@jugadores.size-1
        if (@jugadores[i].saldo == 0)
          fin = true
        end
      end
      return fin
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
    def pasar_turno
      @indice_jugador_actual += 1
      if (@indice_jugador_actual == @jugadores.size)
        @indice_jugador_actual = 1
      end
    end
    
    public
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
    
    def siguiente_paso
      operacion = @gestor.operaciones_permitidas(@jugadores[@indice_jugador_actual], @estado)
      if (operacion == Operaciones_juego::PASAR_TURNO)
        pasar_turno
        siguiente_paso_completado(operacion)
      else if (operacion == Operaciones_juego::AVANZAR)
        avanza_jugador
        siguiente_paso_completado(operacion)
        end
      end
      operacion
    end
    def siguiente_paso_completado(operacion)
      @estado = @gestor.siguiente_estado(@jugadores[@indice_jugador_actual], @estado, operacion)
    end
    
    def vender(ip)
      @jugadores[@indice_jugador_actual].vender(ip)
    end
  end
end
