

angular.module('cotizador').controller('EditResultadoValorizacionController', function($scope, $routeParams, $location, flash, ResultadoValorizacionResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.resultadoValorizacion = new ResultadoValorizacionResource(self.original);
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The resultadoValorizacion could not be found.'});
            $location.path("/ResultadoValorizacions");
        };
        ResultadoValorizacionResource.get({ResultadoValorizacionId:$routeParams.ResultadoValorizacionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.resultadoValorizacion);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The resultadoValorizacion was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.resultadoValorizacion.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/ResultadoValorizacions");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The resultadoValorizacion was deleted.'});
            $location.path("/ResultadoValorizacions");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.resultadoValorizacion.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});