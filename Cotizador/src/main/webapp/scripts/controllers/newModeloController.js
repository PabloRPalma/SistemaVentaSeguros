
angular.module('cotizador').controller('NewModeloController', function ($scope, $location, locationParser, flash, ModeloResource , MarcaResource, DatosVehiculoResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.modelo = $scope.modelo || {};
    
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
            $scope.modelo.marca = {};
            $scope.modelo.marca.marcaId = selection.value;
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
            $scope.modelo.datosVehiculos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.valorizacionId = selectedItem.value;
                $scope.modelo.datosVehiculos.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The modelo was created successfully.'});
            $location.path('/Modelos');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        ModeloResource.save($scope.modelo, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Modelos");
    };
});