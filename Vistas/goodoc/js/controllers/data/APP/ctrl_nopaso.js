function ctrl_nopaso($scope, conexion_app, conexion, catalogos,$rootScope,$filter) {
           
         console.info("Ok no paso");  
           
        if(!$rootScope.escnumero && !$rootScope.escId && !$rootScope.idtarea){
            window.history(-1);
        }else{
            $scope.escNumero = $rootScope.escnumero; 
            $scope.escId = $rootScope.escId; 
            $scope.idTarea = $rootScope.idtarea;
            $scope.isprorroga = $rootScope.isprorroga;
            
            delete $rootScope.escnumero; 
            delete $rootScope.escId; 
            delete $rootScope.idtarea;
            delete $rootScope.isprorroga;
        }
        
        $scope.paso_data = function(variable, valor){
            $scope.ejecutaTarea($scope.idTarea,variable,valor,function(){
                $scope.goTo("/tareasbo");
            });
        }
        
        $scope.nopaso = function(){
          if(!confirm("¿Realmente deseas colocar la escritura como no paso?")) return false;
            conexion_app.get_escritura_x_id({
                "usuario":$scope.usr_global,
                "escritura":{
                    "idescritura":$scope.escId
                },
                "expediente":$rootScope.expediente
            },function(data){
                if(!data.exito){
                    ejecutaAlerta(2,"Algo malo paso al obtener la escritura");
                }else{
                    var esc = data.escritura;
                    esc.nopaso = true;
                    conexion_app.nopaso({
                            "usuario":$scope.usr_global,
                            "escritura":esc
                         },function(data){
                             if(data.exito){
                                 ejecutaAlerta(1,"La escritura se coloco en estatus no paso correctamente");
                                 $scope.paso(false);
                                 
                             }else{
                                 ejecutaAlerta(2,data.estatus);
                             }
                         },function(error){
                             alert("Ocurrio un error al colocar la escritura en no paso:"+error.status)
                         }) 
                }
            
            
            },function(error){
                ejecutaAlerta(2,"Algo malo paso al obtener la escritura");
            })
             
        }
        
        $scope.prorroga = function(){
            if(!confirm("¿Realmente deseas solicitar una prórroga de 5 días?")) return false;
            conexion_app.get_escritura_x_id({
                "usuario":$scope.usr_global,
                "escritura":{
                    "idescritura":$scope.escId
                },
                "expediente":$rootScope.expediente
            },function(data){
                if(!data.exito){
                    ejecutaAlerta(2,"Algo malo paso al obtener la escritura");
                }else{
                    var esc = data.escritura;
                    esc.nopaso = true;
                    conexion_app.nopaso({
                            "usuario":$scope.usr_global,
                            "escritura":esc
                         },function(data){
                             if(data.exito){
                                 ejecutaAlerta(1,"La escritura se coloco en estatus no paso correctamente");
                                 $scope.paso(false);
                                 
                             }else{
                                 ejecutaAlerta(2,data.estatus);
                             }
                         },function(error){
                             alert("Ocurrio un error al colocar la escritura en no paso:"+error.status)
                         }) 
                }
            
            
            },function(error){
                ejecutaAlerta(2,"Algo malo paso al obtener la escritura");
            })
        }
         
    }

