define(['../module'],function(controllers){
    'use strict';
    controllers.controller("ctrl_index",function ctrl_index($scope,$rootScope,$location,$timeout,$routeParams,conexion,conexion_app,catalogos,$window){$scope.persona={};$scope.persona.dsnombrecompleto="";$timeout(function(){centrarElemento('#contenedor_general',"#wrap_prin_center");},50);window.onbeforeunload=confirmaSalida;function confirmaSalida(){if(!cerrarSesion){return"Se cerrará la sesión";}}
$rootScope.IVA=0.16;

$scope.asignaTramite=function(exp,url,obj){
    console.info("entro al tramite y cambio",url);
    $rootScope.idtramite=exp.tramite.idtramite;$rootScope.expediente=exp;
    var view = "";
    if(typeof url=="undefined"){
        url = "exp";
    }
      switch (url){
          case "exp":
            view = "asignacion_operaciones";
            break;
            case "esc":
            view = "escritura_nopaso";
            if(typeof obj!="undefined"){
                if(typeof obj.numero != "undefined"){
                    $rootScope.escnumero = obj.numero; 
                }
                if(typeof obj.id != "undefined"){
                    $rootScope.escId =obj.id
                
                }
                if(typeof obj.idtarea != "undefined"){
                    $rootScope.idtarea = obj.idtarea;
                }
                
            }
            
            break;
            default:
            view = "asignacion_operaciones";
            
      }
    force_redirect('index.html#/'+view+'?'+exp.tramite.idtramite);
    
}

/*
 * 
 * @param {type} idtarea
 * @param {type} paso si paso la escritura o no, solo sirve para el input de la tarea de no paso o si paso
 *               lo tuve que hacer uno por uno por que no serializaba los objetos
 * @param {type} cb
 * @returns {undefined}
 * 
 * 
*/
$scope.ejecutaTarea = function(idtarea,param,valor,cb){
            
         modal.show();
         var datostarea = 
                 {"usuario":$rootScope.usr_global,
                    "idTarea":idtarea,
                  "paso":false,
                  "prorroga":false
                 }
        datostarea[param]  = valor;
        conexion_app.tareas_exe(datostarea,function(data){
                                        if (data.exito){
                                            ejecutaAlerta(1,"La tarea se ejecuto correctamente");
                                            if(typeof cb !="undefined") cb();
                                        }else{
                                            ejecutaAlerta(2,"Ocurrio un error al ejecutar la tarea se ejecuto correctamente")
                                        }
                    },function(error){
                        alert("Algo malo ocurrio al procesar la tarea");
                    }).$promise.finally(destroyModal);
}

$scope.buscarExpedientes=function(){if($scope.persona.dsnombrecompleto==""){ejecutaAlerta(2,"Introduzca el nombre del cliente a buscar");return;};modal.show();conexion_app.expediente_busqueda({"usuario":$rootScope.usr_global,"persona":$scope.persona,},function(data){$scope.resultados=data.lista;$scope.goTo("/resultados");},function(error){alert('Algo malo paso al obtener los resultados'+error.status);}).$promise.finally(destroyModal);}
if($rootScope.resultados){$scope.resultados=$rootScope.resultados;$rootScope.resultados='';}
$rootScope.parseFecha=function(valor){if(valor==""||typeof valor=="undefined"||valor==null)
return valor="00-00-0000";valor=parseFecha(valor);return valor;}
$scope.refreshListVars=function(callback,filtrado){conexion.var_listar({"usuario":$scope.usr_global,"filtrado":filtrado},function(data){if(data.exito){$scope.lista=data;if(typeof callback!="undefined")
callback();return $scope.lista;}else{ejecutaAlerta(2,"Ocurrió un error:"+data.estatus);}},function(error){console.log("Algo malo ocurrió :: Error "+error.status);});}
$rootScope.template="views/lateral/subir_documento.html";$rootScope.carga_contenido=function(url){$rootScope.template="views/lateral/"+url+'.html';console.info("actualizo el template"+$rootScope.template);}
$rootScope.lpop=true;
$scope.idsesionactual=($location.search()).idsesionactual;
$scope.idusuario=($location.search()).idusuario;
$scope.nombreUsuario=($location.search()).nombreUsuario;

$scope.urlUser = function(url){
    url += "?idusuario="+$scope.usr_global.idusuario;
    url +="&idsesionactual="+$scope.usr_global.idsesionactual;
    url +="&nombreUsuario="+$scope.usr_global.dsnombre + " " + $scope.usr_global.dspaterno+ " "+ $scope.usr_global.dsmaterno;
    window.open(url,"pizarron");
}

$scope.goTo=function(url){$location.url(url);}


$scope.print_tarjeton = function(idtramite){
                    var data = "idtramite="+idtramite+"&";
                    data+="idusuario="+$rootScope.usr_global.idusuario+"&";
                    data += "idsesionactual="+$rootScope.usr_global.idsesionactual;
                    window.open("/tarjeton/tarjeton.html?"+data,"tarjeton");
                    return;
}
                    
                    

$scope.$on('$routeChangeSuccess',function(event,next,current){
        if($location.path()!="/"){$("#menuLat").fadeIn("slow");
                setTimeout(function(){
                    $scope.listar_tareas();
                    
                },3000);
    }
            else{$("#menuLat").fadeOut("slow");$("#wrap_actos_seleccionado").hide("slow");}
            if(generalCount<1){if(typeof $scope.idsesionactual!="undefined"||typeof $scope.idusuario!="undefined"){conexion.checkSesion({idsesionactual:$scope.idsesionactual,idusuario:$scope.idusuario},function(data){
            $scope.usr_global=data;$rootScope.usr_global=data;set_usr_global(data);
            console.info("usuario",$scope.usr_global);
            $scope.listar_tareas();

           
            
        },function(error){ejecutaAlerta(2,"Ocurrió un error al validar el usuario: "+error.status);setTimeout(function(){cerrarSesion=true;force_redirect('login.html#/');},1500);});}else{cerrarSesion=true;force_redirect('login.html#/');}}else{generalCount=0;}
generalCount++;showNavExp($location.path());centrarElemento('#contenedor_general',"#wrap_prin_center");});

$scope.old = false;

$scope.listar_tareas = function(){
                conexion_app.bitacora_listar({
                    "usuario":$rootScope.usr_global
                },function(data){
                    if(data.exito){
                        $scope.bitacora_datos= data.bitacoras;
                        $scope.bitacora_size= $scope.bitacora_datos.length;
                    }
                    else{
                        ejecutaAlerta(2,"Ocurrió un error al obtener la bitácora del usuario")
                    }
                },function(error){
                    alert("Algo malo paso al obtener la lista de bitacoras")
                })

}

$scope.desactivaAlerta = function(idalerta){
    if(!confirm("Realmente deseas eliminar esta alerta")) return;
    
    conexion_app.bitacora_desactivar({
        "usuario":$scope.usr_global,
        "bitacorausuario":{
            "idbitusu":idalerta}
        },function(data){
            if(data.exito){
                    ejecutaAlerta(1,"Se elimino correctamente la alerta");
                    $scope.listar_tareas();

            }else{
                    ejecutaAlerta(2,"Algo malo ocurrio al eliminar la alerta"+data.estatus);
            }
        },function(error){
            alert("Algo malo apso al eliminar la alerta:"+error.status);
        }
    );
    
}


$scope.validaCuenta = function(usr,tipo){
    if(usr.rol.dsprefijo!=tipo){
                    console.info(usr);
                    ejecutaAlerta(2,"No se tiene acceso a este módulo");
                    window.history.back(-1);
        }
}




$rootScope.asignaExpedienteXid = function(idexpediente,url,obj){
     conexion_app.expediente_x_id({
        usuario:$scope.usr_global,
        expediente:{'idexpediente':idexpediente}
    }
        ,function(data){
            if(data.exito){
                $scope.asignaTramite(data.expediente,url,obj);
            }else{
                ejecutaAlerta(2,"Ocurrio un error al obtener el expediente para asignarlo en la bitacora")
            }
        },
        function(error){
            
        }
        
    )
}


$scope.lanza_comentarios = function(tipo,id,ubicacion,old,expediente){
        Avgrund.show("#comments_wrapp");
        $("#comments_wrapp").fadeIn("slow");
        $rootScope.tipoComment = (tipo!="e")?"Documento":"Expediente";
        catalogos.get_comentarioXObj($scope,id);
        $rootScope.idObjetoComment=id;
        $rootScope.tipoDocumentoComentario = tipo;
        console.log("Tiene comentarios: "+old);
        $scope.old = old;
        if(typeof ubicacion !="undefined"){
            $rootScope.tipoDocumentoNombre = ubicacion;
        }
        if(!$rootScope.expediente && typeof expediente != "undefined"){
            $rootScope.expediente = expediente;
        }
    }
    
 $scope.fechaComment = function(val){
     val = val.toString()
     val = val.substr(0,val.length - 3)
     return moment.unix(val).format("DD-MM-YYYY HH:mm");
 }
  
$scope.isguardadoComment = false;
$scope.guardaComentario = function(){
    if(typeof $scope.comentario_txt =="undefined" || $scope.comentario_txt ==""){
        ejecutaAlerta(2,"Debe colocar un comentario");
        return;
    }
    
    if(!$rootScope.expediente){
        $scope.isguardadoComment = false;
    }
    
    
    if($scope.isguardadoComment){
        $scope.comentario_txt = "Comentario en el documento \""+$rootScope.tipoDocumentoNombre+"\": "+$scope.comentario_txt;
        $rootScope.idObjetoComment = $rootScope.expediente.idexpediente;
        delete $rootScope.tipoDocumentoNombre;
        $scope.isguardadoComment = false;
        $scope.expediente = $rootScope.expediente;
    }
    
    $scope.setBitacora = ($rootScope.tipoDocumentoComentario=="e")?true:false;
    
    conexion_app.comentario_guardar({
        "comentario":{
            "dstexto":$scope.comentario_txt,
            "idobjeto":$rootScope.idObjetoComment
        },
        "usuario":$scope.usr_global,
        "expediente":$scope.expediente,
        "setBitacora":$scope.setBitacora
    },function(data){
        if(data.exito){
            if($rootScope.tipoDocumentoComentario=="d"){
                
            // VERIFICO SI ANTERIORMENTE YA TENÍA COMENTARIOS
                if(!$scope.old){
                        conexion_app.comentario_tienecomentdoc({
                            "comentario":{"idobjeto":$rootScope.idObjetoComment}
                        },function(data){
                            if(!data.exito) ejecutaAlerta(2,"Algo malo paso al colocar la alerta de comentario en documento:"+data.estatus);
                            else $scope.old = true;
                        },function(error){
                            ejecutaAlerta(2,"Algo malo paso, en la respuesta de la alerta de comentario en documento:"+error.status);
                        });

                    }
                    $scope.isguardadoComment = true;
                    $scope.tmpidObjeto = $rootScope.idObjetoComment +"";
                    $rootScope.tipoDocumentoComentario = "e";
                    $scope.guardaComentario();
                    
            }else{
                $scope.listar_tareas();
            }
            
            if($rootScope.expediente && !$rootScope.expediente.tienecomments){
                //VERIFICO SI EL EXPEDIENTE YA TENIA ANTES UN COMENTARIO
                  conexion_app.comentario_tienecomentexp({
                        "comentario":{"idobjeto":$rootScope.expediente.idexpediente}
                    },function(data){
                        if(!data.exito) ejecutaAlerta(2,"Algo malo paso al colocar la alerta de comentario en el expediente:"+data.estatus);

                    },function(error){
                        ejecutaAlerta(2,"Algo malo paso, en la respuesta de la alerta de comentario en el expediente:"+error.status);
                    })
            }
            
            ejecutaAlerta(1,"Se guardó correctamente el comentario");
            $scope.comentario_txt = "";
            if(typeof $scope.tmpidObjeto !="undefined"){
                $rootScope.idObjetoComment=$scope.tmpidObjeto;
                delete $scope.tmpidObjeto;
            }
        catalogos.get_comentarioXObj($scope,$rootScope.idObjetoComment);
        
    }else{
            ejecutaAlerta(2,"Ocurrió un error al guardar el comentario "+ data.estatus);
        }
    },function(error){
        alert("Algo malo paso al guardar el comentario "+error.status);
    })
}

});});


