

angular.module('cotizador').controller('EditMarcaController', function($scope, $routeParams, $location, flash, MarcaResource , ModeloResource, DatosVehiculoResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.marca = new MarcaResource(self.original);
            ModeloResource.queryAll(function(items) {
                $scope.modelosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.marca.modelos){
                        $.each($scope.marca.modelos, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.modelosSelection.push(labelObject);
                                $scope.marca.modelos.push(wrappedObject);
                            }
                        });
                        self.original.modelos = $scope.marca.modelos;
                    }
                    return labelObject;
                });
            });
            DatosVehiculoResource.queryAll(function(items) {
                $scope.datosVehiculosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        valorizacionId : item.valorizacionId
                    };
                    var labelObject = {
                        value : item.valorizacionId,
                        text : item.valorizacionId
                    };
                    if($scope.marca.datosVehiculos){
                        $.each($scope.marca.datosVehiculos, function(idx, element) {
                            if(item.valorizacionId == element.valorizacionId) {
                                $scope.datosVehiculosSelection.push(labelObject);
                                $scope.marca.datosVehiculos.push(wrappedObject);
                            }
                        });
                        self.original.datosVehiculos = $scope.marca.datosVehiculos;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The marca could not be found.'});
            $location.path("/Marcas");
        };
        MarcaResource.get({MarcaId:$routeParams.MarcaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.marca);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The marca was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.marca.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Marcas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The marca was deleted.'});
            $location.path("/Marcas");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.marca.$remove(successCallback, errorCallback);
    };
    
    $scope.modelosSelection = $scope.modelosSelection || [];
    $scope.$watch("modelosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.marca) {
            $scope.marca.modelos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.marca.modelos.push(collectionItem);
            });
        }
    });
    $scope.datosVehiculosSelection = $scope.datosVehiculosSelection || [];
    $scope.$watch("datosVehiculosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.marca) {
            $scope.marca.datosVehiculos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.valorizacionId = selectedItem.value;
                $scope.marca.datosVehiculos.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});