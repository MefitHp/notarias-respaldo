/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_lista_pago_solicitud", function ctrl_lista_pago_solicitud($scope,$rootScope,$route, conexion_app, conexion, catalogos) {
        catalogos.get_expediente_x_tramite($scope,function(){
            catalogos.get_solicitudes_pago_x_exp($scope);
        })
        
        $scope.formateaValor = function(valor){
            valor = formato_numero(valor,"2",".",",");            
            return valor;
        }
        
        $scope.pagar_solicitud = function(id){
            $rootScope.idsolicitud = id;
            $scope.goTo('/recepcion_de_pago1');
        }
        
        $scope.elimina_pago = function(idPago){
            if(confirm("Realmente desea eliminar la solicitud de pago")){
                conexion_app.eliminar_solicitud_pago({
                "usuario":$scope.usr_global,
                "expediente":$scope.expediente,
                "solicitudPago":{
                    "idsolpago":idPago
                }
                },function(data){
                    if(data.exito) {
                        ejecutaAlerta(1,"Se eliminó correctamente la solicitud de pago")
                        $route.reload();
                    }
                    else{
                        ejecutaAlerta(2,"Ocurrió un error: "+ data.estatus);
                    } 
                },function(error){
                    alert("Algo malo paso al eliminar la solicitud de pago: "+ error.status);
                });
            }
            
        }
        
    });



});




