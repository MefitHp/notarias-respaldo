// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA
// COMMENT COMMIT DATA

function ctrl_escritura_elaboracion($scope,$timeout,$rootScope,conexion_app,conexion,funcionesutiles,catalogos){$scope.obt_doc_parcial=function(){conexion_app.docnot_par_ultima_version({"usuario":$scope.usr_global,"documentoParcial":{"escritura":{"idescritura":$rootScope.edit_esc}}},function(data){if(data.exito){$scope.doc_parcial=data.documentoParcial;$timeout(function(){$scope.doc_parcial.txtdoc=$scope.doc_parcial.txtdoc.replace(/<pre>/g,"");$scope.doc_parcial.txtdoc=$scope.doc_parcial.txtdoc.replace(/<\/pre>/g,"");CKEDITOR.instances["plan_ck"].insertHtml($scope.doc_parcial.txtdoc);},500)}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener el documento parcial: "+error.status);});}
$scope.popLateral=function(url_cargar,columnas,status){funcionesutiles.popLateral(url_cargar,columnas,status);if(status>0)
delete $rootScope.actos;$rootScope.actos=$scope.actos;}
console.info("nuevo dato");
$scope.obtenEscritura=function(){catalogos.obten_escritura_x_id($scope,$rootScope.edit_esc);}
if($rootScope.edit_esc&&$rootScope.edit_esc!=""){catalogos.get_expediente_x_tramite($scope,$scope.obtenEscritura);$scope.obt_doc_parcial();}
$scope.regresar_lista_esc=function(){delete $rootScope.edit_esc;$scope.goTo("/escrituras");}
$scope.guarda_escritura=function(master){$scope.doc_parcial.escritura=$scope.escritura;$scope.doc_parcial.txtdoc=CKEDITOR.instances["plan_ck"].getData();switch(master){case 0:$scope.doc_parcial.isGenerarMaster=false;$scope.doc_parcial.iscerrado=false;break;case 1:if(!confirm("¿Desea generar una versión de esta escritura?"))return false;$scope.doc_parcial.isGenerarMaster=false;$scope.doc_parcial.iscerrado=true;break;case 2:if(!confirm("Al generar un master, se creará un nueva escritura en la carpeta de documentos. \n¿Desea continuar?"))return false;$scope.doc_parcial.isGenerarMaster=true;$scope.doc_parcial.iscerrado=true;$("#btn_master_gener").text("Procesando master...")
break;default:}
conexion_app.docnot_par_guardar({"usuario":$scope.usr_global,"documentoParcial":$scope.doc_parcial,},function(data){if(data.exito){ejecutaAlerta(1,"Se guardó correctamente la escritura");if(master==2){$("#btn_master_gener").text("Master generado")}}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus)}},function(error){alert("Algo malo paso al guardar la escritura: "+error.status);});};}