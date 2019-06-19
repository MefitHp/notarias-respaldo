var Funcion=function(nombre,id){this.nombre=nombre;this.id=id;this.padre=nombre;this.index=false;this.parametros=[];this.actualizaPadre=[];this.getNombre=function(){return this.nombre;}
this.setNombre=function(nombre){this.nombre=nombre;}
this.getParametros=function(){return this.parametros;}
this.setParametros=function(parametros){this.parametros=parametros;}
this.agregaParametro=function(parametro){this.parametros.push(parametro);}
this.setPadre=function(padre){this.padre=padre;}
this.getPadre=function(){return this.padre;}
this.setIndex=function(index){if(this.padre!="ispadre"){}else{}
this.index=index;}
this.getIndex=function(){return this.index;}}
function escapeRegExp(string){return string.replace(/([.*+?^=!:${}()|\[\]\/\\])/g,"\\$1");}
function busca_ocurrencias(str,find){return str.split(find).length-1;}
function replaceAll(string,find,replace){return string.replace(new RegExp(escapeRegExp(find),'g'),replace);}
function obtieneValor(str,charini,charfin){var ini_pos=str.indexOf(charini)+1;var fin_pos=str.indexOf(charfin,ini_pos);return str.substring(ini_pos,fin_pos);}
function encuentraPosDato(str,ref){var ini_pos=str.indexOf(ref);if(ini_pos>-1){var fin_pos=str.indexOf("}",ini_pos)+1;var str_ref=str.substring(ini_pos,fin_pos);}else{str_ref="notfound"}
return str_ref;}
function encuentraDato(funcion,tipo){var str=funcion.parametros;var str_replace;var str_ref;var tipo_dato_ini="'";var tipo_dato_fin="'";var strreturn=""
var paramSplit=str.split(",");if(funcion.nombre=="si"||funcion.nombre=="function(){si"){switch(tipo){case"c":str_ref=encuentraPosDato(paramSplit[0],"${cmp:");if(str_ref!="notfound"){str_replace=obtieneValor(str_ref,"[","]");tipo_dato_ini="$('#"
tipo_dato_fin="').val()";paramSplit[0]=replaceAll(paramSplit[0],str_ref,tipo_dato_ini+str_replace+tipo_dato_fin);}
for(var i=0;i<paramSplit.length;i++){if(i>0)strreturn+="function(){";strreturn+=paramSplit[i];if(i>0)strreturn+="}";if(typeof paramSplit[i+1]!="undefined")
strreturn+=",";}
strreturn=strreturn.slice(0,(strreturn.length-2));strreturn+="})";return strreturn;break;default:break;}}else if(funcion.nombre=="asignaValor"||funcion.nombre=="function(){asignaValor"||funcion.nombre=="visible"||funcion.nombre=="function(){visible"||funcion.nombre=="activa"||funcion.nombre=="function(){activa"){switch(tipo){case"c":var armaParam="";for(var h=1;h<paramSplit.length;h++){armaParam+=paramSplit[h];}
str_ref=encuentraPosDato(armaParam,"${cmp:");if(str_ref!="notfound"){str_replace=obtieneValor(str_ref,"[","]");tipo_dato_ini="$('#"
tipo_dato_fin="').val()";console.info("REMPLAZO",tipo_dato_ini+str_replace+tipo_dato_fin);console.info("STRING",armaParam);armaParam=replaceAll(armaParam,str_ref,tipo_dato_ini+str_replace+tipo_dato_fin);}
str_ref=encuentraPosDato(paramSplit[0],"${cmp:");if(str_ref!="notfound"){str_replace=obtieneValor(str_ref,"[","]");tipo_dato_ini="'";tipo_dato_fin="'";paramSplit[0]=replaceAll(paramSplit[0],str_ref,tipo_dato_ini+str_replace+tipo_dato_fin);}
strreturn=paramSplit[0]+","+armaParam;return strreturn;break;default:break;}
for(var i=0;i<paramSplit.length;i++){strreturn+=paramSplit[i];if(typeof paramSplit[i+1]!="undefined")
strreturn+=",";}
return strreturn;}
else{switch(tipo){case"c":str_ref=encuentraPosDato(str,"${cmp:");str_replace=obtieneValor(str_ref,"[","]");tipo_dato_ini="'"
tipo_dato_fin="'"
break;case"f":str_ref=encuentraPosDato(str,"${frm:");str_replace=obtieneValor(str_ref,"[","]");tipo_dato_ini="'"
tipo_dato_fin="'"
break;case"g":str_ref=encuentraPosDato(str,"${gpo:");str_replace=obtieneValor(str_ref,":","}");break;case"v":str_ref=encuentraPosDato(str,"${var:");str_replace=obtieneValor(str_ref,":","}");break;default:}}
return replaceAll(str,str_ref,tipo_dato_ini+str_replace+tipo_dato_fin);}
function ordenaFunciones(exp){var funciones=[];var posicion_funciones=[];var nombres_funciones=[];var ids_funciones=[];for(var i=0;i<exp.length;i++){switch(exp[i]){case"f":if(exp[i+1]==":"){var posini=exp.indexOf("(",i);if(posini>-1)
{nombres_funciones.push(exp.substring(i+2,posini));ids_funciones.push(exp.substring(i+2,posini)+posini);posicion_funciones.push(posini);}}
break;case")":var inicio_funcion=posicion_funciones.pop();var nombre_funcion=nombres_funciones[nombres_funciones.length-1];var cadena_remplazo=[];var remplazo="";var idfuncion=ids_funciones.pop();remplazo=exp.substring((inicio_funcion-(nombre_funcion.length)-2),i+1);cadena_remplazo.push(remplazo);cadena_remplazo.push("@"+idfuncion+"@");funcion=new Funcion(nombres_funciones.pop(),"@"+idfuncion+"@");if(typeof ids_funciones[ids_funciones.length-1]!="undefined"){funcion.setPadre("@"+ids_funciones[ids_funciones.length-1]+"@");}else{funcion.setPadre("ispadre");}
funcion.actualizaPadre=cadena_remplazo;funcion.setParametros(exp.substring(inicio_funcion,i+1));funcion.setIndex(posicion_funciones.length);funciones.push(funcion);break;default:break;}}
//console.info(funciones)
return funciones;}
function buscarPadrePos(id_buscar,arreglo){for(var i=0;i<arreglo.length;i++){if(id_buscar==arreglo[i].id)
{return i;}}}
function parserFuncionesAparametro(funciones,exp){for(var i=0;i<funciones.length;i++){var fun=funciones[i];if(fun.getIndex()>0){var miPadrePos=buscarPadrePos(fun.getPadre(),funciones);funciones[miPadrePos].parametros=funciones[miPadrePos].parametros.replace(fun.actualizaPadre[0],fun.actualizaPadre[1]+" ;");}else{exp=exp.replace(fun.actualizaPadre[0],fun.actualizaPadre[1]);}}
return exp;}
function paserComponentesAparametro(funciones){for(var i=0;i<funciones.length;i++){var fun=funciones[i];var ocurr_componentes=busca_ocurrencias(fun.parametros,"${cmp:");var ocurr_variables=busca_ocurrencias(fun.parametros,"${var:");var ocurr_grupos=busca_ocurrencias(fun.parametros,"${gpo:");var ocurr_funciones=busca_ocurrencias(fun.parametros,"${frm:");for(var j=0;j<ocurr_componentes;j++){fun.parametros=encuentraDato(fun,'c');}
for(var j=0;j<ocurr_variables;j++){fun.parametros=encuentraDato(fun,'v');}
for(var j=0;j<ocurr_grupos;j++){fun.parametros=encuentraDato(fun,'g');}
for(var j=0;j<ocurr_funciones;j++){fun.parametros=encuentraDato(fun,'f');}}}
function creaStringDeFunciones(funciones,expresion){var finder=0;while(finder<funciones.length){for(var i=0;i<funciones.length;i++){var encuentroExp=expresion.indexOf(funciones[i].id);if(encuentroExp>-1){expresion=expresion.replace(funciones[i].id,funciones[i].getNombre()+funciones[i].getParametros());finder++;}}}
return expresion;}
function getExpresionParser(expresion){var funciones=ordenaFunciones(expresion);expresion=parserFuncionesAparametro(funciones,expresion);paserComponentesAparametro(funciones);expresion=creaStringDeFunciones(funciones,expresion);return expresion;}
var expresionSaca='f:si(${cmp: gravamen[grav_libre]} != true,f:visible(${cmp:gravamen[grav_canc_m_mom]},false) f:visible(${cmp: gravamen[grav_canc_por_separado]},false) f:visible(${cmp: gravamen[grav_canc_inscrito]} ,false)  f:visible(${cmp: gravamen[grav_num_escritura]},false) f:visible(${cmp:gravamen[grav_insc_folio_real]},false)  ,  f:visible(${cmp:gravamen[grav_canc_m_mom]},true) f:visible(${cmp: gravamen[grav_canc_por_separado]},true) f:visible(${cmp: gravamen[grav_canc_inscrito]} ,true)  f:visible(${cmp: gravamen[grav_num_escritura]},true) f:visible(${cmp:gravamen[grav_insc_folio_real]},true))';expresionSaca=getExpresionParser(expresionSaca);console.info("*********************************************************");console.info(expresionSaca);console.info("*********************************************************");function si(expresion,verdadero,falso){var verisfuncion=(typeof verdadero!="undefined"&&typeof verdadero=="function")?true:false;var falisfuncion=(typeof falso!="undefined"&&typeof falso=="function")?true:false;if(expresion){if(verisfuncion){verdadero();return true;}}else{if(falisfuncion){falso();return false;}}}
function asignar(componente,valor){$("#"+componente).prop("value",valor);}
function activa(componente,estado){var estado=(estado)?false:true;$("#"+componente).prop("disabled",estado);}
function visible(componente,estado){var aesconder=obtienePadreComponente(componente);console.info(aesconder.attr("class"));console.info(aesconder.attr("id"));if(estado)
aesconder.slideDown("slow");else
aesconder.slideUp("slow");}
function obtienePadreComponente(componente){var padreComponente;componente=$("#"+componente);if(componente.parent().attr("class")=="wrap_select"){padreComponente=componente.parent().parent().parent().parent();}else{padreComponente=componente.parent();}
return padreComponente;}