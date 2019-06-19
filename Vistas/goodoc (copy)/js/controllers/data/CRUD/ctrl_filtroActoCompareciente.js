function ctrl_filtroActoCompareciente($scope,$route,conexion_app,conexion,catalogos){
   
   catalogos.get_operaciones($scope);
   catalogos.get_tipo_comparecientes($scope);
   catalogos.get_tipoComparecientesFiltro($scope);
   
   
    $scope.guardar = function(){
        conexion.guardar_filtroTipoCompareciente({
            "usuario":$scope.usr_global,
            "filtroActoCompareciente":$scope.filtroActoCompareciente
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,"Se registró correctamente la operación");
                catalogos.get_tipoComparecientesFiltro($scope);
            }else{
                ejecutaAlerta(2,"Ocurrió un error al guardar la operación"+data.estatus)
            }
        },function(error){
            alert("Algo malo paso al guardar la operación:"+error.status)
        })           
    }
    
    
    $scope.delete = function(filtro){
        conexion.borrar_filtroTipoCompareciente({
            "usuario":$scope.usr_global,
            "filtroActoCompareciente":filtro
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,"Se eliminó correctamente el elemento:"+data.estatus);
                catalogos.get_tipoComparecientesFiltro($scope);

            }else{
                ejecutaAlerta(2,"Ocurrió un error al intentar eliminar el elemento:"+data.estatus);
            }
        },
          function(error){
            alert("Algo malo pasó al intentar borrar el elemento:"+error.status)
        })
    }
    
    
    
}