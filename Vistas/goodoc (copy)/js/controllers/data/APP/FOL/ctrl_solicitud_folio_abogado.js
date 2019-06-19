function ctrl_fol_solicitud_folio($scope, conexion_app, conexion, catalogos) {
    $scope.solicitudPrestamo = {};
    $scope.parseFecha=function(fecha){return moment(fecha).format('DD-MM-YYYY');};


    $scope.eliminar_solicitud = function(sol){
        conexion_app.fol_eliminar({
            "usuario": $scope.usr_global, 
            "solicitudPrestamo": sol
        }
        , function (data) {
            if (data.exito) {
                ejecutaAlerta(1,"Se eliminó correctamente la solicitud");
                $scope.obtenerSolicitudesXAbogado();
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error al eliminar la solicitud:  " + data.estatus);
            }
        }
        , function (error) {
            alert("Algo malo ocurrió al eliminar la solicitud:" + error.status);
        });
    }





    $scope.obtenerSolicitudesXAbogado = function(){
        conexion_app.fol_listar_x_abogado({
            "usuario": $scope.usr_global, 
            "abogado": $scope.usr_global
        }
        , function (data) {
            if (data.exito) {
                $scope.solicitados = data.solicitadosAbogado;
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error al obtener los folios solicitados:  " + data.estatus);
            }
        }
        , function (error) {
            alert("Algo malo ocurrió al obtener los folios solicitados:" + error.status);
        });
    }
    
    $scope.obtenerPrestadosXAbogado = function(){
        conexion_app.fol_listar_prestados_x_abogado({
            "usuario": $scope.usr_global, 
            "abogado": $scope.usr_global
        }
        , function (data) {
            if (data.exito) {
                $scope.prestados = data.prestadosAbogado;
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error al obtener los folios prestados:  " + data.estatus);
            }
        }
        , function (error) {
            alert("Algo malo ocurrió al obtener los folios prestados:" + error.status);
        });
    }
    
    $scope.guarda_folio = function () {
        $scope.solicitudPrestamo.usuarioRecibe = $scope.usr_global;
        conexion_app.fol_guardar_solicitud({
            "usuario": $scope.usr_global, "solicitudPrestamo": $scope.solicitudPrestamo
        }
        , function (data) {
            if (data.exito) {
                ejecutaAlerta(1, data.estatus);
                $scope.obtenerSolicitudesXAbogado();
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
    
   $scope.obtenerSolicitudesXAbogado();
   $scope.obtenerPrestadosXAbogado();

    
    
    
}