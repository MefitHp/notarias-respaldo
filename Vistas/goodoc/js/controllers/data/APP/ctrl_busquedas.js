define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_busquedas", function ctrl_busquedas($scope,conexion_app) {
    
    $scope.buscarCliente = function(){
        conexion_app.buscar_cliente({
            "usuario":$scope.usr_global,
            "nombrePersona":$scope.nombrePersona
        },
            function(data){
                console.info(data);
                $scope.resultados = data.resultados;
                
            },
            function(error){
                alert('Algo malo ocurrio al buscar cliente: '+ error.status);
            })
    }
    
    })



});

