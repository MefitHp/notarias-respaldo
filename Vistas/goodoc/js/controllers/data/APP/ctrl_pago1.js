define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_pago1", function ctrl_pago1($scope,$rootScope,conexion_app, conexion, catalogos) {
        
        catalogos.get_expediente_x_tramite($scope,function(){
            catalogos.get_solicitudes_pago_x_exp($scope);
        });
        $scope.pagos_realizados  = new Array();
           
        $scope.setPagador= function(datosfiscal){
            for(var i = 0;i < datosfiscal.length;i++){
                var pagador = datosfiscal[i].dsnombrepagador;
                console.info("PAGADOS"+pagador);
            for(var j = 0;j < datosfiscal[i].listaDatoPago.length;j++){
                datosfiscal[i].pagador = pagador;
                datosfiscal[i].listaDatoPago[j].fechapago = parseFecha(datosfiscal[i].listaDatoPago[j].fechapago);
                datosfiscal[i].listaDatoPago[j].importepago = formato_numero(datosfiscal[i].listaDatoPago[j].importepago, 2, ".", ",");
                   $scope.pagos_realizados.push(datosfiscal[i]);
            }
               
            }
           return datosfiscal;
        }
        
       if($rootScope.idsolicitud && $rootScope.idsolicitud !=""){
            var idsolpago = $rootScope.idsolicitud;
       }else{
          force_redirect('index.html#/lista_solicitud_de_pago_pendientes');
       }
       
       catalogos.get_solicitud_pago_x_id($scope,idsolpago,function(){
           $scope.solicitudPago.valor = formato_numero($scope.solicitudPago.valor,"2",".",",");
           //$scope.solicitudPago.isanticipo = false;
           var datosfiscal = $scope.solicitudPago.listaDatosFiscalPago;
           
           if($scope.solicitudPago.expediente.abogado && $scope.solicitudPago.expediente.abogado !=null)
           {
            var abogado = $scope.solicitudPago.expediente.abogado;
            $scope.solicitante = abogado.dsnombre +" "+abogado.dspaterno+" "+abogado.dsmaterno;

           }else{
            $scope.solicitante = "";
           }
           $scope.solicitudPago.listaDatosFiscalPago = $scope.setPagador(datosfiscal);
           console.info($scope.solicitudPago.listaDatosFiscalPago);
           
       });

        
        

        $scope.siguientePag = function(){
            $scope.goTo("/recepcion_de_pago2");
        }

    });



});

