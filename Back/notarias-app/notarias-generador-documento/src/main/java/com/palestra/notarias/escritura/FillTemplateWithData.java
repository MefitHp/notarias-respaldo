package com.palestra.notarias.escritura;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.NotariaBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.GrupoBoImpl;
import com.palestra.notaria.bo.impl.NotariaBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.bo.impl.VariableBoImpl;
import com.palestra.notaria.dao.ComparecienteDao;
import com.palestra.notaria.dao.ValorFormularioDao;
import com.palestra.notaria.dao.impl.ComparecienteDaoImpl;
import com.palestra.notaria.dao.impl.ValorFormularioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.Gestor;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.Valuador;
import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.pojo.FormTokens;
import com.palestra.notarias.pojo.TableTokens;
import com.palestra.notarias.pojo.VariablesTokens;
import com.palestra.notarias.variables.TokenExtractor;
import com.palestra.notarias.variables.TokenReplacer;
import com.palestra.notarias.variables.TokenToVariableTransformer;
import com.palestra.notarias.variables.VariableToTokenTransformer;
import com.palestra.notarias.variables.VariableValueAssigner;
import com.palestra.notarias.variables.VariableValueAssignerCompareciente;

/**
 * Clase que remplaza los variables por sus valor correspondiente en la
 * plantilla del documento notarial.
 * 
 * @author sofia
 * 
 */
public class FillTemplateWithData {

	static Logger logger = Logger.getLogger(FillTemplateWithData.class);
	static final String PLANILLA_DOCUMENTO_VACIA = "Plantilla de documento vacío.";
	static final String NO_EXISTEN_FORMULARIOS = "No se existen formularios.";

	/** Llamadas BO **/
	private GrupoBoImpl grupoBo = new GrupoBoImpl();
	private VariableBoImpl variableBo = new VariableBoImpl();
	static private Map<String, String> tokensReferencia = new TreeMap<>();

	/** Clases de operaciones de variables **/
	TokenExtractor tokenExtractor = new TokenExtractor(grupoBo, variableBo);
	TokenToVariableTransformer tokenToVariable = new TokenToVariableTransformer(
			grupoBo, variableBo);
	VariableValueAssigner valueAssigner = new VariableValueAssigner();
	VariableToTokenTransformer variableToToken = new VariableToTokenTransformer();
	TokenReplacer tokenReplacer = new TokenReplacer();

	Expediente expediente=null;
	
	
	/*
	 * Obtengo el expediente del acto para la busqueda de valores en diferentes métodos
	 * 
	 */
	private Expediente getExpediente(String idActo) throws NotariaException{
		
		if(this.expediente !=null){
			return this.expediente;
		}else{
		
			ExpedienteBo expBo = new ExpedienteBoImpl();
			ActoBo actoBo = new ActoBoImpl();
			String idexpediente = actoBo.getExpedienteIdByActoId(idActo);
			this.expediente = new Expediente();
			this.expediente.setIdexpediente(idexpediente);
			this.expediente = expBo.buscarPorIdCompleto(this.expediente);
			if(this.expediente!=null){
				return this.expediente;
			}else{
				return null;
			}			
			
		}	
		
	}
	
	/**
	 * Reemplaza en la plantilla los variables de tipo '${var:definicion}' y
	 * '${gpo:definicion}' por valores de la bd.
	 * 
	 * @param fvariable
	 *            Filtro variable, filtror para obtener los valores de las
	 *            varaibles, puede ser filtrado por idescritura, idacto, etc.
	 * @param htmlText
	 *            Texto en html que contiene las variables a reemplazar por
	 *            valores.
	 * @param forceReplace
	 *            Boolean. Bandera para forzar el replace de null por '_' para
	 *            las variables de tipo escritura.
	 * @return Reader con la plantilla ya reemplazada
	 * @throws NotariaException 
	 */
	public Reader reemplazarVariablesSistema(FiltroVariables fvariable, String htmlText, Boolean forceReplace) throws NotariaException {
		Reader reader = null;
		Map<String, String> tokensValor = getTokenVariable(fvariable, htmlText);
		if (tokensValor != null) {
			reader = tokenReplacer.remplazaTokenVariablePorValor(htmlText,
					tokensValor, EnumVariables.VAR_PREFIX.toString(),
					EnumVariables.VAR_SUFIX.toString());
		}
		return reader;
	}

	/***
	 * 
	 * @param fvariable
	 *            filtro de variables a incluir
	 * @param htmlText
	 *            texto que contiene las variables
	 * @return Mapa de cadenas con el token-variable
	 * @throws NotariaException 
	 */
	public Map<String, String> getTokenVariable(FiltroVariables fvariable,
			String htmlText) throws NotariaException {
		if (fvariable == null) {
			return null;
		}
		if (htmlText.isEmpty()) {
			logger.info(PLANILLA_DOCUMENTO_VACIA);
			return null;
		}

		String[] identificadorPermitidos = {
				EnumVariables.VAR_IDENTIFIER.toString(),
				EnumVariables.GPO_IDENTIFIER.toString(),
				EnumVariables.COMPARECIENTE_IDENTIFIER.toString(),
				EnumVariables.COMPARECIENTES_IDENTIFIER.toString()
		};
		/**
		 * Tokens permitidos '${var:nombreVar}' '${gpo:nombreGrupo}'
		 **/
		String[] tokensToReplace = tokenExtractor.getTokensToReplace(htmlText,
				EnumVariables.VAR_PREFIX.toString(),
				EnumVariables.VAR_SUFIX.toString(), identificadorPermitidos);
		if (tokensToReplace == null) {
			logger.info(String.format("=====> No se lograron encontrar tokens %s y %s para buscar valores. ",EnumVariables.VAR_IDENTIFIER, EnumVariables.GPO_IDENTIFIER));
			return null;
		}
		/** Descomponer grupos en variables **/
		List<VariablesTokens> listaVariables = tokenToVariable.obtenSoloVariables(tokensToReplace, false);
		if (listaVariables != null) {
			/** Obtener valores de variables en base de datos. **/
			valueAssigner.obtenValoresVariables(fvariable, listaVariables);
			/**
			 * Map<token,valor> para sustituir el token de plantilla por el
			 * valor
			 **/
			Map<String, String> tokensValor = variableToToken
					.obtenTokenConValores(listaVariables, tokensToReplace);
			return tokensValor;
		}else{
			logger.info("=====> No se localizaron variables para asignar valor");
		}
		return null;
	}

	
	
	/**
	 * Reemplaza en la plantilla los variables de tipo '$(tbl:definicion)'
	 * 
	 * @param datoTemplate
	 * 
	 * @return Reader con la plantilla ya reemplazada
	 */
	public Reader replaceTableTokenInTemplate(
			List<Formulario> listaFormularios, String htmlText) {
		try {
			if (!this.validaRequeridos(listaFormularios, htmlText)) {
				return null;
			}
			String[] identificadorPermitidos = { EnumVariables.TBL_IDENTIFIER.toString() };
			/**
			 * Tokens permitidos '$(tbl:definicionVariable)'
			 **/
			String[] tokensToReplace = tokenExtractor.getTokensToReplace(htmlText,
							EnumVariables.TBL_PREFIX.toString() + EnumVariables.TBL_IDENTIFIER,
							EnumVariables.TBL_SUFIX.toString(),
							identificadorPermitidos);
			if (tokensToReplace == null) {
				return new StringReader(htmlText);
			}
			/** Obtener las variables **/
			List<TableTokens> listaTablas = tokenToVariable.obtenTablasTokens(tokensToReplace,listaFormularios);
			if (listaTablas == null) {
				return null;
			}
			/** Obtener valores tablas de subformularios **/
			valueAssigner.obtenValoresSubFormulario(listaFormularios, listaTablas);
			Reader reader = tokenReplacer.reemplazaTableTokens(htmlText, listaTablas);
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public String replaceReferenceInside(String valor,Map<String, String> referencias){
		while(valor.contains("&amp;")){
			int inicio = valor.indexOf("&amp;");
			int fin = valor.indexOf(";", inicio+5);
			String ref = valor.substring((inicio+5),fin);
			String refValor = referencias.get("**"+ref+";");
			if(refValor==null){
				StringBuffer valorData = new StringBuffer(valor);
				valorData = valorData.replace(inicio,(fin+1)," ");
				valor = valorData.toString();
			}else{
				StringBuffer valorData = new StringBuffer(valor);
				valorData = valorData.replace(inicio,(fin+1),refValor);
				valor = valorData.toString();
			}
			
		}
		
		return valor;
	}
	
	
	public Reader replaceInlineSubFrmTokenInTemplate(
			List<Formulario> listaFormularios, String htmlText) {
		try {
			if (!this.validaRequeridos(listaFormularios, htmlText)) {
				return null;
			}
			String[] identificadorPermitidos = { EnumVariables.CSV_IDENTIFIER.toString() };
			/**
			 * Tokens permitidos '$(tbl:definicionVariable)'
			 **/
			String[] tokensToReplace = tokenExtractor.getTokensToReplace(htmlText,
							EnumVariables.TBL_PREFIX.toString() + EnumVariables.CSV_IDENTIFIER,
							EnumVariables.CSV_IDENTIFIER.toString(),
							identificadorPermitidos);
			if (tokensToReplace == null) {
				return new StringReader(htmlText);
			}
			/** Obtener las variables **/
			List<TableTokens> listaTablas = tokenToVariable.obtenTablasTokens(tokensToReplace, listaFormularios);
			if (listaTablas == null) {
				return null;
			}
			/** Obtener valores tablas de subformularios **/
			valueAssigner.obtenValoresSubFormulario(listaFormularios, listaTablas);
			Reader reader = tokenReplacer.reemplazaTableTokens(htmlText, listaTablas);
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	/**
	 * Obtiene formTokens y los sustituye por sus valores de BD.
	 * 
	 * @param datoTemplate
	 * @param htmlText
	 * @return
	 */
	public Reader replaceFormTokenInTemplate(List<Formulario> listaFormulario,
			String htmlText) {
		logger.info("===> replaceFormTokenInTemplate()");
		try {
			// cafaray 050814 --> Una puercada, si, no quedo de otra debido al revoltijo (marrano) que mezclo SOV
			// la intencion es obtener las referencias dentro del HTML y mantenerlas disponibles para VariableValueAssigner
			try{
				tokensReferencia = obtenReferenciaToken(htmlText);
			}catch(NotariaException e){
				e.printStackTrace(System.out);				
			}
			//cafaray 050814 <--
			
			
			
			Map<String, String> tokensValor = getPairVariableValue(listaFormulario, htmlText, new FiltroVariables(),false);
						
			// TODO: unificar en tokenExtrator y tokenReplace, los prefix y
			// sufix

			Reader reader = tokenReplacer.remplazaTokenVariablePorValor(
					htmlText, tokensValor, EnumVariables.VAR_PREFIX.toString()
							+ EnumVariables.FRM_IDENTIFIER,
					EnumVariables.VAR_FRM_SUFIX.toString());
			
			for(Entry<String, String> tok:tokensValor.entrySet()){
				System.out.println("KEYS:"+tok.getKey());
				System.out.println("VALOR:"+tok.getValue());
				if(tok.getValue()==null){
					tok.setValue(tok.getKey());
				}
			}
			
			String tmp = htmlText;
			try{
				 tmp = IOUtils.toString(reader);
			}catch(IOException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}catch(StringIndexOutOfBoundsException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			reader = replaceTableTokenInTemplate(listaFormulario, tmp);
			
			return reader;

		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}

	static public Map<String, String> obtieneReferencias(){
		return tokensReferencia;
	}
	
	// CFA 050814 -->
	static public String obtieneVariable(String referencia){
		if(!tokensReferencia.isEmpty()){
			if(tokensReferencia.containsKey(referencia)){
				String variable =tokensReferencia.get(referencia);
				if(variable.indexOf("${")>-1){
					variable = variable.substring(variable.indexOf("${")+6);
					variable = variable.substring(0, variable.contains("[")?variable.indexOf("]"):variable.indexOf("}"));
				}
				return variable;
			}else{
				return null;
			}
		}else{
			return null;
		}
	} 
	
	// CFA 050814 -->
	static public String actualizaValorReferencia(String variable, String valor){
		if(!tokensReferencia.isEmpty()){								
			for(String key:tokensReferencia.keySet()){
				if(tokensReferencia.get(key).startsWith(variable)){
					tokensReferencia.put(key, valor);
					break;
				}
			}				
			return valor;
		}else{
			return null;
		}
	} 
	

	
	/***
	 * 
	 * @param variable debe de llegar el valor sin los corchetes [nombre_variable]
	 * @param idacto acto que se usa como filtro para calcular los formularios que le corresponden y donde se buscará el valor
	 * @return
	 */
	static public String obtieneValorFormulario(String variable, String idacto) throws NotariaException{
		ValorFormularioDao dao = new ValorFormularioDaoImpl();
		ValorFormulario vf = dao.getByIdComponenteActo(idacto, variable);
		return vf.getValorcadena();
	}
	
	
	/***
	 * ObtenReferenciaToken extrae las referencias a variables que se usan dentro del contenido de las variables lógicas.
	 * @param htmlText plantilla donde se localizan las referencias
	 * @return Referencias a variables dentro de la plantilla
	 * 
	 * OBTENER TOKENS, DESPUES OBTENER DATOS Y AL FINAL SUSTITUIR EN EL HTMLS
	 */
	public Map<String,String> obtenReferenciaToken(String htmlText) throws NotariaException{
        Map<String, String> referenciaToken = new TreeMap<>();
		try{
			Pattern paternVars = Pattern.compile("(\\*\\*((\\w,=|.)*))");
			Matcher matchVars = paternVars.matcher(htmlText);
			try{
				int x=0;
		        while(matchVars.find()){
		        	
		        	String match = matchVars.group();
		        	if(match.contains("=")){		        		
			            logger.info(String.format("=====> Match referencia[%d]=variable ::: %s",(x++),match));	            
			            String key = match.substring(0, match.indexOf("=",0));
			            String value = match.substring(match.indexOf("=",0)+1,match.length());
			            value = value.replaceAll("</p>", "");
			            referenciaToken.put(key.trim(), value.trim());			            
		        	}else{
		        		// lo siento un error en la plantilla
		        	}
		        }		       
		        return referenciaToken;
			}catch(StringIndexOutOfBoundsException e){
				throw new NotariaException("No se localizo un patron para la variable adecuado, la estructura de asignacion de la referencia no es correcta. Revise la plantilla o el patron de busqueda de coincidencia.", e.getCause());
			}
		}catch(PatternSyntaxException e){
			throw new NotariaException("El patron de busqueda no es compilable, revise.", e.getCause());
		}
	}
	// CFA 050814 <--

	/***
	 * obtenReferenciaTokenCompleta extrae las referencias a variables que se usan dentro del contenido de las variables lógicas.
	 * @param htmlText plantilla donde se localizan las referencias
	 * @return Referencias a variables dentro de la plantilla
	 */
	public List<String> obtenReferenciaTokenCompleta(String htmlText) throws NotariaException{
        List<String> referenciaToken = new ArrayList<>();
		try{
			Pattern paternVars = Pattern.compile("(\\*\\*((\\w,=|.)*))");
			//Matcher matchVars = paternVars.matcher("&variable1=${var:compareciente_nombre(tipo=COMPRADOR)};\n&variable2=${var:compareciente_nombre(tipo=VENDEDOR)};\n&variable3=${frm:miform[alguna_fecha(funcion=texto)]};");
			Matcher matchVars = paternVars.matcher(htmlText);
			try{
		        while(matchVars.find()){
		        	String match = matchVars.group();
		        	if(match.contains("=")){		        		
			            referenciaToken.add(match);			            
		        	}else{
		        		// lo siento, un error en la plantilla
		        	}
		        }		       
		        return referenciaToken;
			}catch(StringIndexOutOfBoundsException e){
				throw new NotariaException("No se localizo un patron para la variable adecuado, la estructura de asignacion de la referencia no es correcta. Revise la plantilla o el patron de busqueda de coincidencia.", e.getCause());
			}
		}catch(PatternSyntaxException e){
			throw new NotariaException("El patron de busqueda no es compilable, revise.", e.getCause());
		}
	}
	
	
	// CFA 30may14 -->
	/***
	 * getPairVariableValue obtiene el mapa de variable-valor de los formularios
	 * enviados.
	 * 
	 * @param listaFormulario
	 *            listado de los formularios a escarvar para localización de
	 *            pares
	 * @param htmlText
	 *            El texto del cual se extraen las variables y que deberan ser
	 *            asignadas a un valor en el mapa
	 * @return java.util.Map<String, String> mapa de variable-valor segun texto
	 *         y lista de formularios
	 * @throws NotariaException
	 */
	public Map<String, String> getPairVariableValue(
			List<Formulario> listaFormulario, String htmlText,
			FiltroVariables fvariable,boolean isdocument) throws NotariaException {
		logger.info("===> getPairVariableValue()");
		if (!this.validaRequeridos(listaFormulario, htmlText)) {
			return null;
		}
		/** Obtener las variables del formulario **/
		List<FormTokens> listaFrmTokens = tokenToVariable.obtenerFrmTokens(htmlText);

		if (listaFrmTokens == null) {
			return null;
		}
		/** Obtener valores tablas de formularios **/
		valueAssigner.assignValuesToFrmToken(listaFormulario, listaFrmTokens);

		
		Map<String, String> tokensValorFrm = variableToToken.obtenFrmTokenConValores(listaFrmTokens);
		if(isdocument){
			tokensValorFrm = replaceTableTokenInTemplateDocument(listaFormulario, htmlText, tokensValorFrm);
		}
		
		if (fvariable.getIdActoDocumento() != null) {
			
			tokensValorFrm = generaDocumentoPdf(htmlText, fvariable, tokensValorFrm);
		}

		logger.info("===> TOKENS " + tokensValorFrm);
		logger.info("===> FillTemplateWithData.getPairVariableValue() finished witn Map.size()" + tokensValorFrm);
		return tokensValorFrm;
	}

	//VICTOR 27may2015 RECUPERA VALORES DE SUBFORMULARIOS PARA DOCUMENTOS
	private Map<String,String> replaceTableTokenInTemplateDocument(
			List<Formulario> listaFormularios, String htmlText,Map<String,String> tokensValorFrm) {
		try {
			if (!this.validaRequeridos(listaFormularios, htmlText)) {
				return null;
			}
			String[] identificadorPermitidos = { EnumVariables.TBL_IDENTIFIER.toString() };
			/**
			 * Tokens permitidos '$(tbl:definicionVariable)'
			 **/
			String[] tokensToReplace = tokenExtractor.getTokensToReplace(htmlText,
							EnumVariables.TBL_PREFIX.toString() + EnumVariables.TBL_IDENTIFIER,
							EnumVariables.TBL_SUFIX.toString(),
							identificadorPermitidos);
			if (tokensToReplace == null) {
				return tokensValorFrm;
			}
			/** Obtener las variables **/
			for(String token :tokensToReplace){
				tokensValorFrm.put(token, token);
			}
			List<TableTokens> listaTablas = tokenToVariable.obtenTablasTokens(tokensToReplace, listaFormularios);
			if (listaTablas == null) {
				return tokensValorFrm;
			}
			/** Obtener valores tablas de subformularios **/
			valueAssigner.obtenValoresSubFormularioDocumentos(listaFormularios, listaTablas,tokensValorFrm);
			
			//Reader reader = tokenReplacer.reemplazaTableTokens(htmlText, listaTablas);
			return tokensValorFrm;
		} catch (Exception e) {
			e.printStackTrace();
			return tokensValorFrm;
		}
	}
	
	
	
	
	// CFA 30may14 <--

	// cafaray 130814 --> Si se esta emitiendo un documento previo/posterior en formato pdf, entonces se hacen los caulculos:
	private Map<String, String> generaDocumentoPdf(String htmlText, FiltroVariables fvariable, Map<String, String> tokensValorFrm)throws NotariaException {
		// detectar si en el mapa vienen mas variables que no son de
		// formulario:
		if (htmlText.contains(EnumVariables.VAR_PREFIX.toString() + EnumVariables.VAR_IDENTIFIER.toString())) {
			TokenExtractor te = new TokenExtractor();
			// si vienen variables, entonces procedemos a buscar sus valores:			
			String[] identifierAllow = { EnumVariables.VAR_IDENTIFIER.toString() };
			String[] variables = te.getTokensToReplace(htmlText, EnumVariables.VAR_PREFIX.toString(), EnumVariables.VAR_SUFIX.toString(), identifierAllow);			
			for (String variable : variables) {
				StringBuilder tmpString = new StringBuilder();
				ActoDocumentoBo bo = new ActoDocumentoBoImpl();
				ActoDocumento ad = bo.findById(fvariable.getIdActoDocumento());
				
				
				
				if (variable.toLowerCase().contains("notaria_notario_documento")) {
					tmpString.append(ad.getNotario().getDsnombre())
							.append(" ")
							.append(ad.getNotario().getDspaterno())
							.append(" ")
							.append(ad.getNotario().getDsmaterno());			
					// Notaria notaria =
					VariableToTokenTransformer vt = new VariableToTokenTransformer();
					String funcion = vt.getDefinicionFuncion(variable);
					if(!tokensValorFrm.containsKey("notaria_notario_documento")){
						String valor = (!funcion.isEmpty())?vt.evaluaFuncionVariable(funcion,tmpString.toString()):tmpString.toString();
						tokensValorFrm.put("notaria_notario_documento",valor);
					}
					tokensValorFrm.put("notaria_notario_rfc_documento", ad.getNotario().getDsrfc());
					// htmlText.replace("nombre", tmpString.toString());
				}else if(variable.toLowerCase().contains("notaria_numero_documento")){
					NotariaBo boNotaria = new NotariaBoImpl();
					String numeroNotaria = boNotaria.obtenerNumNotariaByInicialesNotario(ad.getNotario().getDsiniciales());
					VariableToTokenTransformer vt = new VariableToTokenTransformer();
					String funcion = vt.getDefinicionFuncion(variable);
					if(!tokensValorFrm.containsKey("notaria_numero_documento")){
						String valor = (!funcion.isEmpty())?vt.evaluaFuncionVariable(funcion,numeroNotaria):numeroNotaria;
						tokensValorFrm.put("notaria_numero_documento", valor);					
					}
					
				} else if(variable.toLowerCase().contains("numero_expediente")){
					
					if(this.expediente==null){
						getExpediente(fvariable.getIdacto());
					}
					
					if(this.expediente!=null){
						tokensValorFrm.put("numero_expediente", this.expediente.getNumexpediente());
					}else{
						tokensValorFrm.put("numero_expediente", " ");
					}
				} else if (variable.toLowerCase().contains("documento_gestor")) {			
					Gestor gestor = ad.getGestor();
					if (gestor!=null){
						tmpString = new StringBuilder();
						tmpString.append(gestor.getDsnombre()).append(" ")
							.append(gestor.getDspaterno()).append(" ")
							.append(gestor.getDsmaterno());
					}else{
						tmpString = new StringBuilder(" ");
					}
					tokensValorFrm.put("documento_gestor", tmpString.toString());
					// htmlText.replace("nombre", tmpString.toString());
				} else if (variable.toLowerCase().contains("documento_valuador")) {			
					Valuador valuador = ad.getValuador();
					if (valuador!=null){
						tmpString = new StringBuilder();
						tmpString.append(valuador.getDsnombre()).append(" ")
							.append(valuador.getDspaterno()).append(" ")
							.append(valuador.getDsmaterno());
					}else{
						tmpString = new StringBuilder(" ");
					}
					tokensValorFrm.put("documento_valuador", tmpString.toString());
					// htmlText.replace("nombre", tmpString.toString());
				}else if (variable.toLowerCase().contains("fecha_texto_hoy")){
					SimpleDateFormat dateFormat = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
					String fecha = dateFormat.format(new Date());
					VariableToTokenTransformer vt = new VariableToTokenTransformer();
					String funcion = vt.getDefinicionFuncion(variable);
					String valor = (!funcion.isEmpty())?vt.evaluaFuncionVariable(funcion,fecha):fecha;
					
					tokensValorFrm.put("fecha_texto_hoy", valor);
				}else if (variable.toLowerCase().contains("fecha_hoy")) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					tokensValorFrm.put("fecha_hoy", dateFormat.format(new Date()));
				} else if (variable.toLowerCase().contains("compareciente_nombre(tipo=comprador)")) {
					String elTipo = "COMPRADOR";
					ComparecienteDao daoCom = new ComparecienteDaoImpl();
					List<String> comparecientes = daoCom.nombresCompareciente(fvariable.getIdacto(), elTipo);
					System.out.printf("comparecientes.size() = %d     idacto=%s    tipo=%s%n", comparecientes.size(), fvariable.getIdacto(), elTipo);
					StringBuilder strTemp = new StringBuilder();
					for (String compareciente : comparecientes) {
						strTemp.append(compareciente).append(", ");
					}
					strTemp.substring(0, strTemp.length() - 1);
					tokensValorFrm.put(
							"compareciente_nombre(tipo=COMPRADOR)",
							strTemp.toString());
				} else if (variable.toLowerCase().contains("compareciente_nombre_nombre(tipo=comprador)")) {
					String elTipo = "COMPRADOR";
					ComparecienteDao daoCom = new ComparecienteDaoImpl();							
					List<Compareciente> comparecientes = daoCom.listadoComparecientes(fvariable.getIdacto(), elTipo);
					for(Compareciente compareciente: comparecientes){
						tokensValorFrm.put(
							"compareciente_nombre_nombre(tipo=COMPRADOR)",
							compareciente.getPersona().getDsnombre());
						tokensValorFrm.put(
							"compareciente_nombre_paterno(tipo=COMPRADOR)",
							compareciente.getPersona().getDsapellidopat());
						tokensValorFrm.put(
							"compareciente_nombre_materno(tipo=COMPRADOR)",
							compareciente.getPersona().getDsapellidomat());
					}							
				}else if (variable.toLowerCase().contains("compareciente_nombre_nombre(tipo=vendedor)")) {
					String elTipo = "VENDEDOR";
					ComparecienteDao daoCom = new ComparecienteDaoImpl();							
					List<Compareciente> comparecientes = daoCom.listadoComparecientes(fvariable.getIdacto(), elTipo);
					for(Compareciente compareciente: comparecientes){
						tokensValorFrm.put(
							"compareciente_nombre_nombre(tipo=VENDEDOR)",
							compareciente.getPersona().getDsnombre());
						tokensValorFrm.put(
							"compareciente_nombre_paterno(tipo=VENDEDOR)",
							compareciente.getPersona().getDsapellidopat());
							tokensValorFrm.put(
							"compareciente_nombre_materno(tipo=VENDEDOR)",
							compareciente.getPersona().getDsapellidomat());
					}							
				}else if (variable.toLowerCase().contains("compareciente_nombre(tipo=vendedor)")) {
					String elTipo = "VENDEDOR";
					ComparecienteDao daoCom = new ComparecienteDaoImpl();
					List<String> comparecientes = daoCom
							.nombresCompareciente(
									fvariable.getIdacto(), elTipo);
					//System.out.printf("comparecientes.size() = %d     idacto=%s    tipo=%s%n",
					//				comparecientes.size(),
					//				fvariable.getIdacto(), elTipo);
					StringBuilder strTemp = new StringBuilder();
					for (String compareciente : comparecientes) {
						strTemp.append(compareciente).append(", ");
					}
					strTemp.substring(0, strTemp.length() - 1);
					tokensValorFrm.put("compareciente_nombre(tipo=VENDEDOR)", strTemp.toString());
				} else if(variable.toLowerCase().contains("cte:")){
					VariableValueAssigner valueAsigner = new VariableValueAssigner();
					String nombreVariable, tipoCompareciente, idacto=fvariable.getIdacto();
					tipoCompareciente = variable.substring(variable.indexOf("cte:")+4, variable.indexOf("["));
					nombreVariable = variable.substring(variable.indexOf("[")+1, variable.indexOf("]"));
					String valor = valueAsigner.calculaVariableCompareciente(nombreVariable, tipoCompareciente, idacto);
					tokensValorFrm.put(tipoCompareciente+"["+nombreVariable, valor);
					//logger.info(tokensValorFrm);
					
					
				}else if (variable.toLowerCase().contains("actos")){
					ActoBo actos = new ActoBoImpl();
					Acto acto = actos.buscarPorIdCompleto(fvariable.getIdacto());
					tokensValorFrm.put("actos", acto.getSuboperacion().getOperacion().getDsnombre());
					
				}else if(variable.toLowerCase().contains("cliente")){
					if(this.expediente==null){
						getExpediente(fvariable.getIdacto());
					}
					tokensValorFrm.put("cliente", this.expediente.getTramite().getCliente().getDsnombrecompleto());

					
				}else if(variable.toLowerCase().contains("abogado_iniciales")){
					if(this.expediente==null){
						getExpediente(fvariable.getIdacto());
					}
					tokensValorFrm.put("abogado_iniciales", this.expediente.getAbogado().getDsiniciales());
					
				}else if(variable.toLowerCase().contains("numero_escritura") || variable.toLowerCase().contains("fecha_escritura") 
						|| variable.toLowerCase().contains("escritura_notari") ||  variable.toLowerCase().contains("mismo_firma")){
					EscrituraActoBo escActo = new EscrituraActoBoImpl();
					String escId = escActo.obtenIdEscrituraPorActoId(fvariable.getIdacto());
					EscrituraBo escBo= new EscrituraBoImpl();
					Escritura escritura = new Escritura();
					escritura.setIdescritura(escId);
					escritura = escBo.buscarPorIdCompleto(escritura);
					
					if(escritura !=null){
						VariableToTokenTransformer vt = new VariableToTokenTransformer();
						String funcion = vt.getDefinicionFuncion(variable);
						String valor = (!funcion.isEmpty())?vt.evaluaFuncionVariable(funcion, escritura.getDsnumescritura()):escritura.getDsnumescritura();
						tokensValorFrm.put("numero_escritura",valor);
						VariableToTokenTransformer vtt = new VariableToTokenTransformer();
						
						String fecha = vtt.evaluaFuncionVariable(funcion,escritura.getFechacreacion().toString());
						if(!tokensValorFrm.containsKey("fecha_escritura") && variable.contains("fecha_escritura")){
							tokensValorFrm.put("fecha_escritura", fecha);								
						}
						
						if(variable.toLowerCase().contains("escritura_notari") || variable.toLowerCase().contains("mismo_firma")){
							Usuario notario = escritura.getNotario();
							NotariaBo notariaBo = new NotariaBoImpl();
							StringBuilder numeroNotaria = new StringBuilder(notariaBo.obtenerNumNotariaByInicialesNotario(notario.getDsiniciales()));
							StringBuilder notarioNombre = new StringBuilder(notario.getDsnombre() + " "+ notario.getDspaterno()+" "+notario.getDsmaterno());
							StringBuilder notarioMismo =  new StringBuilder("el Lic." + notarioNombre);
							
							Usuario notariodoc = ad.getNotario();
							if(notariodoc.getDsiniciales().equals(notario.getDsiniciales())){
								notarioMismo.setLength(0);
								notarioMismo.append("mí");
							}
							
							tokensValorFrm.put("escritura_notario",notarioNombre.toString());
							tokensValorFrm.put("escritura_notaria",numeroNotaria.toString());
							tokensValorFrm.put("mismo_firma",notarioMismo.toString());
						}
						
					}
					
					
					
				}
			}			
		}
		return tokensValorFrm;
	}
	//<-- cafaray 130814
	
	
	/**
	 * Valida los campos requeridos para realizar el replace de variables
	 * 
	 * @param listaForms
	 * @param texto
	 * @return
	 */
	private boolean validaRequeridos(List<Formulario> listaForms, String texto) {
		if (listaForms == null) {
			logger.info(NO_EXISTEN_FORMULARIOS);
			return false;
		}
		if (texto == null || texto.isEmpty()) {
			logger.info(PLANILLA_DOCUMENTO_VACIA);
			return false;
		}
		return true;
	}
	
	public List<VariablesTokens> getListaVariables(String htmlText) throws NotariaException{
		
		if (htmlText.isEmpty()) {
			logger.info(PLANILLA_DOCUMENTO_VACIA);
			return null;
		}

		String[] identificadorPermitidos = {
				EnumVariables.VAR_IDENTIFIER.toString(),
				EnumVariables.GPO_IDENTIFIER.toString(),
				EnumVariables.COMPARECIENTE_IDENTIFIER.toString(),
				EnumVariables.COMPARECIENTES_IDENTIFIER.toString()
		};
		/**
		 * Tokens permitidos '${var:nombreVar}' '${gpo:nombreGrupo}'
		 **/
		String[] tokensToReplace = tokenExtractor.getTokensToReplace(htmlText,
				EnumVariables.VAR_PREFIX.toString(),
				EnumVariables.VAR_SUFIX.toString(), identificadorPermitidos);
		if (tokensToReplace == null) {
			logger.info(String.format("=====> No se lograron encontrar tokens %s y %s para buscar valores. ",EnumVariables.VAR_IDENTIFIER, EnumVariables.GPO_IDENTIFIER));
			return null;
		}
		/** Descomponer grupos en variables **/
		return  tokenToVariable.obtenSoloVariables(tokensToReplace, false);
		
	}
	/*
	 * 
	 * 
	 * */
	public Reader remplazaVariablesGenerales(String htmlText) throws NotariaException{
			String[] identificadorPermitidos = {
					EnumVariables.VAR_IDENTIFIER.toString(),
					EnumVariables.GPO_IDENTIFIER.toString(),
					EnumVariables.COMPARECIENTE_IDENTIFIER.toString(),
					EnumVariables.COMPARECIENTES_IDENTIFIER.toString()
			};
			String[] tokensToReplace = tokenExtractor.getTokensToReplace(htmlText,
					EnumVariables.VAR_PREFIX.toString(),
					EnumVariables.VAR_SUFIX.toString(), identificadorPermitidos);
			List<VariablesTokens> listaVariables = getListaVariables(htmlText);
			if (listaVariables != null) {
				/** Obtener valores de variables en base de datos. **/
				/**
				 * Map<token,valor> para sustituir el token de plantilla por el
				 * valor
				 **/
				VariableValueAssigner varvalassigner = new VariableValueAssigner();
				
				
				
				varvalassigner.obtenValoresComplejosVariables(listaVariables,null);
				
				Map<String, String> tokensValor = variableToToken
						.obtenTokenConValores(listaVariables, tokensToReplace);
				
				
				return remplazaTokensXValor(tokensValor,htmlText);
								
			}else{
				logger.info("=====> No se localizaron variables para asignar valor");
			}
			return null;
		}
	
	
	
	public Reader remplazaTokensXValor(Map<String, String> tokensConValor,String htmlConVariables){

		Reader reader = null;
		if (tokensConValor != null) {
			reader = tokenReplacer.remplazaTokenVariablePorValor(htmlConVariables,
					tokensConValor, EnumVariables.VAR_PREFIX.toString(),
					EnumVariables.VAR_SUFIX.toString());
		}
		
		return reader;
	}
	
	
}
