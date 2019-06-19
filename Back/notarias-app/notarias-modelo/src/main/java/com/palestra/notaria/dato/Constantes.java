package com.palestra.notaria.dato;

import java.io.File;
import java.util.ArrayList;

public class Constantes extends Object{
	
	public static final String ESTATUS_ERROR_CONSULTAR_ARCHIVO = "Error al consultar el archivo registrado";

	public static final String VALENC = "devpal";
	public static final String OPERACION_REGISTRO = "Registro";
	public static final String OPERACION_ACTUALIZACION = "Actualización";
	public static final String OPERACION_ELIMINACION = "Eliminación";
	public static final String OPERACION_ADJUNTAR_DOC_DIGITAL = "Se adjunta el documento digital: ";
	
	public static final String ESTATUS_BUSQUEDA_CORRECTA = "Busqueda correcta";
	public static final String ESTATUS_REGISTRO_CORRECTO = "Registro correcto";
	
	public static final String ESTATUS_SESION_INVALIDA = "La sesión no es válida";
	public static final String ESTATUS_ERROR_CONSULTA = "Error al consultar datos";
	public static final String ESTATUS_FALTAN_PARAMETROS = "Faltan parametros";
	public static final String ESTATUS_FALTAN_SES_USU_EXP = "Faltan parametros: Sesion, Usuario o Expediente";
	
	public static final String ESTATUS_ERROR_REGISTRO ="No se logró guardar en Base de Datos";
	
	public static final String EXPEDIENTES_HOME = File.separator+"devpal"+File.separator+"notaria";
	public static final String PREVIOS_HOME = File.separator+"devpal"+File.separator+"notaria"+
												File.separator+".config"+File.separator+"prev";
	public static final String POSTERIORES_HOME = File.separator+"devpal"+File.separator+"notaria"+
												File.separator+".config"+File.separator+"post";
	
	public static final String DOCGENERALES_HOME = File.separator+"devpal"+File.separator+"notaria"+
			File.separator+".config"+File.separator+"generales";
	
	
	public static final String ID_DOCUMENTO_PREVIO = "eccbc87e4b5ce2fe28308fd9f2a7baf3";
	public static final String ID_DOCUMENTO_POSTERIOR = "c81e728d9d4c2f636f067f89cc14862c";
	public static final String ID_COMPRADOR = "c4ca4238a0b923820dcc509a6f75849b";
	public static final String ID_VENDEDOR = "c81e728d9d4c2f636f067f89cc14862c";
	public static final String ID_CONYUGE = "eccbc87e4b5ce2fe28308fd9f2a7baf3";
	public static final String ID_REPRESENTANTE = "a87ff679a2f3e71d9181a67b7542122c";
	public static final String ID_AUTORIZANTE = "e4da3b7fbbce2345d7772b0674a318d5";
	public static final String ID_ACREEDOR = "7a089f5f2edcce3b85386ebebde37fb6";
	public static final String ID_DEUDOR = "2a3a8bd6ad24e16e1e505824ee4da603";
	public static final String ID_GARANTE = "0fdd0aac4a225dd21651e0b8d58b4ef1";
	public static final String ID_APODERADO = "7a05e17a24e0913d3447cbddcdc650bf";
	public static final String ID_PODERDANTE = "406aab91592a618a19529092dfdc6104";
	public static final String ID_SOCIO = "1b1844daa452df42c6f9123857ca686c";
	public static final String ID_RATIFICANTE = "9d7ffda72b6b92aa10b5b92a50840ded";
	public static final String ID_TESTADOR = "353fc1c2b3ac23d7e579e48d91626a30";
	public static final String ID_ESPOSA = "ef72f789cacaf7014033a6a654e0cd9b";
	public static final String ID_HIJO = "31b397e3088041cb7da5138f51979d75";
	public static final String ID_COMPARECIENTE = "1b0a726c07e26581ec9db4d3de171370";
	public static final String ID_ACREDITADO = "3dbd099e36b1aa506e2fadcf45713619";
	public static final String ID_SOLIDARIO = "2d2c327b8e6b79eb7e5061e773b1131b";
	public static final String ID_ACREDITADOYGARANTE = "7a18b68d1eb63f70744c5fd943691b14";
	public static final String ID_CANCELAHIPOTECA = "88d7e3f0e734275185eed20aabe2fb5c";
	public static final String ID_DEMANDADO = "a1sdf23"; 
	public static final String ID_JUZGADO = "730bf5afe8461798096a78db2a567d2c";
	public static final String ID_BANCO = "4ab10179e578c4421552ab639aef64aa";
	
	
	

	
//	Rutas originales definidas por apolo
//	public static final String RUTA_REAL ="c:/real/";
//	public static final String RUTA_TEMPORAL ="c:/temporal/";
//  TODO: definir ruta final, quitar del tmp
//	public static final String RUTA_REAL ="C:\\uploaded\\";
	public static final String RUTA_REAL =  System.getProperty("user.home") + File.separator + "uploaded";
	public static final String RUTA_TEMPORAL ="/tmp/archivos/temporal/";
	
//	CONSTANTES PARA COMPARAR TIPOS DE PARAMETRO DE FUNCIONES
	public static final String COMPONENTE_STRING = "componente";
	public static final String CIERTOFALSO_STRING = "ciertofalso";
	public static final String VALOR_STRING = "valor";
	public static final String FUNCION_STRING = "funcion";
	public static final String CONDICION_STRING = "condicion";
	
	public static final String ESTATUS_BUSQUEDA_FALLIDA = "No se localizarón resultados.";
	public static final String ESTATUS_FALTAN_REQUERIDOS = "Faltan campos requeridos.";
	public static final String ESTATUS_VALIDACION_NEGOCIO_INVALIDA = "Falla en validaciones de negocio.";
	public static final String ESTATUS_ERROR_ACTUALIZACION = "No se logró actualizar la base de datos.";
	public static final String ESTATUS_ERROR_ELIMINAR = "Eliminación fallida.";
	public static final String ESTATUS_REGISTRO_INEXISTENTE = "No existe el registro";
	public static final String ESTATUS_ACTUALIZACION_CORRECTA = "Actualización correcta.";
	public static final String ESTATUS_ELIMINACION_CORRECTA = "Eliminiación correcta.";
	public static final String ESTATUS_ERROR_LISTAR = "Ocurrió un error al listar";
	public static final String ESTATUS_LISTADO_CORRECTO = "Listado correcto";
	public static final String ESTATUS_LLENADO_CORRECTO = "Reemplazo de variables correcto";
	public static final String ESTATUS_ERROR_GENERACION_TEXTO = "Error al reemplazar variables"; 
	public static final String ESTATUS_CAMBIO_ESTATUS_CORRECTO = "Se ha cambiado la cita a estatus: ";
	public static final String ESTATUS_ERROR_CANCELACION = "Error de cancelación";
	public static final String ESTATUS_ERROR_REPRESNTADOS_DUPLICADOS = "Ya existe un registro con ese nombre";
	public static final String ESTATUS_ERROR_COMPARECIENTE_DUPLICADO = "El compareciente dado no se puede representar a si mismo";
	public static final String ESTATUS_EXISTE_MASTER = "Ya existe documento notarial master, no es posible realizar la operación";
	public static final String ESTATUS_SWITCH_NOTARIO_CORRECTO = "Cambio de notario de la escritura dada correcto"; 
	public static final String ESTATUS_SWITCH_NOTARIO_INCORRECTO = "No fue posible cambiar el notario de la escritura dada";
	public static final String ESTATUS_DOCOBJ_GENERACION_FALLIDA = "No se pudo generar el texto del documento objeto";
	public static final String ESTATUS_ACTODOCUMENTO_ELIMINACION_NOPERMITIDA = "No es posible eliminar el documento, ya se ha entregado y aprovado";
	public static final String ESTATUS_ESTATUS_INCORRECTOS = "Favor de verificar el estatus dado, se requeriere CANCELADA o FINALIZADA";
	public static final String ESTATUS_NO_SE_ENCONTRO_ACTO = "No se logró obtener el acto";
	public static final String ESTATUS_NO_EXISTE_PLANTILLA = "No se logró obtener plantilla";
	public static final String ESTATUS_ERROR_SUBIR_DOC = "Error al subir el archivo";
	public static final String ESTATUS_ERROR_NO_EXISTE_TRA = "No existe tramite del expediente dado.";
	public static final String ESTATUS_ERROR_TRAMITE_YA_ASOCIADO = "El tramite dado, ya se encuentra asociado a otro expediente";
	public static final String ESTATUS_NO_TIENE_NUMESCRITURA = "La escritura dada no tiene numero de escritura, por lo que no se logró generar el MASTER.";
	
	public static final String DocObj1 = "DocObj1";
	public static final String MENSAJE_DocObj1 = "No existen formularios asociados al acto dado.";
	
	public static final String IVA = "IVA";
	public static final String ARCHIVO_NO_ENCONTRADO = "Error al leer el archivo ";
	public static final String NOBRE_VAR_REPEDITA = "El nombre de una variable no es único: ";
	
	
// CONSTANTES PARA TARJETON
	public static final String[] TARJETON_COMPRAVENTA = {"Inmueble","Antecedentes","Operación"};
	public static final String[] TARJETON_PODER = {"Poder"};
	public static final String[] TARJETON_CANCELACIONDEHIPOTECA = {"Inmueble","Antecedentes"};
	public static final String[] TARJETON_SOCIEDAD = {"Sociedad"};
	public static final String[] TARJETON_RATIFICACION = {"Ratificación"};
	public static final String[] TARJETON_TESTAMENTO = {"Testamento"};
	public static final String[] TARJETON_APERTURADECREDITO = {"Inmueble","Antecedentes"};
	public static final String[] TARJETON_GENERICO = {"Genérico"};
	public static final String[] TARJETON_ESPECIAL = {"Inmueble","Antecedentes","Operación"};
	public static final String[] TARJETON_EXPEDIENTEJUDICIAL = {"Operación"};
	public static final String[] TARJETON_PRUEBA1 = {"Quien compra","Quien vende","datos de la obra"};
	
	
// CONSTANTES PARA MANEJO DE DOCUMENTOS POSTERIORES
	
	public static final String PREVENTIVO = "Preventivo";

	
}
