package com.palestra.notarias.escritura;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.PlantillaDocumentoNotarialBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.bo.impl.PlantillaDocumentoNotarialBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notarias.helper.DocNotParcialHelper;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.utils.EscrituraUtills;


/**
 * Genera las escrituras parciales de los actos asociados del expediente
 * proporcionado.
 * 
 * @author sofia
 * 
 */
public class EscrituraParcialGenerator {

	
	static final String SELECTED_TRUE = "true";
	static final String SELECTED_FALSE = "false";
	final static String PREFIJO_VARIABLE_REFERENCIA = "&";
	final static String SUFIJO_VARIABLE_REFERENCIA = ";"; //EJEMPLO DE REFERECNIA: &notaria_numero; o de formulario &form.notaria_numero;
//	final static String IDENTIFICADOR_VALOR_SELECCCION = "::"; //se usa par
//	private final static String SECCION_ANTECENDENTES = "----------ANTECEDENTES.";
//	private final static String SECCION_CLAUSULAS = "----------CLAUSULAS.";
//	private final static String SECCION_IDENTIFICACION = "----------IDENTIFICACION.";
//	private final static String SECCION_CERTIFICACIONES = "----------CERTIFICACIONES.";
	
	static Logger logger = Logger.getLogger(EscrituraParcialGenerator.class);

	/** Llamadas a BO **/
	ActoBoImpl actoBo;
	ExpedienteBoImpl expedienteBo;
	EscrituraBoImpl escrituraBo;
	EscrituraActoBoImpl escrituraActoBoImpl;
	PlantillaDocumentoNotarialBo plantillaBo;

	/** Llamdas helpder **/
	DocNotParcialHelper docNotParcialHelper;

	FillTemplateWithData fillTemplate = new FillTemplateWithData();

	public EscrituraParcialGenerator() {

		actoBo = new ActoBoImpl();
		expedienteBo = new ExpedienteBoImpl();
		escrituraBo = new EscrituraBoImpl();
		escrituraActoBoImpl = new EscrituraActoBoImpl();
		docNotParcialHelper = new DocNotParcialHelper();
		plantillaBo = new PlantillaDocumentoNotarialBoImpl();

	}

	/**
	 * Genera los documentos notariales de las escritura(s) del expediente
	 * indicado.
	 * 
	 * @param idExpediente
	 * @param forceReplace
	 * @throws NotariaException
	 */
	public void generaEscriturasDelExpediente(String idExpediente,
			Boolean forceReplace) throws NotariaException {

		Expediente expediente = expedienteBo.findById(idExpediente);
		if (expediente == null) {
			logger.info("No existe expediente indicado");
			return;
		}

		/**
		 * Obtener escrituras del expediente y generar el documento notarial a
		 * cada una.
		 **/
		List<Escritura> escrituras = escrituraBo
				.findByExpedienteId(idExpediente);

		if (escrituras == null) {
			logger.info("No existen escrituras asociadas al expediente dado. ");
			return;
		}

		logger.info("====> Escrituras del expediente: " + escrituras.size());
		/** Generar el documento por escritura **/
		for (Escritura esc : escrituras) {
			this.guardaDocumentoNotarialDeEscritura(esc.getIdescritura(),
					forceReplace);
		}
	}

	/**
	 * Guarda el documento notarial de la escritura dada. Esta escritura esta
	 * conformada por los documentos notariales de uno o varios actos.
	 * 
	 * @param idEscritura
	 * @param forceReplace
	 *            Boolean. Indica si se forazara el replace para las variables
	 *            de escritura que se indiquen.
	 * @throws NotariaException 
	 */
	public void guardaDocumentoNotarialDeEscritura(String idEscritura,
			Boolean forceReplace) throws NotariaException {

		
		List<EscrituraActo> escrituraActos = escrituraActoBoImpl.findByEscrituraId(idEscritura);
		/**
		 * Tomar la plantilla del documento del acto y sustituir las
		 * variables por los valores de este.
		 **/
		PlantillaDocumentoNotarial plantilla = obtienePlantilla(idEscritura);
		if(plantilla == null){
			throw new NotariaException("No se ha loalizado una plantilla publicada para esta escritura.");
		}
		logger.info("=====>     Se localizo la plantilla para trabajar esta escritura: " + plantilla.getDstitulo());
		for (EscrituraActo escact : escrituraActos) {			
			escact.getActo().setExpediente(escact.getEscritura().getExpediente());
			//logger.info("=====>     Llena plantilla del documento para el acto: "+escact.getActo().getDsnombre());
			String docParcialHtml = this.llenaPlantillaDelActo(escact.getActo(), plantilla, forceReplace);
			
			if (docParcialHtml != null) { // se encontró la plantlilla del documento y se logro la trasnformación
				logger.info("=====>     Se genero un resultado para el acto: "+escact.getActo().getDsnombre());
				if (docParcialHtml.isEmpty()) {
					logger.info("Documento notarial parcial vacio");
					throw new NotariaException("El documento generado no tiene contenido. Documento Notarial Parcial Vacío.");
				} else {
					
					
					
					
					logger.info("Documento notarial parcial generado...");
					
					plantilla.setTxplantilla(docParcialHtml);
					//sb.append(docParcialHtml).append("<p>---------- ---------- ---------- ---------- ---------- ---------- ---------- ----------</p>");
					// se continua con el siguiente acto
				}
			} else {
				throw new NotariaException("No se ha logrado transformar la plantilla para generar el documento.");
			}
		}

		// --------------------------------------------
		StringBuffer documento = new StringBuffer(plantilla.getTxplantilla());
		//quitar las referencias:
		Map<String, String> referencias = FillTemplateWithData.obtieneReferencias();
		if(referencias.size()>0){
			//inicia los reemplazos en documento:
			replaceReferencias(referencias, documento);
		} 	
		
		
		
		int iniciaReferencia = documento.indexOf("REFERENCIAS{");
		if(iniciaReferencia>0){
			documento = new StringBuffer(documento.substring(0, iniciaReferencia));
			//Busco que este bien cerrado el p y no cause conflictos a la hora de generar el .doc
			int refLastP = documento.lastIndexOf("<p>");
			int cierreRefP = documento.indexOf("</p>",refLastP);
			if(cierreRefP<0){
				documento.append("</p>");
			}
			
			plantilla.setTxplantilla(documento.toString());
		}
	

		/// GENERO LOS NÚMEROS ROMANOS
		plantilla.setTxplantilla(EscrituraUtills.replaceMakerRomano(plantilla.getTxplantilla(), "#A"));
		plantilla.setTxplantilla(EscrituraUtills.replaceMakerRomano(plantilla.getTxplantilla(), "#B"));
		plantilla.setTxplantilla(EscrituraUtills.replaceMakerRomano(plantilla.getTxplantilla(), "#C"));
		plantilla.setTxplantilla(EscrituraUtills.replaceMakerRomano(plantilla.getTxplantilla(), "#D"));
		plantilla.setTxplantilla(EscrituraUtills.replaceMakerRomano(plantilla.getTxplantilla(), "#E"));
		plantilla.setTxplantilla(EscrituraUtills.replaceASCII(plantilla.getTxplantilla()));
		
		// --------------------------------------------
		
		
		/** Guarda version inicial del documento notarial parcial sb.toString() **/ 
		docNotParcialHelper.guardaPrimeraVersionDocParcial(plantilla.getTxplantilla(), idEscritura);
	}
	
	public void replaceReferencias(Map<String, String> referencias,StringBuffer documento) throws NotariaException{
		for(String referencia:referencias.keySet()){
			String valor = referencias.get(referencia);
			//referencia = referencia.replace("**", "&");
			referencia = referencia.replace("**", "&amp;");
			if(valor==null){
				continue;
			}
			
			/*if(valor.indexOf("&amp;")>-1){
				int inicio = valor.indexOf("&amp;");
				int fin = valor.indexOf(";", inicio+5);
				String ref = valor.substring((inicio+5),fin+1);
				String refValor = referencias.get("**"+ref);
				StringBuffer valorData = new StringBuffer(valor);
				valorData = valorData.replace(inicio,fin,refValor);
				valor = valorData.toString();
			}*/
			if(valor.indexOf("&amp;")>-1){
				FillTemplateWithData ftwd = new FillTemplateWithData();
				valor = ftwd.replaceReferenceInside(valor, referencias);
			}
			valor = valor.replace("<br />", "");
			valor = valor.replace("<br /", "");
			valor = valor.replace("</p>", "");
			valor = valor.replace("</p", "");
			while(documento.indexOf(referencia)>0){
				int inicio = documento.indexOf(referencia);
				int fin = documento.indexOf(";", inicio+5) +1;
				documento = documento.replace(inicio, fin, valor);
			}
			
		}
		
		
		FillTemplateWithData ftwd = new FillTemplateWithData();
		Map<String,String> mapData = ftwd.obtenReferenciaToken(documento.toString());
		
		System.out.println("Size map data"+mapData.size());
		
		
	}
	

	/*		
//		if(textoEscritura.size()>1){
//			//intercalar los actos en la escritura
//			String[][] secciones = new String[textoEscritura.size()][4];
//			int inicio = 0, fin = 0, intSeccion = 0, escritura = 0;
//			for(String texto: textoEscritura){
//				intSeccion = 0;
//				if(texto.contains(SECCION_IDENTIFICACION)){
//					inicio = texto.indexOf(SECCION_IDENTIFICACION);
//					fin = texto.indexOf(SECCION_ANTECENDENTES);
//					secciones[escritura][intSeccion++] = fin>0?texto.substring(inicio>0?inicio:0, fin):texto.substring(inicio>0?inicio:0);
//				}
//				if(texto.contains(SECCION_ANTECENDENTES)){
//					inicio = texto.indexOf(SECCION_ANTECENDENTES);
//					fin = texto.indexOf(SECCION_CLAUSULAS);
//					secciones[escritura][intSeccion++] = fin>0?texto.substring(inicio>0?inicio:0, fin):texto.substring(inicio>0?inicio:0);
//				} 
//				if(texto.contains(SECCION_CLAUSULAS)){ 
//					inicio = texto.indexOf(SECCION_CLAUSULAS);
//					fin = texto.indexOf(SECCION_CERTIFICACIONES);
//					secciones[escritura][intSeccion++] = fin>0?texto.substring(inicio>0?inicio:0, fin):texto.substring(inicio>0?inicio:0);					
//				}
//				if(texto.contains(SECCION_CERTIFICACIONES)){
//					inicio = texto.indexOf(SECCION_CERTIFICACIONES);
//					secciones[escritura][intSeccion++] = texto.substring(inicio>0?inicio:0);
//				}
//				escritura++;
//			}
//			sb = new StringBuilder("");
//			for(int i=0;i<4;i++){
//				for(int j=0;j<secciones.length;j++){
//					sb.append(secciones[j][i]);
//				}
//			}	
//		}
	 */
	
	private PlantillaDocumentoNotarial obtienePlantilla(String idEscritura)throws NotariaException{
		/** Obtener los actos que conforman la escritura **/
		List<EscrituraActo> escrituraActos = escrituraActoBoImpl.findByEscrituraId(idEscritura);
		if (escrituraActos == null || escrituraActos.size()<=0) {
			logger.info("No exiten actos y escritura relacionados.");
			throw new NotariaException("No existen actos asociados a la escritura, no hay nada para transformar.");
		}
		logger.info("====> Actos que conforman la escriutra: " + escrituraActos.size());		
		/** Recorrer los actos que conforman la escritura para determinar la plantilla**/
		List<Suboperacion> suboperaciones = new ArrayList<Suboperacion>();
		for (EscrituraActo escrituraActo:escrituraActos){
			suboperaciones.add(escrituraActo.getActo().getSuboperacion());
		}
			
		Tramite tramite = escrituraActos.get(0).getActo().getExpediente().getTramite(); //se toman los datos del tramite a partir del primer acto localizado
		if(tramite.getLocacion()==null){
			logger.info("No se localizo la locación del trámite y no se puede asignar por default.");
			throw new NotariaException("Imposible generar el documento sin la locación.");
		}
		ElementoCatalogo locacion = tramite.getLocacion();
		PlantillaDocumentoNotarial plantilla = plantillaBo.obtienePublicadaPorSuboperacionLocacion(locacion, suboperaciones);		
		return plantilla;
	}
	
	/**
	 * A partir de la plantilla de documento notarial del acto dado, se
	 * sustituyen las variables con la informacion correspondiente.
	 * 
	 * @param Acto
	 *            acto
	 * @param forceReplace
	 * @return String documento notarial del acto
	 */
	public String llenaPlantillaDelActo(Acto acto, PlantillaDocumentoNotarial plantilla, Boolean forceReplace) throws NotariaException{

		try {
			logger.info("Generacion documento del acto " + acto.getIdacto());

			if (acto.getSuboperacion() == null
					|| acto.getSuboperacion().getIdsuboperacion() == null
					|| acto.getSuboperacion().getIdsuboperacion().isEmpty()) {
				logger.info("El acto no tiene suboperacion");
				throw new NotariaException("Imposible generar el documento sin la suboperación.");
				//return null;
			}
			
			if (plantilla == null || plantilla.getTxplantilla() == null
					|| plantilla.getTxplantilla().isEmpty()) {
				logger.info("No existe plantilla de documento notarial para el acto especificado. ");
				return null;
			}
			FiltroVariables fvariables = new FiltroVariables();
			fvariables.setIdacto(acto.getIdacto());
			
			// --> 110714 cafaray Se agrega al inicio de la plantilla la información de la versión de documento que se esta tomando como base para la generación
			StringBuilder txPlantilla = new StringBuilder("<b>VERSION DEL MODELO: ");
			txPlantilla.append(plantilla.getId().getInversion()).append("</b><br />");
			txPlantilla.append(plantilla.getTxplantilla());
			// <-- 110714 cafaray
			String varsReplaced = this.reemplazarVariables(fvariables,
					txPlantilla.toString(), forceReplace);
			if (varsReplaced.contains("<p><br />\n&nbsp;</p>")){
				varsReplaced.replace("<p><br />\n&nbsp;</p>", "");
			}
			logger.info("=====>     varsReplaced: "+varsReplaced);
			varsReplaced+="</pre>";
			return varsReplaced;
		
		} catch (NullPointerException e){
			e.printStackTrace();
			throw new NotariaException("No se encontró un valor que se esperaba [NULL]. "+e.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException("Ocurrió una excepción no controlada por el sistema. Notifique a sistemas.");
		}
	}

	/**
	 * A partir de la plantilla de documento notarial del acto dado, se
	 * sustituyen las variables con la informacion correspondiente.
	 * 
	 * @param Acto
	 *            acto
	 * @param forceReplace
	 * @return String documento notarial del acto
	 */
	public String llenaPlantillaDelActo(Acto acto, String plantilla, Boolean forceReplace) {

		try {
			logger.info("Generacion documento del acto-plantilla " + acto.getIdacto());

			if (plantilla == null || plantilla.isEmpty()) {
				logger.info("No existe plantilla de documento notarial para el acto especificado. ");
				return null;
			}
			FiltroVariables fvariables = new FiltroVariables();
			fvariables.setIdacto(acto.getIdacto());
			
			String varsReplaced = this.reemplazarVariables(fvariables, plantilla, forceReplace);
			if (varsReplaced.contains("<p><br />\n&nbsp;</p>")){
				varsReplaced.replace("<p><br />\n&nbsp;</p>", "");
			}
			logger.info("=====>     varsReplaced:\n\n"+varsReplaced+"\n\n");
			return varsReplaced;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Reemplaza variables de tipo: ${var:variable}, ${gpo:variable},
	 * ${frm:nombreFrm[nombreCompoFrm]}, $(tbl:nombreTabla[ ...variablesFrm...
	 * ])
	 * 
	 * @param acto
	 * @param htmlPlantilla
	 * @param forceReplace
	 * @return
	 */
	//TODO: sacar este metodo a otra clase, aparte del generator.
	public String reemplazarVariables(FiltroVariables fvariables,
			String htmlPlantilla, Boolean forceReplace) throws NotariaException {
		StringBuffer documento = null;
		if (fvariables.getIdacto() != null) {// Variables de tipo formulario
			Reader reader = null;
			try{
				reader = fillTemplate.reemplazarVariablesSistema(fvariables, htmlPlantilla, forceReplace);
				if (reader!=null && htmlPlantilla.contains("${frm:")){  
					FormularioBo formularioBo = new FormularioBoImpl();
					List<Formulario> listaForms = formularioBo.findByActoId(fvariables.getIdacto());
					if (listaForms == null) {
						//String mensaje = "====>  No se encontraron formularios cargados para el documuento notarial asociado y hay variables de formualrio asociadas a la plantilla. No se procesará.";
						String mensaje = "====>  Falta captura de datos en formularios asociados a esta plantilla. Favor de revisar.";
						logger.info(mensaje);
						return mensaje;
					}		
					StringBuffer tmp = new StringBuffer(IOUtils.toString(reader));
					reader = fillTemplate.replaceFormTokenInTemplate(listaForms, tmp.toString());
				}
				if(reader!=null){
					documento = new StringBuffer(IOUtils.toString(reader));
					//quitar las referencias:
//					Map<String, String> referencias = FillTemplateWithData.obtieneReferencias();
//					if(referencias.size()>0){
//						//inicia los reemplazos en documento:
//						for(String referencia:referencias.keySet()){
//							String valor = referencias.get(referencia);
//							//referencia = referencia.replace("**", "&");
//							referencia = referencia.replace("**", "&amp;");
//							valor = valor.replace("<br />", "");
//							valor = valor.replace("<br /", "");
//							valor = valor.replace("</p>", "");
//							valor = valor.replace("</p", "");
//							if(documento.indexOf(referencia)>0){
//								int inicio = documento.indexOf(referencia);
//								int fin = documento.indexOf(";", inicio+5) +1;
//								documento = documento.replace(inicio, fin, valor);
//							}
//						}
//					}
//					int iniciaReferencia = documento.indexOf("REFERENCIAS{");
//					if(iniciaReferencia>0){
//						documento = new StringBuffer(documento.substring(0, iniciaReferencia));
//					}
				}else{
					documento = new StringBuffer(htmlPlantilla);
				}
			}catch(NullPointerException | IOException e){
				e.printStackTrace(System.out);
				throw new NotariaException("Ocurrio un error al transformar el documento. Revise la sintáxis de las variables en la plantilla o ejecute el validador de plantillas.",e.getCause());				
			} finally {
				try {
					if(reader!=null){
						reader.close();
					}
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}			
		}
		return documento!=null?documento.toString():null;		
	}

}
