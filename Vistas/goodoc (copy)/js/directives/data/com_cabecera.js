define(['../module'], function (directives) {
    'use strict';
    directives.directive('comCabecera', function () {
        return{restrict: 'AE', templateUrl: urlTemplates + 'componente_cabecera.html', scope: {titulo: "@", idcomponente: "@", dsvar:"@" }, link: function (scope, element) {
            }};
    });
});