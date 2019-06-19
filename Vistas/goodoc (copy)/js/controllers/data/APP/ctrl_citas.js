
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_citas", function ctrl_citas($scope,$route, $timeout,$rootScope, conexion_app, conexion, catalogos, funcionesutiles) {

        $scope.datocita = {};
        $scope.datocita.calendarioCita = {};
        $scope.lista_citas = new Array();

        $scope.popLateral = function(url_cargar, columnas, status) {
            funcionesutiles.popLateral(url_cargar, columnas, status);

        }

        $scope.parseFechaCalendar = function(fi, ft) {
            var diaCita = {};
            var mi = moment(fi);
            var mf = moment(ft);
            diaCita.dia = dias[mi.day()];
            diaCita.ndia = mi.date();
            diaCita.mes = meses[mi.month()];
            var cmi = (mi.minutes() != 0) ? "" : "0";
            var cmf = (mf.minutes() != 0) ? "" : "0";
            diaCita.hora = mi.hours() + ":" + mi.minutes() + cmi + " - " + mf.hours() + ":" + mf.minutes() + cmf;
            return diaCita;
        }


        catalogos.get_expediente_x_tramite($scope, function() {
            catalogos.get_datos_combos_cita($scope);
        })



        $timeout(function() {
            $scope.setNow();
        }, 100)

        $scope.setNow = function() {
            $scope.datocita.calendarioCita.fechainicio = moment().format("dddd DD MMMM YYYY");
            $scope.getLista($scope.datocita.calendarioCita.fechainicio);

        }


        $scope.changeDay = function(n) {
            switch (n) {
                case "next":
                    $scope.datocita.calendarioCita.fechainicio = moment($scope.datocita.calendarioCita.fechainicio).add('days', 1).format("dddd DD MMMM YYYY");
                    break;
                case "prev":
                    $scope.datocita.calendarioCita.fechainicio = moment($scope.datocita.calendarioCita.fechainicio).subtract('days', 1).format("dddd DD MMMM YYYY");
                    break;
                default:

            }
            $scope.lista_citas = new Array();
            $scope.getLista($scope.datocita.calendarioCita.fechainicio);
        }


        $scope.ver_detalle = function(id) {
            conexion_app.obtener_cita_x_id({
                "usuario": $scope.usr_global,
                "expediente": $scope.expediente,
                "datocita": {
                    "calendarioCita": {
                        "id": id
                    },
                }

            },
            function(data) {
                if (data.exito) {
                    $scope.datoCita = data.datocita;
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus)
                }
            },
                    function(error) {
                        alert("Algo malo paso al obtener la cita: " + error.status);
                    })
        }


        $scope.editaCita=function(id){
            $rootScope.idcita = id;
            $scope.popLateral('citas_agendar_e', 2, true);
        }

        $scope.cambiaStatus = function(id, status) {
            var tipomsj = (status == "F") ? "finalizar" : "cancelar";
            var tipomsjConfirm = (status == "F") ? "finalizó" : "canceló";

            if (confirm("¿Realmente deseas " + tipomsj + " esta cita?")) {

                conexion_app.status_cita({
                    "usuario": $scope.usr_global,
                    "expediente": $scope.expediente,
                    "datocita": {
                        "calendarioCita": {
                            "id": id,
                        }
                    },
                    "statusCita": status
                },function(data){
                    if(data.exito){
                        ejecutaAlerta(1,"Se "+tipomsjConfirm+" correctamente la cita");
                        $route.reload();

                    }else{
                        ejecutaAlerta(1,"Se "+tipomsjConfirm+" correctamente la cita");
                    }
                },function(error){
                    alert("Ocurrio algo malo con la cita: "+ error.status);
                })

            }

        }

        $scope.getLista = function(f) {

            var fecha = moment(f).format("YYYY-MM-DD HH:mm:ss");
            conexion_app.listar_citas({
                "usuario": $scope.usr_global,
                "datocita": {
                    "calendarioCita": {
                        "fechainicio": fecha
                    }
                }
            },
            function(data) {
                if (data.exito) {
                    $scope.lista_citas = data.listaCitas;
                    for (var i = 0; i < $scope.lista_citas.length; i++) {
                        $scope.lista_citas[i].diaCita = $scope.parseFechaCalendar($scope.lista_citas[i].fechainicio, $scope.lista_citas[i].fechatermino);
                    }

                } else {
                    if (data.estatus != "No existe el registro") {
                        ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                    }
                }
            },
                    function(error) {
                        alert("Algo malo paso al obtener las citas: " + error.status)
                    })

        }

    })



});



