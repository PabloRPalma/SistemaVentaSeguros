angular.module('cotizador').factory('ComunaResource', function($resource){
    var resource = $resource('rest/comunas/:ComunaId',{ComunaId:'@codigo'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});