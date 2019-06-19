// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA


function ctrl_tramite($scope, $rootScope, $timeout, conexion_app, conexion, catalogos) {
    $scope.tramite = {};
    if ($scope.usr_global.rol.dsprefijo == "abog") {
        $scope.tramite.abogado = $scope.usr_global;
    }
    $scope.buscar_personas = function() {
        catalogos.busca_personas($scope, $scope.buscar_persona);
    }
    $scope.limpia_datos = function() {
        $scope.tramite.cliente = {};
        $(".nopersonselect").slideUp("slow");
        $(".personselect").slideDown("slow");
    }
    $scope.add_data_persona = function(idpersona) {
        $(".personselect").slideUp("slow");
        $(".nopersonselect").slideDown("slow");
        conexion_app.buscar_cliente_detalle({
            "usuario": $scope.usr_global,
            "persona": {
                "idpersona": idpersona
            }
        }, function(data) {
            if (data.exito) {
                $scope.tramite.cliente = data.persona;
                $timeout(function() {
                    if ($scope.tramite.cliente.nacionalidad && $scope.tramite.cliente.nacionalidad != null) {
                        for (var i = 0; i < $scope.nacionalidad.length; i++) {
                            if ($scope.nacionalidad[i].dselemento == $scope.tramite.cliente.nacionalidad.dselemento) {
                                $scope.tramite.cliente.nacionalidad = $scope.nacionalidad[i];
                                break;
                            }
                        }
                    }
                }, 1000);
            } else {
                ejecutaAlerta(2, "Ocurrió un error:" + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al obtener el detalle de la persona; " + error.status);
        });
    }
    catalogos.get_abogados($scope);
    catalogos.get_nacionalidad($scope);
    catalogos.get_locacion($scope);
    $scope.tramite_add = function() {
       if(!$scope.tramite.abogado || $scope.tramite.abogado.rol.dsprefijo != "abog"){
                 ejecutaAlerta(2,"Selecciona un abogado de la lista");
                return;
        }

        modal.show();
        $scope.tramite.cliente.dsnombrecompleto = $scope.tramite.cliente.dsnombre + " ";
        $scope.tramite.cliente.dsnombrecompleto += $scope.tramite.cliente.dsapellidopat + " ";
        $scope.tramite.cliente.dsnombrecompleto += $scope.tramite.cliente.dsapellidomat;
        conexion_app.nuevo_proceso({
            "usuario": $scope.usr_global,
            "tramite": $scope.tramite,
            "contacto": $scope.contacto
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, 'El trámite fue registrado correctamente');
                $rootScope.idtramite = data.idTramite;
                force_redirect('index.html#/creacion_expediente');
                reset_campos('myForm')
            } else {
                ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
            }
        }, function(error) {
            ejecutaAlerta(2, 'Ocurrió un error al guardar el registro: ' + error.status);
        }).$promise.finally(destroyModal);
    }
    
    if($rootScope.is_edit_tramite){
        $scope.exp_edit = $rootScope.edit_tramite;
        delete $rootScope.edit_tramite;
    }
    
    $scope.tramite_edit = function(){
        modal.show();
        $scope.tramite.cliente.dsnombrecompleto = $scope.tramite.cliente.dsnombre + " ";
        $scope.tramite.cliente.dsnombrecompleto += $scope.tramite.cliente.dsapellidopat + " ";
        $scope.tramite.cliente.dsnombrecompleto += $scope.tramite.cliente.dsapellidomat;
        conexion_app.tramite_edit({
            "usuario": $scope.usr_global,
            "expediente": $scope.tramite
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, 'El trámite fue registrado correctamente');
                $rootScope.idtramite = data.idTramite;
                force_redirect('index.html#/creacion_expediente');
                reset_campos('myForm')
            } else {
                ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
            }
        }, function(error) {
            ejecutaAlerta(2, 'Ocurrió un error al guardar el registro: ' + error.status);
        }).$promise.finally(destroyModal);
    }
    
}