function ctrl_gruposTrabajo($scope,$route,conexion_app,conexion,catalogos){
   catalogos.obtener_abogados($scope);
   $scope.grupo_editar = false;
    
      $scope.getGrupos = function(){
        
         catalogos.get_gruposTrabajo($scope);
        
    }
    
   
    $scope.guardar = function(){
        
        var msj =($scope.grupotrabajo.idgrupotrabajo)?"Actualizó":"Guardó";
        
        conexion.guardar_grupos({
            "usuario":$scope.usr_global,
            "grupotrabajo":$scope.grupotrabajo
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,"Se "+msj+" correctamente el grupo de trabajo");
                $scope.cancel();
                $scope.getGrupos();
            }
            else{
                ejecutaAlerta(2,"Ocurrió un error al realizar la operación:"+data.estatus)
            }
        },function(error){
                alert("Algo malo paso al realizar la operación:"+error.status);
        });
        
    }
    
    
    $scope.delete = function(grupotrabajo){
        if(!confirm("¿Realmente quieres eliminar este elemento?")){
            return;
        }
        conexion.borrar_grupos({
            "usuario":$scope.usr_global,
            "grupotrabajo":grupotrabajo
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,"Se eliminó correctamente el grupo");
                $scope.getGrupos();
            }else{
                ejecutaAlerta(2,"Ocurrió un error al borrar el grupo:"+data.estatus)
            }
        },function(error){
            alert("Ocurrió un error al eliminar el grupo:"+error.status)
        })
    }
    
    $scope.carga_datos = function(obj){
        $scope.grupotrabajo = obj;
        $scope.grupo_editar = true;
    }
    
    $scope.cancel = function(){
        $scope.grupo_editar = false;
        $scope.grupotrabajo = {};
    }
    
    $scope.getGrupos();
    
}