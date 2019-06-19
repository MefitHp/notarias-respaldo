define(['../module'], function(directives) {
    'use strict';
    directives.directive('abogado',['catalogos',function(catalogos) {
        return {
            restrict: 'E',
            templateUrl: urlTemplates + 'exp_abogado.html',
            link: function(scope, element,rootScope) {
                creaTarea(element,catalogos,scope);
                
            }
        };
    }]);
});