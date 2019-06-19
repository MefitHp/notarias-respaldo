function ctrl_fol_registro_escritura($scope, conexion_app, conexion, catalogos) {
    $scope.escritura_ext = {
    }
    ;
    var limiteAlerta = 10;
    $scope.init = function(){
        catalogos.get_folios_disponibles($scope, function () {
        var disponibles = $scope.foliosDisponibles.foliosDisponibles;
        var actuales = $scope.foliosDisponibles.folioActual;
        $scope.total_folios = disponibles - actuales;
        $scope.escritura_ext.folioini = (actuales + 1);
        $scope.escritura_ext.foliofin = (actuales + 1);
        if ($scope.total_folios <= 0) {        $scope.escritura_ext.folioini = (actuales + 1);

            $scope.alerta_folios = true;
            $scope.alerta_mensaje = "Se terminaron los folios disponibles";
        }

        else if ($scope.total_folios < limiteAlerta) {
            $scope.alerta_folios = true;
            $scope.alerta_mensaje = "Estan por terminarse los folios disponibles";
        }
    }
    )
    }
    
    $scope.agrega_escritura_ext = function () {
        conexion_app.fol_registro_escritura({
            "usuario": $scope.usr_global, "escrituraExt": $scope.escritura_ext
        }
        , function (data) {
            if (data.exito) {
                ejecutaAlerta(1, data.estatus);
                $scope.escritura_ext = {}
                $scope.init();
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error:  " + data.estatus);
            }
        }
        , function (error) {
            console.info("ERROR DATA", error);
        }
        )
    };
    $scope.init();
}
