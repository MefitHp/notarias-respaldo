define(['../module'],
function(directives){'use strict';directives.directive('tarea',['conexion_app','$rootScope','$route','$timeout','$location',function(conexion_app,$rootScope,$route,$timeout,$location){
        return{restrict:'E',scope:{tarea:"="},templateUrl:urlTemplates+'tarea.html',link:
                    function(scope,element){
                console.info("El topo");
                scope.ejecuta_tarea = function(nombre,tareaid,idexpediente,numero,id){
                    $rootScope.isprorroga = (nombre.indexOf("rroga)")>-1)?true:false;
                    var tipo = getTipoTarea(nombre);
                    var obj = {
                        numero:numero, 
                        id:id,
                        idtarea:tareaid
                    }
                    if(tipo=="liberacaja"){
                       $location.url("/pagos");
                      
                    }else if(tipo=="gestionaimpuesto"){
                       $location.url("/mesa_control");
                    }else if(!tipo==""){
                        $rootScope.asignaExpedienteXid(idexpediente,tipo,obj);
                    }else{
                        
                    modal.show();
                    conexion_app.tareas_exe({
                    "usuario":$rootScope.usr_global,
                    "idTarea":tareaid
                    },function(data){
                                        if (data.exito){
                                            ejecutaAlerta(1,"La tarea se ejecuto correctamente");
                                            $timeout(function(){
                                                $route.reload();
                                            },1000);
                                        }else{
                                            ejecutaAlerta(2,"Ocurrio un error al ejecutar la tarea se ejecuto correctamente")
                                        }
                    },function(error){
                        alert("Algo malo ocurrio al procesar la tarea");
                    }).$promise.finally(destroyModal);
                    
                    }
                    
                }
                       
}
};}]);});