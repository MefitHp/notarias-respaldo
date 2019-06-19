define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_citas_agendar", function ctrl_citas_agendar($scope, $route, $timeout, conexion_app, conexion, funcionesutiles, catalogos) {
        
        $scope.datocita = {}
        $scope.datocita.invitados = new Array();
        $scope.datocita.documentos = new Array();

        
        catalogos.get_expediente_x_tramite($scope, function() {
            catalogos.get_datos_combos_cita($scope);
        })

        

        $scope.add_invitado = function() {
            var id = parseInt($("#sel_inv").val());
            $scope.datocita.invitados.push($scope.sel_inv);
            $scope.invitados.splice(id, 1);

        }

        $scope.del_invitado = function(id, obj) {
            $scope.datocita.invitados.splice(id, 1);
            $scope.invitados.push(obj);
        }

        $scope.add_documento = function(id) {
            var id = $("#sel_doc").val();
            $scope.datocita.documentos.push($scope.sel_doc);
            $scope.documentos.splice(id, 1);
        }

        $scope.del_documento = function(id, obj) {
            $scope.datocita.documentos.splice(id, 1);
            $scope.documentos.push(obj);
        }



        $scope.popLateral = function(url_cargar, columnas, status, reload) {
            funcionesutiles.popLateral(url_cargar, columnas, status);
            if (reload) {
                $timeout(function() {
                    $route.reload();
                }, 4500)
            }

        }


        $scope.guardar_cita = function() {


            $scope.datocita.calendarioCita.usragendo = $scope.usr_global;
            $scope.datocita.calendarioCita.fechainicio = funcionesutiles.fecaltoTimeStamp($scope.fecha_inicio);
            $scope.datocita.calendarioCita.fechatermino = funcionesutiles.fecaltoTimeStamp($scope.fecha_termino);

            conexion_app.guardar_cita({
                "usuario": $scope.usr_global,
                "expediente": $scope.expediente,
                "datocita": $scope.datocita
            },
            function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se guardó correctamente la cita");
                    $timeout(function() {
                        $scope.popLateral('', 2, false,true);
                    }, 1500)

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

