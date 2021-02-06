
angular.module('cotizador').controller('NewMarcaController', function ($scope, $location, locationParser, flash, MarcaResource , ModeloResource, DatosVehiculoResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.marca = $scope.marca || {};
    
    $scope.modelosList = ModeloResource.queryAll(function(items){
        $scope.modelosSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("modelosSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.marca.modelos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.marca.modelos.push(collectionItem);
            });
        }
    });

    $scope.datosVehiculosList = DatosVehiculoResource.queryAll(function(items){
        $scope.datosVehiculosSelectionList = $.map(items, function(item) {
            return ( {
                value : item.valorizacionId,
                text : item.valorizacionId
            });
        });
    });
    $scope.$watch("datosVehiculosSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.marca.datosVehiculos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.valorizacionId = selectedItem.value;
                $scope.marca.datosVehiculos.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The marca was created successfully.'});
            $location.path('/Marcas');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        MarcaResource.save($scope.marca, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Marcas");
    };
});