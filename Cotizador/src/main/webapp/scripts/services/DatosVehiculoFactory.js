angular.module('cotizador').factory('DatosVehiculoResource', function($resource){
    var resource = $resource('rest/datosvehiculos/:DatosVehiculoId',{DatosVehiculoId:'@valorizacionId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});