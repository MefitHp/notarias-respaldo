    function ctrl_tipo_alerta($scope,con_tipo) {
        //alert("Tipo alerta")
       $scope.tipo_editar = false;
       
       $scope.guardar = function(){
           con_tipo.save($scope.alertatipo,
           function(data){
                    if (data.exito){
                        ejecutaAlerta(1,data.status);
                        $scope.alertatipo = {};
               }else{
                        ejecutaAlerta(2,data.status);
                    }
                $scope.get_tipalerts();    
           },function(error){
               alert("Algo malo paso guardar el objeto:"+error.status)
           });
           
       }
       
       $scope.get_tipalerts = function(){
           $scope.tipalerts = con_tipo.query();
       }
       
       $scope.delete= function(dato){
           if(!confirm("¿Realmente deseas eliminar este elemento?")) return ;
           con_tipo.delete({id:dato},  function(data){
                    if (data.exito){
                        ejecutaAlerta(1,data.status);
                        $scope.alertatipo = {};
               }else{
                        ejecutaAlerta(2,data.status);
                    }
                $scope.get_tipalerts();    
           },function(error){
               alert("Algo malo paso guardar el objeto:"+error.status)
           });
       }
       
       $scope.get_tipalerts();
    
    }

