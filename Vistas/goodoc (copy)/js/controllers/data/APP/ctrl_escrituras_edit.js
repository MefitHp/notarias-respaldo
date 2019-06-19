function ctrl_escrituras_edit($scope, $route, conexion_app, catalogos, $rootScope, $timeout) {
    $scope.escritura = {};
    $scope.tmphasnumescritura = false;
    $scope.cf_sinposteriores=false;
    $scope.istraslativa = false;
    $scope.hascertificado = false;
    $scope.sinpreventivo = true;

    $scope.parseaFechasEscritura = function (tipo) {
        var zonaH = (tipo == 0) ? "T00:00:00-06:00" : "";
        if ($scope.escritura.fechadepase != "null" && $scope.escritura.fechadepase != "undefined") {
            $scope.escritura.fechadepase += zonaH;
        } else {
            delete $scope.escritura.fechadepase;
        }
    }
    $scope.btn_borrar_escritura = function (idescritura) {
        if (confirm("Desea borrar la escritura")) {
            conexion_app.borrar_escritura({
                "usuario": $scope.usr_global,
                "escritura": {
                    "idescritura": idescritura
                }
            }, function (data) {
                if (data.exito) {
                    ejecutaAlerta(1, 'La escritura fue eliminada correctamente');
                    reset_campos('myForm');
                    $scope.buscar_escrituras_x_expediente();
                } else {
                    ejecutaAlerta(2, 'Ocurrio un error: ' + data.estatus);
                }
            }, function (error) {
                alert('Ocurrió un error al eliminar la escritura: ' + error.estatus);
            });
        }
    }
    $scope.btn_seleccionar_escritura = function (escritura) {
        $scope.escritura = escritura.escritura;
        $scope.actos = escritura.actos;
    }
    
    
    $scope.escritura_add_cf = function(){   
        if($scope.posterioresacto.length > 0){
            $scope.cf_sinposteriores=true;
        }
         Avgrund.hide();
         Avgrund.show("#confirmpost");
         $("#confirmpost").fadeIn("slow");

    }
    
    $scope.confirm_sinimpuestos = function(){
            $scope.cf_sinposteriores=true;
            $scope.sinpreventivo=false;
    }


    $scope.escritura_add = function () {
        
        //if(!$scope.tmphasnumescritura && $scope.escritura.dsnumescritura !=null){
        $scope.escritura.initproceso = true;
        //}
        
        
        if ($scope.myForm.$valid) {
            
            modal.show();

            //$scope.parseaFechasEscritura(0);
            conexion_app.guardar_escritura_edit({
                "usuario": $scope.usr_global,
                "expediente": $scope.expediente,
                "escritura": $scope.escritura,
                "istraslativa":$scope.istraslativa,
                "actos": $scope.actos
            }, function (data) {
                if (data.exito) {
                    ejecutaAlerta(1, 'La escritura fue registrada correctamente');
                    force_redirect('index.html#/escrituras');
                } else {
                    ejecutaAlerta(2, 'Ocurrió un error: ' + data.estatus);
                    $route.reload();
                }
            }, function (error) {
                alert("Algo malo paso al guardar la escritura: " + error.status);
                //$scope.parseaFechasEscritura(1);
            }).$promise.finally(function(){
                $scope.cerrarModal();
                destroyModal();
            });
            $scope.myForm.submitted = false;
        } else {
            $scope.myForm.submitted = true;
        }
    }
        if(typeof  $rootScope.numeroescritura == "undefined"){
            $rootScope.numeroescritura = 0;
            $scope.numeroescritura = 0;
        }else{
            $scope.numeroescritura = $rootScope.numeroescritura;
        }
        
    $scope.asignanumero = function () {
        $rootScope.numeroescritura = $scope.numeroescritura;
        var n = parseInt($scope.numeroescritura);
        var tmpesc = $scope.numeros[n];

        var libro = {
            idlibro: tmpesc.idlibro,
            innumlibro: tmpesc.libro
        };
        $scope.escritura.dsnumescritura = tmpesc.numeroescritura;
        $scope.escritura.libro = libro;
        
        // genero el select de números
        $timeout(function(){
            jQuery(".chosen-select").chosen({
            allow_single_deselect: true
            }).val($scope.numeroescritura).trigger('chosen:updated');
        },0);
        
    }

    $scope.obtenEscritura = function () {
        catalogos.obten_escritura_x_id($scope, $rootScope.edit_esc, function () {
            //$scope.parseaFechasEscritura(1);
            if ($scope.escritura.dsnumescritura != null) {
                $scope.tmphasnumescritura = true;
            }
            //if(typeof $rootScope.idacto=="undefined" || $rootScope.idacto==""){
                $scope.actosXescritura(function(){
                     $scope.posterioresEnActo($scope.expediente.idexpediente);
                });
            //}            
        });
        
        
    }
    
    
    if ($rootScope.edit_esc && $rootScope.edit_esc != "") {
        catalogos.get_expediente_x_tramite($scope, $scope.obtenEscritura);
    }

    ////////////////// posteriores
    
    $scope.actosXescritura = function(cb){
        conexion_app.escritura_actos_conforman({
            "usuario":$scope.usr_global,
            "escritura":$scope.escritura
        },
        function(data){
            if(data.exito){
                
                if(typeof $rootScope.idacto=="undefined" || $rootScope.idacto==""){
                    $rootScope.idacto = data.actos[0].idacto;
                }
                for(var i=0;i<data.actos.length;i++){
                    if(data.actos[i].nombreOperacion=="Compraventa"){
                         $scope.istraslativa = true;
                         $scope.sinpreventivo = true;
                         $scope.validaCertificado();
                    }else{
                       $scope.sinpreventivo = false;
                       catalogos.get_numeros_x_abogado($scope, $scope.expediente.abogado.idusuario, $scope.asignanumero);
                    }
                }
                if(typeof cb !="undefined") cb();
            }
        },
        function(error){
            alert("Algo malo paso al obtener los actos que conforman:"+error.status);
        });
    }
    
    
    $scope.posterioresEnActo= function(idexpediente,cb){
        conexion_app.obtener_posteriores_expediente({
            "idExpediente":idexpediente,
            "usuario":$scope.usr_global
        },function(data){
             if (data.exito) {
                    $scope.posterioresacto=data.listaPosteriores;
                    if(typeof cb !="undefined") {
                      cb();  
                    }
                    
                    if($scope.istraslativa){
                         
                         $scope.sinpreventivo=true;
                         // BUSCO SI TIENE PREVENTIVO
                         for(var i = 0;$scope.posterioresacto.length>i;i++){
                            if($scope.posterioresacto[i].nombre =="Preventivo"){
                                 $scope.sinpreventivo=false;
                                 break;
                             }
                        } 
                    }
                    
                    
                    
                    
            } else {
                ejecutaAlerta(2, "Ocurrió un error al registrar los documentos posteriores:" + data.estatus);
            }
        },function(err){
              alert("Algo malo paso al mostrar los documentos posteriores:" + err.status);
        })
    }

    $scope.validaPosteriores = function () {
        Avgrund.show("#validaPosteriores");
        $("#validaPosteriores").fadeIn("slow");
        $scope.msj_posteriores = true;
    }


    $scope.setPosteriores = function () {
        $scope.msj_posteriores = true;
        conexion_app.doc_add_posteriores_multi(
                {"actoDocumentos": $scope.posteriores_data, "usuario": $scope.usr_global}
        , function (data) {
            if (data.exito) {
                ejecutaAlerta(1, "Se registrarón correctamente los documentos solicitados");
                $scope.posterioresEnActo($scope.expediente.idexpediente);
                $scope.cerrarModal();
            } else {
                ejecutaAlerta(2, "Ocurrió un error al registrar los documentos posteriores:" + data.estatus);
                $scope.cerrarModal();
            }
        }, function (err) {
            alert("Algo malo paso al registrar los documentos posteriores:" + err.status);
            $scope.cerrarModal();
        }).$promise.finally(destroyModal);

        
    }

    $scope.isChecked = function (id) {
        var match = false;
        for (var i = 0; i < $scope.posteriores_data.length; i++) {
            if ($scope.posteriores_data[i].identificador == id) {
                match = true;
            }
        }
        return match;
    };


    $scope.posteriores_data = [];

    $scope.addPost = function (bool, post) {
        
        console.info("BOL:" + bool, "POST;;;" + post);
        var item = {"acto": {"idacto": $rootScope.idacto},
            "formatoPdf": post}
        if (bool) {
            // add item
            $scope.posteriores_data.push(item);
        } else {
            // remove item
            for (var i = 0; i < $scope.posteriores_data.length; i++) {
                if ($scope.posteriores_data[i].formatoPdf.identificador == post.identificador) {
                    $scope.posteriores_data.splice(i, 1);
                    break;
                }
            }
        }
    }

    $scope.choosePosteriores = function () {
        $scope.msj_posteriores = false;
        console.info($scope.msj_posteriores, "::");

    }


    $scope.listaposteriores = function () {
         Avgrund.hide();
         Avgrund.show("#validaPosteriores");
         $("#validaPosteriores").fadeIn("slow");
         conexion_app.doc_listar_posteriores_escritura({
                "idescritura":$scope.escritura.idescritura,
                "idlocalidad":$scope.expediente.tramite.locacion.idelemento,
                "usuario":$scope.usr_global
            },function (data) {
            if (data.exito) {
                $scope.listaPosteriores = data.docSubopList;
                console.info("POSTERIORES:",$scope.listaPosteriores);
            } else {
                    ejecutaAlerta(2, "Ocurrió un error al obtener los documentos posteriores");
                 Avgrund.hide();
                }
            },
                function (error) {
                    ejecutaAlerta(2, "Ocurrió un error al obtener los documentos posteriores");
                    Avgrund.hide();
                });
        
    }
    
    $scope.del_documento = function(id_doc) {
        if (confirm("¿Realmente deseas eliminar este documento?")) {
            conexion_app.eliminar_documento({
                "usuario": $scope.usr_global,
                "idactodoc": id_doc
            }, function(data) {
                if (data.exito) {
                    ejecutaAlerta(1, "El documento se eliminó correctamente");
                    $scope.posterioresEnActo($scope.expediente.idexpediente);
                } else {
                    ejecutaAlerta(2, "Ocurrió un error: " + data.estatus);
                }
            }, function(error) {
                alert("Algo malo paso al eliminar el documento: " + error.status);
            });
        }
    }
    
    
    $scope.cerrarModal = function () {
        Avgrund.hide(function () {
            $route.reload();
        });
        $("#validaPosteriores").fadeOut("slow");
    }
    
    $scope.cancelar_inicio = function(){
        $scope.cf_sinposteriores = false;
        $scope.cerrarModal();
    }
    
    
    $scope.validaCertificado = function(){
       conexion_app.valida_certificado_escritura(
               {
                   "usuario":$scope.usr_global,
                   "escritura":$scope.escritura
               },
            function(data){
               if(data.exito){
                   if(data.fechacertificado !=null && data.fechacertificado!=""){
                        $scope.hascertificado = true;
                        var moment_certificado = moment(data.fechacertificado);
                        $scope.fechacertificado = data.fechacertificado;
                        $scope.fechaterminocertificado = moment_certificado.add("days",90).format('DD-MM-YYYY');
                        $scope.restodias = moment_certificado.preciseDiff(new Date());
                   
                   
                        console.info("Fecha certificado:",$scope.fechacertificado);
                        console.info("Fecha CERTIFICADO FINALIZA:",$scope.fechaterminocertificado);
                        console.info("QUEDAN:",$scope.restodias);
                   
                   
                    }
                   
                   if(!$scope.istraslativa || ($scope.istraslativa && $scope.hascertificado)){
                       catalogos.get_numeros_x_abogado($scope, $scope.expediente.abogado.idusuario, $scope.asignanumero);
                    }
                   
               }else{
                   console.info("No se logro validar el certificado",data.estatus)
               } 
            },
            function(error){
                  console.info("Algo malo paso al validar el certificado",error.status)
            }) 
    }

}