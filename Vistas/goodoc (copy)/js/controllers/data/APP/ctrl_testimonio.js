define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_testimonio", function ctrl_testimonio($scope,$timeout,$rootScope,catalogos) {
        if($rootScope.idescritura && $rootScope.idescritura !=""){
        $timeout(function(){
            $("#idsesionactual").val($scope.usr_global.idsesionactual);
            $("#idusuario").val($scope.usr_global.idusuario);
            $("#idexpediente").val($rootScope.idexpediente);
            $("#idescritura").val($rootScope.idescritura); 
        },500)
        
       
        
        }else{
           //force_redirect('index.html#/escrituras');
        }
        
    });



});

