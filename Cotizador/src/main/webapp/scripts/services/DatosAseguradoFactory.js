angular.module('cotizador').factory('DatosAseguradoResource', function($resource){
    var resource = $resource('rest/datosasegurados/:DatosAseguradoId',{DatosAseguradoId:'@valorizacionId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});