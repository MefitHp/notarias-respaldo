// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
function ctrl_escrituras($scope, $route, conexion_app, catalogos, $rootScope, $timeout) {
    
    // En caso de estar pendiente mesa de control muestro mensaje;
    $scope.mensajes_mesactrl = [];

    
    delete $rootScope.numeroescritura;
    
    
    
    $scope.escritura = {};
    $scope.actos_seleccionados = [];
    $scope.escritura_add = function() {
        var seleccionados = [];
        for (var i = 0; i < $scope.actos_seleccionados.length; i++) {
            seleccionados.push($scope.actos[$scope.actos_seleccionados[i]]);
        }
        if ($scope.myForm.$valid) {
            modal.show();
            conexion_app.guardar_escritura({
                "usuario": $scope.usr_global,
                "expediente": $scope.expediente,
                "escritura": $scope.escritura,
                "actos": seleccionados
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, 'La escritura fue registrada correctamente');
                    force_redirect('index.html#/escrituras');
                } else {
                    ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                }
            }, function(error) {
                alert("Algo malo paso al guardar la escritura: " + error.status);
            }).$promise.finally(destroyModal);
            $scope.myForm.submitted = false;
        } else {
            $scope.myForm.submitted = true;
        }
    }
    $scope.buscar_escrituras_x_expediente = function() {
        conexion_app.buscar_escrituras_x_expediente({
            "usuario": $scope.usr_global,
            "expediente": $scope.expediente
        }, function(data) {
            if (data.exito) {
                $scope.listaEscrituras = new Array();
                $scope.listaEscrituras = data.listaEscrituras;
                for(var i = 0;$scope.listaEscrituras.length>i;i++){
                    if($scope.listaEscrituras[i].escritura.nopaso){
                        $scope.agregaMesaCtrl($scope.listaEscrituras[i].escritura);
                    }
                }
                
            } else {
                $scope.listaEscrituras = [];
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al obtener la lista de escrituras: " + error.status);
        });
    }
    
    $scope.agregaMesaCtrl = function(escritura){
        conexion_app.obtiene_ultima_bitacora_escritura({
            "usuario":$scope.usr_global,
            "escritura":escritura
        },
            function(data){
                if(data.exito){
                    $scope.mensajes_mesactrl.push(data.ultimaBitacora);
                }else{
                        ejecutaAlerta(2,"Ocurrio un error al obtener la bitácora de Mesa de Control:"+data.estatus);
                }
            },
            function(error){
                alert("Algo malo paso al obtener la bitácora de Mesa de Control:"+error.status);
            }) 
    }
    
    $scope.btn_borrar_escritura = function(idescritura) {
        if (confirm("Desea borrar la escritura")) {
            conexion_app.borrar_escritura({
                "usuario": $scope.usr_global,
                "escritura": {
                    "idescritura": idescritura
                }
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, 'La escritura fue eliminada correctamente');
                    reset_campos('myForm');
                    $scope.buscar_escrituras_x_expediente();
                } else {
                    ejecutaAlerta(2, 'Ocurrio un error: ' + data.estatus);
                }
            }, function(error) {
                alert('Ocurrió un error al eliminar la escritura: ' + error.estatus);
            });
        }
    }
    $scope.add_testimonio = function(escritura) {
        console.info(escritura);
        $rootScope.idescritura = escritura.idescritura;
        $rootScope.idexpediente = escritura.expediente.idexpediente;
        $scope.goTo("/testimonios")
    }
    $scope.obtenData = function() {
        catalogos.buscar_actos_disponibles($scope);
        $scope.buscar_escrituras_x_expediente();
    }
    $scope.inicializa_escritura = function() {
        catalogos.get_expediente_x_tramite($scope, $scope.obtenData);
        catalogos.obtener_notarios($scope);
        //catalogos.obtener_libro($scope);
    }
    $scope.cambia_notario = function(escritura) {
        conexion_app.switch_notario({
            "escritura": escritura,
            "usuario": $scope.usr_global
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizó correctamente al notario");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el notario: " + error.status);
        })
    }
    $scope.del_escritura = function(id_esc) {
        modal.show();
        conexion_app.borrar_escritura({
            "escritura": {
                "idescritura": id_esc
            },
            "usuario": $rootScope.usr_global
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se borro correctamente la escritura");
                $timeout(function() {
                    $route.reload();
                    modal.destroy();
                }, 4500);
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al borrar la escritura: " + error.status);
            modal.destroy();
        })
    }
    console.info("DATO");
    $scope.escritura_edit = function(id_esc, tipo) {
        $rootScope.edit_esc = id_esc;
        switch (tipo) {
            case 0:
                $scope.goTo("/escrituras_editar");
                break;
            case 1:
                $scope.goTo("/escrituras_elaboracion");
                break;
            case 2:
                $scope.goTo("/tachado_de_firmas");
                break;
        }
    }
    $scope.escritura_actualizar = function(escritura) {
        var msj = "Al regenerar la escritura,\n se perderán las versiones generadas anteriormente \n¿Deseas continuar?"
        if (!confirm(msj)) {
            return false;
        }
        modal.show();
        conexion_app.guardar_escritura({
            "usuario": $scope.usr_global,
            "escritura": escritura.escritura,
            "expediente": $scope.expediente,
            "actos": escritura.actos
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizó correctamente la escritura");
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al actualizar la escritura: " + error.status);
        }).$promise.finally(destroyModal);
    }
    $scope.escritura_download = function(escritura) {
        if (escritura.escritura.dsnumescritura == null) {
            ejecutaAlerta(2, "La escritura no se puede descargar, asigna el número de escritura");
            return;
        }
        var params = "idusuario=" + $scope.usr_global.idusuario + "&idsesionactual=" + $scope.usr_global.idsesionactual + "&idescritura=" + escritura.escritura.idescritura + "&idtramite=" + $rootScope.idtramite;
        var url = "http://" + contexto + "/notaria/escritura/obtieneWord?" + params;
        window.open(url);

    }
    $scope.inicializa_escritura();

    $scope.nopaso = function(esc) {
        if(esc.escritura.nopaso){
             if (!confirm("¿Realmente deseas colocar la escritura en su estado original?, tiene que ser validada por mesa de control")) return false;
             conexion_app.mesactrl_cancela_nopaso({
                 "usuario":$scope.usr_global,
                 "escritura":esc.escritura
             },function(data){
               if (data.exito) {
                    ejecutaAlerta(1, "La solicitud fue registrada correctamente, espera a que mesa de control lo apruebe");
                    $route.reload();
             } else {
                    ejecutaAlerta(2, data.estatus);
                    $route.reload();
                }
             },function(error){
                 alert("Ocurrio un error al hacer la solicitud:" + error.status);
                    $route.reload();
             }
                     );
             
        }else{
            if (!confirm("¿Realmente deseas colocar la escritura como no paso?")) return false;
            esc.escritura.nopaso = true;
            conexion_app.nopaso({
                "usuario": $scope.usr_global,
                "escritura": esc.escritura
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "La escritura se coloco en estatus no paso correctamente");
                } else {
                    ejecutaAlerta(2, data.estatus);
                    $route.reload();
                }
            }, function(error) {
                alert("Ocurrio un error al colocar la escritura en no paso:" + error.status);
                    $route.reload();
            });
        }
        
    };

}