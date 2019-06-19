function ctrl_escritura_version($scope, $rootScope, conexion_app, conexion, catalogos, funcionesutiles) {
 /*
     * 
     * 
     * 
     * http://localhost:8080/notarias-web/notaria/documentoNotarialParcial/listarPorEscritura
     * 
     * 
     * {
"documentoParcial":{"escritura":{"idescritura":"15e3304794e06e6e02b222585c692b75"}
},
"usuario":{"idsesionactual":"8640D29CAB67EE26D247FBE42BC05EE8","idusuario":"b4bade787affa09b4992da0248724858"}
}   
     */
    $scope.popLateral = function (url_cargar, columnas, status) {
        funcionesutiles.popLateral(url_cargar, columnas, status);
    }
    
    $scope.getVersiones = function(){
        conexion_app.get_doc_obj_versiones({
            "usuario":$scope.usr_global,
            "documentoParcial":{
                "escritura":{
                            "idescritura":$rootScope.edit_esc      
                }
            }
        },function(data){
            if(data.exito){
                $scope.doumentosParciales = data.documentoParcialList;
            }else{
                ejecutaAlerta(2,"Ocurrió un error al obtener las versiónes del documento:"+data.estatus)
            }
        },function(error){
            alert("Algo malo paso al obtener las versiónes del documento"+error.status)
        });
    }
    
    $scope.setTexto = function(texto){
        if(confirm("Realmente desea regresar a la versión seleccionada")){
            CKEDITOR.instances["plan_ck"].setData(texto);
            $scope.popLateral('', 2, false);
        }
        
    }
    
    $scope.parseFecha = function(fecha){
        return parseFecha(fecha);
    }
    
    $scope.getVersiones();
}