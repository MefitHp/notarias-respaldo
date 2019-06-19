function ctrl_subir_doc($scope, $timeout, $route, $rootScope, conexion_app, conexion, catalogos, funcionesutiles) {

        $scope.popLateral = function(url_cargar, columnas, status, reload) {
            funcionesutiles.popLateral(url_cargar, columnas, status);
            if (reload) {
                $timeout(function() {
                    $route.reload();
                }, 4500);
            }
        }
        
        $scope.loadingDoc = function(){
            modal.show();
        }

        $timeout(function() {

            var link = "";
            var name = "";

            if ($rootScope.tipoDoc == 1) {
                link = "subirArchivoDocumento";
                name = "idactodoc";
            } else {
                link = "subirOriginal"
                name = "iddocor";
                
            }
            $("#idactodoc").attr("name", name);
            $("#archivo").attr("action", "http://" + contexto + "/notaria/gestionDocumentos/" + link);
            $("#idsesionactual").val($scope.usr_global.idsesionactual);
            $("#idusuario").val($scope.usr_global.idusuario);
            $("#idactodoc").val($rootScope.iddocumento);
            $("#desde").val($rootScope.archivoDesde);
            $("#desde").attr("name", "desde");
            if($rootScope.idtarea && $rootScope.idtarea !=""){
                $("#idtarea").val($rootScope.idtarea);
                $("#idtarea").attr("name", "idtarea");
            }

            delete $rootScope.iddocumento;
            delete $rootScope.tipoDoc;
            delete $rootScope.archivoDesde;
            delete $rootScope.idtarea;
        }, 500)
    }

