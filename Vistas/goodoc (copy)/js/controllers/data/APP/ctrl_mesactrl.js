function ctrl_mesactrl($scope, conexion_app, conexion, catalogos,$rootScope,$filter,$route) {
           $scope.validaCuenta($scope.usr_global,"mesa");    

        
            $scope.mesas_filter=[];
            var currentFilter=-1;
            catalogos.get_docs_mesa($scope,function(){
                var fechatmp ="";
                // recorro el arreglo de mesas y busco coincidencias de fecha o creo nuevas y agrego
                for(var i = 0; i < $scope.mesas.length; i++){
                 var fechamesa = getFecha($scope.mesas[i].tmstmp);
                 if(fechatmp != fechamesa){
                    fechatmp = fechamesa;
                    currentFilter +=1;
                    $scope.mesas_filter[currentFilter] ={"fecha":fechatmp,"mesas":[]};    
                 }
                 $scope.mesas_filter[currentFilter].mesas.push($scope.mesas[i]);          
                }});
                    
            
  
            $scope.detalleDocumento = function(mesa){
                $rootScope.mesactrlDoc = mesa;
                $scope.goTo("/mesa_control_detalle");
            };
            
            var getFecha = function(fecha){
                return $filter('date')(fecha,'d/M/yy')
            }
            
            $scope.cancela_nopaso = function(escritura,tipo){
                var msjconfirm = (tipo)?"¿Deseas eliminar el NO PASO de la Mesa de Control y regresar la escritura a su estado original?":"¿Deseas rechazar la solicitud de NO PASO por parte del abogado?";
                if(!confirm(msjconfirm)) return;
                
                conexion_app.mesactrl_cancela_nopaso({
                    "usuario":$scope.usr_global,
                    "escritura":escritura,
                    "cancelanopaso":tipo
                    
                },
                    function(data){
                        if(data.exito){
                            ejecutaAlerta(1,"Se actualizó correctamente el elemento");
                            $route.reload();
                        }else{
                            ejecutaAlerta(2,"Ocurrió un error al actualizar el elemento:"+data.estatus)
                        }
                    },
                    function(error){
                        alert("Algo malo paso al actualizar el elemento:"+error.status);
                    })
                
            }
            
            
            
    }

