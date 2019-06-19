package com.palestra.notarias.escritura;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notarias.constantes.EstilosFormatoDocumento;
import com.palestra.notarias.helper.DocNotMasterHelper;
import com.palestra.notarias.helper.DocNotParcialHelper;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.service.OpenOfficeService;
import com.palestra.notarias.utils.ArchivoUtils;
import com.palestra.notarias.utils.EscrituraUtills;

/**
 * Clase para generar un documento notarial .doc basado tomando como base un
 * documento master e insertarlo en alfresco.
 * 
 * @author sofia
 * 
 */

public class EscrituraMasterGenerator {

	static Logger logger = Logger.getLogger(EscrituraMasterGenerator.class);

	/** Llamadas a helper **/
	DocNotParcialHelper docNotParcialHelper;
	DocNotMasterHelper docNotMasterHelper;

	/** Llamadas a BO **/
	EscrituraBoImpl escrituraBo;
	ExpedienteBoImpl expedienteBo;
	EscrituraActoBoImpl escrituraActoBo;

	public EscrituraMasterGenerator() {
		docNotParcialHelper = new DocNotParcialHelper();
		docNotMasterHelper = new DocNotMasterHelper();
		escrituraBo = new EscrituraBoImpl();
		expedienteBo = new ExpedienteBoImpl();
		escrituraActoBo = new EscrituraActoBoImpl();
	}

	/**
	 * Genera documento notarial master a partir de la ultima version cerrada de
	 * documento notarial parcial y lo sube a alfresco por escritura
	 * 
	 * @param escritura
	 * @param idExpediente
	 * @param textoEnviado
	 * @param idusuario
	 * 
	 */
	public boolean generaMasterCompletoPorEscritura(Escritura escritura, String idExpediente, String textoEnviado, String idusuario) throws NotariaException, Exception {
		String segundaSustitucion;
		String textoAuxiliar;
		String textoFinal;
		try{
			//if (escrituraBo.tieneNumEscritura(escritura.getIdescritura())) {
				//logger.info("====> Generando documento master de la escritura: " + escritura.getIdescritura());
				/**
				 * asignar al filtro variable idescritura, para obtener valores en
				 * DB filtradas por escritura
				 **/
				FiltroVariables fvariables = new FiltroVariables();
				fvariables.setIdescritura(escritura.getIdescritura());
	
				EscrituraParcialGenerator pGenerator = new EscrituraParcialGenerator();
				/** Si valor variable==null, reemplazar por char '_' **/
				
				/** reemplazar las variables asociadas a escritura faltantes **/
				Boolean forceReplace = true;
				// --> cafaray 250215 ::: 
				segundaSustitucion = pGenerator.reemplazarVariables(fvariables, textoEnviado, forceReplace);
				// <-- cafaray
				
				if (segundaSustitucion == null) {
					logger.warn("====> No se ha logrado hacer la transformación antes del master, se continua con el texto enviado desde pantalla.: " + escritura.getIdescritura());
					textoAuxiliar = textoEnviado;
				} else {
					textoAuxiliar = segundaSustitucion;				
				}
				/** Haber el replace de #LETRAS por la letra consecutiva correspondiente **/
				textoFinal  = EscrituraUtills.replaceMarkerLetra(textoAuxiliar);			
				if(textoFinal==null){
					logger.warn("====> No se han logrado reemplazar las referencias a los anexos en #LETRA.");
					textoFinal = textoEnviado;
				}
				/**  guardar un documento notarial parcial, por que se hizo replace de variables. **/
				// --> cafaray 250215 ::: 
				this.docNotParcialHelper.guardarParcial(textoFinal, escritura.getIdescritura(), idusuario);
			    // <--
					
				/** Inserta el documento notarial master generado en alfresco **/
				boolean generoDoc = this.insertaEnAlfresco(escritura.getIdescritura(), idExpediente, textoFinal);
				//// VICTOR:20160204
				/// SE COMENTA TODO ESTO PUESTO QUE YA SE GUARDA LOCAL Y NO EN ALFRESCO ///
				//////////////
				if(generoDoc){
					/** Genera documento notarial master, con el texto dado **/
					this.generaDocumentoMaster(textoFinal, escritura.getIdescritura());
				} else {
					throw new NotariaException("No se ha logrado generar el documento de Master. Revise el log de operaciones para mayor información.");
				}
				
				return generoDoc;
			/*} else {
				logger.info(Constantes.ESTATUS_NO_TIENE_NUMESCRITURA);
				throw new NotariaException(Constantes.ESTATUS_NO_TIENE_NUMESCRITURA);
			}*/
		}catch(Exception e){
			e.printStackTrace(System.out);
			throw new NotariaException("Ha ocurrido una excepción al generar el documento Master. Revise el log de operaciones para mayor información.");
		}
	}

	/**
	 * Genera un documento notarial master de la escritura dada.
	 * 
	 * @param idEscritura
	 */
	public void generaDocumentoMaster(String textoDocumento, String idEscritura) throws NotariaException, Exception {

		if (textoDocumento == null || textoDocumento.isEmpty()) {
			logger.info("No existe documento notarial parcial asociado a la escritura dada.");
			throw new NotariaException(String.format("No existe el documento notarial parcial asociado a la escritura mencionada [%s].",idEscritura));
		}
		try{
			DocumentoNotarialMaster docNotMaster = docNotMasterHelper.getDocumentoMaster(idEscritura);
			if (docNotMaster == null) {
				/** Registrar el documento notarial master si no existe **/
				docNotMasterHelper.guardaDocumentoNotarialMaster(textoDocumento, idEscritura);
			} else {
				/** Editar el documenento notarial master **/
				docNotMasterHelper.editaDocumentoNotarialMaster(textoDocumento, docNotMaster);
			}
		}catch(Exception e){
			throw new NotariaException("Ha ocurrido una excepción al validar la existencia del master del documento. Revise el log de operaciones para más información.");
		}
	}

	/**
	 * Genera un .doc a partir del documento notarial master y lo inserta en
	 * alfresco.
	 * 
	 * @param idEscritura
	 */
	public boolean insertaEnAlfresco(String idEscritura, String idExpediente, String texto)
			throws Exception {

//		DocumentoNotarialMaster docMaster = docNotMasterHelper.getDocumentoMaster(idEscritura);
//		if (docMaster == null) {
//			logger.info("No existe documento notarial master asociado al expediente dado. ");
//			return false;
//		}
		String incialesNotario = escrituraActoBo.obtenNotarioDatoByEscrituraId(idEscritura);
		logger.info("=====> Inciales de Notario: " + incialesNotario);
		if (incialesNotario == null) {
			logger.warn("=====> No se logró obtener las iniciales del notario, se colocará una por default");
			incialesNotario = "notaria";
		}
		/*String numEscritura = escrituraBo.obtenNumEscritura(idEscritura);
		if (numEscritura == null) {
			logger.error("=====> La escritura dada no tiene numero de escritura asociada.");
			//throw new NotariaException(Constantes.ESTATUS_NO_TIENE_NUMESCRITURA);
		}*/
		String numExpediente = expedienteBo.obtenerNumExpedientePorId(idExpediente);
		if (numExpediente == null) {
			logger.error("No se pudo obtener el numero de expediente por id");
			throw new NotariaException(Constantes.ESTATUS_ERROR_NO_EXISTE_TRA);
		}
		
		// Victor:Ceron pidio que aunque no tenga número se pueda generar la escritura
		String numEscritura = escrituraBo.obtenNumEscritura(idEscritura);
		if (numEscritura == null) {
			numEscritura = "Escritura_Exp_"+numExpediente.replace("/", "-");
		}
		/** Convierte html a .doc **/
		String documentoGenerado = this.convierteDocumento(texto, numEscritura,numExpediente);

		if (documentoGenerado != null && !documentoGenerado.isEmpty()) {
			return true;
		}else {
			throw new NotariaException("Ha ocurrido una excepción al procesar la conversión del documento en formato de \"Documento\". Revise el log de operaciones para más información.");
		}
	}

	/**
	 * Genera un documento .doc a partir de una texto en html.
	 * 
	 * @param htmldoc
	 * @param fileName
	 * @return Nombre del documento: numeroEscritura.doc
	 */
	public String convierteDocumento(String htmldoc, String fileName) throws NotariaException {
		try{
			/** Dar formato al documento **/
			StringBuilder sb = new StringBuilder();
			sb.append(EstilosFormatoDocumento.HEADER);
			sb.append(EstilosFormatoDocumento.BODY_FORMAT_BEGIN);
			sb.append(htmldoc);
			sb.append(EstilosFormatoDocumento.BODY_FORMAT_END);
			sb.append(EstilosFormatoDocumento.FOOTER);
	
			/** Crear el archivo html temporal para posterior convertirlo a .doc **/
			ArchivoUtils.writeStringToFile(sb.toString(), ArchivoUtils.createFullPath(fileName + ".html"));
	
			/** Convertir .html to .doc **/
			OpenOfficeService openOfficeHelper = new OpenOfficeService();
			boolean b = openOfficeHelper.convertFile(ArchivoUtils.createFullPath(fileName + ".html"), ArchivoUtils.createFullPath(fileName + ".doc"));
			if(!b){
				throw new NotariaException("No se ha logrado generar la versión del documento Notarial. Revise el log de operaciones para más información.");
			}
	
			/** Eliminar archivo auxiliar .html **/
			ArchivoUtils.deleteFile(ArchivoUtils.createFullPath(fileName + ".html"));
			return fileName + ".doc";
		}catch(Exception e){
			throw new NotariaException("No se ha logrado generar el documento. Revise el log de operaciones para más información.");
		}
	}
	
	public String convierteDocumento(String htmldoc, String fileName,String numExpediente) throws NotariaException {
		try{
			/** Dar formato al documento **/
			
			//LIMPIO LAS CABECERAS DEL TEXTO DE LA ESCRITURAS
	    	int headDebug = htmldoc.indexOf("--dm--", 0);
	        if(headDebug>0){
	        	headDebug = htmldoc.indexOf("</p>",headDebug);
	        	htmldoc = htmldoc.substring((headDebug+4),htmldoc.length());
	        	htmldoc = htmldoc.replace("<p>&nbsp;</p>", "");
	        }
	        
	        
			StringBuilder sb = new StringBuilder();
			sb.append(EstilosFormatoDocumento.HEADER);
			sb.append(EstilosFormatoDocumento.BODY_FORMAT_BEGIN);
			sb.append(htmldoc);
			sb.append(EstilosFormatoDocumento.BODY_FORMAT_END);
			sb.append(EstilosFormatoDocumento.FOOTER);
	
			/** Crear el archivo html temporal para posterior convertirlo a .doc **/
			ArchivoUtils.writeStringToFile(sb.toString(), ArchivoUtils.createFullPath(fileName + ".html",numExpediente));
	
			/** Convertir .html to .doc **/
			OpenOfficeService openOfficeHelper = new OpenOfficeService();
			boolean b = openOfficeHelper.convertFile(ArchivoUtils.createFullPath(fileName + ".html",numExpediente), ArchivoUtils.createFullPath(fileName + ".doc",numExpediente));
			if(!b){
				throw new NotariaException("No se ha logrado generar la versión del documento Notarial. Revise el log de operaciones para más información.");
			}
	
			/** Eliminar archivo auxiliar .html **/
			ArchivoUtils.deleteFile(ArchivoUtils.createFullPath(fileName + ".html",numExpediente));
			return fileName + ".doc";
		}catch(Exception e){
			throw new NotariaException("No se ha logrado generar el documento. Revise el log de operaciones para más información.");
		}
	}
	
	

}
