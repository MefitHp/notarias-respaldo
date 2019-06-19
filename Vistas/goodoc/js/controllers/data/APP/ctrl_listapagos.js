function ctrl_listapagos($scope, conexion_app, conexion, catalogos,$route) {
            $scope.validaCuenta($scope.usr_global,"caja");    

            catalogos.get_pagos_pendientes($scope);
            
            $scope.actualiza_status = function(pago,idtarea){
                if(!confirm("¿Este documento esta pagado en su totalidado?")) return;
                    if(pago.pago){
                        pago.pago.statuspago = "PAGADO";
                    }
                conexion_app.caja_status_pago({
                    "usuario":$scope.usr_global,
                    "pago":pago.pago,
                    "idtarea":idtarea
                },function(data){
                    if(data.exito){
                        ejecutaAlerta(1,"Se actualizó correctamente el pago");
                        $route.reload();
                        
                    }else{
                        ejecutaAlerta(2,"Ocurrió un error al actualizar el pago: "+data.status);
                    }
                },function(error){
                    alert("Algo malo paso al guardar el pago:"+error.status);
                })
                
            }
            
    $scope.lanza_detalle = function(pago) {
        console.info(pago)
        Avgrund.show("#pago");
        $("#pago").fadeIn("slow");
        $scope.pagoselect = pago;
    }
        
    }

