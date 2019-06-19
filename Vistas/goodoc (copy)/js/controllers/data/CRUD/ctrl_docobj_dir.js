define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_docobj_dir", function ctrl_docobj_dir($scope,$rootScope, conexion_app, conexion, catalogos) {
        
        $scope.actualizar = function(documento){
              $rootScope.edit_docobj = documento;
              force_redirect("index_adm.html#/documento_objeto?n="+moment());
                    
             
          }


    });



});

