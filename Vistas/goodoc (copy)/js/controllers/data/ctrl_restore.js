define(['../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_restore", function ctrl_restore($scope, $location,conexion, con_login, $rootScope) {
        

        //RECUPERACION DE PARAMETRO DE SESIÓN POR URL
        $scope.usuario = {};
       
        $scope.usuario.dscorreo = ($location.search()).usuario;
        $scope.token = ($location.search()).token;
        
        // VALIDO SI LA URL TIENE TODOS LOS PARAMETROS SOLICITADOS
        if(typeof $scope.usuario.dscorreo == "undefined" || typeof $scope.token =="undefined"){
                       window.location.href ="login.html";
        }
        
        
        
        $scope.restauraPass = function(){
            if ($scope.myForm.$valid) {
                conexion.actualizaPass({
                "usuario":$scope.usuario,
                "tokenRestore":$scope.token
            },
                function(data){
                    if(data.exito){
                        ejecutaAlerta(1,data.estatus);
                        setTimeout(function(){
                           window.location.href ="login.html";
                        },1500);
                    }
                    else{
                        ejecutaAlerta(2,"Ocurrió un error: "+ data.estatus);
                    }
                },
                function(error){
                    alert(2,"Algo malo ocurrio al regenerar la contraseña: "+ error.status);
                });
                
                $scope.myForm.submitted = false;

            } else {

                $scope.myForm.submitted = true;
            }

            
         
        }
        
    });



});

