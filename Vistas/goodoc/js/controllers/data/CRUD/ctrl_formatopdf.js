function ctrl_formatopdf($scope, $rootScope, $route, $timeout, conexion_app, conexion, catalogos,con_tipo) {

    
    $scope.onlineUrl = function(){
        var div = $("#rutawrap");
        if($scope.formatoPdf.isonline=="true"){
            div.show("slow");
        }else{
            div.hide("slow");
        }
    }
    
    catalogos.get_tipo_documento($scope);
    catalogos.get_suboperaciones($scope);
    $scope.formatoPdf = {};
    $scope.formatoPdf.detalleList = [];
    if ($rootScope.editDocPdf) {
        $scope.formatoPdf = $rootScope.editDocPdf;
        $scope.isEditForm = true;
        delete $rootScope.editDocPdf;
        
        $timeout(function(){
            $scope.formatoPdf.ispagorequire +='';
            
            for (var i = 0; i < $scope.tipos_alertas.length; i++) {
                
                if ($scope.tipos_alertas[i].tipoobjeto == $scope.formatoPdf.tipoalerta) {
                    
                    $scope.formatoPdf.tipoalerta = $scope.tipos_alertas[i].tipoobjeto;
                    break;
                }
            }
            
        },1000)
        
    } else {
        $scope.isEditForm = false;
    }
    $scope.variables_forms = [];
    function parsea_variables() {
        console.info("MI LISTA", $scope.lista);
        $scope.lista.variableList = _.uniq($scope.lista.variableList, function (item) {
            return item.dsnombre
        });
        $scope.lista.varFormDinamicos = _.uniq($scope.lista.varFormDinamicos, function (item) {
            return item.nombre
        });
        $scope.lista.componentesSubformulario = _.uniq($scope.lista.componentesSubformulario, function (item) {
            return item.nombre
        });
        $scope.lista.variableCompareciente = _.uniq($scope.lista.variableCompareciente, function (item) {
            return item.dsnombre
        });
        _.each($scope.lista.variableList, function (variable, i) {
            $scope.variables_forms.push({"desc": "${var:" + variable.dsnombre + "}"})
        });
        _.each($scope.lista.varFormDinamicos, function (variable, i) {
            $scope.variables_forms.push({"desc": "${frm:" + variable.nombre + "}"})
        });
        _.each($scope.lista.componentesSubformulario, function (variable, i) {
            $scope.variables_forms.push({"desc": "${frm.tbl:" + variable.nombre + "}"})
        })
        _.each($scope.lista.variableCompareciente, function (variable, i) {
            $scope.variables_forms.push({"desc": "${cte:" + variable.dsnombre + "}"})
        })
    }
    $scope.refreshListVars(parsea_variables);
    $scope.aplica_todos_data = function (aplica) {
        if (aplica) {
            $scope.sel_sub_op = {};
        } else {
            $scope.aplica_todos = false;
        }
    }
    $scope.add_detalle = function () {
        if ($scope.aplica_todos) {
            $scope.sel_sub_op = null;
        }
        var detalle = {"dscampo": $scope.sel_campo, "dsvariable": $("#variable_value").val(), "suboperacion": $scope.sel_sub_op, "aplicaTodasSuboperaciones": $scope.aplica_todos};
        $scope.formatoPdf.detalleList.push(detalle);
        $scope.sel_campo = "";
        $("#variable_value").val("");
        $scope.sel_variable = 0;
    }
    $scope.delete_detalle = function (obj) {
        if (!confirm("¿Realmente desea borrar este dato?"))
            return;
        var index = $scope.formatoPdf.detalleList.indexOf(obj);
        if (index > -1) {
            $scope.formatoPdf.detalleList.splice(index, 1)
        }
        $scope.formatoPdf.detalleList;
    }
    $scope.guarda_docPdf = function () {
        conexion_app.guarda_docPdf({"usuario": $scope.usr_global, "formatoPdf": $scope.formatoPdf}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se guardó correctamente el documento");
                $timeout(function () {
                    force_redirect("index_adm.html#/listar_formato_pdf")
                }, 1000);
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus)
            }
        }, function (error) {
            alert("Algo malo paso al guardar el documento: " + error.status)
        });
    }
    $scope.actualiza_docPdf = function () {
        conexion_app.actualiza_docPdf({"usuario": $scope.usr_global, "formatoPdf": $scope.formatoPdf}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizó correctamente el documento");
                $timeout(function () {
                    force_redirect("index_adm.html#/listar_formato_pdf")
                }, 1000);
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al actualizar el documento: " + error.status);
        });
    }

        $timeout(function(){
            $("#isonlineselect").val($scope.formatoPdf.isonline.toString());
             $scope.onlineUrl();
        },500)
       

}