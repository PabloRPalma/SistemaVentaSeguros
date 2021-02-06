
angular.module('cotizador').controller('NewResultadoValorizacionController', function ($scope, $location, locationParser, flash, ResultadoValorizacionResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.resultadoValorizacion = $scope.resultadoValorizacion || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The resultadoValorizacion was created successfully.'});
            $location.path('/ResultadoValorizacions');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        ResultadoValorizacionResource.save($scope.resultadoValorizacion, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/ResultadoValorizacions");
    };
});