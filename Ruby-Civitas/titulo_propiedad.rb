# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class TituloPropiedad
    
    attr_accessor :nombre, :numCasas, :numHoteles, :precioCompra, :precioEdificar, :propietario, :hipotecado, :hipotecaBase
    
    def initialize(nomb, prec_alquiler, fac_reval, prec_hipo, prec_compra, prec_edif)
      @nombre = nomb
      @alquilerBase = prec_alquiler
      @factorRevalorizacion = fac_reval
      @FACTOR_INTERESES_HIPOTECA = 1.1
      @hipotecaBase = prec_hipo
      @precioCompra = prec_compra
      @precioEdificar = prec_edif
      @propietario = null
      @numCasas = 0
      @numHoteles = 0
      @hipotecado = false
    end
    
    def actualizaPropietarioPorConversion(jug)
      @propietario = jug
    end
    
    def esEsteElPropietario(jug)
      esPropietario = false
      
      if(@propietario == jug)
        esPropietario = true
      end
      return esPropietario
    end
    
    def getPrecioAlquiler()
      precio_alquiler = 0
      
      if(!@hipotecado) && (!@propietario)
        precio_alquiler = @alquilerBase * (1 + (@numCasas * 0.5) + (@numHoteles * 2.5))
      end
      
      return precio_alquiler
    end
    
    def getImporteCancelarHipoteca()
      cantidadRecibida = @hipotecaBase * (1 + (@numCasas * 0.5) + (@numHoteles * 2.5))
      return cantidadRecibida * @FACTOR_INTERESES_HIPOTECA
    end
    
    def propietarioEncarcelado()
      @encarcelado = false
      
      if(tienePropietario()) && (@propietario.isEncarcelado())
        @encarcelado = true
      end
      
      return @encarcelado
    end
    
    def tramitarAlquiler(jug)
      if(tienePropietario()) && (!esEsteElPropietario(jug))
        jug.pagaAlquiler(getPrecioAlquiler())
        propietario.recibe(getPrecioAlquiler())
      end
    end
    
    def tienePropietario()
      tiene_prop = false
      
      if(propietario != nil)
        tiene_prop = true
      end
      
      return tiene_prop
    end
    
    def cantidadCasasHoteles()
      return @numCasas + @numHoteles
    end
    
    def precioVenta()
      return @precioCompra + @precioEdificar * @factorRevalorizacion
    end
    
    def derruirCasas(n, jug)
      derruir = false
      
      if(esEsteElPropietario(jug)) && (@numCasas >= n)
        @numCasas -= n
        derruir = true
      end
      
      return derruir
    end
    
    def vender(jug)
      vendido = false
      
      if(esEsteElPropietario(jug)) && (!@hipotecado)
        @propietario.recibe(getPrecioVenta())
        @propietario = nil
        @numCasas = 0
        @numHoteles = 0
        vendido = true
      end
      
      return vendido
    end
    
    def toString()
      return "Nombre Propiedad: " + @nombre.to_s + "\n"
      + "Precio Alquiler: " + getPrecioAlquiler().to_s + "\n"
      + "Precio Compra: " + @precioCompra.to_s + "\n"
      + "Precio Edificacion: " + @precioEdificar.to_s + "\n"
      + "Precio Venta: " + getPrecioVenta().to_s + "\n"
      + "Propietario: " + @propietario.nombre + "\n"
      + "Hipoteca: " + @hipotecaBase.to_s + "\n"
      + "Hipotecada: " + @hipotecado.to_s + "\n"
      + "Numero Casas: " + @numCasas + "\n"
      + "Numero Hoteles: " + @numHoteles;
    end
  end
end
