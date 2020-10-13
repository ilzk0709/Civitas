module Civitas
  module Estados_juego
    
		INICIO_TURNO = :inicio_turno  
		DESPUES_CARCEL = :despues_carcel
    DESPUES_AVANZAR = :despues_avanzar
    DESPUES_COMPRAR = :despues_comprar
    DESPUES_GESTIONAR = :despues_gestionar
  end
  
  module Operaciones_juego
    
		AVANZAR = :avanzar
    COMPRAR = :comprar
    GESTIONAR = :gestionar
    SALIR_CARCEL = :salir_carcel
    PASAR_TURNO = :pasar_turno
  end
  
  module Tipo_casilla
    
		CALLE = :calle  
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
end
