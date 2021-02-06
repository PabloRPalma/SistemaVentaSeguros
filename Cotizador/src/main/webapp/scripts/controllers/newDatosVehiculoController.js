
angular.module('cotizador').controller('NewDatosVehiculoController', function ($scope, $location, locationParser, flash, DatosVehiculoResource , MarcaResource, ModeloResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.datosVehiculo = $scope.datosVehiculo || {};
    
    $scope.marcaList = MarcaResource.queryAll(function(items){
        $scope.marcaSelectionList = $.map(items, function(item) {
            return ( {
                value : item.marcaId,
                text : item.marcaId
            });
        });
    });
    $scope.$watch("marcaSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.datosVehiculo.marca = {};
            $scope.datosVehiculo.marca.marcaId = selection.value;
        }
    });
    
    $scope.modeloList = ModeloResource.queryAll(function(items){
        $scope.modeloSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("modeloSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.datosVehiculo.modelo = {};
            $scope.datosVehiculo.modelo.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The datosVehiculo was created successfully.'});
            $location.path('/DatosVehiculos');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DatosVehiculoResource.save($scope.datosVehiculo, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DatosVehiculos");
    };
});