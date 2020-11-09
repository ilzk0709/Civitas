#encoding:utf-8

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class Casilla
    def initialize(*j)
      if (j.size != 0)
        @nombre = j[0]
      else
        @nombre = ""
      end
    end
  end
end
