define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_grupos_def", function ctrl_grupos_def($scope, $timeout, conexion, catalogos, $rootScope) {

        if ($rootScope.def_gpo) {
            $scope.gpo_def = $rootScope.def_gpo;
            console.info($scope.gpo_def);
            
            conexion.gpo_buscar_def({
                "usuario":$scope.usr_global,
                "grupo": {
                    "idgrupo":$scope.gpo_def.idgrupo
                }
            },
              function(data){
                  $scope.varGrupoList = data.varGrupoList;
              },
              function(error){
                  console.info(error);
              });
            
            
            delete $rootScope.def_gpo;
        }
        else {
            $scope.goTo("/catalogo_grupos");
        }

        $scope.varGrupoList = new Array();

        $scope.refreshListVars = function() {
            catalogos.get_lista_variables($scope);
        }

        $scope.append_grupo = function() {
            var tmp_gpo = {
                "variable": $scope.var_select.originalObject,
                "grupo": $scope.gpo_def,
                "dsorden": $scope.varGrupoList.length
            }
            $scope.varGrupoList.push(tmp_gpo);
        }

        $scope.del_grupo = function(objeto) {
            if (confirm("¿Estas seguro de borrar este elemento?")) {
                var index = $scope.varGrupoList.indexOf(objeto);

                if (index > -1) {
                    $scope.varGrupoList.splice(index, 1);
                }
                ejecutaAlerta(1, "Se borro correctamente el elemento");
            }
        }

        $scope.def_grupo_guardar = function() {

            conexion.gpo_def_grupo({
                "usuario": $scope.usr_global,
                "varGrupoList": $scope.varGrupoList
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se registró correctamente la definición del grupo");
                    $timeout(function() {
                        force_redirect("index_adm.html#/catalogo_grupos")
                    }, 1000)
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
            }
            , function(error) {
                alert("Algo malo ocurrió al guardar los dator: " + error.status);
            }
            );
        }

        $scope.refreshListVars();
    });

});



