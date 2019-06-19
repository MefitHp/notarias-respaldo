define(['../module','jquery'],function(service){'use strict';service.factory('catalogos',function(conexion,conexion_app,$timeout,$rootScope){return{get_catalogos:function($scope){conexion.obtiene_catalogos({"usuario":$scope.usr_global,},function(data){$scope.catalogos=data.tipoCatList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_tratamientos:function($scope,callback){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"tratamiento"}}},function(data){$scope.tratamientos=data.catalogoList;$scope.tratamientos=_.sortBy($scope.tratamientos,function(tratamiento){return tratamiento.dselemento;})
if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_folios_disponibles:function($scope,callback){
    conexion_app.fol_listar_disponibles({"usuario":$scope.usr_global},function(data){
    if(data.exito){
        $scope.foliosDisponibles=data.controlFolios;
        $scope.libro = data.libro;
        if(typeof callback!="undefined")
            callback();
        }
else{ejecutaAlerta(2,"Ocurrió un error al obtener los folios disponibles: "+data.estatus)}},function(error){alerta("Algo malo paso al obtener los folios: "+error.status);});},get_regimenes_fiscales:function($scope,callback){modal.show();conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"regimen_fiscal"}}},function(data){$scope.regimenes_fiscales=data.catalogoList;$scope.regimenes_fiscales=_.sortBy($scope.regimenes_fiscales,function(regimen){return regimen.dselemento;});if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);})},get_entidades:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"Entidad"}}},function(data){$scope.entidades=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_var_filtros:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"Variables Filtro"}}},function(data){$scope.var_filtros=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_lista_funciones:function($scope){conexion.fns_listar({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.funciones=data.funciones;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al listar las funciones: "+error.status);});},get_lista_variables:function($scope){conexion.var_listar({"usuario":$scope.usr_global,},function(data){$scope.variables=data.variableList;$scope.variablescompareciente=data.variableCompareciente;$scope.componentes=data.varFormDinamicos;$scope.componentessubform=data.componentesSubformulario;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_lista_grupos:function($scope){conexion.gpo_listar({"usuario":$scope.usr_global,},function(data){console.info(data);$scope.grupos=data.grupoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_regimenes:function($scope,callback){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"regimen"}}},function(data){$scope.regimenes=data.catalogoList;if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_nacionalidad:function($scope,callback){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"nacionalidad"}}},function(data){$scope.nacionalidad=data.catalogoList;if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_tipo_documento:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"tipo_documento"}}},function(data){$scope.tipos_documento=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_documentos_oficiales:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"docs_oficiales"}}},function(data){$scope.documentos_oficiales=data.catalogoList;console.info($scope.documentos_oficiales);},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_instituciones_gob:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"instituciones_gobierno"}}},function(data){$scope.instituciones_gobierno=data.catalogoList;console.info($scope.instituciones_gobierno);},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_medio_pago:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"medio_pago"}}},function(data){$scope.mediosPago=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_ocupaciones:function($scope,callback){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"ocupacion"}}},function(data){$scope.ocupaciones=data.catalogoList;if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_componentes:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"tipo_componente_formulario"}}},function(data){$scope.componentes=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_suboperaciones:function($scope,callback){conexion.suboperaciones_listar({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.suboperaciones=data.suboperacionList;if(typeof callback!="undefined")
callback();}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener las sub-operaciones: "+error.status);});},get_tipos_personas:function($scope,callback){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"tipo_persona"}}},function(data){$scope.tipos_personas=data.catalogoList;if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);});},busca_personas:function($scope,persona,callback){modal.show();conexion_app.buscar_cliente({"usuario":$scope.usr_global,"nombrePersona":persona},function(data){if(data.exito){$scope.clientes_resultado=data.resultados;}else{ejecutaAlerta(2,data.estatus);}
if(typeof callback!="undefined")
callback();},function(error){alert("Algo malo paso al buscar cliente: "+error.status);}).$promise.finally(destroyModal)},get_domicilios_x_acto:function($scope){conexion_app.domicilio_x_acto({"usuario":$scope.usr_global,"acto":{"idacto":$rootScope.idacto}},function(data){if(data.exito){$scope.domicilios_actos=data.domiciliosDeActo;}else{ejecutaAlerta(1)}},function(error){alert("Algo malo paso al obtener los domicilios");});},busca_personas_detalle:function($scope,nombrepersona,callback){conexion_app.buscar_personas({"usuario":$scope.usr_global,"persona":{"dsnombrecompleto":nombrepersona}},function(data){if(data.exito){$scope.personas_encontradas=_.sortBy(data.personaList,function(persona){return persona.dsnombrecompleto;})}else{ejecutaAlerta(2,data.estatus);}
if(typeof callback!="undefined")
callback();},function(error){alert("Algo malo paso al buscar cliente: "+error.status);});},get_estado_civil:function($scope,callback){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"estado_civil"}}},function(data){$scope.estados_civiles=data.catalogoList;if(typeof callback!="undefined")
callback();},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_locacion:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"locacion"}}},function(data){$scope.locacion=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_concepto_pago:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"concepto_pago"}}},function(data){$scope.conceptoPagos=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_tipo_dato:function($scope,callback){$scope.tipo_dato=conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"tipo_dato"}}},function(data){console.info("TIPOS DE DATOS",data);$scope.tipo_dato=data.catalogoList;if(typeof callback!="undefined")
callback();},function(error){console.log("aAlgo malo ocurrió: "+error.status);});},get_operadores:function($scope){$scope.tipo_dato=conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"operador"}}},function(data){console.info("OPERADORES",data);$scope.operadores=data.catalogoList;},function(error){console.log("Algo malo ocurrió: "+error.status);});},get_operaciones:function($scope,callback){conexion.operaciones_listar({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.sel_operaciones=data.operacionList;if(typeof callback!="undefined")
callback();}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("ocurrio un error al obtener las operaciones: "+error.status);});},get_subop_x_op:function($scope,operacion){conexion_app.obtener_subop_x_op({"usuario":$scope.usr_global,"operacion":operacion,},function(data){if(data.exito){$scope.suboperaciones=data.suboperacionList;}
else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener las suboperaciones: "+error.status);});},get_previos:function($scope){conexion.previos_listar({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.previosListar=data.documentoList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("ocurrio un error al obtener los documentos previos: "+error.status);});},get_posteriores:function($scope){conexion.posteriores_listar({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.posterioresListar=data.documentoList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Algo malo paso al obtener los documentos previos: "+error.status);});},get_expediente_x_tramite:function($scope,callback){modal.show()

conexion_app.obtener_expediente_x_tramite({"usuario":$scope.usr_global,"tramite":{"idtramite":$rootScope.idtramite}},function(data){if(data.exito){$scope.expediente=data.expediente;
    $rootScope.valoresTarjeton = data.valoresTarjeton;
    $rootScope.comparecientesActo =data.comparecientesCompletos;
    $rootScope.comentariosExpediente = data.comentariosExpediente;
    if(typeof callback!="undefined")
callback();}else{}},function(error){alert("Algo malo ocurrio al obtener el expediente: "+error.status);}).$promise.finally(destroyModal);},

get_abogados:function($scope,callback){
conexion.listar_X_rol({
        "usuario": $scope.usr_global,
        "rol": {
            "idrol": "eccbc87e4b5ce2fe28308fd9f2a7baf3",
            "dsprefijo": "abog"
        }
    }, function(data) {
        $scope.abogados = data.usuarioList;
          if(typeof callback!="undefined")
            callback();
    }, function(error) {
        alert("Algo malo ocurrió :: Error: " + error.status);
    });
},

get_datos_tarjeton:function($scope,callback){
modal.show()
conexion_app.obtener_datos_tarjeton({"usuario":$scope.usr_global,"tramite":{"idtramite":$rootScope.idtramite}},
    function(data){if(data.exito){
    $scope.expediente=data.expediente;
    $rootScope.valoresTarjeton = data.valoresTarjeton;
    $rootScope.comparecientesActo =data.comparecientesCompletos;
    $rootScope.comentariosExpediente = data.comentariosExpediente;
    if(typeof callback!="undefined")
    callback();}
},function(error){alert("Algo malo ocurrio al obtener los datos del tarjetón: "+error.status);}).$promise.finally(destroyModal);},


get_expedientes_x_usuario:function($scope)
{modal.show();conexion_app.expediente_listar_x_usuario({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.expedientes=data.lista;}
else{ejecutaAlerta("Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener el listado de los expedientes: "+error.status);}).$promise.finally(destroyModal)},get_expedientes_lista:function($scope)
{conexion_app.listar_expedientes({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.expedientes=data.lista;}
else{ejecutaAlerta("Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener el listado de los expedientes: "+error.status);});},get_vocacion_terreno:function($scope){conexion.obtiene_catalogo({"usuario":$scope.usr_global,"elementoCatalogo":{"catalogo":{"dsnombre":"vocacion"}}},function(data){$scope.vocaciones=data.catalogoList;console.info($scope.vocaciones);},function(error){alert("Algo malo ocurrió al obtener las vocaciones: "+error.status);});},get_estados:function($scope){conexion.obtiene_estados({"usuario":$scope.usr_global},function(data){$scope.estados=data.listaEstados;},function(error){alert("Algo malo ocurrió al obtener los estados: "+error.status);});},get_municipios:function($scope){conexion.obtiene_municipios({"usuario":$scope.usr_global},function(data){$scope.municipios=data.listaMunicipios;},function(error){alert("Algo malo ocurrió al obtener los municipios: "+error.status);});},get_localidades:function($scope){conexion.obtiene_localidades({"usuario":$scope.usr_global},function(data){$scope.localidades=data.listaLocalidades;},function(error){alert("Algo malo ocurrió al obtener las localidades: "+error.status);});},get_acto_x_id:function($scope,callback){conexion_app.obtener_acto_x_id({"usuario":$scope.usr_global,"acto":{"idacto":$rootScope.idacto}},function(data){if(data.exito){$scope.acto=data.acto;if(typeof callback!="undefined")
callback();}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Ocurrio un error al obtener el acto: "+error.status);});},get_actos:function($scope){conexion_app.obtener_actos({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.actos=data.actoList;if(typeof callback!="undefined")
callback();}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Ocurrio un error al obtener el acto: "+error.status);});},get_roles:function($scope){conexion.listar_rol({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.roles=data.rolesList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los roles: "+error.status);});},obtener_notarios:function($scope){conexion.listar_X_rol({"usuario":$scope.usr_global,"rol":{"idrol":"45c48cce2e2d7fbdea1afc51c7c6ad26","dsprefijo":"not"}},function(data){if(data.exito){$scope.notarios=data.usuarioList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Ocurrio un error al obtener los notarios: "+error.status);});},obtener_abogados:function($scope){modal.show();conexion.listar_X_rol({"usuario":$scope.usr_global,"rol":{"idrol":"eccbc87e4b5ce2fe28308fd9f2a7baf3","dsprefijo":"abog"}},function(data){if(data.exito){$scope.abogados=data.usuarioList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estaus);}},function(error){alert("Ocurrio un error al obtener los notarios: "+error.status);}).$promise.finally(destroyModal);},obtener_usuarios_caja:function($scope){conexion.listar_X_rol({"usuario":$scope.usr_global,"rol":{"idrol":"e4da3b7fbbce2345d7772b0674a318d5","dsprefijo":"caja"}},function(data){if(data.exito){$scope.usuarioscaja=data.usuarioList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Ocurrio un error al obtener los notarios: "+error.status);});},obtener_para_asignar:function($scope){conexion.listarParaAsignar({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.usuarios_asignar=data.usuarioList;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Ocurrio un error al obtener los usuarios para asignar: "+error.status);});},
buscar_actos_disponibles:function($scope,callback){conexion_app.buscar_actos_disponibles({"usuario":$scope.usr_global,"expediente":$scope.expediente},function(data){
    if(data.exito){$scope.actos=data.actos;if(typeof callback!="undefined")
callback();}
else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},
function(error){alert("Ocurrio un error al obtener los actos: "+error.status);});},
    obtener_libro:function($scope){conexion_app.obtener_libro({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.escritura.libro=data.libro;}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Ocurrio un error al obtener el libro: "+error.status);});},obtener_datos_tarjeta_amarilla:function($scope){conexion_app.tarjeta_x_acto({"usuario":$scope.usr_global,"acto":$scope.acto},function(data){if(data){$scope.datoActoDeTarjeta=data.datoActoDeTarjeta;$scope.listaComparecientes=data.listaComparecientes;}
else{ejecutaAlerta(2,"Ocurrio un error: "+data.estatus);}
console.info(data);},function(error){alert("Algo malo ocurrio al obtener los datos de la tarjeta amarilla: "+error.status);});},get_comparecientes_simple:function($scope,acto,callback){conexion_app.lista_comparecientes_simple({"compareciente":{"acto":acto},"usuario":$scope.usr_global},function(data){if(data.exito){$scope.comparecientes=data.comparecienteList;if(typeof callback!="undefined")
$timeout(callback,100);}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener a los comparecientes: "+error.status);});},get_comparecientes_x_expediente:function($scope){conexion_app.lista_comparecientes_simple({"compareciente":{"acto":{"expediente":$scope.expediente}},"usuario":$scope.usr_global},function(data){if(data.exito){$scope.comparecientes_x_expediente=data.comparecienteList;if(typeof callback!="undefined")
$timeout(callback,100);}else{ejecutaAlerta(2,'Ocurrió un error:'+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener a los comparecientes por expediente: "+error.status);});},get_comparecientes_bitacora:function($scope,callback){conexion_app.listar_bitacora_compareciente({"escritura":$scope.escritura,"usuario":$scope.usr_global},function(data){if(data.exito){$scope.comparecientes=data.listaComparecientes;if(typeof callback!="undefined")
$timeout(callback,100);}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener a los comparecientes: "+error.status);});},get_comparecientes_completo:function($scope,acto,callback){conexion_app.lista_comparecientes_completo({"compareciente":{"acto":acto},"usuario":$scope.usr_global},function(data){if(data.exito){$scope.comparecientes=data.comparecienteCompletos;if(data.escritura!=null){$scope.escritura = data.escritura};if(typeof callback!="undefined")
$timeout(callback,100);}else{ejecutaAlerta(2,'Ocurrió un error: '+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener a los comparecientes: "+error.status);});},get_tipo_comparecientes:function($scope,callback){conexion_app.obtener_tipo_comparecientes({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.tipo_comparecientes=data.tipoComparecienteList;if(typeof callback!="undefined")
callback();}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrio al obtener los comparecientes: "+error.status);});},get_autorizantes:function($scope){conexion_app.obtener_x_tipo_compareciente({"tipoCompareciente":"Autorizante","compareciente":{"acto":{"idacto":$rootScope.idacto}},"usuario":$scope.usr_global},function(data){if(data.exito){$scope.autorizantes=data.comparecienteList;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener los autorizantes: "+error.status);});},get_representantes:function($scope){conexion_app.obtener_x_tipo_compareciente({"tipoCompareciente":"Representante","compareciente":{"acto":{"idacto":$rootScope.idacto}},"usuario":$scope.usr_global},function(data){if(data.exito){$scope.representantes=_.uniq(data.comparecienteList,function(item){return item.persona.idpersona;});$scope.representantes
console.info("REPRESENTANTES",$scope.representantes);}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener los representantes: "+error.status);});},
obten_escritura_x_id:function($scope,id_esc,callback){
console.info("OK ESC:",id_esc);
    conexion_app.get_escritura_x_id(
            {
                "usuario":$scope.usr_global,
                "expediente":$scope.expediente,
                "escritura":{"idescritura":id_esc}
            },
               function(data){
                if(data.exito){
                    $scope.escritura=data.escritura;
                    $scope.actos=data.actos;
                    $scope.archivo = data.archivofinal;
                    $scope.paginas = data.paginas;
                    $scope.porcentaje = data.porcentajeUltimaPag;
                if(typeof callback!="undefined")
                    $timeout(callback,100);
                }else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}
}
,function(error){alert("Algo malo ocurrio al obtener la escritura: "+error.status);});},get_solicitudes_pago_x_exp:function($scope,callback){conexion_app.solicitudes_pago_x_exp({"usuario":$scope.usr_global,"expediente":$scope.expediente},function(data){if(data.exito){$scope.solicitudes=data.listaSolicitudes;if(typeof callback!="undefined")
$timeout(callback,100);}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener las solicitudes: "+error.status);});},get_solicitud_pago_x_id:function($scope,idpago,callback){conexion_app.solicitud_anticipo_x_id({"usuario":$scope.usr_global,"solicitudPago":{"idsolpago":idpago}},function(data){if(data.exito){$scope.solicitudPago=data.solicitudPago;if(typeof callback!="undefined")
$timeout(callback,100);}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo paso al obtener la solicitud de pago: "+error.status);});},

                                                                                                            
get_documentos_previos:function($scope,cb)
{
    modal.show();
    conexion_app.obtener_previos(
            {"usuario":$scope.usr_global,"idExpediente":$scope.expediente.idexpediente,"idacto":$rootScope.idacto},
            function(data){if(data.exito){
                    $scope.previos=data.listaPrevios;
                    if(typeof cb !="undefined") {
                       cb(); 
                    }
                }else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los documentos previos: "+error.status);}).$promise.finally(destroyModal);
},

get_documentos_posteriores:function($scope,cb){
    modal.show();conexion_app.obtener_posteriores(
            {"usuario":$scope.usr_global,"idExpediente":$scope.expediente.idexpediente,"idacto":$rootScope.idacto},
           function(data){if(data.exito){$scope.posteriores=data.listaPosteriores;$scope.acto={};$scope.acto.hasdim = data.hasdim;$scope.acto.hasanexo5 = data.hasanexo5;  if(typeof cb !="undefined") cb()}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los documentos posteriores: "+error.status);}).$promise.finally(destroyModal);},

get_documentos_originales:function($scope,cb){
    modal.show();
    conexion_app.obtener_originales({"usuario":$scope.usr_global,"idacto":$rootScope.idacto},
            function(data){if(data.exito){
                    
                    $scope.originales=data.listaOrigiales;
                    if(typeof cb !="undefined"){
                        cb();
                    } 
                }else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los documentos originales: "+error.status);}).$promise.finally(destroyModal);},

                                                                                                                get_usuarios:function($scope){
conexion.listar_usuarios({usuario:$scope.usr_global},function(data){$scope.user_lista=data.usuarioList;},function(error){alert("Algo malo ocurrió al obtener los usuarios :: Error "+error.status);});
}
,
get_tipoComparecientesFiltro:function($scope){
    conexion.listar_filtroTipoCompareciente({usuario:$scope.usr_global},function(data){
        if(data.exito){
            $scope.listaTipoComparecientesFiltro = data.listafiltros;
        }else{
            ejecutaAlerta(2,"Ocurrió un error al obtener la lista de filtros:"+data.estatus)
        }
    },function(error){
        alert("Algo malo paso al obtener la lista de filtros:"+error.status)
    })
}
,
get_gruposTrabajo:function($scope){
conexion.listar_grupos({usuario:$scope.usr_global},function(data){
            if(data.exito){f
                $scope.gruposTrabajo = data.grupos;
            }else{
                ejecutaAlerta(2,"Ocurrió un error al obtener los grupos de trabajo:"+data.estatus)
            }
        },function(error){
            alert("Algo malo paso al obtener los grupos de trabajo:"+error.status);
        });
},
get_tipoComparecienteXSubop:function($scope,cb){
    conexion.listar_filtroTipoComparecienteXsubOp({usuario:$scope.usr_global,"acto":{"idacto":$rootScope.idacto}},function(data){
            if(data.exito){
                $scope.tipo_comparecientes = data.tipoComparecienteList;
                if(typeof cb !="undefined"){
                    cb();
                }
            }else{
                ejecutaAlerta(2,"Ocurrió un error al obtener los tipos de compareciente:"+data.estatus)
            }
        },function(error){
            alert("Algo malo paso al obtener los tipos de compareciente:"+error.status);
        });
}
,
get_comentarioXObj:function($scope,idobjeto,cb){
    conexion_app.comentario_listar({usuario:$scope.usr_global,"comentario":{"idobjeto":idobjeto}},function(data){
            if(data.exito){
                $scope.comentarios = data.comentarios;
                if($rootScope.tipoDocumentoComentario=="e"){
                    $rootScope.comentariosExpediente = data.comentarios;
                }
                if(typeof cb !="undefined"){
                    cb();
                }
            }else{
                ejecutaAlerta(2,"Ocurrió un error al obtener los tipos de compareciente:"+data.estatus)
            }
        },function(error){
            alert("Algo malo paso al obtener los tipos de compareciente:"+error.status);
        });
},get_docs_mesa:function($scope,cb){
    conexion_app.mesactrl_listar({
            "usuario":$scope.usr_global
            },
            function(data){
                if(data.exito){
                    $scope.mesas = data.documentos;
                    if(typeof cb !="undefined") cb();
                }else{
                    ejecutaAlerta(2,"Ocurrió un error al listar los documentos:"+data.estatus);
                }
            },
            function(error){
                alert("Algo malo pasó al listar los documentos:"+error.status);
            });
},get_pagos_pendientes:function($scope,cb){
    conexion_app.caja_pagos_pendientes({
            "usuario":$scope.usr_global,
            "pago":{"statuspago":"PENDIENTE"}
            },
            function(data){
                if(data.exito){
                    $scope.pagos = data.pagos;
                    if(typeof cb !="undefined") cb();
                }else{
                    ejecutaAlerta(2,"Ocurrió un error al listar los pagos:"+data.estatus);
                }
            },
            function(error){
                alert("Algo malo pasó al listar los pagos:"+error.status);
            });
}
,
        get_tareas_asignadas:function($scope,cb){
            modal.show()

    conexion_app.tareas_asignadas({
            "usuario":$scope.usr_global            },
            function(data){
                if(data.exito){
                    $scope.tareas_asignadas = data.tareasasignadas;
                    if(typeof cb !="undefined") cb();
                }else{
                    ejecutaAlerta(2,"Ocurrió un error al listar las tareas asignadas:"+data.estatus);
                }
            },
            function(error){
                alert("Algo malo pasó al listar las tareas asignadas:"+error.status);
            }).$promise.finally(destroyModal);
}
,


get_numeros_x_abogado:function($scope,idabogado,cb){
  conexion_app.obtener_numeros_abogados({"usuario":$scope.usr_global,"idabogado":idabogado},
        function(data){
            if(data.exito){
                $scope.numeros = data.pendientes;
             if(typeof cb !="undefined") cb();
                }else{
                    ejecutaAlerta(2,"Ocurrió un error al listar los numeros x abogado:"+data.estatus);
                }
            },
            function(error){
                alert("Algo malo pasó al listar los numeros x abogado:"+error.status);
            });
                
},
   
//get_documentos_originales:function($scope){conexion_app.obtener_originales({"usuario":$scope.usr_global,"idExpediente":$scope.expediente.idexpediente},function(data){if(data.exito){$scope.originales=data.listaOrigiales;console.log("cargo originales");}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los documentos posteriores: "+error.status);});},
get_datos_combos_cita:function($scope){conexion_app.obtiene_combos_cita({"usuario":$scope.usr_global,"expediente":$scope.expediente},function(data){if(data.exito){$scope.documentos=data.comboDocumentos;$scope.invitados=data.comboInvitados;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los datos para llenar los combos de citas: "+error.status);});},get_formularios_x_acto_expediente:function($scope,tipo){conexion_app.obtener_lista_formularios_expediente({"usuario":$scope.usr_global,"expediente":$scope.expediente,"acto":{"idacto":$rootScope.idacto},"conformulario":{"tipoform":tipo}},function(data){if(data.exito){$scope.formularios=data.listaFormularios;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los formularios por acto: "+error.status);});},get_formularios_x_acto:function($scope,idsuboperacion){conexion_app.obtener_lista_formularios({"usuario":$scope.usr_global,"idsuboperacion":idsuboperacion},function(data){if(data.exito){$scope.formularios=data.formularioList;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Algo malo ocurrió al obtener los formularios por acto: "+error.status);});},get_gestores:function($scope){conexion.obtener_gestores({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.gestores=data.gestorList;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Ocurrió un error al obtener los gestores: "+error.status);});},get_valuadores:function($scope){conexion.obtener_valuadores({"usuario":$scope.usr_global},function(data){if(data.exito){$scope.valuadores=data.valuadorList;}else{ejecutaAlerta(2,"Ocurrió un error: "+data.estatus);}},function(error){alert("Ocurrió un error al obtener los valuadores: "+error.status);});},}});});