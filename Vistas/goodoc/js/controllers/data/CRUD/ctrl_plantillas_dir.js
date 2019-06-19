define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_plantillas_dir", function ctrl_plantillas_dir($route,$scope,$rootScope, conexion_app, conexion, catalogos) {
        
        $scope.actualizar = function(plantilla){
            $rootScope.edit_plantilla = plantilla;
            force_redirect("index_adm.html#/plantillas_actualizar?n="+moment());
        }
          
        $scope.eliminar = function(plantilla) {
            if(confirm("Estas seguro de querer eliminar esta plantilla. La acción no se podrá deshacer.")){
                var wrapper = {"usuario":$rootScope.usr_global,"plantilla":JSON.parse(plantilla)};
                conexion.plantilla_eliminar(wrapper,
                    function(data){alert(data.estatus);$route.reload();},
                    function(error){alert("Algo ocurrio al eliminar la plantilla: "+error.status);});
            }
        }  
        $scope.verDoc = function(plantilla){
           $rootScope.edit_plantilla = plantilla;
           $rootScope.onlyread = true;
                    force_redirect("index_adm.html#/plantillas_actualizar?n="+moment());
               
        }


    })



});

