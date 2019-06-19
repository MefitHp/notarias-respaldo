define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_list_tarjeta_am", function ctrl_list_tarjeta_am($scope,$rootScope,conexion_app, conexion, catalogos) {
        
        $scope.obtieneLista = function(){
            console.info("obtiene Tarjeta amarilla")
            conexion_app.listar_ta({
                "tarjetaAmarilla":{                        
                    "acto":$scope.acto 
                    
                },
                "usuario":$rootScope.usr_global,

            },
            function(data){
                $scope.lista_ta = data.listaTarjetas;
            },
            function(error){
                ejecutaAlerta(2,'Ocurrio un error al listar Tarjetas: '+ error.status);
            });
            
        }
        
        
        
        
        
        catalogos.get_acto_x_id($scope,$scope.obtieneLista);
        
        
        
        $scope.edita_tar_am = function(id){
            $rootScope.have_ta = id;
            $scope.goTo("/tarjeta_amarilla_agregar");
        }
        
        


    });



});

