define(['../../module'],function(controllers){'use strict';controllers.controller("ctrl_operaciones_actos",function ctrl_operaciones_actos($scope,$route,$compile,$rootScope,funcionesutiles,conexion_app){$scope.parseaDocumentosLista=function(arreglo){var tmpArr=new Array();_.each(arreglo,function(arr,i){var tmparr=arr;arr={};arr.documento=tmparr;arr.status="C";tmpArr.push(arr);})
return tmpArr;}
$scope.btn_suboperacion=function(contenedor,index_op){var tmpPrevios=$scope.parseaDocumentosLista($scope.sel_suboperacion.previosList);var tmpPosteriores=$scope.parseaDocumentosLista($scope.sel_suboperacion.posterioresList);if(typeof $scope.sel_objNombre=="undefined"){$scope.sel_objNombre=$scope.sel_suboperacion.dsnombre;}
$scope.cantidadSubOp=$rootScope.operaciones[index_op].listaActos.length;contenedor.append($compile("<sub-operacion id='subopt"+index_op+$scope.cantidadSubOp+"' nombre = '"+$scope.sel_objNombre+"' idopt='"+index_op+"' idsubopt='"+$scope.cantidadSubOp+"'></sub-operacion>")($scope));$rootScope.operaciones[index_op].listaActos.push({"suboperacion":$scope.sel_suboperacion});$rootScope.operaciones[index_op].listaActos[$scope.cantidadSubOp].status="C";$rootScope.operaciones[index_op].listaActos[$scope.cantidadSubOp].dsnombre=$scope.sel_objNombre;$rootScope.operaciones[index_op].listaActos[$scope.cantidadSubOp].previos=tmpPrevios;$rootScope.operaciones[index_op].listaActos[$scope.cantidadSubOp].previos.status="C";$rootScope.operaciones[index_op].listaActos[$scope.cantidadSubOp].posteriores=tmpPosteriores;$rootScope.operaciones[index_op].listaActos[$scope.cantidadSubOp].posteriores.status="C";$scope.sel_objNombre="";}
$scope.operacionSelect=function(op){$rootScope.operacionselec=op;conexion_app.obtener_subop_x_op({"usuario":$rootScope.usr_global,"operacion":{"idoperacion":$rootScope.operaciones[$rootScope.operacionselec].operacion.idoperacion},"tramite":{"idtramite":$rootScope.idtramite}},function(data){$scope.select_subop_actual=data.suboperacionList;},function(error){alert("ocurrió un error al obtener la lista de suboperaciones: "+error.status);});}
$scope.suboperacionSelect=function(subop){$rootScope.suboperacionselec=subop;console.info("SUBoperacionSelect",$rootScope)}
$scope.popLateral=function(url_cargar,columnas,status){funcionesutiles.popLateral(url_cargar,columnas,status);}
$scope.guarda_operacion=function(operacion){modal.show();var tmpOp=$rootScope.operaciones[operacion];tmpOp.locacion=$rootScope.sel_localidad;delete tmpOp.operacion.subOp;conexion_app.operacion_crear({"operacion":tmpOp,"expediente":$scope.expediente,"usuario":$rootScope.usr_global},function(data){if(data.exito){ejecutaAlerta(1,'La operación se guardó correctamente');delete $rootScope.sel_localidad;setTimeout(function(){$route.reload();},2000);}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert('Ocurrió un error al guardar la operacion: '+error.status);}).$promise.finally(destroyModal);}
$scope.elimina_operacion=function(id){if(confirm("Realmente desea eliminar la operación")){var obj_del={"usuario":$rootScope.usr_global,"expediente":$scope.expediente,"operacion":{"operacion":{"idoperacion":id}},}
conexion_app.operacion_borrar(obj_del,function(data){if(data.exito){ejecutaAlerta(1,'La operación fué borrada correctamente');setTimeout(function(){$route.reload();},2000);}
else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert('Ocurrió un error al guardar la operacion: '+error.status);});console.info("objeto a borrar",obj_del);}}
$scope.delete_documento=function(doc){if(confirm("¿Realmente desea eliminar el documento del acto seleccionado?"))
{doc.status="E";}}
$scope.delete_subOp=function(idopt,subop,idacto){if(confirm("¿Realmente desea eliminar el acto seleccionado?"))
{modal.show();conexion_app.eliminar_objeto_acto({"usuario":$rootScope.usr_global,"idacto":idacto},function(data){if(data.exito){ejecutaAlerta(1,"Se eliminó correctamente el elemento");}else{ejecutaAlerta(2,"Ocurrió un error al eliminar el elemento");}},function(error){alert("Algo malo ocurrió al eliminar el objeto del acto:"+error.status);}).$promise.finally(destroyModal);$route.reload();}}
$scope.activa_expediente=function(idacto,nombreActo,nombreope){
    $(".tarjeton_dinamic").removeClass("expediente_acto");        
    
    conexion_app.expediente_tarjeton({
        "idActo":idacto,
        "nombreActo":nombreActo,
        "operacionNombre":nombreope
    },function(data){
    if(!data.exito){
        ejecutaAlerta(2,"Ocurrió un error al obtener el tarjetón:"+data.estatus);
    }else{
        $(".tarjeton_dinamic").hide(0);
        
        for(var i=0;i<data.tarjeton.length;i++){
            $("#enav"+i).text(data.tarjeton[i]).addClass("expediente_acto");
        }
        if(!data.tieneDocumentos){
            $("#btnDocs").removeClass("expediente_acto");
        }else{
            $("#btnDocs").addClass("expediente_acto");
        }
        
        $(".expediente_acto").fadeIn("slow");
        
        $("#wrap_actos_seleccionado").show("slow");
        
        
        $("#acto_seleccionado").text(nombreActo);
        $rootScope.idacto=idacto;
    }
        
    },
    function(error){
        alert("Algo malo pasóal obtener el tarjetón:"+error.status);
    })
    
    
    
}
$scope.activa_escritura=function(){force_redirect('index.html#/escrituras');}});});