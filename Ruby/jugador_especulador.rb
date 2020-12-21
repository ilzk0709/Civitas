module Civitas
  class JugadorEspeculador < Jugador
    
    @@FACTORESPECULADOR = 2
    def self.otro(jug, fianza)
      super(jug)
      @fianza = fianza
      
      for i in range super.propiedades.size
        super.propiedades[i].actualizarPropietarioPorConversion(self)
      end
    end
    
    def debeSerEncarcelado()
      encarcelar = super
      
      encarcelar = super.puedoGastar(@fianza) if (encarcelar)
      
      return encarcelar
    end
    
    def pagaImpuesto(cantidad)
      return super(cantidad/2)
    end
    
    def puedoEdificarCasa(propiedad)
      return propiedad.numCasas < @@CasasMax * @@factorEspeculador && super.puedoGastar(propiedad.precioEdificar)
    end
    
    def puedoEdificarHotel(propiedad)
      return propiedad.numHoteles < @@HotelesMax * @@factorEspeculador && super.puedoGastar(3 * propiedad.precioEdificar) && propiedad.numCasas >= @@CasasPorHotel
    end
    
    def to_s
      "Jugador Especulador \n" + super.to_s + "\n"
    end
  end
end
