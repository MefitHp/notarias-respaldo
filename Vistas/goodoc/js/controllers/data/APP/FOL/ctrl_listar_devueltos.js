function ctrl_fol_listar_devueltos($scope, conexion_app, conexion, catalogos) {
    $scope.historico = {};
    $scope.parseFecha=function(fecha){return moment(fecha).format('DD-MM-YYYY');};
    
    $scope.lanza_detalle_folio = function(Detalle) {
        $scope.historico.numeroescritura = Detalle.numeroescritura;
        Avgrund.show("#alerta_detalle_devueltos");
        
        conexion_app.historial_x_numesc_folios({
         "usuario":$scope.usr_global,
         "solicitudPrestamo":{
             "numeroEscritura":Detalle.numeroescritura
         }
        },
        function(data){
            if(data.exito){
                $scope.historialDocumento = data.foliosDevueltos
            }
            else{
                ejecutaAlerta(2,"Ocurrió un error al obtener el historial de la escritura:"+data.estatus);
            }
        },
        function(error){
            alert("Algo malo paso al obtener el historial de la escritura:"+error.status)
        })
        
        $("#alerta_detalle_devueltos").fadeIn("slow");
    };
    
    $scope.cerrar = function(){
        Avgrund.hide();
    }
    
    conexion_app.fol_listar_devueltos({
        "usuario": {
            "idusuario": "c81e728d9d4c2f636f067f89cc14862c",
            "idsesionactual": "1"
        }
    }, function(data) {
        console.info("OK DATA", data);
        $scope.listaDevueltos = data.foliosDevueltos;
    }, function(error) {
        console.info("ERROR DATA", error);
    });
}