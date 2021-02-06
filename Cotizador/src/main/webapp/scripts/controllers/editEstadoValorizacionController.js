

angular.module('cotizador').controller('EditEstadoValorizacionController', function($scope, $routeParams, $location, flash, EstadoValorizacionResource , ValorizacionResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.estadoValorizacion = new EstadoValorizacionResource(self.original);
            ValorizacionResource.queryAll(function(items) {
                $scope.valorizacionsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        valorizacionId : item.valorizacionId
                    };
                    var labelObject = {
                        value : item.valorizacionId,
                        text : item.valorizacionId
                    };
                    if($scope.estadoValorizacion.valorizacions){
                        $.each($scope.estadoValorizacion.valorizacions, function(idx, element) {
                            if(item.valorizacionId == element.valorizacionId) {
                                $scope.valorizacionsSelection.push(labelObject);
                                $scope.estadoValorizacion.valorizacions.push(wrappedObject);
                            }
                        });
                        self.original.valorizacions = $scope.estadoValorizacion.valorizacions;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The estadoValorizacion could not be found.'});
            $location.path("/EstadoValorizacions");
        };
        EstadoValorizacionResource.get({EstadoValorizacionId:$routeParams.EstadoValorizacionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.estadoValorizacion);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The estadoValorizacion was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.estadoValorizacion.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/EstadoValorizacions");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The estadoValorizacion was deleted.'});
            $location.path("/EstadoValorizacions");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.estadoValorizacion.$remove(successCallback, errorCallback);
    };
    
    $scope.valorizacionsSelection = $scope.valorizacionsSelection || [];
    $scope.$watch("valorizacionsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.estadoValorizacion) {
            $scope.estadoValorizacion.valorizacions = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.valorizacionId = selectedItem.value;
                $scope.estadoValorizacion.valorizacions.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});