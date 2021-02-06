

angular.module('cotizador').controller('EditValorizacionController', function($scope, $routeParams, $location, flash, ValorizacionResource , EstadoValorizacionResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.valorizacion = new ValorizacionResource(self.original);
            EstadoValorizacionResource.queryAll(function(items) {
                $scope.estadoValorizacionSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        estadoValorizacionId : item.estadoValorizacionId
                    };
                    var labelObject = {
                        value : item.estadoValorizacionId,
                        text : item.estadoValorizacionId
                    };
                    if($scope.valorizacion.estadoValorizacion && item.estadoValorizacionId == $scope.valorizacion.estadoValorizacion.estadoValorizacionId) {
                        $scope.estadoValorizacionSelection = labelObject;
                        $scope.valorizacion.estadoValorizacion = wrappedObject;
                        self.original.estadoValorizacion = $scope.valorizacion.estadoValorizacion;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The valorizacion could not be found.'});
            $location.path("/Valorizacions");
        };
        ValorizacionResource.get({ValorizacionId:$routeParams.ValorizacionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.valorizacion);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The valorizacion was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.valorizacion.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Valorizacions");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The valorizacion was deleted.'});
            $location.path("/Valorizacions");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.valorizacion.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("estadoValorizacionSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.valorizacion.estadoValorizacion = {};
            $scope.valorizacion.estadoValorizacion.estadoValorizacionId = selection.value;
        }
    });
    $scope.clienteNuevoList = [
        "true",
        "false"
    ];
    
    $scope.get();
});