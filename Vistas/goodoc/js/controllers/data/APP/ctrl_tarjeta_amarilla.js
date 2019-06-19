define(['../../module'], function(controllers) {
    'use strict';

//    controllers.controller("ctrl_tarjeta_amarilla", function ctrl_tarjeta_amarilla($scope, conexion_app, conexion, catalogos) {
    controllers.controller("ctrl_tarjeta_amarilla", function ctrl_tarjeta_amarilla($scope, $timeout, conexion_app, catalogos, $rootScope) {


        if ($rootScope.have_ta && $rootScope.have_ta != "") {

            conexion_app.obtener_ta({
                "tarjetaAmarilla": {
                    "idtrjamarilla": $rootScope.have_ta
                },
                "usuario": $rootScope.usr_global
            },
            function(data) {
                if (data.exito) {
                    $scope.tarjetaAmarilla = data.tarjetaAmarilla;
                    $scope.tarjetaAmarilla.fecha = parseFecha($scope.tarjetaAmarilla.fecha);

                    $("#clientes_value").val($scope.tarjetaAmarilla.dsnomcliente);
                    $scope.calculaHonorario();
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: "+ data.estatus);
                }

            },
                    function(error) {
                        alert("Algo malo paso al obtener la tarjeta amarilla: "+ error.status);
                    });

        }

        $scope.regresarLista = function() {
            delete $rootScope.have_ta;
            force_redirect("index.html#/tarjeta_amarilla");
        }

        $scope.calculaHonorario = function() {
            if (!$scope.tarjetaAmarilla.honorario)
                $scope.tarjetaAmarilla.honorario = "0.00";
            if (!$scope.tarjetaAmarilla.ide)
                $scope.tarjetaAmarilla.ide = "0.00";

            var h = deformato_numero($scope.tarjetaAmarilla.honorario);
            var ide = deformato_numero($scope.tarjetaAmarilla.ide);

            var h = parseFloat(h);
            var ide = parseFloat(ide);
            var piva = h * $rootScope.IVA;
            var subhiva = h + piva;
            var total = subhiva + ide;

            $scope.tarjetaAmarilla.honorario = formato_numero(h, 2, ".", ",");
            $scope.tarjetaAmarilla.ide = formato_numero(ide, 2, ".", ",");

            $scope.tarjetaAmarilla.porcentajeIVA = formato_numero(piva, 2, ".", ",");
            $scope.tarjetaAmarilla.subhiva = formato_numero(subhiva, 2, ".", ",");
            $scope.tarjetaAmarilla.total = formato_numero(total, 2, ".", ",");


        };

        $scope.parseFecha = function() {


        }
        
        
        function set_campos(campo){
            var inp_show = campo.dscodigo;
            //$("."+inp_show).show("slow");
            if(inp_show !="hon"){
            $("."+inp_show).find("input").prop("disabled",false);
            }else{
                $("."+inp_show).slideDown("slow");
            }
        }
        
        
        $scope.get_campos = function(){
            conexion_app.listar_campos({
                "usuario":$scope.usr_global,
                "acto":{
                    "idacto":$rootScope.idacto
                }
            },function(data){
                if(data.exito){
                    _.each(data.camposTarjetaAmarillaList,function(campo,index){
                        set_campos(campo);
                    })
                    
                }
                else{
                    ejecutaAlerta(2,"Ocurrió un error: "+ data.estatus);
                }
                
            },
              function(error){
                alert("Algo malo paso al obtener los campos a listar: "+ error.status);  
              });
        }
        
        
        $scope.guardarTarjeta = function() {

          

            if ($scope.myForm.$valid) {
                
            $scope.tarjetaAmarilla.fecha = parseFecha($scope.tarjetaAmarilla.fecha)+"T00:00:00-06:00";
            $scope.tarjetaAmarilla.dsnomcliente = $("#clientes_value").val();
            $scope.tarjetaAmarilla.ide = deformato_numero($scope.tarjetaAmarilla.ide);
            $scope.tarjetaAmarilla.honorario = deformato_numero($scope.tarjetaAmarilla.honorario);
            $scope.tarjetaAmarilla.total = deformato_numero($scope.tarjetaAmarilla.total);
            $scope.tarjetaAmarilla.porcentajeIVA = deformato_numero($scope.tarjetaAmarilla.porcentajeIVA);
            $scope.tarjetaAmarilla.subhiva = deformato_numero($scope.tarjetaAmarilla.subhiva);
            //$scope.tarjetaAmarilla.numesc = $scope.datoActoDeTarjeta.numEscritura; // REVISAR EL NUMERO DE ESCRITURA
            $scope.tarjetaAmarilla.acto = $scope.acto;
            $scope.tarjetaAmarilla.acto.expediente = $scope.expediente;
            $scope.tarjetaAmarilla.usuarioelab = $rootScope.usr_global;
            
            

                if (!$rootScope.have_ta) {
                    conexion_app.guardar_ta({
                        "usuario": $rootScope.usr_global,
                        "tarjetaAmarilla": $scope.tarjetaAmarilla,
                    },
                    function(data) {
                        if (data.exito) {
                            ejecutaAlerta(1, "Se guardó correctamente la Tarjeta");
                            $timeout(function() {
                                $scope.regresarLista();
                            }, 2000);
                        }

                        else {
                            ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                            $scope.tarjetaAmarilla.fecha = parseFecha($scope.tarjetaAmarilla.fecha);

                        }
                    },
                            function(error) {
                                alert("Algo malo ocurrió al guardar la tarjeta amarilla: "+ error.status);
                                $scope.tarjetaAmarilla.fecha = parseFecha($scope.tarjetaAmarilla.fecha);
                            })
                } else {


                    conexion_app.actualizar_ta(
                            {
                                "usuario": $rootScope.usr_global,
                                "tarjetaAmarilla": $scope.tarjetaAmarilla
                            },
                    function(data) {
                        if (data.exito) {
                            ejecutaAlerta(1, "Se actualizó correctamente la tarjeta amarilla");
                            delete $rootScope.have_ta;
                            $timeout(function() {
                                $scope.regresarLista();
                            }, 2000);
                        }

                        else {
                            ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                            $scope.tarjetaAmarilla.fecha = parseFecha($scope.tarjetaAmarilla.fecha);

                        }
                    },
                            function(error) {
                                alert("Algo malo ocurrió al actualizar la tarjeta amarilla: "+ error.status);
                                $scope.tarjetaAmarilla.fecha = parseFecha($scope.tarjetaAmarilla.fecha);
                            });



                }


            } else {
                
                $scope.myForm.submitted = true;

            }


        };

        $scope.tarjetaAmarilla = {};
        $scope.tarjetaAmarilla.acto = {};
        $scope.tarjetaAmarilla.usuarioelab = {};


        $scope.get_campos();

        catalogos.get_expediente_x_tramite($scope);
        catalogos.get_acto_x_id($scope, function() {
            catalogos.obtener_datos_tarjeta_amarilla($scope);
            
        });


    });



});
