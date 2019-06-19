define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_formatos_dir", function ctrl_formatos_dir($scope,$rootScope, conexion_app, conexion, catalogos) {
        
        $scope.actualizar = function(documento){
              $rootScope.edit_doc = documento;
              force_redirect("index_adm.html#/formatos_actualizar?n="+moment());
              
          }


    });



});
