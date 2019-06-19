define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_pago3", function ctrl_pago3($scope,$rootScope, conexion_app, conexion, catalogos,$timeout) {
        
        if(!$rootScope.pagoRealizado || $rootScope.pagoRealizado =="")
        force_redirect('index.html#/recepcion_de_pago1');
        
        
        
        $scope.datoPago = {};
        $scope.datosPagador = {};
        $scope.datosPagador.iddatofiscapago = $rootScope.pagoRealizado;
        delete $rootScope.pagoRealizado;
        catalogos.obtener_usuarios_caja($scope);
        catalogos.get_medio_pago($scope);
        catalogos.get_expediente_x_tramite($scope);
        
        $scope.formatoValor = function(tipo){
            if(tipo)$scope.datoPago.importepago = formato_numero($scope.datoPago.importepago,2,".",",");
            else $scope.datoPago.importepago = deformato_numero($scope.datoPago.importepago)
        }
        
        
        $scope.guardarDatosPago = function(){
            
            $scope.datoPago.importepago = deformato_numero($scope.datoPago.importepago);
            $scope.datoPago.fechapago = parseFecha($scope.datoPago.fechapago);
            
                conexion_app.guardar_datos_pago_solicitud_anticipo({
                "datoPago":$scope.datoPago,
                "expediente":$scope.expediente,
                "datosPagador":$scope.datosPagador,
                "usuario":$rootScope.usr_global
            },
            function(data){
                if(data.exito){
                    ejecutaAlerta(1,'Se registró correctamente el pago ');
                    $timeout(function(){
                        force_redirect('index.html#/recepcion_de_pago1');
                    })
                    
                }else{
                    ejecutaAlerta(2,'Ocurrio un error: '+ data.estatus);
                    $scope.datoPago.fechapago = parseFecha($scope.datoPago.fechapago);
                }
            },
            function(error){
                alert('Algo malo paso al guardar datos de pago: '+ error.status);
            });
           
            
        }
        


    });



});

