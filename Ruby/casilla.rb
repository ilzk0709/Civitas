#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'estados_juego'
module Civitas
  
  attr_reader :nombre, :titulo_propiedad  
  
  class Casilla
    
    @@carcel = 10
    private
    def initialize( _tipo, _nombre = "", _importe = 0, _mazo = nil, _sorpresa = nil, _titulo_propiedad = nil)
        @nombre = _nombre
        @importe = _importe
        @mazo = _mazo
        @sorpresa = _sorpresa
        @titulo_propiedad = _titulo_propiedad
        @tipo = _tipo
    end
    
    def self.new_casilla_calle(_titulo_propiedad)
      new(Tipo_casilla::CALLE, _titulo_propiedad.nombre, _titulo_propiedad.precioCompra, nil, nil, _titulo_propiedad)
    end
    
    def self.new_casilla_sorpresa(_mazo, _nombre)
      new(Tipo_casilla::SORPRESA, _nombre, 0 , _mazo)
    end
    
    def self.new_casilla_juez(_num_carcel, _nombre)
      @@Carcel = _num_carcel
      new(Tipo_casilla::JUEZ, _nombre)
    end
    
    def self.new_casilla_impuesto(cantidad, _nombre)
      new(Tipo_casilla::IMPUESTO, _nombre, cantidad)
    end
    
    def self.new_casilla_descanso(_nombre)
      new(Tipo_casilla::DESCANSO, _nombre)
    end
    
    def informe(actual, todos)
      Diario.instance.ocurre_evento("El jugador" + todos[actual].to_s + 
          " ha caido en la casilla" + todos[actual].numCasillaActual.to_s + "\n" + self.to_s)
    end
    
    def jugador_correcto(actual, todos)
      actual >= 0 && actual < todos.size()
    end
    public
    def recibe_jugador(actual, todos)
      case @tipo
      when Tipo_casilla::CALLE
        recibe_jugador_calle(actual, todos)
      when Tipo_casilla::SORPRESA
        recibe_jugador_sorpresa(actual, todos)
      when Tipo_casilla::IMPUESTO
        recibe_jugador_impuesto(actual, todos)
      when Tipo_casilla::DESCANSO
        informe(actual,todos)
      when Tipo_casilla::JUEZ
        recibe_jugador_juez(actual, todos)
      end
    end
    
    private
    def recibe_jugador_calle(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        jugador = todos[actual]
        if(!@titulo_propiedad.tienePropietario())
          jugador.puedeComprarCasilla()
        else
          @titulo_propiedad.tramitarAlquiler(jugador)
        end
        todos[actual] = jugador
      end
    end
    
    private
    def recibe_jugador_sorpresa(actual, todos)
      if (jugador_correcto(actual, todos))
        @sorpresa = @mazo.siguiente
        informe(actual, todos)
        @sorpresa.aplicar_a_jugador(actual, todos)
      end
    end
    
    private
    def recibe_jugador_juez(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual].encarcelar(@@carcel)
      end
    end
    
    private
    def recibe_jugador_impuesto(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual].pagaImpuesto(@importe)
      end
    end
    
    def self.to_s
      "Tipo: " + @tipo + "\nNombre: " + @nombre + "\nImporte: " + @importe
    end
  end
end

