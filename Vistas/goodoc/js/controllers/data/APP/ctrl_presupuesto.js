define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_presupuesto", function ctrl_presupuesto($scope, conexion_app, catalogos, $rootScope) {
        
//        $rootScope.presupuestos = new Array();
        
        catalogos.get_expediente_x_tramite($scope);
        catalogos.get_acto_x_id($scope);
        catalogos.get_concepto_pago($scope);
        
        
        $scope.formatoValor = function(valor){
            valor = formato_numero(valor,2,".",",");
            return valor;
           }
        
        
        
        
        
        
        setTimeout(function() {
            conexion_app.obtener_presupuesto_x_expediente({
                "usuario": $scope.usr_global,
                "expediente": $scope.expediente,
                
            },
            function(data) {
                
                if (data.exito) {
                    
//                    $scope.presupuestos = data.listaPresupuestosExp;
                    $scope.preTotal = data;
                    $scope.preTotal.sumaImporte=$scope.formatoValor($scope.preTotal.sumaImporte);
                    $scope.preTotal.sumaIva=$scope.formatoValor($scope.preTotal.sumaIva);
                    $scope.preTotal.sumaTotal=$scope.formatoValor($scope.preTotal.sumaTotal); 

                } else {
                    $rootScope.presupuestos = [];
                }
//                $scope.renderOp();
            },
                    function(error) {
                    }
            );

        }, 500);
        
        $scope.buscar_presupuesto_x_acto = function() {
            conexion_app.obtener_presupuesto_x_acto({
                "usuario": $scope.usr_global,
                "acto": $scope.acto
                
            },
            function(data) {
                
                if (data.exito) {
                    
//                    $scope.presupuestos = data.listaPresupuestosExp;
                    $scope.envio = data;
                    $scope.envio.sumaImporte=$scope.formatoValor($scope.envio.sumaImporte);
                    $scope.envio.sumaIva=$scope.formatoValor($scope.envio.sumaIva);
                    $scope.envio.sumaTotal=$scope.formatoValor($scope.envio.sumaTotal);

                } else {
                     $scope.envio = data;
                }
//                $scope.renderOp();
            },
                    function(error){
                    }
            );

        }
        
//        $scope.buscar_presupuesto_x_acto();
        setTimeout($scope.buscar_presupuesto_x_acto,1000);
        
        $scope.presupuesto_add = function() {

            if ($scope.myForm.$valid) {
                conexion_app.guardar_presupuesto({
                    "usuario": $scope.usr_global,
                    "presupuesto": $scope.presupuesto,
                    "acto":$scope.acto,
                    "expediente": $scope.expediente
                }, function(data) {
                    if (data.exito) {
                        ejecutaAlerta(1, 'El presupuesto fue registrado correctamente');
                        reset_campos('myForm');
                        $scope.buscar_presupuesto_x_acto();
            
                        } else {
                        ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                    }

                }, function(error) {
                   ejecutaAlerta(2, 'Ocurrió un error al suspender al usuario: ' + error.status);
                })

                $scope.myForm.submitted = false;

            } else {
                $scope.myForm.submitted = true;
            }
        }
        
        $scope.btn_borrar_presupuesto = function(presupuesto) {
            if(confirm("Desea borrar el presupuesto")){
                conexion_app.borrar_presupuesto({
                    "usuario": $scope.usr_global,
                    "presupuesto": presupuesto
                }, function(data) {
                    if (data.exito) {
                        ejecutaAlerta(1, 'El presupuesto fue eliminado correctamente');
                        reset_campos('myForm');
                        $scope.buscar_presupuesto_x_acto();

                        } else {
                        ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                    }

                }, function(error) {
                   ejecutaAlerta(2, 'Ocurrió un error al suspender al usuario: ' + error.status);
                });
            }
            
        }
        
       function busca_in_combo(obj_buscar,arreglo){
           $.each(arreglo,function(i){
               if(obj_buscar == this.conceptoPago ){
                   return i;
               }
           })
       }
        
        $scope.btn_seleccionar_presupuesto = function(presupuesto) {
            $scope.presupuesto = presupuesto;
            $("#conceptoPago").find(".tmp_opt").removeAttr("selected");
             $("#conceptoPago option[value="+ 1 +"]").attr("selected",true);
            
//            var apuntador = busca_in_combo($scope.presupuesto.conceptoPago,$scope.conceptoPagos);
//            alert('apuntador '+apuntador);
//            $scope.presupuesto.conceptoPago = $scope.conceptoPagos[apuntador];
        }
        
        $scope.btn_editar_presupuesto = function(presupuesto) {
            conexion_app.borrar_presupuesto({
                "usuario": $scope.usr_global,
                "presupuesto": presupuesto
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, 'El presupuesto fue actualizado correctamente');
                    reset_campos('myForm');
                    $scope.buscar_presupuesto_x_acto();

                    } else {
                    ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                }

            }, function(error) {
               ejecutaAlerta(2, 'Ocurrió un error al suspender al usuario: ' + error.status);
            });
            
        }
        
    });
    
//    $scope.presupuesto_add = function() {
//
//            if ($scope.myForm.$valid) {
//                conexion_app.guardar_presupuesto({
//                    "usuario": $scope.usr_global,
//                    "presupuesto": $scope.presupuesto
//                }, function(data) {
//                    if (data.exito) {
//                        ejecutaAlerta(1, 'El presupuesto fue registrado correctamente');
//                        reset_campos('myForm')
//                        } else {
//                        ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
//                    }
//
//                }, function(error) {
//                   ejecutaAlerta(2, 'Ocurrió un error al suspender al usuario: ' + error.status);
//                })
//
//                $scope.myForm.submitted = false;
//
//            } else {
//                $scope.myForm.submitted = true;
//            }
//        }



});

