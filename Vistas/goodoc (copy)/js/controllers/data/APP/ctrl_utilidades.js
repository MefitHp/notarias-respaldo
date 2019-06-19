define(['../../module'], function (controllers) {
    'use strict';
    controllers.controller("ctrl_utilidades", function ctrl_utilidades($scope, $timeout, conexion_app, catalogos, $rootScope) {
        catalogos.get_estados($scope);
        catalogos.get_municipios($scope);
        catalogos.get_localidades($scope);
        $scope.domicilio = {};
        $scope.domicilio.dsdircompleta = "";
        function init_domicilio() {
            $scope.domicilio.dscalle = "";
            $scope.domicilio.dsnumext = "";
            $scope.domicilio.dsnumint = "";
            $scope.domicilio.dsmanzana = "";
            $scope.domicilio.dslote = "";
            $scope.domicilio.dscolonia = "";
            $scope.domicilio.dsestado = "";
            $scope.domicilio.dsmunicipio = "";
            $scope.domicilio.dscodpos = "";
            $scope.domicilio.dsciudad = "";
            $scope.domicilio.dslocalidad = "";
            $scope.selected_localidad = {}
            $scope.selected_localidad.title = "";
            $scope.selected_estado = {};
            $scope.selected_estado.title = "";
            $scope.selected_municipio = {};
            $scope.selected_municipio.title = "";
            $("#estado_value").val("");
            $("#municipio_value").val("");
            $("#localidad_value").val("");
        }
        $timeout(function () {
            $("#estado_value").keyup($scope.actualiza_completo);
            $("#estado_value").focusout(function () {
                setTimeout(function () {
                    $scope.actualiza_completo()
                }, 1000)
            });
            $("#municipio_value").keyup($scope.actualiza_completo);
            $("#municipio_value").focusout(function () {
                setTimeout(function () {
                    $scope.actualiza_completo()
                }, 1000)
            });
            $("#localidad_value").keyup($scope.actualiza_completo);
            $("#localidad_value").focusout(function () {
                setTimeout(function () {
                    $scope.actualiza_completo()
                }, 1000)
            });
        }, 1000)
        $rootScope.initDom = function () {
            init_domicilio()
        }
        if ($rootScope.haveDom) {
            $scope.domicilio = $rootScope.domicilio;
            $timeout(function () {
                $("#estado_value").val($scope.domicilio.dsestado);
                $("#municipio_value").val($scope.domicilio.dsmunicipio);
                $("#localidad_value").val($scope.domicilio.dslocalidad);
            }, 500)
        } else {
            init_domicilio();
        }
        $scope.actualiza_completo = function () {
            var arrFinal = "";
            if ($scope.domicilio.isasistido) {
                console.info($scope.selected_estado)
                console.info($scope.selected_localidad)
                console.info($scope.selected_municipio)
                if ($scope.domicilio.dscalle && $scope.domicilio.dscalle != "")
                    arrFinal = $scope.domicilio.dscalle;
                if ($scope.domicilio.dsnumext != "")
                    arrFinal += " #" + $scope.domicilio.dsnumext;
                if ($scope.domicilio.dsnumint && $scope.domicilio.dsnumint != "")
                    arrFinal += "  Interior " + $scope.domicilio.dsnumint;
                if ($scope.domicilio.dsmanzana && $scope.domicilio.dsmanzana != "")
                    arrFinal += "  Manzana " + $scope.domicilio.dsmanzana;
                if ($scope.domicilio.dslote && $scope.domicilio.dslote != "")
                    arrFinal += "  Lote " + $scope.domicilio.dslote;
                if ($scope.domicilio.dscolonia && $scope.domicilio.dscolonia != "")
                    arrFinal += ", Colonia " + $scope.domicilio.dscolonia;
                if ($scope.selected_estado && $scope.selected_estado.title != "" || $("#estado_value").val() != "") {
                    $scope.domicilio.dsestado = $("#estado_value").val();
                    arrFinal += ", " + $scope.domicilio.dsestado;
                }
                if ($scope.selected_localidad && $scope.selected_localidad.title != "" || $("#localidad_value").val() != "") {
                    $scope.domicilio.dslocalidad = $("#localidad_value").val();
                    arrFinal += ", " + $scope.domicilio.dslocalidad;
                }
                if ($scope.selected_municipio && $scope.selected_municipio.title != "" || $("#municipio_value").val() != "") {
                    $scope.domicilio.dsmunicipio = $("#municipio_value").val();
                    arrFinal += ", " + $scope.domicilio.dsmunicipio;
                }
                if ($scope.domicilio.dscodpos && $scope.domicilio.dscodpos != "")
                    arrFinal += ", Código Postal " + $scope.domicilio.dscodpos;
                
                $scope.domicilio.dsdircompleta = arrFinal;
                $rootScope.haveDom = true;
            } else {
                init_domicilio();
            }
            $rootScope.domicilio = $scope.domicilio;
        }
    });
});