'use strict';

angular.module('cotizador').directive('rut', function($timeout, $interpolate, validarRut) {
    return {
        restrict: 'E',
        template: '<pre><code></code></pre>',
        replace: true,
        require: '?ngModel',
        link: function(scope, elm, attrs, ngModel) {
            scope.rutValido = '';
            ngModel.$render = function() {
                
                var tmp = $interpolate(scope.rutValido)(scope);
                
             elm.find('code').html(validarRut(tmp));
            }
        }
    };
});