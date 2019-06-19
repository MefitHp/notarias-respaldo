
function ctrl_usuariosgrupostrabajo($scope,$route,conexion_app,conexion,catalogos){
    
        $scope.usuariogrupotrabajo = {};
    
        conexion.listar_usuarios({usuario:$scope.usr_global},
        function(data){$scope.user_lista=data.usuarioList;},
        function(error){alert("Algo malo ocurrió :: Error "+error.status);});


         catalogos.get_gruposTrabajo($scope);
         
         $scope.obtenGruposUsuario = function(){
           conexion.listar_gruposUsuario({
             "usuario":$scope.usr_global
         },function(data){
             if(data.exito){
                 $scope.gruposAsociados = data.gruposdetrabajo;
             }else{
                 ejecutaAlerta(2,"Ocurrió un error al obtener los grupos asociados:"+data.estatus)
             }
         },function(error){
             alerta("Algo malo ocurrió al obtener los grupos asociados:"+error.status);
         })  
         }
         


         $scope.guardar  = function(){
             conexion.guardar_gruposUsuario({
                 "usuario":$scope.usr_global,
                 "usuariogrupotrabajo":$scope.usuariogrupotrabajo
             },function(data){
                 if(data.exito){
                     ejecutaAlerta(1,"Se guardó correctamente la asociación");
                     $scope.usuariogrupotrabajo.usuario = {};
                     $scope.obtenGruposUsuario();
                 }else{
                     ejecutaAlerta(2,"Ocurrió un error al guardar la asociación:"+data.estatus)
                 }
             },function(error){
                 alert("Algo malo paso al guardar la asociación:"+error.status)
             })
         }
         
         $scope.delete = function(objeto){
              if(!confirm("¿Realmente quieres eliminar este elemento?")){
            return;
            }
            conexion.borrar_gruposUsuario({
            "usuario":$scope.usr_global,
            "usuariogrupotrabajo":objeto
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,"Se eliminó correctamente la asociación");
                $scope.obtenGruposUsuario();
            }else{
                ejecutaAlerta(2,"Ocurrió un error al borrar la asociación:"+data.estatus)
            }
        },function(error){
            alert("Ocurrió un error al eliminar la asociación:"+error.status)
        })
            
            
         }
    
    
    $scope.obtenGruposUsuario();
    
    }


