module Civitas
  attr_reader :num_propiedad, :gestion
  class Operacion_inmobiliaria
    def initialize (_gest, _num_propiedad)
      @num_propiedad = _num_propiedad
      @gestion = _gest
    end
  end
end