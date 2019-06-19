define(['../module'],function(controllers){'use strict';controllers.controller("ctrl_login",function ctrl_login($scope,$location,conexion,con_login,$rootScope,$timeout){cerrarSesion=false;function get_data_user(){$scope.user={};$scope.user.cdusuario=$("#usuario").val();$scope.user.dsvalenc=$("#password").val();}
      
      /*$(document).bind('keydown', 'esc', function() {  
            //$scope.submit();
            console.info("Click esc");
      });*/
        
      $scope.ischrome = !!window.chrome && !!window.chrome.webstore;
      
   
   $scope.submit=function(){modal.show();if($scope.myForm.$valid){get_data_user();con_login.checkLogin($scope.user,function(data){window.location.href="index"+data.acceso+".html#/?idsesionactual="+data.idsesionactual+"&idusuario="+data.idusuario+"&nombreUsuario="+data.dsnombre+" "+data.dspaterno+" "+data.dsmaterno;},function(error){console.info(error)
if(error.status!=404){ejecutaAlerta(2,' El usuario o contraseña son incorrectos');}else{ejecutaAlerta(2,"El servidor no esta disponible en este momento: "+error.status);}
modal.destroy();});$scope.myForm.submitted=false;}else{$scope.myForm.submitted=true;}}
$scope.logout=function(){if(confirm("Terminará la sesión actual, desea continuar?")){con_login.logout($rootScope.usr_global,function(data){if(data){cerrarSesion=true;force_redirect('login.html#/');}else
{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert('Ocurrió un error al terminar la sesión: '+error.status);});}}
$scope.recuperaPass=function(){conexion.restaurar({"usuario":$scope.restaurapass},function(data){if(data.exito){ejecutaAlerta(1,data.estatus);switchElemento('#frm_remember','#frm_login');}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al procesar la solicitud: "+error.status);});};$timeout(function(){modal.destroy();},600)});});