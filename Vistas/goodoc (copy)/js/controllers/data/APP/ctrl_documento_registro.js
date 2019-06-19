/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_documento_registro", function ctrl_documento_registro($scope,$timeout, conexion_app, conexion, catalogos) {
        
        catalogos.get_expediente_x_tramite($scope);
        
        
        $scope.guardar_evidencia = function(){
            
            conexion_app.documento_guarda_original({
                "usuario":$scope.usr_global,
                "idExpediente":$scope.expediente.idexpediente,
                "original":$scope.evidencia
            },
            function(data){
                if(data.exito){
                            ejecutaAlerta(1,"Se registro correctamente el documento original")
                            $timeout(function(){
                                force_redirect('index.html#/documentos');
                            });
                }else{
                            ejecutaAlerta(2, "Ocurrió un error: "+ data.estatus);
                }
            },
            function(error){
                alert("Algo malo paso al guardar el documento original: "+ error.status);
            });
        }
        


    });



});


