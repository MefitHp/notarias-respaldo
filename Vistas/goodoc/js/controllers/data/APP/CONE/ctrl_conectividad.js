function ctrl_conectividad($scope,conect_conexiones,$rootScope,catalogos,$timeout){$scope.expediente_search={}
$scope.obtieneExpedientes=function(){$scope.expediente_search={};$scope.expediente_search.pag=0;$scope.buscarExpedientesFiltro(0);}
$scope.totalPags=[];$scope.paginador=[];$scope.expediente_search.pag=0;$scope.cantidadTotal=0;var cantidadPaginador=10;var cantobtpag=(cantidadPaginador / 2);function cargaPaginador(cantidad){$scope.totalPags=[];$scope.cantidadTotal=cantidad-1;$scope.paginador=[];for(var i=0;i<$scope.cantidadTotal;i++){$scope.totalPags.push(i);}
if($scope.expediente_search.pag<cantobtpag)
var tmpPagInicial=0;else
var tmpPagInicial=$scope.expediente_search.pag-cantobtpag;$scope.paginador=$scope.totalPags.slice(tmpPagInicial,(tmpPagInicial+cantidadPaginador+1))
if($scope.expediente_search.pag<($scope.cantidadTotal-cantobtpag)){$scope.paginador.push("...")}}
function selectChild(child){$(".numeral_list ul li span").attr("class","");$("#linum"+child).find("span").addClass("current");console.info("selecciono el ",child);}
$scope.prevPage=function(){$scope.expediente_search.pag-=1;$scope.buscarExpedientesFiltro($scope.expediente_search.pag);}
$scope.prevPageLong=function(){$scope.expediente_search.pag=0;$scope.buscarExpedientesFiltro($scope.expediente_search.pag);}
$scope.nextPage=function(){$scope.expediente_search.pag+=1;$scope.buscarExpedientesFiltro($scope.expediente_search.pag);}
$scope.nextPageLong=function(){$scope.expediente_search.pag=$scope.cantidadTotal;$scope.buscarExpedientesFiltro($scope.expediente_search.pag);}
$scope.goToPage=function(page){if(page=="...")
page=$scope.expediente_search.pag+cantidadPaginador;$scope.expediente_search.pag=page;$scope.buscarExpedientesFiltro(page);}
$scope.getDetalleExpediente=function(expediente){$rootScope.expedienteRacoo=expediente;$scope.goTo("/conectividad/expedientes/detalle")}
$scope.buscarExpedientesFiltro=function(pagina_select){$scope.expediente_search.esc=parseInt($scope.expediente_search.esc);if(!$scope.expediente_search.esc)
delete $scope.expediente_search.esc;modal.show();conect_conexiones.busquedaExtedienteParametro($scope.expediente_search,function(data){if(data.exito){$scope.expedientes=data.vistaExpedienteList;cargaPaginador(data.totalPags);$timeout(function(){selectChild(pagina_select);},100)}else{ejecutaAlerta(2,"Ocurrió un error al obtener los expedientes:"+data.estatus)}},function(error){alert("algo malo paso al obtener los expedientes"+error.status);}).$promise.finally(destroyModal);}
$scope.obtieneExpedientes();}