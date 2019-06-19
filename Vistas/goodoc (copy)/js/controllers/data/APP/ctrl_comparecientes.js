var ponDatos;
function ctrl_comparecientes($scope, $route, $rootScope, conexion_app, catalogos, $timeout) {
    $scope.myFormMoral = {};
    $scope.myFormFisica = {};
    $scope.myFormMoral.submitted = false;
    $scope.myFormFisica.submitted = false;
    $scope.tmpTp = 0;
    $scope.compareciente = {};
    $scope.compareciente.persona = {};
    $scope.onDuplicadoData = false;
    catalogos.get_tratamientos($scope, function () {
        $timeout(function () {
            if (!$scope.compareciente.tratamiento)
                $scope.compareciente.tratamiento = $scope.tratamientos[0];
        }, 1000)
    });
        
    catalogos.get_nacionalidad($scope, function () {
        $timeout(function () {
            if (!$scope.compareciente.persona.nacionalidad)
                $scope.compareciente.persona.nacionalidad = $scope.nacionalidad[1];
        }, 1000)
    });
    
    catalogos.get_estado_civil($scope, function () {
        $timeout(function () {
            if (!$scope.compareciente.estadoCivil)
                $scope.compareciente.estadoCivil = $scope.estados_civiles[0];
        }, 1000)
    });
    catalogos.get_tipos_personas($scope, function () {
        $timeout(function () {
            if (!$scope.compareciente.persona.tipopersona)
                $scope.compareciente.persona.tipopersona = $scope.tipos_personas[0];
        }, 1000);
    });
    catalogos.get_ocupaciones($scope);
    
    catalogos.get_tipoComparecienteXSubop($scope,function(){
        $timeout(function () {
            if (!$scope.compareciente.tipoCompareciente)
                $scope.compareciente.tipoCompareciente = $scope.tipo_comparecientes[0];
        }, 1000);
    });
    catalogos.get_domicilios_x_acto($scope);
    $scope.flagAutocomplete = false;
    $scope.buscar_clientes = function () {
        catalogos.busca_personas($scope, $scope.buscar_persona);
    }
    
    $scope.mostrarRegimen = function(){
        if($scope.compareciente.estadoCivil.dselemento!="Casado"){
                    $(".regimen").hide("slow");

        }else{
                    $(".regimen").show("slow");

        }
    }
    
    $scope.buscar_personas = function (personabuscar) {
        catalogos.busca_personas_detalle($scope, personabuscar, function () {
            $scope.personaFound = $scope.personas_encontradas[0];
            if ($scope.personaFound) {
                if ($scope.personaFound.tipopersona.dselemento == 'Moral') {
                    $scope.add_data_persona($scope.personaFound);
                    $("#form_persona_moral").prop("disabled", "true");
                } else {
                    $scope.add_data_persona($scope.personaFound);
                    $("#form_persona_fisica").prop("disabled", "true");
                }
            }
        });
    }
    
    
    $scope.admisionExtranjero = function(){
        if($scope.compareciente.persona.isextranjero=='true'){
            
            $("#participaExtranjero").show("slow");
            
        }else{
            $("#participaExtranjero").hide("slow");
            $(".participacion").hide("slow");
            
            $scope.compareciente.persona.iscapitalextranjero='false';
            $scope.compareciente.isavisoextranjero='false';
            $scope.compareciente.isextranjeroinscrito='false'
        }
    }
    
    $scope.capitalExtranjero = function(){
        
        if($scope.compareciente.persona.iscapitalextranjero=='true')
        {
            $(".participacion").show("slow");
        }else{
            $(".participacion").hide("slow");
            $scope.compareciente.isavisoextranjero='false';
            $scope.compareciente.isextranjeroinscrito='false'
        }
        
    }
    
    $scope.avisoExtranjero = function(){
        if($scope.compareciente.isavisoextranjero=='true'){
            $("#avisoInscrito").show("slow");
        }else{
            $("#avisoInscrito").hide("slow");
            $scope.compareciente.isextranjeroinscrito=='false'
        }
    }
    
    ponDatos = function () {
        if ($scope.personaFound) {
            $scope.personaFound = $scope.personaFound.originalObject;
            modal.show();
            $timeout(function () {
                $(".datoPersonaAuto").val($scope.personaFound.dsnombrecompleto);
                if ($scope.personaFound.tipopersona && $scope.personaFound.tipopersona.dselemento) {
                    if ($scope.personaFound.tipopersona.dselemento == 'Moral') {
                        $("#form_persona_moral").prop("disabled", "true");
                    } else {
                        $("#form_persona_fisica").prop("disabled", "true");
                    }
                }
                $scope.add_data_persona($scope.personaFound);
                conexion_app.buscar_contacto_x_persona({"idpersona": $scope.personaFound.idpersona}, function (data) {
                    $scope.compareciente.contacto = data;
                    delete $scope.compareciente.contacto.idpersona;
                }, function (error) {
                    alert("Algo malo paso al obtener el teléfono o email: " + error.status);
                }).$promise.finally(destroyModal);
            }, 1000);
        }
    }
    $scope.busca_duplicado = function (personabuscar) {
        catalogos.busca_personas_detalle($scope, personabuscar, function () {
            if (!$rootScope.isActualizacion) {
                if ($scope.personas_encontradas.length > 0 && !$scope.onDuplicadoData) {
                    ejecutaAlerta(2, "Se encontraron personas dentro del sistema con el mismo nombre");
                    return;
                }
            }
            $scope.guarda_compareciente_p1();
        });
    }
    $scope.add_data_persona = function (persona) {
        $scope.compareciente.persona = persona;
        $scope.actualiza_datos_persona();
        if (persona.domicilio && persona.domicilio.dsdircompleta != "") {
            $rootScope.haveDom = true;
            $rootScope.domicilio = persona.domicilio;
        }
    };
    $scope.edit_campos_persona = function () {
        if (confirm("Se actualizarán los datos de la persona, esto podría afectar los datos en otros actos ¿Desea continuar?")) {
            $(".ispersonadata").removeAttr("disabled");
            $(".ispersonadata").css("background-color", "#F5F6CE");
            $rootScope.editDataPersona = true;
        }
    }
    $scope.cancel_select_persona = function () {
        $(".ispersonadata").removeAttr("disabled");
        $scope.compareciente.persona = {};
        $scope.onDuplicadoData = false;
    }
    if ($rootScope.sel_com_to_add && $rootScope.tipo_reg_com) {
        console.info("TIPO DE OTORGANTE", $rootScope.tipo_reg_com);
        switch ($rootScope.tipo_reg_com) {
            case"r":
                $rootScope.representadoList = [$rootScope.sel_com_to_add];
                $scope.compareciente.tipoCompareciente = {};
                $scope.compareciente.tipoCompareciente.idtipocompareciente = "a87ff679a2f3e71d9181a67b7542122c";
                $timeout(function () {
                    /*$scope.compareciente.tipoCompareciente = _.find($scope.tipo_comparecientes, function (item) {
                        return item.dsnombre == "Representante";
                    });*/
                }, 1000);
                break;
            default:
            }
    }
    if ($rootScope.actualizaComp && $rootScope.actualizaComp != "") {
        conexion_app.obtener_compareciente_x_id({"usuario": $scope.usr_global, "compareciente": {"idcompareciente": $rootScope.actualizaComp}}, function (data) {
            if (data.exito) {
                $rootScope.compareciente = data.compareciente;
                $rootScope.domicilio = data.compareciente.domicilio;
                $rootScope.representadoList = data.representadosList;
                $("#ocupacion_value").val($rootScope.compareciente.dsocupacion);
                $scope.actualiza_datos_comp_edit(true);
                $scope.actualiza_nombre_completo();
                
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Ocurrió un error al obtener el compareciente para actualizar: " + error.status);
        });
        $rootScope.isActualizacion = true;
        delete $rootScope.actualizaComp;
    }
    $scope.cambiaTipoPersona = function () {
        var tipoPersona = $("#tipoPersona option:selected").text();
        if (tipoPersona != "Moral") {
            $("#body_moral").slideUp("slow", function () {
                $("#body_fisica").slideDown("slow");
            });
            $scope.tmpTp = 0;
        } else {
            $("#body_fisica").slideUp("slow", function () {
                $("#body_moral").slideDown("slow");
            });
            $scope.tmpTp = 1;
        }
    }
    $scope.actualiza_nombre_completo = function () {
        var tcom = "";
        if ($scope.compareciente.persona.dsnombre && typeof $scope.compareciente.persona.dsnombre != "undefined" && $scope.compareciente.persona.dsnombre != "") {
            tcom += $scope.compareciente.persona.dsnombre + " ";
        }
        if ($scope.compareciente.persona.dsapellidopat && typeof $scope.compareciente.persona.dsapellidopat != "undefined" && $scope.compareciente.persona.dsapellidopat != "") {
            tcom += $scope.compareciente.persona.dsapellidopat + " ";
        }
        if ($scope.compareciente.persona.dsapellidomat && typeof $scope.compareciente.persona.dsapellidomat != "undefined" && $scope.compareciente.persona.dsapellidomat) {
            tcom += $scope.compareciente.persona.dsapellidomat;
        }
        $scope.compareciente.persona.dsnombrecompleto = tcom;
    }
    if ($rootScope.havecomp) {
        if ($route.current.$$route.originalPath == "/comparecientes_registrar") {
            $scope.actualiza_nombre_completo();
        }
    }
    $scope.guarda_compareciente_p1 = function () {
        if ($scope.tmpTp == 1) {
            $scope.compareciente.persona.dsapellidopat = "";
            $scope.compareciente.persona.dsnombrecompleto = $scope.compareciente.persona.dsnombre;
            delete $scope.compareciente.persona.dsapellidomat;
            delete $scope.compareciente.persona.estadocivil;
            delete $scope.compareciente.persona.ocupacion;
            delete $scope.compareciente.persona.fechanacimiento;
            $scope.compareciente.tratamiento = null;
        } else {
            delete $scope.compareciente.persona.regimenfiscal;
        }
        if (!$scope.compareciente.persona || !$scope.compareciente.persona.dsnombre || $scope.compareciente.persona.dsnombre == "") {
            ejecutaAlerta(2, "El nombre de usuario es requerido para continuar");
            return;
        }
        $scope.compareciente.persona.fechanacimiento = parseFecha($scope.compareciente.persona.fechanacimiento) + "T00:00:00-06:00";
        $rootScope.compareciente = $scope.compareciente;
        $rootScope.compareciente.acto = {"idacto": $rootScope.idacto};
        $rootScope.compareciente.dsocupacion = $("#ocupacion_value").val();
        $rootScope.havecomp = true;
        $scope.goTo("/comparecientes_domicilio");
    }
    function reinicia_data_global_compareciente() {
        $rootScope.compareciente = {};
        $rootScope.contacto = {};
        $rootScope.compareciente.persona = {};
        delete $rootScope.representadoList;
        delete $rootScope.domicilio;
        delete $rootScope.haveDom;
    }
    $scope.guarda_compareciente_p3 = function () {
        $rootScope.compareciente.domicilio = $rootScope.domicilio;
        if (!$rootScope.compareciente.domicilio) {
            $rootScope.compareciente.domicilio = {"isasistido": true, "dsdircompleta": "...Domicilio aún no registrado"};
        }
        modal.show();
        conexion_app.guardar_compareciente({"usuario": $rootScope.usr_global, "compareciente": $rootScope.compareciente, "representadosList": $scope.representadoList}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, 'Se registró correctamente el compareciente');
                reinicia_data_global_compareciente();
                setTimeout(function () {
                    $scope.return_lista();
                }, 2000);
            } else {
                ejecutaAlerta(2, 'Ocurrió un error:' + data.estatus);
            }
        }, function (error) {
            alert("Algo malo ocurrió al guardar al compareciente" + error.status);
        }).$promise.finally(destroyModal);
        ;
    }
    $scope.actualiza_compareciente = function () {
        modal.show();
        conexion_app.actualiza_compareciente({"usuario": $rootScope.usr_global, "compareciente": $rootScope.compareciente, "representadosList": $scope.representadoList}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, 'Se actualizó correctamente el compareciente');
                reinicia_data_global_compareciente();
                setTimeout(function () {
                    $scope.return_lista();
                }, 2000);
            } else {
                ejecutaAlerta(2, 'Ocurrió un error:' + data.estatus);
            }
        }, function (error) {
            alert("Algo malo ocurrió al actualizar al compareciente" + error.status);
        }).$promise.finally(destroyModal);
    }
    $scope.return_reg = function () {
        $scope.return_lista();
    }
    $scope.return_dom = function () {
        $scope.return_lista();
    }
    $scope.return_lista = function () {
        reinicia_data_global_compareciente();
        force_redirect('index.html#/comparecientes');
    }
    if (!$scope.representadoList || $scope.representadoList.length < 0)
    {
        $scope.representadoList = [];
    } else {
        if ($rootScope.haveRepList) {
            $scope.representadoList = $rootScope.representadoList;
        }
    }
    $scope.actualiza_datos_persona = function () {
        $(".ispersonadata").prop("disabled", "true");
        $scope.onDuplicadoData = true;
        if ($scope.compareciente.persona && $scope.compareciente.persona.fechanacimiento != null) {
            $scope.compareciente.persona.fechanacimiento = parseFecha($scope.compareciente.persona.fechanacimiento);
        }
        if ($scope.compareciente.persona.nacionalidad && $scope.compareciente.persona.nacionalidad != null) {
            for (var i = 0; i < $scope.nacionalidad.length; i++) {
                if ($scope.nacionalidad[i].dselemento == $scope.compareciente.persona.nacionalidad.dselemento) {
                    $scope.compareciente.persona.nacionalidad = $scope.nacionalidad[i];
                    break;
                }
            }
        }
        
        
        
        
        if ($scope.compareciente.persona.tipopersona && $scope.compareciente.persona.tipopersona != null) {
            for (var i = 0; i < $scope.tipos_personas.length; i++) {
                if ($scope.tipos_personas[i].dselemento == $scope.compareciente.persona.tipopersona.dselemento) {
                    $scope.compareciente.persona.tipopersona = $scope.tipos_personas[i];
                    setTimeout(function () {
                        $scope.cambiaTipoPersona();
                    }, 500);
                    break;
                }
            }
        }
        if ($scope.compareciente.persona.regimenfiscal && $scope.compareciente.persona.regimenfiscal != null) {
            for (var i = 0; i < $scope.regimenes_fiscales.length; i++) {
                if ($scope.regimenes_fiscales[i].dselemento == $scope.compareciente.persona.regimenfiscal.dselemento) {
                    $scope.compareciente.persona.regimenfiscal = $scope.regimenes_fiscales[i];
                    break;
                }
            }
        }
        
         if($scope.compareciente.persona.isextranjero!=null){
                $scope.compareciente.persona.isextranjero = $scope.compareciente.persona.isextranjero.toString();
                $scope.admisionExtranjero();
            }
            
            
            if($scope.compareciente.persona.iscapitalextranjero!=null){
                $scope.compareciente.persona.iscapitalextranjero = $scope.compareciente.persona.iscapitalextranjero.toString();
                $scope.capitalExtranjero()
            }
            
            if($scope.compareciente.isavisoextranjero !=null){
                $scope.compareciente.isavisoextranjero = $scope.compareciente.isavisoextranjero.toString();
                $scope.avisoExtranjero();
            }
            
            if($scope.compareciente.isextranjeroinscrito !=null){
                $scope.compareciente.isextranjeroinscrito = $scope.compareciente.isextranjeroinscrito.toString()
            }
        
        if (!$scope.compareciente.persona.tipopersona) {
            $("#tipoPersona").removeAttr("disabled");
            $scope.compareciente.persona.tipopersona = $scope.tipos_personas[0];
        }
        if (!$scope.compareciente.persona.fechanacimiento) {
            $("#fechanacimiento").removeAttr("disabled");
        }
        if (!$scope.compareciente.persona.dslugarnacimiento) {
            $("#lugarnacimiento").removeAttr("disabled");
        }
        if (!$scope.compareciente.persona.nacionalidad) {
            $(".nacionalidad").removeAttr("disabled");
        }
        if (!$scope.compareciente.persona.dsrfc) {
            $(".rfc").removeAttr("disabled");
        }
        if (!$scope.compareciente.persona.dscurp) {
            $(".curp").removeAttr("disabled");
        }
    };
    $scope.actualiza_datos_comp_edit = function (iscomp) {
        $rootScope.haveDom = true;
        $rootScope.haveRepList = true;
        if (!$rootScope.compareciente || !$rootScope.compareciente.domicilio) {
            $rootScope.compareciente = {};
        }
        $rootScope.domicilio = $rootScope.compareciente.domicilio;
        $scope.compareciente = $rootScope.compareciente;
        $timeout(function () {
            $scope.actualiza_datos_persona();
            if ($scope.compareciente.estadoCivil && $scope.compareciente.estadoCivil != null) {
                for (var i = 0; i < $scope.estados_civiles.length; i++) {
                    if ($scope.estados_civiles[i].dselemento == $scope.compareciente.estadoCivil.dselemento) {
                        $scope.compareciente.estadoCivil = $scope.estados_civiles[i];
                        break;
                    }
                }
            }
            if ($scope.compareciente.tratamiento && $scope.compareciente.tratamiento != null) {
                for (var i = 0; i < $scope.tratamientos.length; i++) {
                    if ($scope.tratamientos[i].dselemento == $scope.compareciente.tratamiento.dselemento) {
                        $scope.compareciente.tratamiento = $scope.tratamientos[i];
                        break;
                    }
                }
            }
            if ($scope.compareciente.tipoCompareciente && $scope.compareciente.tipoCompareciente != null) {
                for (var i = 0; i < $scope.tipo_comparecientes.length; i++) {
                    if ($scope.tipo_comparecientes[i].dsnombre == $scope.compareciente.tipoCompareciente.dsnombre) {
                        $scope.compareciente.tipoCompareciente = $scope.tipo_comparecientes[i];
                        break;
                    }
                }
            }
            if ($scope.compareciente.persona.regimenfiscal && $scope.compareciente.persona.regimenfiscal != null) {
                for (var i = 0; i < $scope.regimenes_fiscales.length; i++) {
                    if ($scope.regimenes_fiscales[i].dselemento == $scope.compareciente.persona.regimenfiscal.dselemento) {
                        $scope.compareciente.persona.regimenfiscal = $scope.regimenes_fiscales[i];
                        break;
                    }
                }
            }
            
            if($scope.compareciente.persona.isextranjero!=null){
                $scope.compareciente.persona.isextranjero = $scope.compareciente.persona.isextranjero.toString();
                $scope.admisionExtranjero();
            }
            
            
            if($scope.compareciente.persona.iscapitalextranjero!=null){
                $scope.compareciente.persona.iscapitalextranjero = $scope.compareciente.persona.iscapitalextranjero.toString();
                $scope.capitalExtranjero()
            }
            
            if($scope.compareciente.isavisoextranjero !=null){
                $scope.compareciente.isavisoextranjero = $scope.compareciente.isavisoextranjero.toString();
                $scope.avisoExtranjero();
            }
            
            if($scope.compareciente.isextranjeroinscrito !=null){
                $scope.compareciente.isextranjeroinscrito = $scope.compareciente.isextranjeroinscrito.toString()
            }
            
            
            
            
        }, 1000)
    }
    $scope.ver_domicilios = function () {
        $("#dom_asoc").slideToggle("slow");
    }
    $scope.select_domicilio_prev = function (domicilio) {
        alert("En caso de modificar el domicilio actual se modificará en todos los otorgantes asociados")
        $rootScope.domicilio = domicilio;
        $rootScope.compareciente.domicilio = domicilio;
        $("#d_completo").val($rootScope.domicilio.dsdircompleta);
    }
    $scope.desasociar_domicilio = function () {
        if ($rootScope.domicilio && $rootScope.domicilio.iddomicilio)
            delete $rootScope.domicilio.iddomicilio;
    }
}