
angular.module('cotizador').controller('NewEstadoValorizacionController', function ($scope, $location, locationParser, flash, EstadoValorizacionResource , ValorizacionResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.estadoValorizacion = $scope.estadoValorizacion || {};
    
    $scope.valorizacionsList = ValorizacionResource.queryAll(function(items){
        $scope.valorizacionsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.valorizacionId,
                text : item.valorizacionId
            });
        });
    });
    $scope.$watch("valorizacionsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.estadoValorizacion.valorizacions = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.valorizacionId = selectedItem.value;
                $scope.estadoValorizacion.valorizacions.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The estadoValorizacion was created successfully.'});
            $location.path('/EstadoValorizacions');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        EstadoValorizacionResource.save($scope.estadoValorizacion, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/EstadoValorizacions");
    };
});