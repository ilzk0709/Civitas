require_relative "jugador"
require_relative "jugador_especulador"
require_relative "titulo_propiedad"
module Civitas
  class TestP4
    def main
      
      jugador = Jugador.new("Manolo")
      titulo = TituloPropiedad.new("Finca Flores", 200, 2, 10000, 13000, 400)
      
      jugador.propiedades.push(titulo)
      puts "---Jugador antes de convertirse"
      puts jugador.to_s
      
      especulador = JugadorEspeculador.new(jugador, 2000)
     
      puts "---Jugador despues de convertirse"
      puts jugador.to_s
      
    end
  end
  
  test = TestP4.new
  test.main
end
