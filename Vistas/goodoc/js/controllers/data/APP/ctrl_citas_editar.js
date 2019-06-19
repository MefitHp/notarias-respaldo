define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_citas_editar", function ctrl_citas_editar($scope,$rootScope,$route, conexion_app, conexion,funcionesutiles, catalogos,$timeout) {
        
       
        
        var format_moment="DD-MM-YYYY / HH:mm";
        $scope.datocita = {};
        $scope.datocita.calendarioCita = {};
        
        $scope.datocita.invitados = new Array();
        $scope.datocita.documentos = new Array();
        $scope.invitados = new Array();
        $scope.documentos = new Array();
        
       
       
        
        $scope.popLateral = function(url_cargar, columnas, status, reload) {
            funcionesutiles.popLateral(url_cargar, columnas, status);
            
        }
        
        
        if($rootScope.idcita && $rootScope.idcita !=""){
            catalogos.get_expediente_x_tramite($scope, function() {
                $scope.ver_detalle($rootScope.idcita);
                delete $rootScope.idcita;
            })
           
            
        }else{
           popLateral('', 2, false, false) 
        }
        
        $scope.buscarDatos = function(lista_buscados,lista_buscar){
           
            for(var i=0;lista_buscados.length>i;i++){
                var idInvitado = lista_buscados[i].id;

                for(var j=0;lista_buscar.length>j;j++){
                   
                   if(idInvitado == lista_buscar[j].id){
                        lista_buscar.splice(j,1);
                    } 
                }

            }
        }
        
        $scope.ver_detalle = function(id){
            conexion_app.obtener_cita_x_id({
                "usuario":$scope.usr_global,
                "expediente":$scope.expediente,
                "datocita":{
                    "calendarioCita":{
                        "id":id
                    },
                    
                }
            },
                function(data){
                    if(data.exito){
                            $scope.datocita = data.datocita;
                            $scope.invitados = data.comboInvitados;
                            $scope.documentos = data.comboDocumentos;
                            $scope.fecha_inicio = moment(data.datocita.calendarioCita.fechainicio).format(format_moment);
                            $scope.fecha_termino = moment(data.datocita.calendarioCita.fechatermino).format(format_moment);
                            $timeout(function(){
                                $scope.buscarDatos($scope.datocita.invitados,data.comboInvitados);
                            },1000);
                            //$scope.buscarDatos($scope.datocita.documentos,$scope.documentos);
                    
                    }else{
                        ejecutaAlerta(2,"Ocurrió un error: "+ data.estatus);
                    }
                },
                function(error){
                    alert("Algo malo paso al obtener la cita: "+error.status);
                });
        };
        
        
        
        $scope.add_invitado = function() {
            var id = parseInt($("#sel_inv").val());
            $scope.datocita.invitados.push($scope.sel_inv);
            $scope.invitados.splice(id, 1);

        };

        $scope.del_invitado = function(id, obj) {
            $scope.datocita.invitados.splice(id, 1);
            $scope.invitados.push(obj);
        };

        $scope.add_documento = function(id) {
            var id = $("#sel_doc").val();
            $scope.datocita.documentos.push($scope.sel_doc);
            $scope.documentos.splice(id, 1);
        };

        $scope.del_documento = function(id, obj) {
            $scope.datocita.documentos.splice(id, 1);
            $scope.documentos.push(obj);
        };
        
        
        $scope.actualiza_evento = function(){
            
            $scope.datocita.calendarioCita.usragendo = $scope.usr_global;
            $scope.datocita.calendarioCita.fechainicio = funcionesutiles.fecaltoTimeStamp($scope.fecha_inicio);
            $scope.datocita.calendarioCita.fechatermino = funcionesutiles.fecaltoTimeStamp($scope.fecha_termino);

             conexion_app.actualizar_cita({
                "usuario": $scope.usr_global,
                "expediente": $scope.expediente,
                "datocita": $scope.datocita
            },
            function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se actualizó correctamente la cita");
                    $timeout(function() {
                        $scope.popLateral('', 2, false,true);
                    }, 1500);

                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
            },
                    function(error) {
                        alert("Algo malo paso al guardar la cita:" + error.status);
                    }
            )
            
        }
        
        
        


    })



});

