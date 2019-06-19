function ctrl_frm_dinamico($scope, $timeout, $route, $rootScope, conexion_app, conexion, $compile, catalogos) {
    var dir = "views/_adm/frms/";
    //$('.tab-container').easytabs('select','tb_content');
    
   
    
    $scope.frm_content = function(){
        
    }
    
    
    $rootScope.componente_dinamico = dir + "text.html";
    $scope.com = {};
    $scope.orden_componentes = [];
    $scope.com.idcomponente = null;
    $scope.com.tipocomponente = {};
    $scope.isparasubform = false;
    $scope.idsubform = 0;
     
     
     
     
    $scope.subformselect = "";
    catalogos.get_suboperaciones($scope);
    catalogos.get_roles($scope);
    catalogos.get_componentes($scope);
    catalogos.get_locacion($scope);
    $scope.conformulario = {}
    $scope.conformulario.listaComponentes = [];
    $scope.conformulario.listaActoFormularios = [];
    $scope.conformulario.listaPermisosRol = [];
    $scope.conformulario.listaSubFormularios = [];
    $timeout(function () {
        var tmpOrden_pos = 0;
        $("#wrap_componentes_vp").sortable({start: function (event, ui) {
                tmpOrden_pos = $scope.orden_componentes[(ui.item.index())];
                $scope.orden_componentes.splice(ui.item.index(), 1);
                ui.item.addClass("grabbing");
            }, stop: function (event, ui) {
                var pos = ui.item.index();
                $scope.orden_componentes.splice(pos, 0, tmpOrden_pos);
                ui.item.removeClass("grabbing");
            }, placeholder: "ui-state-highlight ui-state-highlight1"});
    }, 2000);
    if ($rootScope.edit_formulario) {
        $scope.formulario = $rootScope.edit_formulario;
        delete $rootScope.edit_formulario;
        $scope.act_form_dinam = true;
        conexion_app.obtener_config_formulario({"usuario": $scope.usr_global, "conformulario": $scope.formulario, }, function (data) {
            if (!data.exito) {
                ejecutaAlerta(2, "Ocurrió un error al obtener el formulario:" + data.estatus);
                force_redirect("index_adm.html#/lista_formulario_dinamico");
            }
            $scope.conformulario = data.conformulario;
            if ($rootScope.cloneForm) {
                delete $rootScope.cloneForm;
                delete $scope.conformulario.id;
                $scope.conformulario.ispublicado = false;
            }
            $scope.isedit = true;
            $scope.parseaRolesEdit($scope.conformulario.listaPermisosRol);
            var listComponentesEdit = $scope.conformulario.listaComponentes;
            listComponentesEdit = _.sortBy(listComponentesEdit, function (comp) {
                return comp.inorden;
            })
            if ($scope.conformulario.locacion) {
                for (var i = 0; i < $scope.locacion.length; i++) {
                    if ($scope.locacion[i].dselemento == $scope.conformulario.locacion.dselemento) {
                        $scope.conformulario.locacion = $scope.locacion[i];
                        break;
                    }
                }
            }
            $scope.conformulario.listaComponentes = [];
            $scope.render_frm_componentes(listComponentesEdit);
        }, function (error) {
            alert("Ocurrió un error al obtener el formulario: " + error.status);
        });
    }
    $scope.render_frm_componentes = function (lista_componentes) {
        var lista_componentes_subform = [];
        _.each(lista_componentes, function (componente) {
            $scope.agrega_componente(componente);
            if (componente.tipocomponente.dscodigo == "subform") {
                //if($scope.conformulario.listaSubFormularios[lista_componentes_subform.length].listaComponentes.length>0){
                    lista_componentes_subform.push($scope.conformulario.listaSubFormularios[lista_componentes_subform.length].listaComponentes);
                //}
                var inposactual = lista_componentes_subform.length - 1;
                $scope.conformulario.listaSubFormularios[inposactual].listaComponentes = [];
                $scope.conformulario.listaSubFormularios[$scope.idsubform].orden_componentes = [];
                $scope.close_subform_dinamic("sub-form" + inposactual);
            }
        });
        $timeout(function () {
            _.each(lista_componentes_subform, function (lista_componentes, index) {
                console.info("index::::>", index);
                _.each(lista_componentes, function (componente_subfrm,indexinto) {
                    $scope.edita_subform(lista_componentes[indexinto].inposicion);
                    $scope.agrega_componente(componente_subfrm);
                    $scope.close_subform_dinamic("sub-form" + index);
                })
            })
        }, 500)
        $scope.isedit = false;
    };
    $scope.parseaRolesEdit = function (listaRoles) {
        _.each(listaRoles, function (rol, index) {
            var permisos = dec2bin(rol.inpermiso);
            permisos = permisos.split("");
            console.info(permisos);
            rol.inpermiso = permisos;
        });
    };
    $scope.add_acto = function (acto) {
        var formsubop = {};
        formsubop.suboperacion = $scope.acto_select;
        $scope.conformulario.listaActoFormularios.push(formsubop);
        var index = $scope.suboperaciones.indexOf(acto);
        if (index != -1) {
            $scope.suboperaciones.splice(index, 1);
        }
    };
    $scope.del_acto = function (i, obj) {
        $scope.conformulario.listaActoFormularios.splice(i, 1);
        $scope.suboperaciones.push(obj);
    }
    $scope.add_rol = function (rol) {
        var frm_rol = {};
        frm_rol.rol = $scope.rol_select;
        frm_rol.inpermiso = ["0", "0", "0"];
        $scope.conformulario.listaPermisosRol.push(frm_rol);
        var index = $scope.roles.indexOf(rol);
        if (index != -1) {
            $scope.roles.splice(index, 1);
        }
    };
    $scope.del_rol = function (i, obj) {
        $scope.conformulario.listaPermisosRol.splice(i, 1);
        $scope.roles.push(obj);
    }
    $scope.cambiaPlantilla = function () {
        var plantilla = $scope.com.tipocomponente.dscodigo;
        switch (plantilla) {
            case"radio":
            case"check":
            case"select":
            case"sel-check":
                plantilla = "lista";
                break;
            case"subform":
            case"header":
                $(".h_header").hide("slow");
                break;
            default:
                $(".h_header").show("slow");
        }
        $rootScope.componente_dinamico = dir + plantilla + ".html";
    }
    var compile = "";
    $scope.total_componentes = 0;
    $scope.agrega_componente = function (componenteAdd) {
        if (typeof componenteAdd == "undefined")
        {
            if ($scope.com.tipocomponente.dscodigo != "subform" && $scope.com.tipocomponente.dscodigo != "header" && typeof $scope.com.dsnombrevariable == "undefined") {
                alert("Verifique que el nombre de variable no contenga números o caracteres especiales");
                return;
            }
            componenteAdd = $scope.com;
        } else {
            $scope.com = componenteAdd;
        }
        console.info("****************ok termine********************", componenteAdd);
        var contenedor = $("#wrap_componentes_vp");
        var idwrap = "wrap_comp" + $scope.total_componentes;
        var num_componente = $scope.total_componentes;
        var valores;
        if ($scope.com.dslistavalores) {
            valores = $scope.com.dslistavalores;
        } else {
            if (!$scope.isdefault) {
                var valores = $("#valores_no_default").val();
            }
        }
        switch (componenteAdd.tipocomponente.dscodigo) {
            case"text":
                compile = '<com-texto name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" maxima="' + $scope.com.longitudmaxima + '" regex="' + $scope.com.dsexpvalidacion + '" ayuda="' + $scope.com.dsayuda + '" isrequerido="' + $scope.com.isrequerido + '"></com-texto>';
                break;
            case"datetime":
                compile = '<com-fecha  name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" ayuda="' + $scope.com.dsayuda + '" isrequerido="' + $scope.com.isrequerido + '" posbk="540"></com-fecha>'
                break;
            case"radio":
                compile = '<com-lista-ordenada name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" valores="' + valores + '" ayuda="' + $scope.com.dsayuda + '" isrequerido="' + $scope.com.isrequerido + '"></com-lista-ordenada>'
                break;
            case"check":
                compile = '<com-lista-ordenada-multi name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" valores="' + valores + '" ayuda="' + $scope.com.dsayuda + '" isrequerido="' + $scope.com.isrequerido + '"></com-lista-ordenada-multi>'
                break;
            case"select":
                compile = '<com-combo name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" valores="' + valores + '" ayuda="' + $scope.com.dsayuda + '" isrequerido="' + $scope.com.isrequerido + '" anchoselect = "570"></com-combo>'
                break;
            case"sel-check":
                compile = '<com-combo-multi name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" valores="' + valores + '" ayuda="' + $scope.com.dsayuda + '" isrequerido="' + $scope.com.isrequerido + '" anchoselect="570"></com-combo-multi>'
                break;
            case"singlechk":
                compile = '<com-sino name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" ayuda="' + $scope.com.dsayuda + '" anchoselect = "570"></com-sino>'
                break;
            case"singlechkn":
                compile = '<com-nosi name="' + $scope.com.dsnombrevariable + '" titulo="' + $scope.com.dsetiqueta + '" ayuda="' + $scope.com.dsayuda + '" anchoselect = "570"></com-nosi>'
                break;
            case"header":
                compile = '<com-cabecera titulo="' + $scope.com.dsetiqueta + '"></com-fecha>';
                $scope.com.dsnombrevariable = "tit_"+(moment(new Date()).unix()+Math.floor(Math.random()*100)+1);
                break;
            case"subform":
                $scope.isparasubform = true;
                if (!$scope.sub_form_componentes || typeof $scope.sub_form_componentes == "undefined") {
                    $scope.sub_form_componentes = 0;
                }
                if ($scope.conformulario.listaSubFormularios.length > 0 && !$scope.isedit) {
                    $scope.idsubform += 1;
                }
                if (!$scope.com.inposicion || typeof $scope.com.inposicion == "undefined") {
                    $scope.com.inposicion = $scope.idsubform;
                } else {
                    $scope.idsubform = $scope.com.inposicion;
                }
                $scope.com.dsnombrevariable = "subform_" + $scope.idsubform;
                if (!$scope.isedit) {
                    $scope.conformulario.listaSubFormularios[$scope.idsubform] = {"idconsubform": $scope.idsubform, "inposicion": $scope.idsubform, "dsnombrecorto": $scope.conformulario.dsnombrecorto + "_subform_" + $scope.idsubform, "listaComponentes": []};
                    console.info("COMPONENTES SUBFORM", $scope.conformulario.listaSubFormularios[$scope.idsubform].listaComponentes);
                } else {
                    if (!$scope.conformulario.listaSubFormularios[$scope.idsubform] || typeof $scope.conformulario.listaSubFormularios[$scope.idsubform].listaComponentes == "undefined") {
                        $scope.conformulario.listaSubFormularios[$scope.idsubform] = {"idconsubform": $scope.idsubform, "inposicion": $scope.idsubform, "dsnombrecorto": $scope.conformulario.dsnombrecorto + "_subform_" + $scope.idsubform, "listaComponentes": []};
                    }
                }
                //$scope.tmp_select_subform = $scope.idsubform;
                compile = "<com-sub-form titulo='" + $scope.com.dsetiqueta + "' idsubform='" + $scope.idsubform + "'></com-sub-form>";
                compile += "<button id='trigger_sub-form" + $scope.idsubform + "' class='btn-blue save_subform' style='float:right;width:180px;' ng-click='close_subform(\"sub-form" + $scope.idsubform + "\")'>Guardar Sub-formulario</button>";
                compile += "<button id='trigger_sub-form" + $scope.idsubform + "_edit' class='btn-blue edit_subform' style='float:right;width:180px; display:none' ng-click='edita_subform(" + $scope.idsubform + ")'>Editar Sub-formulario</button>";
                break;
            default:
                return;
        }
        var del_action = "delete_comp('" + num_componente + "','" + $scope.com.idcomponente + "')";
        if ($scope.isparasubform && $scope.subformselect != "") {
            contenedor = $("#" + $scope.subformselect);
            if($scope.com.inposicion && typeof $scope.com.inposicion !="undefined"){
                $scope.idsubform = $scope.com.inposicion;
            }
            idwrap = $scope.subformselect + $scope.sub_form_componentes;
            del_action = "delete_comp_subform('" + $scope.idsubform + "','" + $scope.sub_form_componentes + "','" + $scope.com.idcomponente + "')";
        }
        $scope.com.isparasubform = $scope.isparasubform;
        if ($scope.isparasubform) {
            if (!$scope.com.inposicion || typeof $scope.com.inposicion == "undefined") {
                $scope.com.inposicion = $scope.idsubform;
            } else {
                $scope.idsubform = $scope.com.inposicion;
            }
        }
        contenedor.append($compile("<div id='" + idwrap + "' class='component_wraper_vp grab'><div class='btn_del' ng-click=" + del_action + ">x</div>" + compile + "</div>")($scope));
        if ($scope.isparasubform && $scope.com.tipocomponente.dscodigo != "subform") {
            $scope.conformulario.listaSubFormularios[$scope.idsubform].listaComponentes.push($scope.com);
        } else {
            $scope.orden_componentes.push($scope.conformulario.listaComponentes.length)
            $scope.conformulario.listaComponentes.push($scope.com);
            $scope.total_componentes++;
        }
        $scope.com = {};
        $scope.com.idcomponente = "new_" + moment(new Date()).unix();
        if (!$scope.isparasubform) {
        } else {
            if ($scope.subformselect != "")
                $scope.sub_form_componentes++;
            $scope.subformselect = "sub-form" + $scope.idsubform;
        }
        $timeout(function () {
            window.scrollTo(0, document.body.scrollHeight);
        }, 100);
    };
    $scope.delete_comp = function (id_comp, idComponente) {
        if (confirm("¿Realmente desea elminar este componente de la lista?")) {
            var wraper = "wrap_comp" + id_comp;
            var index = -1;
            _.each($scope.conformulario.listaComponentes, function (comp, i) {
                if (comp.idcomponente == idComponente) {
                    if (comp.tipocomponente.dscodigo == "subform") {
                        var index_del_subform = -1;
                        _.each($scope.conformulario.listaSubFormularios, function (subform_del, j) {
                            if (subform_del.inposicion == comp.inposicion) {
                                index_del_subform = j;
                                return;
                            }
                        })
                        if (index_del_subform != -1) {
                            $scope.conformulario.listaSubFormularios.splice(index_del_subform, 1);
                        }
                        $scope.isparasubform = false;
                        $scope.subformselect = "";
                    }
                    index = i;
                    return;
                }
            });
            if (index != -1) {
                $scope.conformulario.listaComponentes.splice(index, 1);
            }
            $scope.total_componentes--;
            $("#" + wraper).remove();
        }
    }
    $scope.delete_comp_subform = function (idsubform, idsubform_componente, idComponente) {
        if (confirm("¿Realmente desea elminar este componente de la lista?")) {
            var index = -1;
            _.each($scope.conformulario.listaSubFormularios[idsubform].listaComponentes, function (comp, i) {
                if (comp.idcomponente == idComponente) {
                    index = i;
                    return;
                }
            });
            if (index != -1) {
                $scope.conformulario.listaSubFormularios[idsubform].listaComponentes.splice(index, 1);
            }
            var wraper = "sub-form" + idsubform + idsubform_componente;
            $("#" + wraper).remove();
        }
    }
    $scope.close_subform = function (id_subform) {
        if (confirm("¿Realmente desea finalizar este formulario?")) {
            var btn = "#trigger_" + id_subform;
            $(btn).css({"display": "none"});
            $(btn + "_edit").css({"display": "block"});
            $scope.isparasubform = false;
            $scope.subformselect = "";
        }
    }
    $scope.close_subform_dinamic = function (id_subform) {
        var btn = "#trigger_" + id_subform;
        $(btn).css({"display": "none"});
        $(btn + "_edit").css({"display": "block"});
        $scope.isparasubform = false;
        $scope.subformselect = "";
    }
    $scope.edita_subform = function (id_subform) {
        if ($scope.isparasubform) {
            alert("Existe Sub-formulario sin guardar debe guardarlo para continuar")
            return;
        }
        $scope.isparasubform = true;
        $scope.subformselect = "sub-form" + id_subform;
        var btn = "#trigger_sub-form" + id_subform;
        $(".save_subform").css("display", "none");
        $(".edit_subform").css("display", "block");
        $(btn + "_edit").css({"display": "none"});
        $(btn).css({"display": "block"});
        $scope.idsubform = id_subform;
    }
    
    
    
     
     
     
    $scope.guardar_formulario = function () {
        for (var i = 0; i < $scope.conformulario.listaPermisosRol.length; i++) {
            var tmp_rol = $scope.conformulario.listaPermisosRol[i];
            var permisos = tmp_rol.inpermiso;
            permisos = permisos.toString().replace(/,/g, "");
            permisos = bin2dec(permisos);
            $scope.conformulario.listaPermisosRol[i].inpermiso = permisos;
        }
        _.each($scope.conformulario.listaComponentes, function (comp, i) {
            comp.inorden = $scope.orden_componentes.indexOf(i);
            if (comp.idcomponente && comp.idcomponente.indexOf("new_") > -1) {
                comp.idcomponente = null;
            }
        })
        
        // DEBO CREAR OTRA LISTA TEMPORAL POR QUE NO PUEDO MODIFICARLA DENTRO DEL FOR
        var tmpSubfrm = [];
        _.each($scope.conformulario.listaSubFormularios, function (co, i) {
            delete $scope.conformulario.listaSubFormularios[i].orden_componentes;
            
            if (co.idconsubform && co.idconsubform.toString().length < 32) {
                co.idconsubform = null;
            }
            
            // BORRO EL SUBFORMULARIO POR QUE VIENE VACIO O SE ENCUENTRA REPETIDO
            _.each(co.listaComponentes, function (subco, j) {
                if (subco.idcomponente && subco.idcomponente.indexOf("new_") > -1) {
                    subco.idcomponente = null;
                }
                subco.inorden = j;
            });
            if (co.listaComponentes && co.listaComponentes.length>0){
                //co.dsnombrecorto = $scope.conformulario.dsnombrecorto + "_subform_" +i;
                co.inposicion = co.listaComponentes[0].inposicion;
                co.dsnombrecorto = $scope.conformulario.dsnombrecorto + "_subform_"+co.listaComponentes[0].inposicion;
                tmpSubfrm.push(co);
            }
            
            
        })
        
        //RECALCULO POSICIONES DE SUBFORMULARIOS PARA LA VISTA Y QUE SEAN CONSECUTIVOS SIN IMPORTAR DONDE SE ENCUENTREN
        var i=0;
        _.each($scope.conformulario.listaComponentes,function(c){
          if(c.inposicion!=null){
              var pos = c.inposicion;
              _.each(tmpSubfrm,function(lista){
                  if(lista.inposicion == pos){
                      lista.inposicion = i;
                      _.each(lista.listaComponentes,function(clist){
                          clist.inposicion=i;
                      })
                  }
              })
              c.inposicion=i;
              i+=1;
          }
          
            
        })
        
        
        $scope.conformulario.listaSubFormularios = tmpSubfrm;
        console.info($scope.conformulario)
        conexion.guardar_formulario_dinamico({"usuario": $scope.usr_global, "conformulario": $scope.conformulario}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "El formulario se registro correctamente");
                conexion.publicar_formulario({"usuario": $scope.usr_global, "conformulario": {"id": {"idconFormulario": data.idConformulario, "version": data.version}}}, function (data) {
                    if (data.exito) {
                        ejecutaAlerta(1, "Se publicó correctamente el formulario");
                        force_redirect("index_adm.html#/lista_formulario_dinamico");
                    } else {
                        ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                    }
                }, function (error) {
                    alert("Algo malo paso al publicar el formulario: " + error.status);
                });
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al guardar el formulario dinámico: " + error.status)
        });
    }

}
