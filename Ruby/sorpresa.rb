require_relative 'estados_juego'
module Civitas
  attr_accessor :valor 
  class Sorpresa
    
    def initialize(_tipo, _texto = "", _valor = -1, _mazo = nil,_tablero = nil)
      @texto = _texto
      @valor = _valor
      @mazo = _mazo
      @tipo = _tipo
      @tablero = _tablero
    end
    
    def self.new_sorpresa_pagcob(_tipo)
      new(_tipo)
      case _tipo
      when Tipo_sorpresa::PAGARCOBRAR
        @valor = 10
      when Tipo_sorpresa::PORCASAHOTEL
        @valor = 10
      when Tipo_sorpresa::PORJUGADOR
        @valor = 10
      end
    end
    
    def self.new_sorpresa_ir_carcel(_tablero)
      new(Tipo_sorpresa::IRCARCEL, "Ir carcel", -1, nil, _tablero)
    end
    
    def self.new_sorpresa_ir_casilla(_tablero, _num_casilla)
      new(Tipo_sorpresa::IRCASILLA, "Ir casilla", _num_casilla, nil, _tablero)
    end
    def self.new_salir_carcel( _mazo)
      new(Tipo_sorpresa::SALIRCARCEL, "Salir carcel", -1, _mazo)
    end
    
    def jugador_correcto(actual, todos)
      actual >= 0 && todos.size > actual
    end
    
    def informe(actual, todos)
      Diario.instance.ocurre_evento("Se esta aplicando la sorpresa " + to_s() +
          " al jugador " + todos[actual].nombre)
    end
    
    def aplicar_a_jugador(actual, todos)
      case @tipo
      when Tipo_sorpresa::IRCASILLA
        aplicar_a_jugador_ir_casilla(actual, todos)
      when Tipo_sorpresa::IRCARCEL
        aplicar_a_jugador_ir_carcel(actual, todos)
      when Tipo_casilla::PAGARCOBRAR
        aplicar_a_jugador_pagar_cobrar(actual, todos)
      when Tipo_casilla::PORCASAHOTEL
        aplicar_a_jugador_por_casa_hotel(actual,todos)
      when Tipo_casilla::PORJUGADOR
        aplicar_a_jugador_por_jugador(actual, todos)
      when Tipo_casilla::SALIRCARCEL
        aplicar_a_jugador_salir_carcel(actual, todos)
      end
    end
    private
    def aplicar_a_jugador_ir_casilla(actual, todos)
      casilla_actual = 0
      tirada = 0
      if(jugador_correcto(actual, todos))
        casilla_actual = todos[actual].numCasillaActual
        tirada = @tablero.nueva_posicion(casilla_actual, @valor)
        todos[actual].moverACasilla(tirada)
        @tablero.get_casilla(tirada).recibe_jugador(actual, todos)
      end
    end
    
    private
    def aplicar_a_jugador_ir_carcel(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual].encarcelar(@tablero.num_casilla_carcel)
      end
    end
    
    private
    def aplicar_a_jugador_pagar_cobrar(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor)
      end
    end
    
    private
    def aplicar_a_jugador_por_casa_hotel(actual,todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        for i in todos[actual].cantidadCasasHoteles()
          todos[actual].modificarSaldo(@valor)
        end
      end
    end
    
    private
    def aplicar_a_jugador_por_jugador(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual,todos)
        quitar = Sorpresa.new(Tipo_sorpresa::PAGARCOBRAR)
        quitar.valor = -@valor
        for i in todos.size
          if (i != actual)
            quitar.aplicar_a_jugador(i, todos)
          end
        end
        dar = Sorpresa.new(Tipo_sorpresa::PAGARCOBRAR)
        dar.valor = todos.size * @valor
        dar.aplicar_a_jugador(actual, todos)
      end
    end
    
    private
    def aplicar_a_jugador_salir_carcel(actual, todos)
      if (jugador_correcto(actual,todos))
        informe(actual, todos)
        tienen = false
        for i in todos.size
          if (todos[i].tieneSalvoconducto())
            tienen = true
          end
        end
        if (!tienen)
          todos[actual].obtenerSalvoconducto(self)
          salir_del_mazo()
        end
      end
    end
    
    def salir_del_mazo()
      if (@tipo == Tipo_sorpresa::SALIRCARCEL)
        @mazo.inhabilitar_carta_especial(self)
      end
    end
    
    def usada()
      if (@tipo == Tipo_sorpresa::SALIRCARCEL)
        @mazo.habilitar_carta_especial(self)
      end
    end
    
    def to_s()
      case @tipo
      when Tipo_sorpresa::IRCARCEL
        "Ir carcel"
      when Tipo_sorpresa::IRCASILLA
        "Ir casilla"
      when Tipo_sorpresa::PAGARCOBRAR
        "Pagar cobrar"
      when Tipo_sorpresa::PORCASAHOTEL
        "Por casa hotel"
      when Tipo_sorpresa::PORJUGADOR
        "Por jugador"
      when Tipo_sorpresa::SALIRCARCEL
        "Salir carcel"
      end
    end
  end
end