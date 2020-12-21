#encoding:utf-8
require_relative 'estados_juego'
require 'io/console'
require_relative 'diario'

module JuegoTexto
  
  class Vista_textual
    
    attr_reader :i_gestion, :i_propiedad
    
    def mostrar_estado(estado)
      puts estado
    end


    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
        "\n"+tab+"Elige una opción: ",
        tab+"Valor erróneo")
      return opcion
    end

    def salir_carcel
      lista_salidas_carcel = [Salidas_carcel::PAGANDO, Salidas_carcel::TIRANDO]
      opcion = menu("Elige la forma para intentar salir de la carcel", ["Pagando","Tirando"])
      lista_salidas_carcel[opcion]
    end

    def comprar
      lista_respuestas = [Respuestas::NO,Respuestas::SI]
      opcion = menu("Compras esta propiedad?", ["Si","No"])
      lista_respuestas[opcion]
    end

    def gestionar
      lista_gestiones = [Gestiones_inmobiliarias::VENDER,Gestiones_inmobiliarias::HIPOTECAR,Gestiones_inmobiliarias::CANCELAR_HIPOTECA,Gestiones_inmobiliarias::TERMINARGestiones_inmobiliarias::CONSTRUIR_CASA,Gestiones_inmobiliarias::CONSTRUIR_HOTEL]
      opcion = menu("Que gestion vas a hacer?", ["Vender","Hipotecar","Cancelar hipoteca","Construir casa","Construir hotel","Terminar"])
      @i_gestion = opcion
      if (@i_gestion != 5)
        titulos = @juegoModel.get_jugador_actual.propiedades
        nombres = []
        for i in titulos.size
          nombres[i] = titulos[i].to_s
        end
        opcion = menu("Con que propiedad?", nombres)
        @i_propiedad = opcion
      end
    end

    def mostrarSiguienteOperacion(operacion)
      puts "La siguiente operacion es " + operacion.to_s
    end

    def mostrarEventos
      while Diario.instance.eventos_pendientes
        puts Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
      @juegoModel=civitas
      self.actualizarVista
    end

    def actualizarVista
      puts @juegoModel.jugador_actual.to_s + @juegoModel.casilla_actual.to_s
    end
  end
end