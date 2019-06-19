

define(['../../module'], function(controllers) {
    'use strict';
    controllers.controller("ctrl_prev_post", function ctrl_prev_post($scope, catalogos,$rootScope) {
      
        catalogos.get_previos($scope);
        catalogos.get_posteriores($scope);
        
        $scope.documentos_previos = new Array();
        $scope.documentos_posteriores = new Array();
        
        
        function validaLongitudArreglo(tipo,mivariable){
            var content =(tipo=="pre")?$scope.documentos_previos:$scope.documentos_posteriores;
            
            if(mivariable && mivariable.length){
                addArray(content,mivariable)
            };
            
        }
        validaLongitudArreglo("pre",$rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec].previos);
        validaLongitudArreglo("post",$rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec].posteriores);
        
        
        $scope.add_doc_prev = function(tipo){
            var contenedor = (tipo =="pre")?$scope.documentos_previos:$scope.documentos_posteriores;
            var seleccionado = (tipo=="pre")?$scope.sel_previo:$scope.sel_posterior;
            if(!seleccionado.dsdescripcion) return;
            var lista = (tipo=="pre")?$scope.previosListar:$scope.posterioresListar;
            contenedor.push({"documento":seleccionado,"status":"C"});
            removeItem(lista,seleccionado);
            $scope.sel_previo = {};
            $scope.sel_posterior={};
        }
        
        
        function removeItem(content,objeto){
            var index = content.indexOf(objeto); 
            if(index>-1){
                content.splice(index,1);
                
            }
        }
        
        
        
        function addArray(contenedor,contenido){
            for(var i=0;i<contenido.length;i++){
                contenedor.push(contenido[i]);
            }
                
                console.info($rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec]);
            
        }
        
        
        $scope.guarda_total_previo = function(){
            $rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec].previos =new Array();
            addArray($rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec].previos,$scope.documentos_previos);
            alert("Se actualizarón correctamente los documentos");

        }
        
        
        $scope.guarda_total_posterior= function(){
            $rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec].posteriores = new Array();
            addArray($rootScope.operaciones[$rootScope.operacionselec].listaActos[$rootScope.suboperacionselec].posteriores,$scope.documentos_posteriores);
            alert("Se actualizarón correctamente los documentos");
        }
        
        var tmp_delete_doc = new Array();
        $scope.delete_documento=function(id,tipo){
           var contenedor = (tipo =="pre")?$scope.documentos_previos:$scope.documentos_posteriores;
            if(confirm("¿Realmente desea eliminar el documento del acto seleccionado?")){
                contenedor[id].status = "E";
            }
         alert("Se actualizarón correctamente los documentos");
            
        }
       
       
    });



});


