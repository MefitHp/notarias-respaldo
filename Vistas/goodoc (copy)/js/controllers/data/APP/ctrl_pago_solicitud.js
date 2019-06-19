define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_pago_solicitud", function ctrl_pago_solicitud($scope,$timeout, conexion_app, conexion, catalogos) {
        
        catalogos.get_expediente_x_tramite($scope);
        
        $scope.formatoValor = function(tipo){
            if(tipo)$scope.solicitudPago.valor = formato_numero($scope.solicitudPago.valor,2,".",",");
            else $scope.solicitudPago.valor = deformato_numero($scope.solicitudPago.valor)
        }
        
        $scope.guardar_solicitud_pago = function(){
            
            $scope.solicitudPago.expediente = $scope.expediente;
            $scope.solicitudPago.valor = deformato_numero($scope.solicitudPago.valor);
            
            //formato_numero(piva, 2, ".", ",");
            //deformato_numero($scope.tarjetaAmarilla.honorario);

            conexion_app.guardar_solicitud_pago(
            {
                "usuario":$scope.usr_global,
                "expediente":$scope.expediente,
                "solicitudPago":$scope.solicitudPago
            },
            function(data){
                if(data.exito){
                    ejecutaAlerta(1,"Se registró correctamente la solicitud del pago");
                    
                    $timeout(function(){
                        force_redirect('index.html#/lista_solicitud_de_pago');
                    },1000)
                }else{
                    ejecutaAlerta(2,"Ocurrió un error: "+ data.estatus);
                }
            },
            function(error){
                alert("Algo malo paso al registrar la solicitud del pago: "+ error.status);
            });
            
        }


    });



});

