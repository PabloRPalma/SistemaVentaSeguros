angular.module('cotizador').factory('ResultadoValorizacionResource', function($resource){
    var resource = $resource('rest/resultadovalorizacions/:ResultadoValorizacionId',{ResultadoValorizacionId:'@resultadoId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});