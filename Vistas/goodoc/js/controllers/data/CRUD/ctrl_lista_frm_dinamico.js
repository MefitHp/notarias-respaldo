function ctrl_lista_frm_dinamico($scope, $route, $rootScope, conexion_app, conexion, catalogos) {
    catalogos.get_suboperaciones($scope);
    $scope.get_formularios = function() {
        catalogos.get_formularios_x_acto($scope, $scope.sel_suboperacion.idsuboperacion);
    }
    $scope.edit_frm = function(formulario) {
        console.info("::FORMULARIO DINAMICO ::", formulario);
        $rootScope.edit_formulario = formulario;
        console.info($rootScope.edit_formulario);
        force_redirect('index_adm.html#/formulario_dinamico');
    }
    $scope.clone_frm = function(formulario) {
        $rootScope.edit_formulario = formulario;
        $rootScope.cloneForm = true;
        force_redirect('index_adm.html#/formulario_dinamico');
    }
    $scope.remove_frm = function(formulario) {
        if (confirm("¿Realmente deseas eliminar este formulario dinámico?")) {
            conexion.eliminar_formulario_dinamico({
                "usuario": $scope.usr_global,
                "conformulario": {
                    "id": formulario.id
                }
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se eliminó correctamente el fomulario dinamico");
                    var index = $scope.formularios.indexOf(formulario);
                    if (index > -1) {
                        $scope.formularios.splice(index, 1);
                    }
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
            }, function(error) {
                alert("Algo malo paso al eliminar el fomulario dinámico:" + error.status);
            });
        }
    }
}