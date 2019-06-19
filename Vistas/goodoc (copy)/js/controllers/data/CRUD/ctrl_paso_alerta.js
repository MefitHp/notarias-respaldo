    
    function ctrl_paso_alerta($scope,con_step,con_tipo) {
        //alert("Paso alerta")
       $scope.tipo_editar = false;
       $scope.paso = {}
       $scope.paso.isfinal = "false";
       $scope.paso.estadoactual="OK";
       $scope.paso.tipoalerta = {};
       
       $scope.guardar = function(){
           con_step.save($scope.paso,
           function(data){
                    if (data.exito){
                        ejecutaAlerta(1,data.status);
                        $scope.paso = {};
                        $scope.paso.tipoalerta = {}
               }else{
                        ejecutaAlerta(2,data.status);
                    }
                $scope.getSteps();    
           },function(error){
               alert("Algo malo paso guardar el objeto:"+error.status)
           });
           
       }
       
       $scope.delete= function(dato){
           if(!confirm("¿Realmente deseas eliminar este elemento?")) return ;
           con_step.delete({id:dato},  function(data){
                    if (data.exito){
                        ejecutaAlerta(1,data.status);
                        $scope.paso = {};
                        $scope.paso.tipoalerta = {}
               }else{
                        ejecutaAlerta(2,data.status);
                    }
                $scope.getSteps();    
           },function(error){
               alert("Algo malo paso guardar el objeto:"+error.status)
           });
       }
       
        $scope.getSteps = function(){
            $scope.steps = con_step.query();
        };
        
        $scope.getSteps();
        
        $scope.tipalerts = con_tipo.query();
    
    }

