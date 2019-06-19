function ctrl_list_comparecientes($scope, $route, $rootScope, conexion_app, conexion, catalogos, $timeout) {
    var msjnopaso = 'No es posible actualizar ni crear nuevos comparecientes por que la escritura se encuentra como "NO PASO"';
    
    $scope.hasprev = false;
    
    catalogos.get_expediente_x_tramite($scope, actualizaListaComparecientes);
    catalogos.get_representantes($scope);
    
    catalogos.get_tipos_personas($scope, function () {
        $scope.tipopersona = $scope.tipos_personas[0];
    });
    
    
    
    $rootScope.isActualizacion = false;
    if ($rootScope.edit_rep) {
        delete $rootScope.edit_rep;
    }
    ;
    if ($rootScope.conyug_edit) {
        delete $rootScope.comp_cony_edit;
    }
    ;
    delete $rootScope.sel_com_to_add;
    delete $rootScope.tipo_reg_com;
    delete $rootScope.actualizaComp;

    $scope.add_to_com_new = function (com, tipo) {
         if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        } 
        $rootScope.sel_com_to_add = com;
        $rootScope.tipo_reg_com = tipo;
        if (tipo == 'c') {
            $scope.goTo("/comparecientes_conyuge");
        } else if (tipo == 'h') {
            $scope.goTo("/comparecientes_hijo");
        } else {
            $scope.goTo("/comparecientes_registrar");
        }
    }



    $scope.del_compareciente = function (com) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
            
        if (com.representantes) {
            ejecutaAlerta(2, "No se pueden eliminar comparecientes con representantes asociados");
            return;
        }
        if (com.conyuge && (com.conyuge.conyugeActual || com.conyuge.conyugeCompra)) {
            ejecutaAlerta(2, "No se pueden eliminar comparecientes con conyuges asociados");
            return;
        }
        if (!confirm("¿Esta seguro que desea eliminar este elemento de la lista?"))
            return;
        conexion_app.elimina_compareciente({"usuario": $scope.usr_global, "compareciente": com.compareciente}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se eliminó correctamente el otorgante");
                $timeout(function () {
                    $route.reload();
                }, 1500)
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al eliminar el compareciente: " + error.status);
        })
    }
    
    $scope.ejecutaTachadoDeFirmas =function(conmsj){
        var msj = (conmsj)?"Todas las ":"Revisar que todas las ";
        if(!confirm(msj+"firmas se encuentren tachadas,¿Deseas completar esta tarea?")) return;
        {
            $scope.ejecutaTarea($scope.idtarea,false,function(){
                $timeout(function(){
                  $scope.goTo("/tareasbo");
                },2000)
                ;
            });
        }
        }
    
    function actualizaListaComparecientes() {
        modal.show();
        var acto = {"idacto": $rootScope.idacto};
        catalogos.get_comparecientes_completo($scope, acto,
                function () {
                    if($scope.escritura!=null){
                        $scope.nopaso = $scope.escritura.nopaso;
                        $scope.idtarea = $scope.escritura.idtachafirma;
                        if($scope.escritura.dsnumescritura!=null && $scope.idtarea!=null){
                            $scope.check_firmascompletas($scope.escritura);
                        }
                    }
                    $scope.comparecientes_conyuge = $scope.comparecientes;
                    if ($scope.comparecientes_conyuge.length > 0) {
                        var subopt = $scope.comparecientes_conyuge[0].compareciente.acto.suboperacion;
                        console.info("expediente::", $scope.expediente);
                        conexion_app.doc_listar_posteriores_subOp({
                            "usuario": $scope.usr_global,
                            "suboperacion": subopt,
                            "localidad": $scope.expediente.tramite.locacion
                        },
                        function (data) {
                            if (data.exito) {
                                $scope.listaPosteriores = data.docSubopList;
                                $scope.posteriores_preventivo_validacion_previa();
                            } else {
                                ejecutaAlerta(2, "Ocurrió un error al obtener los documentos posteriores");
                            }
                        },
                                function (error) {
                                    ejecutaAlerta(2, "Ocurrió un error al obtener los documentos posteriores");

                                });
                    }
                })

    }

    $scope.posteriores_preventivo_validacion_previa = function(){
        conexion_app.obtener_posteriores_expediente_xnombre(
                {"usuario":$scope.usr_global,"idExpediente":$scope.expediente.idexpediente,"idacto":$rootScope.idacto,"nombreDoc":"Preventivo"},
                function(data){
                    if(data.exito){
                        if(data.listaPosteriores && data.listaPosteriores.length > 0 && data.listaPosteriores[0].mesacontrol == null){   
                            $scope.hasprev = true;
                        }         
                    }
                    else{
                        ejecutaAlerta(2,"Ocurrió un error al obtener la validación del preventivo: "+data.estatus);
                    }
                },
                function(error){
                    alert("Algo malo paso al obtener la validación del preventivo:"+error.status);
                }
                )
    }

    function lanzaListaComparecientes(compData) {
        modal.show();
        conexion_app.actualiza_representados_compareciente({"usuario": $scope.usr_global, "datoCompareciente": compData}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizó correctamente la lista de representados");
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
            actualizaListaComparecientes();
        }, function (error) {
            alert("Algo malo ocurrio al actualizar la lista de representados: " + error.status);
        }).$promise.finally(destroyModal);
    }
    $scope.delete_com_lista = function (compData, i) {
        if (confirm("¿Esta seguro de eliminar el elemento de la lista?")) {
            compData.representante = compData.representantes[i];
            compData.representantes.splice(i, 1);
            lanzaListaComparecientes(compData);
        }
    }
    $scope.add_com_lista = function (compData, i) {
        modal.show();
        var sel = $("#select" + i).val();
        conexion_app.set_representado({"usuario": $scope.usr_global, "compareciente": compData.compareciente, "representante": $scope.representantes[sel]}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizó correctamente la lista de representantes");
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
            actualizaListaComparecientes();
        }, function (error) {
            alert("Algo malo ocurrio al actualizar la lista de representantes: " + error.status);
        }).$promise.finally(destroyModal);
    }
    $scope.show_busc = true;
    $scope.show_buscar_otorgante = function () {
        $scope.show_busc = $scope.show_busc ? false : true;
        var msj = $scope.show_busc ? "Buscar otorgantes" : "Cerrar buscador";
        $("#btn_buscador_trigger").text(msj);
        $("#buscador_wrapper").slideToggle("slow");
        catalogos.get_comparecientes_x_expediente($scope);
    }
    $scope.guarda_select_compareciente = function (compareciente) {
        modal.show();
        if (confirm("¿Realmente desea guardar al compareciente en este acto?")) {
            compareciente.acto = {"idacto": $rootScope.idacto};
            conexion_app.guardar_compareciente({"usuario": $rootScope.usr_global, "compareciente": compareciente, }, function (data) {
                if (data.exito) {
                    ejecutaAlerta(1, 'Se registró correctamente el compareciente');
                    $route.reload();
                } else {
                    ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                }
            }, function (error) {
                alert("Algo malo ocurrió al guardar al compareciente: " + error.status);
            }).$promise.finally(destroyModal);
        }
    }
    $scope.show_select_more_comp = function (i) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
        var sd = $("#select_add_comp" + i);
        var cd = $(".select_comp_more");
        if (sd.is(":visible")) {
            sd.hide("slow");
            return;
        }
        cd.hide("slow")
        sd.show("slow");
    }
    $scope.show_select_conyuge = function (i) {
        var sd = $("#select_add_conyuge" + i);
        var cd = $(".select_comp_more");
        if (sd.is(":visible")) {
            sd.hide("slow");
            return;
        }
        cd.hide("slow")
        sd.show("slow");
    }
    $scope.add_conyuge = function (com, index) {
        if (com.conyuge) {
            if (!confirm("¿Este compareciente ya tiene asignado un conyuge deseas modificarlo?"))
            {
                return;
            }
        }
        modal.show();
        var conyuge = $scope.comparecientes_conyuge[$("#select_conyuge" + index).val()];
        conexion_app.add_conyuge({"usuario": $scope.usr_global, "compConyuge": {"sujeto": {"idcompareciente": com.compareciente.idcompareciente}, "conyuge": {"idcompareciente": conyuge.compareciente.idcompareciente}}}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizó correctamente la lista de comparecientes");
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
            actualizaListaComparecientes();
        }, function (error) {
            alert("Algo malo ocurrio agregar al cónyuge: " + error.status);
        }).$promise.finally(destroyModal);
    }

    $scope.delete_conyuge = function (com, tipo) {
        if (confirm("¿Realmente deseas eliminar al cónyuge de este compareciente?")) {
            modal.show();

           


            conexion_app.delete_conyuge({"usuario": $scope.usr_global, "compareciente": com},
            function (data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se eliminó correctamente al cónyuge del compareciente");
                }
                else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
                actualizaListaComparecientes();
            },
                    function (error) {
                        alert("Algo malo ocurrio al eliminar al cónyuge: " + error.status);
                    }).$promise.finally(destroyModal);

        }
    }

    $scope.actualiza_compareciente = function (idComp) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
        $rootScope.actualizaComp = idComp;
        $scope.goTo('/comparecientes_registrar');
    }
    $scope.edit_representante = function (rep) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
        $rootScope.actualizaComp = rep.idcompareciente;
        $rootScope.tipo_reg_com = "r";
        $rootScope.edit_rep = true;
        $scope.goTo("/comparecientes_registrar");
    }

    $scope.edit_conyuge = function (com) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
        $rootScope.comp_cony_edit = com.conyuge;
        $rootScope.sel_com_to_add = com.compareciente;
        console.info("Compareciente::", com);
        $scope.goTo("/comparecientes_conyuge");

    }
    $scope.goto_ri = function (com) {
        $rootScope.tmp_comp = com;
        force_redirect('index.html#/comparecientes_registrar_ri');
    }
    $scope.edit_hijo = function (com, hijo) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
        $rootScope.sel_com_to_add = com.compareciente;
        $rootScope.actualizaComp = hijo;
        $rootScope.edit_hijo = true;
        $scope.goTo("/comparecientes_hijo");
    }
    $scope.delete_hijo = function (com, hijo) {
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso)
            return;
        }
        if (confirm("¿Esta seguro de eliminar el elemento de la lista?")) {
            conexion_app.eliminar_hijo({
                "usuario": $scope.usr_global,
                "compareciente": com.compareciente,
                "hijo": hijo
            }, function (data) {
                if (data.exito) {
                    ejecutaAlerta(1, "Se eliminó correctamente el elemento");
                    $route.reload();
                } else {
                    ejecutaAlerta(2, "Ocurrió un error al elimiminar el elemento" + data.estatus);
                }
            }, function (error) {
                alert("Algo malo ocurrio al eliminar el elemento" + error.status);
            })
        }
    }
    
    $scope.deleteTmp = function(){
                delete $scope.tmpcomp;
                delete $scope.tmptipo;
    }
    
    $scope.lanzaServicioFirma = function(preventivo){
        
        if($scope.nopaso){
            ejecutaAlerta(2,msjnopaso);
            return;
        }
        if(!$scope.tmpcomp.acto){
            $scope.tmpcomp.acto ={
                idacto:$rootScope.idacto
            }
        }
        
        if (!$scope.tmptipo) {
            conexion_app.tacha_firma({
                "usuario": $scope.usr_global,
                "compareciente": $scope.tmpcomp,
                "expediente":$scope.expediente,
                "solicitaPreventivo":preventivo
            }, function (data) {
                 $scope.deleteTmp();
                if (data.exito) {
                    ejecutaAlerta(1, "Se registró correctamente la firma");
                    
                } else {
                    ejecutaAlerta(2, "Ocurrió un error al guardar la firma: " + data.estatus)
                }
                $scope.cerrarModal();
                
            }, function (error) {
                alert("Algo malo paso al ejecutar el proceso de la firma: " + error.status);
                 $scope.deleteTmp();
                 $scope.cerrarModal();

            })
        } else {

            if (!confirm("¿Realmente deseas borrar el tachado de la firma?"))
                return;
            modal.show();
            conexion_app.tacha_firma_borrar({
                "usuario": $scope.usr_global,
                "firma": $scope.tmpcomp.firma
            }, function (data) {
                 $scope.deleteTmp();
                if (data.exito) {
                    ejecutaAlerta(1, "Se eliminó correctamente la firma");
                    $route.reload();
                   
                } else {
                    
                    ejecutaAlerta(2, "Ocurrió un error al eliminar la firma: " + data.estatus)
                }
            }, function (error) {
                $scope.deleteTmp();
                alert("Algo malo paso al eliminar la firma:" + error.status)
            }).$promise.finally(destroyModal);

        }
    }
    

    $scope.tachaFirma = function (comp, tipo) {
        $scope.tmpcomp = comp;
        $scope.tmptipo = tipo;
        if (!$scope.tmptipo && $scope.hasprev) {
            $scope.validaPreventivo();
        }else{
            $scope.lanzaServicioFirma(false);
        }
    }



        $scope.validaPosteriores = function () {
            Avgrund.show("#validaPosteriores");
            $("#validaPosteriores").fadeIn("slow");
            $scope.msj_posteriores = true;
        }
        
        $scope.validaPreventivo = function () {
            Avgrund.show("#validaPreventivo");
            $("#validaPreventivo").fadeIn("slow");
        }



    $scope.cerrarModal = function () {
        Avgrund.hide(function () {
            $route.reload();
        });
        $("#validaPosteriores").fadeOut("slow");
        $("#validaPreventivo").fadeOut("slow");
    }

    $scope.posteriores = ["DIM", "Art.27", "Aviso de otorgamiento", "ANEXO 5"]
    $scope.selection = ["Aviso de otorgamiento"]


    $scope.setPosteriores = function () {

        $scope.msj_posteriores = true;
        conexion_app.doc_add_posteriores_multi(
                {"actoDocumentos": $scope.posteriores_data, "usuario": $scope.usr_global}
        , function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se registrarón correctamente los documentos solicitados");
            } else {
                ejecutaAlerta(2, "Ocurrió un error al registrar los documentos posteriores:" + data.estatus);
            }
        }, function (err) {
            alert("Algo malo paso al registrar los documentos posteriores:" + err.status);
        }).$promise.finally(destroyModal);

        $(".cerrarModal").trigger("click");
    }

    $scope.isChecked = function (id) {
        var match = false;
        for (var i = 0; i < $scope.posteriores_data.length; i++) {
            if ($scope.posteriores_data[i].identificador == id) {
                match = true;
            }
        }
        return match;
    };


    $scope.posteriores_data = [];

    $scope.addPost = function (bool, post) {
        console.info("BOL:" + bool, "POST;;;" + post);
        var item = {"acto": {"idacto": $rootScope.idacto},
            "formatoPdf": post}
        if (bool) {
            // add item
            $scope.posteriores_data.push(item);
        } else {
            // remove item
            for (var i = 0; i < $scope.posteriores_data.length; i++) {
                if ($scope.posteriores_data[i].formatoPdf.identificador == post.identificador) {
                    $scope.posteriores_data.splice(i, 1);
                    break;
                }
            }
        }
        console.info("DATOS:::", $scope.posteriores_data)
    }


    $scope.check_firmascompletas = function(escritura){
        conexion_app.tacha_firma_completas({
            "escritura":escritura,
            "usuario":$scope.usr_global
        },
        function(data){
            if(data.exito){
                $scope.firmascompletas = data.completas;
                if($scope.firmascompletas && !$scope.nopaso){
                    $scope.ejecutaTachadoDeFirmas(true);
                }
                
            }else{
                ejecutaAlerta("Ocurrió un error al revisar las firmas de los comparecientes: "+data.estatus);
            }
        },
        
        function(error){
            alert("Algo malo paso al validar las firmas de los comparecientes: ",error.status);
        });
        
    }

    $scope.choosePosteriores = function () {
        $scope.msj_posteriores = false;
        console.info($scope.msj_posteriores, "::");

    }

}