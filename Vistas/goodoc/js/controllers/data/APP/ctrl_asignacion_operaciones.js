
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA

function ctrl_asignacion_operaciones($scope, $compile, conexion_app, catalogos, $rootScope) {
    $rootScope.operaciones = [];
    $rootScope.totaldocumentosprevios = [];
    $rootScope.totaldocumentosposteriores = [];
    $rootScope.operacionselec = 0;
    $rootScope.suboperacionselec = 0;
    
    catalogos.get_operaciones($scope, function() {
        modal.show();
        $scope.parseaObjetos($scope.sel_operaciones);
    });
    catalogos.get_expediente_x_tramite($scope, function() {
        modal.show();
        if (!$scope.expediente.tramite.locacion) {
            $("#localidad_wrap").slideDown("slow");
        }
        conexion_app.operacion_listar_expediente({
            "usuario": $scope.usr_global,
            "expediente": $scope.expediente
        }, function(data) {
            if (data.listaOperaciones != null) {
                $rootScope.operaciones = data.listaOperaciones;
                console.info("DATOS OPERACIONES:",data.listaOperaciones);
                agrega_actualizacion($rootScope.operaciones);
            } else {
                $rootScope.operaciones = [];
            }
            $scope.renderOp();
        }, function(error) {
            alert("Algo malo ocurrio al listar expediente: " + error.status);
        }).$promise.finally(destroyModal);
        if($scope.expediente.tramite.locacion==null){
           catalogos.get_locacion($scope);
        }
    });
    $scope.addSubtoOp = function(arreglo) {
        for (var i = 0; i < arreglo.length; i++) {
            $scope.rellenaSubOp(arreglo[i]);
        }
    }
    $scope.parseaObjetos = function(arreglo) {
        for (var i = 0; i < arreglo.length; i++) {
            var tmpObj = arreglo[i];
            arreglo[i] = {};
            arreglo[i].operacion = tmpObj;
            //$scope.rellenaSubOp(arreglo[i]);
        }
    }
    $scope.rellenaSubOp = function(operacion) {
        conexion_app.obtener_subop_x_op({
            "usuario": $scope.usr_global,
            "operacion": {
                "idoperacion": operacion.operacion.idoperacion
            },
            "tramite": {
                "idtramite": $rootScope.idtramite
            }
        }, function(data) {
            operacion.listaActos = data.suboperacionList;
        }, function(error) {
            alert("ocurrió un error al obtener la lista de suboperaciones: " + error.status);
        })
    }
    $scope.btn_operacion = function() {
        var idopt = $rootScope.operaciones.length;
        $scope.sel_operacion.status = "C";
        $rootScope.operaciones.push($scope.sel_operacion);
        $rootScope.operaciones[idopt].listaActos = new Array();
        $("#wrapp_operaciones").html("");
        $scope.renderOp();
    }
    $scope.renderOp = function() {
        for (var idopt = 0; idopt < $rootScope.operaciones.length; idopt++) {
            $("#wrapp_operaciones").append($compile("<operacion reference='" + $rootScope.operaciones[idopt].operacion.idoperacion + "' idopt='" + idopt + "' expediente='" + JSON.stringify($scope.expediente) + "' subopt='" + JSON.stringify($rootScope.operaciones[idopt].listaActos) + "' nombre='" + $rootScope.operaciones[idopt].operacion.dsnombre + "' desc='" + $rootScope.operaciones[idopt].operacion.dsdescripcion + "'></operacion>")($scope));
            $scope.renderSubOp($rootScope.operaciones[idopt], idopt, $rootScope.operaciones[idopt].operacion.dsnombre);
        }
    }
    $scope.renderSubOp = function(op, idopt, nombreOpe) {
        setTimeout(function() {
            for (var idsubop = 0; idsubop < op.listaActos.length; idsubop++) {
                var tmpContent = "#so" + op.operacion.idoperacion;
                $(tmpContent).append($compile("<sub-operacion id='subopt" + idopt + idsubop + "' idacto = '" + op.listaActos[idsubop].idacto + "' nombre = '" + op.listaActos[idsubop].dsnombre + "'  idopt='" + idopt + "' nombreOpe='" + nombreOpe + "' idsubopt='" + idsubop + "'></sub-operacion>")($scope));
            }
            console.info("ROOT TARJETON", $rootScope)
        }, 1000);
    }

    function filtraOperaciones(contenedor, contenido) {
        var arrTmp = new Array();
        $.each(contenido, function(i) {
            var objComp = this.operacion.idoperacion;
            $.each(contenedor, function(j) {
                if (this.operacion.idoperacion == objComp) {
                    arrTmp.push(j);
                }
            })
        })
        var d = 0;
        $.each(arrTmp, function() {
            var sp = contenedor.splice((this - d), 1);
            d++;
        })
    }

    function agrega_actualizacion(arreglo) {
        $.each(arreglo, function() {
            this.status = "A";
        })
    }
}