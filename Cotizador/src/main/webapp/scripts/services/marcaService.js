'use strict';

angular.module('cotizador').factory('MarcaService' , function () {
    var marcas = [];
    var comunas = [];

  
    
    

    return {
      getMarcas: function () {
        return marcas;
      },
      setMarcas: function(m) {
    	  marcas= m;
      },
      getComunas: function () {
          return comunas;
        },
        setComunas: function(m) {
      	  comunas= m;
        },
    };
});
