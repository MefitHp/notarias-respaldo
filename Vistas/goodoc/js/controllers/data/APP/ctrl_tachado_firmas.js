define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_tachado_firmas", function ctrl_tachado_firmas($scope, $rootScope, $timeout, conexion_app, conexion, catalogos) {



        //  se obtienee el acto por id y luego los comparecientes
        if ($rootScope.edit_esc && $rootScope.edit_esc != "") {
            catalogos.get_expediente_x_tramite($scope, function() {
                catalogos.obten_escritura_x_id($scope, $rootScope.edit_esc, function() {
                    catalogos.get_comparecientes_bitacora($scope, $scope.filtrar_comparecientes);
                })
            })

            conexion_app.listar_bitacora_firmas(
                    {
                        "bitacoraFirma": {
                            "escritura": {
                                "idescritura": $rootScope.edit_esc
                            }
                        },
                        "usuario": $scope.usr_global
                    },
            function(data) {
                if (data.exito) {
                    _.each(data.listaBitacora, function(com) {
                        var tmpcom = {}
                        tmpcom.nombrecompleto = com.compareciente.persona.dsnombrecompleto
                        $scope.tmp_comparecientes.push(tmpcom);
                        console.info($scope.tmp_comparecientes);
                    });

                }
            },
                    function(error) {

                    });

        } else {
            $scope.goTo("/escrituras");
        }



        $scope.comparecientes_filtrados = new Array();
        $scope.filtrar_comparecientes = function(callback) {
            var lc = $scope.comparecientes;
            for (var i = 0; i < lc.length; i++) {
                var np = {};
                np.pos = i;
                np.nombre = lc[i].persona.dsnombre;
                np.nombrecompleto = lc[i].persona.dsnombrecompleto;
                np.apellido_paterno = lc[i].persona.dsapellidopat;
                np.apellido_materno = lc[i].persona.dsapellidomat;
                np.alias = lc[i].alias;
                $scope.comparecientes_filtrados.push(np);
            }
            if (typeof callback != "undefined") {
                callback()
            }
            console.info($scope.comparecientes_filtrados);
        }

        $scope.tmp_comparecientes = new Array();
        $scope.envio_comp = new Array();
        $scope.tmp_seleccionados = new Array();
        $scope.add_firmante = function() {
            if ($.inArray($scope.sel_comp.originalObject.pos, $scope.tmp_seleccionados) != -1) {
                $("#comp_value").val("");
                alert("Este compareciente ya se encuentra registrado");
                return;
            }
            $scope.tmp_comparecientes.push($scope.sel_comp.originalObject);
            $scope.envio_comp.push($scope.comparecientes[$scope.sel_comp.originalObject.pos]);
            $scope.tmp_seleccionados.push($scope.sel_comp.originalObject.pos);

            $("#comp_value").val("");
        }

        $scope.del_firmante = function(obj) {

            var index = $scope.tmp_comparecientes.indexOf(obj);
            if (index > -1) {
                $scope.tmp_comparecientes.splice(index, 1);
                $scope.tmp_seleccionados.splice(index, 1);
                $scope.envio_comp.splice(index, 1);
            }


        }

        $scope.return_esc = function() {

            delete $rootScope.edit_esc;
            force_redirect('index.html#/escrituras');

        }


        $scope.guardarFirmas = function() {

            conexion_app.guardar_tachado_firmas({
                "escritura": $scope.escritura,
                "comparecientesSelected": $scope.envio_comp,
                "isfirmaditto": $scope.isfirmaditto,
                "usuario": $rootScope.usr_global
            },
            function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se registraron correctamente los datos ");

                    $timeout(function() {
                        $scope.return_esc();
                    }, 1000);

                } else {
                    ejecutaAlerta(2, 'Ocurrio un error: ' + data.estatus);
                }
            },
                    function(error) {
                        alert("Algo malo paso al registrar los datos: " + error.status);
                    });
        }



    });

});