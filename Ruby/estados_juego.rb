module Civitas
  
  module Estados_juego
		INICIO_TURNO = :inicio_turno  
		DESPUES_CARCEL = :despues_carcel
    DESPUES_AVANZAR = :despues_avanzar
    DESPUES_COMPRAR = :despues_comprar
    DESPUES_GESTIONAR = :despues_gestionar
  end
  module Tipo_casilla
    CALLE =:calle
    SORPRESA = :sorpresa
    JUEZ = :juez
    IMPUESTO = :impuesto
    DESCANSO = :descanso
  end
  module Tipo_sorpresa
    IRCARCEL = :ircarcel
    IRCASILLA = :ircasilla
    PAGARCOBRAR = :pagarcobrar
    PORCASAHOTEL = :porcasahotel
    PORJUGADOR = :porjugador
    SALIRCARCEL = :salircarcel
  end
  module Operaciones_juego
    AVANZAR = :avanzar
    COMPRAR = :comprar
    GESTIONAR = :gestionar
    SALIR_CARCEL = :salir_carcel
    PASAR_TURNO = :pasar_turno
  end
  
end

module JuegoTexto
  
  module Respuestas
    SI = :si
    NO = :no
  end
  
  module Gestiones_inmobiliarias
    VENDER = :vender
    HIPOTECAR = :hipotecar
    TERMINAR = :terminar
    CANCELAR_HIPOTECA = :cancelar_hipoteca
    CONSTRUIR_CASA = :construir_casa
    CONSTRUIR_HOTEL = :construir_hotel
    
  end
  
  module Salidas_carcel
    PAGANDO = :pagando
    TIRANDO = :tirando
  end
  
end
