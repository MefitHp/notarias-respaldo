function ctrl_expedientes_listar($scope,$rootScope,conexion_app,conexion,catalogos){catalogos.get_expedientes_x_usuario($scope);$scope.expediente_compartir=function(exp){$rootScope.exp_compartido=exp;$scope.goTo('/expedientes_compartir')}
$rootScope.idacto="";$(".expediente_acto").fadeOut("slow");$("#wrap_actos_seleccionado").hide("slow");$("#acto_seleccionado").text("");

$scope.edit_tramite = function(exp){
    $rootScope.edit_tramite = exp;
    $rootScope.is_edit_tramite = true;
    $scope.goTo('/edit_tramite')
}


$scope.clone_expediente = function(exp){
    $rootScope.edit_tramite = exp;
    $rootScope.is_edit_tramite = true;
    $scope.goTo('/clone_expediente');
}


}