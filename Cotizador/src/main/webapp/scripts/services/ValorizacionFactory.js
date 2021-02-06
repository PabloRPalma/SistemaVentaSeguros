angular.module('cotizador').factory('ValorizacionResource', function($resource){
    var resource = $resource('rest/valorizacions/:ValorizacionId',{ValorizacionId:'@valorizacionId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});