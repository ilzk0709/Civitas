module JuegoTexto
  require_relative 'civitas_juego'
  require_relative 'vista_textual'
  class Controlador
    def initialize(_juego, _vista)
      @juego = _juego
      @vista = _vista
    end
    def juega
      @vista.setCivitasJuego(@juego)
      while (@juego.final_del_juego)
        @vista.actualizarVista
        @vista.pausa
        if (@juego.siguiente_paso != Civitas::Operaciones_juego::PASAR_TURNO)
          while Civitas::Diario.instance.eventos_pendientes
            puts Civitas::Diario.instance.leer_evento
            puts "-------------------------------------"
          end
          puts "______________________________________"
        end
        if (!@juego.final_del_juego)
          case @juego.siguiente_paso
          when Civitas::Operaciones_juego::COMPRAR
            if (@vista.comprar() == Respuestas::SI)
              @juego.comprar()
            end
            @juego.siguiente_paso_completado(@juego.siguiente_paso)
          when Civitas::Operaciones_juego::GESTIONAR
            gestion = lista_gestiones[@vista.i_gestion]
            propiedad = @vista.i_propiedad
            case gestion
            when Gestiones_inmobiliarias::VENDER 
            when Gestiones_inmobiliarias::HIPOTECAR
              @juego.hipotecar(propiedad)
            when Gestiones_inmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelar_hipoteca(propiedad)
            when Gestiones_inmobiliarias::TERMINAR
              @juego.siguiente_paso_completado(@juego.siguiente_paso)
            when Gestiones_inmobiliarias::CONSTRUIR_CASA
              @juego.construir_casa(propiedad)
            when Gestiones_inmobiliarias::CONSTRUIR_HOTEL
              @juego.construir_hotel(propiedad)
            end
          when Civitas::Operaciones_juego::SALIR_CARCEL
            case(@vista.salir_carcel)
            when Salidas_carcel::PAGANDO
              @juego.salir_carcel_pagando
            when Salidas_carcel::TIRANDO
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completado(@juego.siguiente_paso)
          end
        end
      end
    end
  end
end