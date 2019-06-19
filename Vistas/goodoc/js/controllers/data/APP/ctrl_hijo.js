function ctrl_hijo($scope, $rootScope, $timeout, conexion_app, catalogos) {
   
    if(!$rootScope.sel_com_to_add){
        $scope.goTo("/comparecientes");
    }
    
    
    if($rootScope.edit_hijo){
            $scope.contwo = $rootScope.actualizaComp;
            $rootScope.edit_hijo = false;
    }
    
    
        
    $scope.guarda_hijo = function () {
    
        conexion_app.guardar_hijo({
            "usuario":$scope.usr_global,
            "compareciente":$rootScope.sel_com_to_add,
            "hijo":$scope.contwo
            
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,"Se registro correctamente");
                $scope.goTo("/comparecientes")
            }else{
                ejecutaAlerta(2,"Ocurrio un error al guardar el registro: "+data.estatus)
            }
        },function(error){
            alert("Algo malo paso al guardar el registro: "+error.status)
        })
       
    }
    
    
    
   
}