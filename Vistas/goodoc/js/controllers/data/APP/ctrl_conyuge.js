var ponDatos;
var is_mismo;
function ctrl_conyuge($scope, $rootScope, $timeout, conexion_app, catalogos) {
    $scope.contwo = {};
    $scope.contre = {};
    $scope.contwo.amboscompran = true;
    $scope.contre.amboscompran = true;
    $scope.con = {};
    $scope.contwo.persona = {};
    $scope.contre.persona = {};
    $scope.con.persona = {};
    $scope.domicilioConyuge = null;
    $scope.onDuplicadoData = false;
    $scope.mismo_domicilio=true;

  
    
    
     conexion_app.obtener_compareciente_x_id(
             {"usuario": $scope.usr_global, "compareciente": {"idcompareciente": $rootScope.sel_com_to_add.idcompareciente}}, function (data) {
            if (data.exito) {
                $scope.domicilioConyuge = data.compareciente.domicilio;
               
            } else {
                ejecutaAlerta(2, "Ocurrió un error al obtener los datos del conyuge: " + data.estatus);
            }
        }, function (error) {
            alert("Ocurrió un error al obtener los datos del conyuge para actualizar: " + error.status);
        });
    
    catalogos.get_estado_civil($scope, function () {
        $timeout(function () {
                if($scope.contwo)
                $scope.contwo.estadoCivil = $scope.estados_civiles[0];
        }, 1000)
    });
    
    catalogos.get_tratamientos($scope, function () {
        $timeout(function () {
            if ($scope.contwo && !$scope.contwo.tratamiento) {
                $scope.contwo.tratamiento = $scope.tratamientos[0];
            } else {
                for (var i = 0; i < $scope.tratamientos.length; i++) {
                    if ($scope.contwo && $scope.tratamientos[i].dselemento == $scope.contwo.tratamiento.dselemento) {
                        $scope.contwo.tratamiento = $scope.tratamientos[i];
                        break;
                    }
                }
            }
            
            if ($scope.contre && !$scope.contre.tratamiento) {
                $scope.contre.tratamiento = $scope.tratamientos[0];
            } else {
                for (var i = 0; i < $scope.tratamientos.length; i++) {
                    if ($scope.contre && $scope.tratamientos[i].dselemento == $scope.contre.tratamiento.dselemento) {
                        $scope.contre.tratamiento = $scope.tratamientos[i];
                        break;
                    }
                }
            }
        }, 500)
    });
    
    catalogos.get_tipos_personas($scope, function () {
        $timeout(function () {
            if($scope.contwo)
                $scope.contwo.persona.tipopersona = $scope.tipos_personas[0];
        }, 1000);
    });
    
    
    catalogos.get_regimenes($scope, function () {
        $timeout(function () {
            if ($scope.contwo && !$scope.contwo.regimen) {
                $scope.contwo.regimen = $scope.regimenes[0];
            } else {
                for (var i = 0; i < $scope.regimenes.length; i++) {
                    if ($scope.contwo && $scope.regimenes[i].dselemento == $scope.contwo.regimen.dselemento) {
                        $scope.contwo.regimen = $scope.regimenes[i];
                        break;
                    }
                }
            }
            
            if ($scope.contre && !$scope.contre.regimen) {
                $scope.contre.regimen = $scope.regimenes[0];
            } else {
                for (var i = 0; i < $scope.regimenes.length; i++) {
                    if ($scope.contre && $scope.regimenes[i].dselemento == $scope.contre.regimen.dselemento) {
                        $scope.contre.regimen = $scope.regimenes[i];
                        break;
                    }
                }
            }
            
            
        }, 500)
    });
    
     catalogos.get_nacionalidad($scope, function () {
        $timeout(function () {
            if ($scope.contwo && !$scope.contwo.persona.nacionalidad)
                $scope.contwo.persona.nacionalidad = $scope.nacionalidad[1];
                $scope.contre.persona.nacionalidad = $scope.nacionalidad[1];
        }, 1000)
    });
    
    catalogos.get_ocupaciones($scope);

    
    
    if ($rootScope.comp_cony_edit) {
        console.info("Datos para actualizar",$rootScope.comp_cony_edit)
        //Obtengo los datos del conyuge para actualizar
        if(!$rootScope.comp_cony_edit.ismismoconyuge){
            $scope.contwo = $rootScope.comp_cony_edit.conyugeCompra;
            $scope.contre = $rootScope.comp_cony_edit.conyugeActual;
            if($scope.contre!=null){
               $scope.contre.persona.fechanacimiento =  $scope.parseFecha($scope.contre.persona.fechanacimiento);
            }
            if($scope.contwo!=null){
               $scope.contwo.persona.fechanacimiento= $scope.parseFecha($scope.contwo.persona.fechanacimiento);
            }
            $timeout(function(){
                $scope.ismismoconyuge = "false";
                $("#ocupacionActual_value").val($scope.contre.dsocupacion);
                $scope.nomismo();
            },2000);
        }else{
             console.info("conyugeCompra",$scope.contwo);
            console.info("conyugeActual",$scope.contre);
            $rootScope.comp_cony_edit.conyugeActual = null;
            $scope.contwo = $rootScope.comp_cony_edit.conyugeCompra;
            $scope.contwo.persona.fechanacimiento= $scope.parseFecha($scope.contwo.persona.fechanacimiento);
        }
        
        if($scope.contwo && $scope.contwo.domicilio){
            $rootScope.domicilio = $scope.contwo.domicilio;
        }
        $timeout(function(){
            if($scope.contwo)
                $("#ocupacion_value").val($scope.contwo.dsocupacion);
        },100);
    
        //$scope.contwo.persona.fechanacimiento = parseFecha($scope.contwo.persona.fechanacimiento);
        delete $rootScope.comp_cony_edit;
    }
    
    
    $timeout(function () {
        if($scope.contwo)
        $("#amboscompran").prop("value", $scope.contwo.amboscompran);
        if($scope.contre)
        $("#amboscompran2").prop("value", $scope.contre.amboscompran);
        
    }, 500);
  
    $scope.actualiza_nombre_completo = function () {
        var tcom = "";
        if ($scope.con.persona.dsnombre && $scope.con.persona.dsnombre != "") {
            tcom += $scope.con.persona.dsnombre + " ";
        }
        if ($scope.con.persona.dsapellidopat && $scope.con.persona.dsapellidopat != "") {
            tcom += $scope.con.persona.dsapellidopat + " ";
        }
        if ($scope.con.persona.dsapellidomat && $scope.con.persona.dsapellidomat) {
            tcom += $scope.con.persona.dsapellidomat + " ";
        }
        $scope.con.persona.dsnombrecompleto = tcom;
    }
    $scope.actualiza_nombre_completo1 = function () {
        var tcom = "";
        if ($scope.contwo.persona.dsnombre && $scope.contwo.persona.dsnombre != "") {
            tcom += $scope.contwo.persona.dsnombre + " ";
        }
        if ($scope.contwo.persona.dsapellidopat && $scope.contwo.persona.dsapellidopat != "") {
            tcom += $scope.contwo.persona.dsapellidopat + " ";
        }
        if ($scope.contwo.persona.dsapellidomat && $scope.contwo.persona.dsapellidomat) {
            tcom += $scope.contwo.persona.dsapellidomat + " ";
        }
        $scope.contwo.persona.dsnombrecompleto = tcom;
    }
    
    $scope.actualiza_nombre_completo2 = function () {
        var tcom = "";
        if ($scope.contre.persona.dsnombre && $scope.contre.persona.dsnombre != "") {
            tcom += $scope.contre.persona.dsnombre + " ";
        }
        if ($scope.contre.persona.dsapellidopat && $scope.contre.persona.dsapellidopat != "") {
            tcom += $scope.contre.persona.dsapellidopat + " ";
        }
        if ($scope.contre.persona.dsapellidomat && $scope.contre.persona.dsapellidomat) {
            tcom += $scope.contre.persona.dsapellidomat + " ";
        }
        $scope.contre.persona.dsnombrecompleto = tcom;
    }
    
    
    $scope.guarda_conyuge = function () {
        $scope.contwo.dsocupacion = $("#ocupacion_value").val();
        $scope.contwo.persona.fechanacimiento = parseFecha($scope.contwo.persona.fechanacimiento) + "T00:00:00-06:00";
        
         //Victor: POR AHORA SETEO EN AUTOMATICO EL DOMICILIO DEL CONYUGE EN LO QUE SE DEFINE LA FUNCIONALIDAD QUE TENDRÁ
        $scope.contwo.domicilio = $scope.domicilioConyuge;
        
        
        if($scope.ismismoconyuge!="true"){
            $scope.contre.persona.fechanacimiento = parseFecha($scope.contre.persona.fechanacimiento) + "T00:00:00-06:00";
            $scope.contre.dsocupacion = $("#ocupacionActual_value").val();
            $scope.contre.domicilio = $scope.domicilioConyuge;
        }
        
       
        //FIN Comentario
         modal.show();
        conexion_app.add_conyuge({"usuario": $rootScope.usr_global, "compConyuge": {"ismismoconyuge": $scope.ismismoconyuge, "sujeto": $rootScope.sel_com_to_add, "conyugeCompra": $scope.contwo,"conyugeActual":$scope.contre }}, function (data) {
            if (data.exito) {
                ejecutaAlerta(1, 'Se registró correctamente el conyuge');
                $timeout(function () {
                    force_redirect('index.html#/comparecientes');
                }, 1000);
            } else {
                ejecutaAlerta(2, 'Ocurrió un error:' + data.estatus);
            }
        }, function (error) {
            alert("Algo malo ocurrió al guardar el conyuge" + error.status);
        }).$promise.finally(destroyModal);
    }
    $scope.buscar_clientes = function () {
        catalogos.busca_personas($scope, $scope.contwo.persona.dsnombrecompleto, function () {
            if (typeof $scope.clientes_resultado != "undefined") {
                $scope.haveResults = true;
            } else {
                $scope.guarda_conyuge();
            }
        });
    }
    $scope.add_data_persona = function (idpersona) {
        conexion_app.buscar_cliente_detalle({"usuario": $scope.usr_global, "persona": {"idpersona": idpersona}}, function (data) {
            if (data.exito) {
                $scope.contwo.persona = data.persona;
                if (data.persona.compareciente != null) {
                    if (data.persona.compareciente.tratamiento && data.persona.compareciente.tratamiento != null) {
                        for (var i = 0; i < $scope.tratamientos.length; i++) {
                            if ($scope.tratamientos[i].dselemento == data.persona.compareciente.tratamiento.dselemento) {
                                $scope.contwo.tratamiento = $scope.tratamientos[i];
                                break;
                            }
                        }
                    }
                } else {
                    if($scope.contwo)
                    $scope.contwo.tratamiento = {};
                }
                $timeout(function () {
                    $scope.guarda_conyuge();
                }, 1000)
            } else {
                ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
            }
        }, function (error) {
            alert("Algo malo paso al obtener el detalle de la persona" + error.status);
        });
        $scope.actualiza_nombre_completo1();
    };
    
     ponDatos = function(){
        $scope.contwo.persona = $scope.personaFound.originalObject;
        $scope.contwo.persona.fechanacimiento = $scope.parseFecha($scope.contwo.persona.fechanacimiento);
        
         $rootScope.domicilio = $scope.contwo.persona.domicilio;
            
            
        
        modal.show();
        conexion_app.buscar_contacto_x_persona({"idpersona": $scope.contwo.persona.idpersona}, function (data) {
                    $scope.contwo.contacto = data;
                    delete $scope.contwo.contacto.idpersona;
                }, function (error) {
                    alert("Algo malo paso al obtener el teléfono o email: " + error.status);
                }).$promise.finally(destroyModal);
         
          if ($scope.contwo.persona.nacionalidad && $scope.contwo.persona.nacionalidad != null) {
            for (var i = 0; i < $scope.nacionalidad.length; i++) {
                if ($scope.nacionalidad[i].dselemento == $scope.contwo.persona.nacionalidad.dselemento) {
                    $scope.contwo.persona.nacionalidad = $scope.nacionalidad[i];
                    break;
                }
            }
        }
        if ($scope.contre.persona.nacionalidad && $scope.contre.persona.nacionalidad != null) {
            for (var i = 0; i < $scope.nacionalidad.length; i++) {
                if ($scope.nacionalidad[i].dselemento == $scope.contre.persona.nacionalidad.dselemento) {
                    $scope.contre.persona.nacionalidad = $scope.nacionalidad[i];
                    break;
                }
            }
        }
        
        $(".ispersonadata").prop("disabled", "true");
        
        
        if (!$scope.contwo.persona.fechanacimiento) {
            $("#fechanacimiento").removeAttr("disabled");
        }
        if (!$scope.contwo.persona.dslugarnacimiento) {
            $("#lugarnacimiento").removeAttr("disabled");
        }
        if (!$scope.contwo.persona.nacionalidad) {
            $(".nacionalidad").removeAttr("disabled");
        }
        if (!$scope.contwo.persona.dsrfc) {
            $(".rfc").removeAttr("disabled");
        }
        if (!$scope.contwo.persona.dscurp) {
            $(".curp").removeAttr("disabled");
        }
        
        $scope.onDuplicadoData = true;
         $scope.$apply();
        
    }
    
    is_mismo = function(mismo){
        $scope.mismo_domicilio=mismo;
    }
    
    
    $scope.nomismo = function(){
        if($scope.ismismoconyuge =="true"){
            $(".actual").hide("slow");
        }else{
            $(".actual").show("slow");
        }
    }
    
    $scope.siguiente = function(){
        
        $scope.contwo.dsocupacion = $("#ocupacion_value").val();
        $scope.contwo.persona.fechanacimiento = parseFecha($scope.contwo.persona.fechanacimiento) + "T00:00:00-06:00";
        
        $rootScope.compConyuge = {"ismismoconyuge": false, "sujeto": $rootScope.sel_com_to_add, "conyugeCompra": $scope.contwo};
        delete $rootScope.sel_com_to_add;
        $scope.goTo("/comparecientes_conyuge_domicilio");

    }
    
    $scope.cancel_select_persona = function () {
        $(".ispersonadata").removeAttr("disabled");
        $scope.contwo.persona = {};
        $scope.onDuplicadoData = false;
    }
    
     $scope.edit_campos_persona = function () {
        if (confirm("Se actualizarán los datos de la persona, esto podría afectar los datos en otros actos ¿Desea continuar?")) {
            $(".ispersonadata").removeAttr("disabled");
            $(".ispersonadata").css("background-color", "#F5F6CE");
        }
    }
   
}