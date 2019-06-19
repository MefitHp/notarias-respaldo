define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_pago2", function ctrl_pago2($rootScope, $scope, conexion_app, conexion, catalogos) {

       if($rootScope.idsolicitud && $rootScope.idsolicitud !=""){
            var idsolpago = $rootScope.idsolicitud;
       }else{
          force_redirect('index.html#/lista_solicitud_de_pago_pendientes');
       }
        
        $scope.comparecientes_filtrados = new Array();
        $scope.filtrar_comparecientes = function() {
            var lc = $scope.comparecientes;
            for (var i = 0; i < lc.length; i++) {
                var np = {};
                np.pos = i;
                np.idcomp = lc[i].compareciente.idcompareciente;
                np.nombre = lc[i].compareciente.persona.dsnombre;
                np.nombrecompleto = lc[i].compareciente.persona.dsnombrecompleto;
                np.apellido_paterno = lc[i].compareciente.persona.dsapellidopat;
                np.apellido_materno = lc[i].compareciente.persona.dsapellidomat;
                np.alias = lc[i].compareciente.alias;
                $scope.comparecientes_filtrados.push(np);
            }
            console.info("ESTE ES LO QUE TIENE FILTRO::***"+$scope.comparecientes_filtrados);

        }
        
        
        
        
        
        
        /*catalogos.get_acto_x_id($scope,function(){
            catalogos.get_comparecientes_simple($scope,$scope.acto,$scope.filtrar_comparecientes);
        })*/
        
        catalogos.get_comparecientes_completo($scope,{},$scope.filtrar_comparecientes);

        

        $scope.datosPagador = {};
        $scope.datoNombrePagador = {};
        $scope.solicitudPago = {};
        catalogos.get_expediente_x_tramite($scope)
        $scope.guardarDatosPagador = function() {
             if($("#comp_value").val()!=""){
                   if($scope.sel_comp && $scope.sel_comp.title != ""){
                      $scope.idcompareciente = $scope.sel_comp.originalObject.idcomp;
                   }
                   $scope.nombrecompletopagador = $("#comp_value").val()
                }else{
                    $scope.myForm.$valid = false;
                }
            if ($scope.myForm.$valid) {
                
                conexion_app.guardar_pagador_solicitud_anticipo({
                    "datosPagador": {
                        "dsrfc": $scope.rfc,
                        "dsdircompleta": $rootScope.domicilio.dsdircompleta
                    },
                    "usuario": $rootScope.usr_global,
                    "datoNombrePagador": {
                        "idCompareciente":$scope.idcompareciente,
                        "nombreCompletoPagador": $scope.nombrecompletopagador
                    },
                    "solicitudPago": {
                        "idsolpago": idsolpago
                    },
                    "expediente": $scope.expediente
                },
                function(data) {
                    if (data.exito) {
                        ejecutaAlerta(1, 'Se guardaron los datos del pagador');
                        reset_campos("myForm");
                        $rootScope.pagoRealizado = data.datosPagador.iddatofiscapago;
                        force_redirect('index.html#/recepcion_de_pago3');
                    } else {
                        ejecutaAlerta(2, 'Ocurrio un error: ' + data.status);
                    }
                },
                        function(error) {
                            ejecutaAlerta(2, 'Ocurrio un error al guardar datos del pagador: ' + error.status);
                        });

            } else {
                $scope.myForm.submitted = true;
            }
        }


    });



});

