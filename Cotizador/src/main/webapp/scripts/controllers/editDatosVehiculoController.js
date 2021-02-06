

angular.module('cotizador').controller('EditDatosVehiculoController', function($scope, $routeParams, $location, flash, DatosVehiculoResource , MarcaResource, ModeloResource,MarcaService) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;

    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.datosVehiculo = new DatosVehiculoResource(self.original);
            	var items= MarcaService.getMarcas();
                $scope.marcaSelectionList = items;
                console.log(items)
           
         
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The datosVehiculo could not be found.'});
            $location.path("/DatosVehiculos");
        };
        var id =localStorage.getItem('valorizacion');
        
        DatosVehiculoResource.get({DatosVehiculoId:id}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.datosVehiculo);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The datosVehiculo was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.datosVehiculo.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DatosVehiculos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The datosVehiculo was deleted.'});
            $location.path("/DatosVehiculos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.datosVehiculo.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("marcaSelection", function(selection) {
    	
    	
        if ( selection != undefined) {
        	
            $scope.datosVehiculo.marca = selection;
            $scope.modeloSelectionList= selection.modelos;
               }else{
            	 $scope.modeloSelectionList= [];
             }

    		
            
        }
        
    );
    $scope.$watch("modeloSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.datosVehiculo.modelo = {};
            $scope.datosVehiculo.modelo.id = selection.id;
        }
    });
    
    $scope.get();
});