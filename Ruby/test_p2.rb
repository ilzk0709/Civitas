# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'civitas_juego'
require_relative 'controlador'
  
module JuegoTexto
  class Test_P2
    def main
      nombres = []
      for i in 1..4
        nombres[i] = Civitas::Jugador.new("j" + i.to_s)
      end
      juego = Civitas::Civitas_juego.new(1, nombres)
      Civitas::Dado.instance.set_debug(true)
      control = Controlador.new(juego, Vista_textual.new())
      control.juega()
    end
  end
  
  test = Test_P2.new
  test.main
end