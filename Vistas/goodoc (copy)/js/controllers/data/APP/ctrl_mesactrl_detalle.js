function ctrl_mesactrl_detalle($scope, conexion_app, conexion, catalogos,$rootScope,funcionesutiles) {
    $scope.validaCuenta($scope.usr_global,"mesa");    
        
        $scope.popLateral = function(url_cargar, columnas, status) {
        funcionesutiles.popLateral(url_cargar, columnas, status);
    }
    $scope.subir_doc = function(id_doc, tipoDoc,desde,idtarea) {
        $scope.popLateral('subir_documento', 2, true);
        $rootScope.iddocumento = id_doc;
        $rootScope.tipoDoc = tipoDoc;
        $rootScope.archivoDesde = desde;
        $rootScope.idtarea = idtarea;
    }
    
    conexion_app.mesactrl_estatus({},function(data){
        $scope.estatusdocs = data.estatusDoc;
        if($scope.mesa.estatusdoc!=null){
            $scope.estatusActual = $scope.mesa.estatusdoc;
        }else{
            $scope.estatusActual = $scope.estatusdocs[0];
        }
        
    },function(error){
        ejecutaAlerta(2,"Algo malo paso al obtener los estatus del documento");
    });
        if($rootScope.mesactrlDoc){
            $scope.mesa=$rootScope.mesactrlDoc;
            if($scope,$scope.mesa.actodocumento!=null){
                catalogos.get_comentarioXObj($scope,$scope.mesa.actodocumento.idactodoc);
            }
            delete $rootScope.mesactrlDoc;
            
        }else{
            $scope.goTo("/mesa_control");
        }
        
        $scope.verArchivo = function(iddoc, tipo) {
        var params = "idusuario=" + $scope.usr_global.idusuario + "&idsesionactual=" + $scope.usr_global.idsesionactual + "&tipo=" + tipo + "&id=" + iddoc;
        var url = "http://" + contexto + "/notaria/gestionDocumentos/mostrarDocumento?" + params;
        window.open(url);
    }
    
        $scope.ActualizaEstatusDoc  = function(mesa){
        if($scope.estatusActual=="DEVUELTO"){
            ejecutaAlerta(2,"Para poder marcar el documento como devuelto, debes subir un acuse y se marcará en automático");
            return;
        }
        if($scope.estatusActual=="NO_PASO"){
            ejecutaAlerta(2,"NO PASO, no es una opción válida para este tipo de documento");
            return;
        }
        
        mesa.estatusdoc = $scope.estatusActual;
        modal.show();
        conexion_app.mesactrl_actualizar({
            "usuario":$scope.usr_global,
            "documento": mesa
        },
            function(data){
                if(data.exito){
                    ejecutaAlerta(1,"Se actualizo correctamente el documento");
                    $scope.mesa = mesa;
                }
                else{
                    ejecutaAlerta(2,"Ocurrio un error al actualizar el documento"+ data.estatus)
                }
            },
            function(error){
                    alert("Algo malo paso al actualizar el documento"+error.status);
            }).$promise.finally(destroyModal);
        }
        
        $scope.verOnline= function(doc){
        console.info(doc);
        //window.open(doc.rutaArchivo,"documentos");
        window.open("/tarjeton/documento.html","documentos");
    }
                        
    }

