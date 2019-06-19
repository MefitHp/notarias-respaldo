function ctrl_search_resultado($scope,$rootScope,$timeout,catalogos,$location,conexion_app){$scope.buscarExpedientes=function(){conexion_app.expediente_busqueda({"usuario":$rootScope.usr_global,"persona":$scope.persona,},function(data){if(data.exito){$rootScope.resultados=data.lista;$scope.goTo("/resultados");}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert('Algo malo paso al obtener los expedientes: '+error.status);});}
if($rootScope.resultados){$scope.resultados=$rootScope.resultados;}
    $scope.expediente_compartir=function(exp){$rootScope.exp_compartido=exp;$scope.goTo('/expedientes_compartir')}
    
    $scope.clone_expediente = function(exp){
        $rootScope.edit_tramite = exp;
        $rootScope.is_edit_tramite = true;
        $scope.goTo('/clone_expediente');
    }
    
    $scope.edit_tramite = function(exp){
        $rootScope.edit_tramite = exp;
        $rootScope.is_edit_tramite = true;
        $scope.goTo('/edit_tramite')
    }
    
    
$scope.buscarHistorico=function(){conexion_app.expediente_buscar_historico({"usuario":$rootScope.usr_global,"historico":$scope},function(data){console.info(data.lista);},function(error){});}
$scope.expediente={};$scope.compareciente={};$scope.compareciente.persona={};$scope.compareciente.persona.dsnombrecompleto={};$scope.expediente.tramite={};$scope.expediente.tramite.cliente={};$scope.expediente.abogado={};$scope.fechainicial="";$scope.fechafinal="";$scope.searchAvanzado=function(){if(($scope.fechainicial&&$scope.fechainicial!="")&&($scope.fechafinal&&$scope.fechafinal!="")){$scope.expediente.fechainicial=parseFecha($scope.fechainicial)+"T00:00:00-06:00";$scope.expediente.fechafinal=parseFecha($scope.fechafinal)+"T00:00:00-06:00";}else{delete $scope.expediente.fechainicial;delete $scope.expediente.fechafinal;}
modal.show();$scope.resultados=[];conexion_app.expediente_busqueda_avanzada({"usuario":$rootScope.usr_global,"expediente":$scope.expediente,"compareciente":$scope.compareciente},function(data){if(data.exito){$scope.resultados=data.lista;if($scope.resultados.length<1){ejecutaAlerta(2,"No se encontraron resultados en la búsqueda")}else{ejecutaAlerta(1,"Resultados en la búsqueda: "+$scope.resultados.length);}}else{alert("Ocurrió un error:"+data.estatus);}},function(error){alert("Algo malo ocurrió al realizar la búsqueda: "+error.status);}).$promise.finally(destroyModal);}}