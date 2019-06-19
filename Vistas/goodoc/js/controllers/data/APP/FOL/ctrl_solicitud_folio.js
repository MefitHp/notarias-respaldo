function ctrl_fol_solicitud_folio($scope, conexion_app, conexion, catalogos) {
    catalogos.obtener_abogados($scope);
    $scope.lanza_guardarEscritura = function () {
        $('.ocultar_Escritura').fadeIn('slow');
        $('.ocultar_Folios').fadeOut('fast');
        delete $scope.solicitudPrestamo.infolioinicio;
        delete $scope.solicitudPrestamo.infoliofin;
    }
    ;
    $scope.lanza_guardarFolio = function () {
        $('.ocultar_Folios').fadeIn('slow');
        $('.ocultar_Escritura').fadeOut('fast');
        delete $scope.solicitudPrestamo.escrituraSolicitada;
    }
    ;
    $scope.guarda_folio = function () {
        conexion_app.fol_guardar_solicitud({
            "usuario": $scope.usr_global, "solicitudPrestamo": $scope.solicitudPrestamo
        }
        , function (data) {
            if (data.exito) {
                ejecutaAlerta(1, data.estatus);
                $scope.goTo('/folios/prestados');
                delete $scope.solicitudPrestamo;
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error:  " + data.estatus);
            }
        }
        , function (error) {
            console.info("ERROR DATA", error);
        }
        );
    }
}