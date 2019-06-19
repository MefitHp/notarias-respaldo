function ctrl_antecedentes_detalle($scope, $timeout, $compile, $rootScope, conexion_app, conexion, catalogos) {
    var tipoFrm = $rootScope.tipo_edit_frm;
    delete $rootScope.tipo_edit_frm;
    $scope.redirect_to_frm = function () {
        switch (tipoFrm) {
            case"O":
                force_redirect('index.html#/antecedentes_operativos');
                break;
            case"A":
                force_redirect('index.html#/antecedentes_documentos');
                break;
            default:
                force_redirect('index.html#/antecedentes');
                break;
            }
    }
    var compile = "";
    catalogos.get_expediente_x_tramite($scope);
    $scope.listaValoresFormularios = [];
    $scope.listaValoresFormularios[0] = {};
    $scope.listaValSubFormulario = [];
    $scope.wrap_formgeneral_id = 0;
    $scope.frm_wrap_select = "frm_dinamic";
    $scope.wrap_subform;
    $scope.issubform = false;
    $scope.posicioncomponente;
    $scope.listaValFormulario = [];
    $scope.listaInputs = [];
    $scope.valoresSubForm = [];
    $scope.issubformEdit = false;
    var tieneSubFormData = false;
    $scope.posSubformEdit = "";
    $scope.indexDataEdit = "";
    $scope.idFormEdit = "";
    
    
    function escapaCharacter(str){
        if(str==null || typeof str !="string") return;
        str = str.replace(/"/g, '&#8221;')
        return str;
    }
    
    function crea_envolvente(issubform, nombresubform) {
        var apertura = (!issubform) ? "" : "<div><div id='" + nombresubform + "'>";
        var cierre = (!issubform) ? '</div></div>' : '</div></div></div></div>';
        var wrap_componente = apertura;
        wrap_componente += '<div class="wra_form_data" id="wraper_form_dinamico' + $scope.wrap_formgeneral_id + '" style="overflow: visible;padding-bottom: 30px;">';
        wrap_componente += '<div class="body_form_data" id="' + $scope.frm_wrap_select + $scope.wrap_formgeneral_id + '" style="overflow: visible">';
        wrap_componente += cierre;
        $("#wraper_general_form_dinamico").append(wrap_componente);
    }
    function quita_wrapp_vacios() {
        $(".wra_form_data").each(function () {
            if ($(this).find('.body_form_data div').size() < 1) {
                $(this).remove();
            }
        })
    }
    if (!$rootScope.edit_formulario) {
        $scope.goTo("/antecedentes");
    } else {
        $scope.formulario = $rootScope.edit_formulario;
        delete $rootScope.edit_formulario;
        conexion_app.obtener_config_formulario({"usuario": $scope.usr_global, "conformulario": $scope.formulario, "acto": {"idacto": $rootScope.idacto}}, function (data) {
            if (data.exito) {
                $scope.frm_edit = data.conformulario;
                $scope.sortByOrder(function () {
                    crea_envolvente(false, "");
                    $scope.render_form($scope.frm_edit.listaComponentes, false, quita_wrapp_vacios);
                    for (var i = 0; $scope.frm_edit.listaSubFormularios.length > i; i++) {
                        $scope.valoresSubForm.push(new Array());
                    }
                    conexion_app.obtener_valores_formulario({"usuario": $scope.usr_global, "acto": {"idacto": $rootScope.idacto}, "conformulario": $scope.formulario, }, function (data) {
                        if (data.exito && data.listaValoresFormularios[0] != null) {
                            $("#btn_editar_general_form").fadeIn("slow");
                            $scope.listaComponentesDatos = data.listaValoresFormularios[0].listaValFormulario;
                            if (data.listaValoresFormularios[0].listaValSubFormulario == null) {
                                data.listaValoresFormularios[0].listaValSubFormulario = [];
                            }
                            var miDato = {};
                            var datoEnvio = {};
                            var registro;
                            var posicion = 0;
                            var datosIterados = data.listaValoresFormularios[0].listaValSubFormulario;
                            var tmpArrayDatosEnvio = [];
                            for (var contador = 0; datosIterados.length > contador; ) {
                                registro = 0;
                                console.info("DATOS ITERADOS", datosIterados)
                                for (; datosIterados.length > contador; contador++) {
                                    if (registro != datosIterados[contador].registro) {
                                        tmpArrayDatosEnvio = _.sortBy(tmpArrayDatosEnvio, function (comp) {
                                            return comp.componente.inorden;
                                        })
                                        miDato.datoEnvio = tmpArrayDatosEnvio;
                                        $scope.valoresSubForm[datosIterados[contador].componente.inposicion].push(miDato);
                                        miDato = {};
                                        tmpArrayDatosEnvio = [];
                                    }
                                    miDato[datosIterados[contador].componente.dsnombrevariable] = datosIterados[contador].valorcadena;
                                    datoEnvio.componente = datosIterados[contador].componente;
                                    datoEnvio.registro = datosIterados[contador].registro;
                                    datoEnvio.valorcadena = datosIterados[contador].valorcadena;
                                    tmpArrayDatosEnvio.push(datoEnvio);
                                    datoEnvio = {};
                                    if (datosIterados.length > (contador + 1) && datosIterados[contador + 1].componente.inposicion != posicion) {
                                        tmpArrayDatosEnvio = _.sortBy(tmpArrayDatosEnvio, function (comp) {
                                            return comp.componente.inorden;
                                        })
                                        miDato.datoEnvio = tmpArrayDatosEnvio;
                                        $scope.valoresSubForm[datosIterados[contador].componente.inposicion].push(miDato);
                                        miDato = {};
                                        tmpArrayDatosEnvio = [];
                                        contador++;
                                        break;
                                    }
                                    registro = datosIterados[contador].registro;
                                }
                                if (datosIterados.length > contador) {
                                    posicion = datosIterados[contador].componente.inposicion;
                                }
                                if (datosIterados.length <= contador) {
                                    tmpArrayDatosEnvio = _.sortBy(tmpArrayDatosEnvio, function (comp) {
                                        return comp.componente.inorden
                                    })
                                    miDato.datoEnvio = tmpArrayDatosEnvio;
                                    $scope.valoresSubForm[datosIterados[(datosIterados.length - 1)].componente.inposicion].push(miDato);
                                    miDato = {};
                                    tmpArrayDatosEnvio = [];
                                }
                            }
                            $scope.idformulario = data.listaValoresFormularios[0].idformulario;
                            _.each($scope.listaComponentesDatos, function (dato, indice) {
                                $("#" + dato.componente.dsnombrevariable).val(dato.valorcadena);
                            })
                        }
                        else {
                            $("#btn_guardar_general_form").fadeIn("slow");
                        }
                    }, function (error) {
                    });
                });
            }
            else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al obtener los componentes:" + error.status)
        });
    }
    function crea_form(componente, posicion, cantidad_componentes) {
        var valores = escapaCharacter(componente.dslistavalores);
        $scope.issubform = false
        if (!componente.isparasubform) {
            $scope.listaValFormulario.push({"componente": {"idcomponente": componente.idcomponente}});
            $scope.listaInputs.push(componente.dsnombrevariable);
        }
        var expresion = "";
        if (componente.expresion != null && componente.expresion.dsexpresion && componente.expresion.dsexpresion != null) {
            expresion = getExpresionParser(componente.expresion.dsexpresion);
        }
        console.info("EXPRESION::", expresion);
        switch (componente.tipocomponente.dscodigo) {
            case"text":
                compile = '<com-texto idcomponente="' + componente.idcomponente + '"  name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" maxima="' + componente.longitudmaxima + '" regex="' + componente.dsexpvalidacion + '" ayuda="' + componente.dsayuda + '" isrequerido="' + componente.isrequerido + '"></com-texto>';
                break;
            case"datetime":
                compile = '<com-fecha idcomponente="' + componente.idcomponente + '" name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" ayuda="' + componente.dsayuda + '" isrequerido="' + componente.isrequerido + '" posbk="820"></com-fecha>'
                break;
            case"singlechk":
                compile = '<com-sino idcomponente="' + componente.idcomponente + '" totalcmp="' + cantidad_componentes + '" =  posicion="' + posicion + '" expresion = "' + expresion + '" name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" ayuda="' + componente.dsayuda + '"  anchoselect="850"></com-sino>'
                break;
            case"singlechkn":
                compile = '<com-nosi idcomponente="' + componente.idcomponente + '" totalcmp="' + cantidad_componentes + '" =  posicion="' + posicion + '" expresion = "' + expresion + '" name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" ayuda="' + componente.dsayuda + '"  anchoselect="850"></com-nosi>'
                break;
            case"header":
                compile = '<com-cabecera idcomponente="' + componente.idcomponente + '" titulo="' + componente.dsetiqueta + '" dsvar="'+componente.dsnombrevariable+'"></com-fecha>'
                break;
            case"radio":
                compile = '<com-lista-ordenada idcomponente="' + componente.idcomponente + '" name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" valores="' + valores + '" ayuda="' + componente.dsayuda + '" isrequerido="' + componente.isrequerido + '"></com-lista-ordenada>'
                break;
            case"check":
                compile = '<com-lista-ordenada-multi idcomponente="' + componente.idcomponente + '" name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" valores="' + valores + '" ayuda="' + componente.dsayuda + '" isrequerido="' + componente.isrequerido + '"></com-lista-ordenada-multi>'
                break;
            case"select":
                compile = '<com-combo idcomponente="' + componente.idcomponente + '" totalcmp="' + cantidad_componentes + '" =  posicion="' + posicion + '" expresion = "' + expresion + '"  name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" valores="' + valores + '" ayuda="' + componente.dsayuda + '" isrequerido="' + componente.isrequerido + '" anchoselect="850"></com-combo>'
                break;
            case"sel-check":
                compile = '<com-combo-multi idcomponente="' + componente.idcomponente + '" name="' + componente.dsnombrevariable + '" titulo="' + componente.dsetiqueta + '" valores="' + valores + '" ayuda="' + componente.dsayuda + '" isrequerido="' + componente.isrequerido + '" anchoselect="850"></com-combo-multi>'
                break;
            case"subform":
                $scope.wrap_formgeneral_id++;
                //console.info("COMPONENTE", componente)
                crea_envolvente(true, componente.dsnombrevariable);
                compile = "<com-sub-form  titulo='" + componente.dsetiqueta + "' idcomponente='" + componente.idcomponente + "' idsubform='" + componente.inposicion + "'></com-sub-form>";
                $scope.issubform = true;
                break;
            default:
                break;
        }
        var content = '<div class="contentInputs">';
        content += '<div class="colum colums1">';
        content += compile;
        content += '</div>';
        content += '</div>';
        var contenedor = $("#" + $scope.frm_wrap_select + $scope.wrap_formgeneral_id);
        contenedor.append($compile(content)($scope));
        var listaComponentes=[];
        var newpos = 0;
        if ($scope.issubform) {
            
            _.each($scope.frm_edit.listaSubFormularios,function(sub,index){
               if(sub.inposicion == componente.inposicion){
                   listaComponentes = sub.listaComponentes;
                   newpos = index;
               }
            })
            var arregloDatos = $scope.parseDataSubform(listaComponentes);
            if (!tieneSubFormData) {
                $scope.valoresSubForm.push(new Array());
            }
            $scope.render_form(listaComponentes, $scope.issubform, function () {
                $scope.addTable(contenedor, arregloDatos, newpos, $scope.wrap_formgeneral_id);
            });
        }
    }
    $scope.parseDataSubform = function (datos) {
        var arregloData = [];
        _.each(datos, function (dato, index) {
            var newDato = {"label": dato.dsetiqueta, "name": dato.dsnombrevariable}
            arregloData.push(newDato);
        })
        return arregloData;
    }
    $scope.creaContentTable = function (datos, posSubform, idForm) {
        var table = "<div class='contentInputs'>";
        table += "<table class='tableForm' style='max-width:888px'>"
        table += "<tr>";
        table += "<th style='width:5%'></th>"
        _.each(datos, function (dato, index) {
            table += "<th id='" + dato.name + index + "'>" + dato.label + "</th>";
        })
        table += "<th style='width:5%'></th><th style='width:5%'></th>";
        table += "</tr>";
        table += "<tr ng-repeat='dato in valoresSubForm[" + posSubform + "] track by $index'>";
        table += "<td>{{$index}}</td>"
        _.each(datos, function (dato, index) {
            table += "<td>{{parseDatoSelect(dato." + dato.name + ")}}</td>"
        })
        table += "<td><span class='tbl_icon ico_edit' ng-click=cargaDatosFields(" + posSubform + "," + idForm + ",$index)></span></td>"
        table += "<td><span class='tbl_icon ico_delete' ng-click=deleteDataTable(" + posSubform + ",dato)></span></td>"
        table += "</tr>";
        table += "</table></div>";
        return table;
    }
    $scope.parseDatoSelect = function (dato) {
        return(dato.indexOf("::") > -1) ? dato.substring((dato.indexOf("::") + 2), dato.length) : dato;
    }
    $scope.addTable = function (contenedor, datosSubform, posSubform, idForm) {
        var content = "";
        var nameData = [];
        content = '<button class="btn-blue btn-table-subform btn-add-subform" ng-click="addDataTable(' + posSubform + ',' + idForm + ')">Agregar datos</button>'
        content += '<button class="btn-blue btn-table-subform btn-edit-subform" style="display:none" ng-click="editDataTable()">Editar datos</button>'
        content += $scope.creaContentTable(datosSubform, posSubform, idForm);
        $timeout(function () {
            contenedor.append($compile(content)($scope));
        }, 500)
    }
    $scope.addDataTable = function (posSubform, idForm) {
        var miDato = {};
        var tmpArrayDatosEnvio = [];
        $("#frm_dinamic" + (idForm - 1) + " .frm_componente").each(function (i) {
            miDato[$(this).attr("name")] = ($(this).is("select")) ? $(this).find("option:selected").val() + "::" + $(this).find("option:selected").text() : $(this).val();
            var datoEnvio = {};
            datoEnvio.componente = {"idcomponente": $(this).attr("idcomponente")};
            datoEnvio.registro = $scope.valoresSubForm[posSubform].length;
            datoEnvio.valorcadena = ($(this).is("select")) ? $(this).find("option:selected").val() + "::" + $(this).find("option:selected").text() : $(this).val();
            tmpArrayDatosEnvio.push(datoEnvio);
            $(this).val("");
        });
        miDato.datoEnvio = tmpArrayDatosEnvio;
        $scope.valoresSubForm[posSubform].push(miDato);
    }
    $scope.cargaDatosFields = function (posSubform, idForm, indexData) {
        if ($scope.issubformEdit) {
            if (!confirm("Actualmente se esta editando un subformulario ¿desea continuar y detener la actualización de datos anterior?"))
            {
                return;
            }
            else {
                $scope.resetDataEditVars();
                $(".btn-edit-subform").fadeOut("fast");
            }
        }
        $scope.issubformEdit = true;
        $scope.idFormEdit = idForm;
        $scope.posSubformEdit = posSubform;
        $scope.indexDataEdit = indexData;
        console.info("********************** DATO A EDITAR *****************************************************");
        console.info($scope.valoresSubForm[$scope.posSubformEdit][$scope.indexDataEdit]);
        console.info("*************************************************************************");
        $("#frm_dinamic" + (idForm - 1) + " .frm_componente").each(function () {
            console.info("****::", $scope.valoresSubForm[posSubform][indexData][$(this).attr("name")])
            var valorcadena = $scope.valoresSubForm[posSubform][indexData][$(this).attr("name")];
            var haveKeyData = valorcadena.indexOf("::");
            if(haveKeyData > -1){
              valorcadena =  valorcadena.substring(0, haveKeyData)
              /*if(valorcadena == "si"){
                  valorcadena = "true";
              }else if(valorcadena=="no"){
                  valorcadena = "false";
              }*/
            }else{
                valorcadena= valorcadena
            }
            $(this).val(valorcadena);
        })
        $("#frm_dinamic" + (idForm - 1)).find(".btn-edit-subform").fadeIn("fast");
    }
    $scope.resetDataEditVars = function () {
        $("#frm_dinamic" + ($scope.idFormEdit - 1) + " .frm_componente").each(function () {
            $(this).val("");
        });
        $scope.idFormEdit = "";
        $scope.posSubformEdit = "";
        $scope.indexDataEdit = "";
        $scope.issubformEdit = false;
    }
    $scope.editDataTable = function () {
        $("#frm_dinamic" + ($scope.idFormEdit - 1) + " .frm_componente").each(function (indice) {
            $scope.valoresSubForm[$scope.posSubformEdit][$scope.indexDataEdit][$(this).attr("name")] = ($(this).is("select")) ? $(this).find("option:selected").val() + "::" + $(this).find("option:selected").text() : $(this).val();
            $scope.valoresSubForm[$scope.posSubformEdit][$scope.indexDataEdit].datoEnvio[indice].valorcadena = ($(this).is("select")) ? $(this).find("option:selected").val() + "::" + $(this).find("option:selected").text() : $(this).val();
            console.info("**DESPUES DE EDITAR**");
            console.info($scope.valoresSubForm[$scope.posSubformEdit][$scope.indexDataEdit])
            console.info("************************")
        })
        $("#frm_dinamic" + ($scope.idFormEdit - 1)).find(".btn-edit-subform").fadeOut("fast");
        $scope.resetDataEditVars();
    }
    $scope.deleteDataTable = function (posSubform, obj) {
        if (confirm("¿Realmente desea eliminar este elemento?")) {
            var index = $scope.valoresSubForm[posSubform].indexOf(obj);
            if (index > -1) {
                $scope.valoresSubForm[posSubform].splice(index, 1);
            }
        }
    }
    $scope.sortByOrder = function (callback) {
        $scope.frm_edit.listaComponentes = _.sortBy($scope.frm_edit.listaComponentes, function (comp) {
            return comp.inorden
        })
        _.each($scope.frm_edit.listaSubFormularios, function (subform, index) {
            subform.listaComponentes = _.sortBy(subform.listaComponentes, function (comsubform) {
                return comsubform.inorden
            })
        })
        if (typeof callback != "undefined")
            callback();
    }
    $scope.render_form = function (listaComponentes, boleano, callback) {
        _.each(listaComponentes, function (item, index) {
            crea_form(item, index, listaComponentes.length);
        })
        if (boleano) {
            $scope.wrap_formgeneral_id++;
            crea_envolvente(false, "");
        }
        if (typeof callback != "undefined")
            callback();
    }
    $scope.verInputs = function () {
        $scope.recuperaDatos()
    }
    $scope.recuperaDatos = function () {
        _.each($scope.listaInputs, function (comp, i) {
            var valor = "";
            if (typeof $("#" + comp).val() != "undefined") {
                valor = $("#" + comp).val();
            }
            if(valor !=null && valor.trim()==""){
             valor= null;  
            }
            $scope.listaValFormulario[i].valorcadena = valor;
        })
        console.info($scope.listaValFormulario);
    }
    $scope.guardar_datos = function () {
        $scope.recuperaDatos();
        var tmpArraySubFormDatosEnvio = [];
        _.each($scope.valoresSubForm, function (valor, indice) {
            _.each(valor, function (v, j) {
                _.each(v.datoEnvio, function (datenvio, i) {
                    tmpArraySubFormDatosEnvio.push(datenvio);
                })
            })
        })
        $scope.listaValoresFormularios[0].acto = {"idacto": $rootScope.idacto}
        $scope.listaValoresFormularios[0].conFormulario = {"id": $scope.frm_edit.id};
        $scope.listaValoresFormularios[0].listaValFormulario = $scope.listaValFormulario;
        $scope.listaValoresFormularios[0].listaValSubFormulario = tmpArraySubFormDatosEnvio;
        conexion_app.guarda_valores_formulario({"usuario": $scope.usr_global, "expediente": {"idexpediente": $scope.expediente.idexpediente}, "listaValoresFormularios": $scope.listaValoresFormularios, }, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se guardaron correctamente los datos");
                $scope.redirect_to_frm();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al guardar los datos" + error.status);
        })
    }
    $scope.editar_datos = function () {
        $scope.recuperaDatos();
        var tmpArraySubFormDatosEnvio = [];
        _.each($scope.valoresSubForm, function (valor, indice) {
            _.each(valor, function (v, j) {
                var reg = j;
                _.each(v.datoEnvio, function (datenvio, i) {
                    datenvio.registro = reg;
                    tmpArraySubFormDatosEnvio.push(datenvio);
                })
            })
        })
        $scope.listaValoresFormularios[0].acto = {"idacto": $rootScope.idacto}
        $scope.listaValoresFormularios[0].conFormulario = {"id": $scope.frm_edit.id};
        $scope.listaValoresFormularios[0].idformulario = $scope.idformulario;
        $scope.listaValoresFormularios[0].listaValFormulario = $scope.listaValFormulario;
        $scope.listaValoresFormularios[0].listaValSubFormulario = tmpArraySubFormDatosEnvio;
        conexion_app.actualiza_valores_formulario({"usuario": $scope.usr_global, "expediente": {"idexpediente": $scope.expediente.idexpediente}, "listaValoresFormularios": $scope.listaValoresFormularios, }, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se actualizaron correctamente los datos");
                $scope.redirect_to_frm();
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al actualizar los datos" + error.status);
        })
    }
}