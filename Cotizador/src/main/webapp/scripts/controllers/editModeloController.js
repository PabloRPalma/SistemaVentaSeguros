

angular.module('cotizador').controller('EditModeloController', function($scope, $routeParams, $location, flash, ModeloResource , MarcaResource, DatosVehiculoResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.modelo = new ModeloResource(self.original);
            MarcaResource.queryAll(function(items) {
                $scope.marcaSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        marcaId : item.marcaId
                    };
                    var labelObject = {
                        value : item.marcaId,
                        text : item.marcaId
                    };
                    if($scope.modelo.marca && item.marcaId == $scope.modelo.marca.marcaId) {
                        $scope.marcaSelection = labelObject;
                        $scope.modelo.marca = wrappedObject;
                        self.original.marca = $scope.modelo.marca;
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
                    if($scope.modelo.datosVehiculos){
                        $.each($scope.modelo.datosVehiculos, function(idx, element) {
                            if(item.valorizacionId == element.valorizacionId) {
                                $scope.datosVehiculosSelection.push(labelObject);
                                $scope.modelo.datosVehiculos.push(wrappedObject);
                            }
                        });
                        self.original.datosVehiculos = $scope.modelo.datosVehiculos;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The modelo could not be found.'});
            $location.path("/Modelos");
        };
        ModeloResource.get({ModeloId:$routeParams.ModeloId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.modelo);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The modelo was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.modelo.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Modelos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The modelo was deleted.'});
            $location.path("/Modelos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.modelo.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("marcaSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.modelo.marca = {};
            $scope.modelo.marca.marcaId = selection.value;
        }
    });
    $scope.datosVehiculosSelection = $scope.datosVehiculosSelection || [];
    $scope.$watch("datosVehiculosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.modelo) {
            $scope.modelo.datosVehiculos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.valorizacionId = selectedItem.value;
                $scope.modelo.datosVehiculos.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});