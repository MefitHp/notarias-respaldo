    
    function ctrl_domicilio($scope,catalogos,conexion_app,$rootScope,$timeout) {
        
        catalogos.get_domicilios_x_acto($scope);

        if(!$rootScope.compConyuge){
            $scope.goTo("/comparecientes");
        }
        
        console.info("DOMI", $rootScope.domicilio)
        if($rootScope.domicilio){
            $timeout(function(){
                        $("#d_completo").val($rootScope.domicilio.dsdircompleta);
            },1000);
        }
        
        $scope.guarda_conyuge = function () {
        
        $rootScope.compConyuge.conyugeCompra.domicilio = $rootScope.domicilio;
        delete $rootScope.domicilio;
        //FIN Comentario
         modal.show();
        conexion_app.add_conyuge({"usuario": $rootScope.usr_global, "compConyuge": $rootScope.compConyuge}, function (data) {
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
        
        
        $scope.select_domicilio_prev = function (domicilio) {
        alert("En caso de modificar el domicilio actual se modificará en todos los otorgantes asociados")
        $rootScope.domicilio = domicilio;
        $rootScope.compConyuge.conyugeCompra.domicilio = domicilio;
        console.info("domicilio>>", domicilio);
        console.info("domicilio root>>", $rootScope.domicilio);
        $("#d_completo").val($rootScope.domicilio.dsdircompleta);
    }
        
        
        $scope.ver_domicilios = function () {
        $("#dom_asoc").slideToggle("slow");
    }
    
    $scope.desasociar_domicilio = function () {
        if ($rootScope.domicilio && $rootScope.domicilio.iddomicilio)
            delete $rootScope.domicilio.iddomicilio;
    }
    
    }

