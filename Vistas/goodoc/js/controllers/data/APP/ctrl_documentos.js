function ctrl_documentos($scope, $timeout, $rootScope, $http, $route, conexion_app, conexion, catalogos, funcionesutiles) {
    catalogos.get_expediente_x_tramite($scope, function() {
        
        catalogos.get_documentos_originales($scope,function(){
                    catalogos.get_documentos_previos($scope,function(){
                $scope.getDocPosteriores();
                });
        });
    })
    
    $scope.getDocPosteriores = function(){
        catalogos.get_documentos_posteriores($scope,function(){
                console.info($scope.acto);
                $timeout(function(){
                    if($scope.acto && $scope.acto.hasdim ==false){
                        $scope.acto.hasdim = "false";
                    }
                    if($scope.acto && $scope.acto.hasanexo5 ==false){
                        $scope.acto.hasanexo5 = "false";
                    }
                },1000);
                
                catalogos.obtener_notarios($scope);
        });
     
       
     
    }
    
    $scope.activa_formulario = function(id) {
        $scope.iddocumentoEnvio = id;
        $("#formulario_subir").toggle("slow");
    }
    $scope.verArchivo = function(iddoc, tipo) {
        console.info("idDoc:" + iddoc, "tipo:" + tipo);
        var params = "idusuario=" + $scope.usr_global.idusuario + "&idsesionactual=" + $scope.usr_global.idsesionactual + "&tipo=" + tipo + "&id=" + iddoc;
        var url = "http://" + contexto + "/notaria/gestionDocumentos/mostrarDocumento?" + params;
        window.open(url,"documentos");
    }
    $scope.ejecuta_proceso = function(id_doc) {
        modal.show();
        conexion_app.variables_x_valor({
            "usuario": $scope.usr_global,
            "idactodoc": id_doc
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se ejecutó correctamente el proceso");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al ejecutar el proceso variables por valor: " + error.status);
        }).$promise.finally(destroyModal);
    }
    $scope.del_documento = function(id_doc) {
        if (confirm("¿Realmente deseas eliminar este documento?")) {
            conexion_app.eliminar_documento({
                "usuario": $scope.usr_global,
                "idactodoc": id_doc
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "El documento se eliminó correctamente");
                    $route.reload();
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
            }, function(error) {
                alert("Algo malo paso al eliminar el documento: " + error.status);
            });
        }
    }
    $scope.eliminar_original = function(original) {
        if (confirm("¿Realmente deseas eliminar este documento?")) {
            conexion_app.elimina_original({
                "usuario": $scope.usr_global,
                "original": original,
                "idExpediente": $scope.expediente.idexpediente,
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "El documento se eliminó correctamente");
                    $route.reload();
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
            }, function(error) {
                alert("Algo malo paso al eliminar el documento: " + error.status);
            });
        }
    }
    $scope.is_entregado = function(id_doc) {
        modal.show();
        conexion_app.documento_entregado({
            "usuario": $scope.usr_global,
            "idactodoc": id_doc
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se actualizó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
            modal.destroy();
        })
    }
    $scope.is_aprobado = function(id_doc) {
        modal.show();
        conexion_app.documento_aprobado({
            "usuario": $scope.usr_global,
            "idactodoc": id_doc
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se actualizó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
            modal.destroy();
        })
    }
    $scope.popLateral = function(url_cargar, columnas, status) {
        funcionesutiles.popLateral(url_cargar, columnas, status);
    }
    $scope.subir_doc = function(id_doc, tipoDoc,desde) {
        $scope.popLateral('subir_documento', 2, true);
        $rootScope.iddocumento = id_doc;
        $rootScope.tipoDoc = tipoDoc;
        $rootScope.archivoDesde = desde;
    }
    $scope.set_notario_val = function(yo) {
        $scope.sel_notario = yo.sel_notario;
    }
    $scope.actualiza_doc_notario = function(iddoc, notario, toall) {
        modal.show();
        conexion_app.set_doc_notario({
            "usuario": $scope.usr_global,
            "datoActoDoc": {
                "idactodoc": iddoc,
                "notario": notario,
                "switchNotario": true
            }
        }, function(data) {
            if (data.exito) {
                if (!toall) {
                    ejecutaAlerta(1, "Se actualizó correctamente el documento");
                    $route.reload();
                }
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
        })
    }
    $scope.set_notario = function(documento, all) {
        var notario = documento.notario;
        if (!confirm("¿Deseas asignar el mismo notario a todos los documentos?")) {
            $scope.actualiza_doc_notario(documento.idactodoc, notario, false);
        } else {
            $scope.asignar_to_all_notario(all, notario);
        }
    }
    $scope.asignar_to_all_notario = function(all, notario) {
        _.each(all, function(doc, index) {
            $scope.actualiza_doc_notario(doc.idactodoc, notario, true);
        })
        $timeout(function() {
            ejecutaAlerta(1, "Se actualizarón correctamente los documentos");
            $route.reload()
        }, 2000)
    }
    $scope.lanza_detalle = function(doc) {
        Avgrund.show("#changeKey");
        $("#changeKey").fadeIn("slow");
        $scope.tmpDoc = doc;
    }
    
    /*$scope.lanza_comentarios_doc = function(doc){
        Avgrund.show("#comments_wrapp");
        $("#comments_wrapp").fadeIn("slow");
        $scope.docComment = doc;
    }*/
    
    
    
    $scope.lanza_detalle_valuador = function(doc) {
        Avgrund.show("#valuador_content");
        $("#valuador_content").fadeIn("slow");
        $scope.tmpDoc = doc;
    }
    $scope.lanza_set_valuador = function() {
        $scope.tmpDoc.valuador = $scope.sel_valuador;
        conexion_app.set_valuador({
            "usuario": $scope.usr_global,
            "datoActoDoc": $scope.tmpDoc,
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se asigno correctamente el valuador");
                Avgrund.hide();
                $("#valuador_content").fadeOut("slow");
                $("#changeKey").fadeOut("slow");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al asignar el valuador: " + error.status);
        })
    }
    $scope.lanza_set_gestor = function(documento, todos) {
        documento.gestor = $scope.sel_gestor
        conexion_app.set_gestor({
            "usuario": $scope.usr_global,
            "datoActoDoc": documento,
        }, function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se asigno correctamente el gestor");
                if (!todos) {
                    $timeout(function() {
                        Avgrund.hide();
                        $("#valuador_content").fadeOut("slow");
                        $("#changeKey").fadeOut("slow");
                    }, 1500)
                    $timeout(function() {
                        $route.reload();
                    }, 2500)
                }
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function(error) {
            alert("Algo malo paso al asignar el gestor: " + error.status);
        });
    }
    $scope.asignarGestor = function(doc, i) {
        if (confirm("¿Asignar este gestor a todos los documentos?")) {
            _.each($scope.previos, function(doc) {
                $scope.lanza_set_gestor(doc, true);
            })
            _.each($scope.posteriores, function(doc) {
                $scope.lanza_set_gestor(doc, true);
            })
            $timeout(function() {
                Avgrund.hide();
                $("#valuador_content").fadeOut("slow");
                $("#changeKey").fadeOut("slow");
            }, 1500)
            $timeout(function() {
                ejecutaAlerta(1, "Se actualizarón correctamente todos los documentos");
                $route.reload()
            }, 2000)
        } else {
            $scope.lanza_set_gestor($scope.tmpDoc, false);
        }
    }
    
    
    
    //MANEJO DE DOCUMENTOS ORIGINALES
    $scope.docOr_add = function(){
        modal.show();
        conexion_app.documento_guarda_original({
            documentoOriginal:{
                "dsnombre":$scope.docOr_new_name,
                "acto":{"idacto":$rootScope.idacto},
            },
            "usuario":$scope.usr_global

        },function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se guardó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al guardar el documento: " + error.status);
            modal.destroy();
        })
    };
    $scope.docOr_delete = function(obj){
        if(!confirm("Realmente desea eliminar este elemento")) return;
         modal.show();
        conexion_app.elimina_original({
            documentoOriginal:obj,
            "usuario":$scope.usr_global

        },function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se eliminó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al eliminar el documento: " + error.status);
            modal.destroy();
        })
    };
    $scope.docOr_edit = function(obj){
        if(!confirm("Realmente desea actualizar este elemento")) return;
         modal.show();
        conexion_app.documento_actualiza_original({
            documentoOriginal:obj,
            "usuario":$scope.usr_global

        },function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se actualizó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
            modal.destroy();
        })    
    };
    
    $scope.docOr_isEntregado = function(obj){
        obj.isentregado = (obj.isentregado)?false:true;
         modal.show();
        conexion_app.documento_actualiza_original({
            documentoOriginal:obj,
            "usuario":$scope.usr_global

        },function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se actualizó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
            modal.destroy();
        })    
    };
    
    $scope.docOr_isValidado = function(obj){
        obj.isvalidado = (obj.isvalidado)?false:true;
         modal.show();
        conexion_app.documento_actualiza_original({
            documentoOriginal:obj,
            "usuario":$scope.usr_global

        },function(data) {
            if (data.exito) {
                ejecutaAlerta(1, "El documento se actualizó correctamente");
                $route.reload();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                modal.destroy();
            }
        }, function(error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
            modal.destroy();
        })    
    }; 
    
    
    $scope.actualizaDimAnexo5 = function(){
        $scope.acto.idacto = $rootScope.idacto;
        if($scope.acto.hasdim=="null"){
        $scope.acto.hasdim = null;
        }  
        if($scope.acto.hasanexo5=="null"){
        $scope.acto.hasanexo5 = null;
        }
                modal.show();

        conexion_app.acto_NoDimAnexo5({
            "usuario":$scope.usr_global,
            "acto":$scope.acto
        },function(data){
            if(data.exito){
                ejecutaAlerta(1,data.estatus);
            }else{
                ejecutaAlerta(2,data.estatus);
            }
        },function(error){
            alert("Algo malo paso al actualizar el acto"+error.status);
        }).$promise.finally(destroyModal);
    }
    
    $scope.solicita_control = function(documento){
        if(documento.issolicitado){
           alert("El documento ya se encuentra pendiente de aprobación en caja");
           return;
       }
       if(!confirm("¿Deseas solicitar confirmación de pago de este documento?")) return;
       
           
       conexion_app.mesactrl_add(
               {
                   "usuario":$scope.usr_global,
                   "documento":{
                       "actodocumento":{"idactodoc":documento.idactodoc},
                       "ispagorequire":documento.ispagorequire
                   }
                   
                },
            function(data){
                if(data.exito){
                    ejecutaAlerta(1,"Se solicitó el documento correctamente a mesa de control");
                    $route.reload();
                }else{
                    ejecutaAlerta(2,"Ocurrió uno error al solicitar el documento posterior"+data.estatus);
                }
            },
            function(error){
                    alert("Algo malo paso al solicitar el documento posterior:"+error.status);
            });
       
        
    }
   
    $scope.verOnline= function(doc){
        console.info(doc);
        //window.open(doc.rutaArchivo,"documentos");
        window.open("/tarjeton/documento.html","documentos");
    }
    
}