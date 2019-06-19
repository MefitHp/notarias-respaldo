
function ctrl_tramite_clone($scope, $rootScope, $timeout, conexion_app,catalogos) {
    $scope.tramite = {};
   
    $scope.buscar_personas = function() {
        catalogos.busca_personas($scope, $scope.buscar_persona);
    }
    $scope.limpia_datos = function() {
        $scope.tramite.cliente = $scope.personatmp;
        $(".nopersonselect").slideUp("slow");
        $(".personselect").slideDown("slow");
    }
    $scope.add_data_persona = function(idpersona) {
        $scope.personatmp = $scope.tramite.cliente;
        $(".personselect").slideUp("slow");
        $(".nopersonselect").slideDown("slow");
        conexion_app.buscar_cliente_detalle({
            "usuario": $scope.usr_global,
            "persona": {
                "idpersona": idpersona
            }
        }, function(data) {
            if (data.exito) {
                $scope.exp_edit.tramite.cliente = data.persona;
            } else {
                ejecutaAlerta(2, "Ocurrió un error:" + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al obtener el detalle de la persona; " + error.status);
        });
    }
    
    
    if($rootScope.is_edit_tramite){
        $scope.exp_edit = $rootScope.edit_tramite;
        $scope.exp_edit.tramite.cliente={};
        delete $rootScope.edit_tramite;
        
        catalogos.get_abogados($scope,function(){
               
               $timeout(function(){
                    if ($scope.exp_edit.abogado && $scope.exp_edit.abogado != null) {
                        for (var i = 0; i < $scope.abogados.length; i++) {
                            if ($scope.abogados[i].idusuario == $scope.exp_edit.abogado.idusuario) {
                                $scope.exp_edit.abogado = $scope.abogados[i];
                                break;
                            }
                        }
                    }
               },1000)
              
               
           });
    }
    
    $scope.tramite_clone = function(){
        if(!confirm("Se creará una copia de todos los datos registrados en este expediente...¿Desea continuar?")) return;
        modal.show();
        $scope.exp_edit.tramite.cliente.dsnombrecompleto = $scope.exp_edit.tramite.cliente.dsnombre + " ";
        $scope.exp_edit.tramite.cliente.dsnombrecompleto += $scope.exp_edit.tramite.cliente.dsapellidopat + " ";
        $scope.exp_edit.tramite.cliente.dsnombrecompleto += $scope.exp_edit.tramite.cliente.dsapellidomat;
        

        conexion_app.expediente_clone({
            "usuario": $scope.usr_global,
            "expediente": $scope.exp_edit
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, 'El trámite se clonó correctamente');
                force_redirect('index.html#/expedientes_listar');
                reset_campos('myForm');
            } else {
                ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
            }
        }, function(error) {
            ejecutaAlerta(2, 'Ocurrió un error al clonar el expediente: ' + error.status);
        }).$promise.finally(destroyModal);
    }
    
}