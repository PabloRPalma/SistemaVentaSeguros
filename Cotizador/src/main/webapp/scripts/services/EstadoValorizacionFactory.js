angular.module('cotizador').factory('EstadoValorizacionResource', function($resource){
    var resource = $resource('rest/estadovalorizacions/:EstadoValorizacionId',{EstadoValorizacionId:'@estadoValorizacionId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});