angular.module('cotizador').factory('ModeloResource', function($resource){
    var resource = $resource('rest/modelos/:ModeloId',{ModeloId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});