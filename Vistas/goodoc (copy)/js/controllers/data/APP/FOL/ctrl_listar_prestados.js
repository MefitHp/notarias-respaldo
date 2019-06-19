function ctrl_fol_listar_prestados($scope,conexion_app,conexion,catalogos)
{

if($scope.usr_global.rol.dsprefijo!="arch"){$scope.goTo("/folios/solicitud_folios_abogado");}
    
catalogos.obtener_abogados($scope);
$scope.comentario_folio = "";
$scope.cerrar = function(){
    Avgrund.hide();
}
$scope.parseFecha=function(fecha){return moment(fecha).format('DD-MM-YYYY');};
$scope.lanza_recibir_folios=function(List_folio){
    Avgrund.show("#alerta_recibir_folios");
    $("#alerta_recibir_folios").fadeIn("slow");
    $scope.folioFinal=List_folio;
    console.info("SELECCIONADO",List_folio)
};
    $scope.activa_comentario=function(){
        //$('#cerrar_devolucion').fadeToggle('fast');
        $('#Comentario_rechazo').fadeToggle('fast');};
    conexion_app.fol_listar_prestados({"usuario":{"idusuario":"c81e728d9d4c2f636f067f89cc14862c","idsesionactual":"1"}},function(data){console.info("OK DATA",data);$scope.lista_prestados=data.foliosPrestadosList;},function(error){console.info("ERROR DATA",error);});

$scope.lanza_Devolucion=function(Solic,Prest){
    $scope.folioFinal.usuarioPrestador = Solic;
    $scope.folioFinal.usuarioRecibe = $scope.usr_global;
    conexion_app.fol_devolucion_folios({
        "usuario":$scope.usr_global,"solicitudPrestamo":$scope.folioFinal,"comentario":$scope.comentario_folio},
            function(data){
                if(data.exito){
                ejecutaAlerta(1,data.estatus);
                                var index=$scope.lista_prestados.indexOf($scope.folioFinal);
                if(index>-1){
                    $scope.lista_prestados.splice(index,1);
                    $scope.cerrar();
                    
                }}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}
                
        },
                function(error){console.info("ERROR DATA",error);console.info("Json de usuario"+JSON.stringify(Solic.idusuario));
    });
};
$scope.lanza_Rechazar=function(Solic,Prest,Comen){conexion_app.fol_rechazar_folios({"usuario":$scope.usr_global,"d  evolucionFolios":{"usuarioEntrega":{"idusuario":Solic.idusuario},"infolioinicio":$scope.folioFinal.infolioinicio,"infoliofin":$scope.folioFinal.infoliofin},"devolucionRechazada":{"motivo":Comen}},function(data){console.info("Ok Data:",JSON.stringify(data));if(data.exito){ejecutaAlerta(1,data.estatus);var index=$scope.lista_prestados.indexOf($scope.folioFinal);if(index>-1){$scope.lista_prestados.splice(index,1);}}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){console.info("Error Data:",error);});};}