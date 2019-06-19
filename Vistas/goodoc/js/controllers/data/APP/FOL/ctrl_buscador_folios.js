function ctrl_buscador_folios($scope, conexion_app, conexion, catalogos) {
    $scope.historico = {};
    $scope.numero_esc="";
    catalogos.get_usuarios($scope);
    $scope.parseFecha=function(fecha){return moment(fecha).format('DD-MM-YYYY');};
    $scope.buscar_x_usuario = function(){
        conexion_app.historial_x_usuario_folios({
            "usuario":usr_global,
            "abogado":$scope.usr_busqueda
        },function(data){
            if(data.exito){
                console.info("Devueltos usuario",data);
                $scope.historialDocumento = data.foliosDevueltos
            }
            else{
                ejecutaAlerta(2,"Ocurrió un error al obtener el historial de la escritura:"+data.estatus);
            }
        },function(error){
            alert("Algo malo paso al obtener el historial de la escritura"+error.estatus)
        })
    }
    
       $scope.buscar_x_fecha = function(){
        conexion_app.historial_x_fecha_folios({
            "usuario":usr_global,
            "fechaInicio":$scope.fechaInicio,
            "fechaFin":$scope.fechaFin
        },function(data){
            if(data.exito){
                console.info("Devueltos x fecha",data);
                $scope.historialDocumento = data.foliosDevueltos
            }
            else{
                ejecutaAlerta(2,"Ocurrió un error al obtener el historial de la escritura:"+data.estatus);
            }
        },function(error){
            alert("Algo malo paso al obtener el historial de la escritura"+error.estatus)
        })
    }
    
    $scope.buscar_x_numero = function(){
        console.info("numero esc a buscar",$scope.numero_esc)
        if($scope.numero_esc==""){
            ejecutaAlerta(2,"Debes ingresar un número de escritura a buscar");
        }
        buscaxnumeroesc($scope.numero_esc);
    }
    
    
    function buscaxnumeroesc (numero){
         conexion_app.historial_x_numesc_folios({
         "usuario":$scope.usr_global,
         "solicitudPrestamo":{
             "numeroEscritura":numero
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
    }
    
    $scope.lanza_detalle_folio = function(Detalle) {
        $scope.historico.numeroescritura = Detalle.numeroescritura;
        Avgrund.show("#alerta_detalle_devueltos");
        buscaxnumeroesc(Detalle.numeroescritura)
        $("#alerta_detalle_devueltos").fadeIn("slow");
    };
    
    
    
    $scope.cerrar = function(){
        Avgrund.hide();
    }
    
    $scope.change_filter = function(){
        
        $(".hon").hide("fast")
        $("#"+$scope.tipobusqueda).show("slow");
        
    }
    
    $("#tipoBusqueda")
}/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


