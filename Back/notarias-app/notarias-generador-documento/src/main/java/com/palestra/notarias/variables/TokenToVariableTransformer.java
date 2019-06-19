package com.palestra.notarias.variables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.impl.ConSubFormularioBoImpl;
import com.palestra.notaria.bo.impl.GrupoBoImpl;
import com.palestra.notaria.bo.impl.VariableBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.modelo.VariableGrupo;
import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.pojo.FormTokens;
import com.palestra.notarias.pojo.TableTokens;
import com.palestra.notarias.pojo.VariablesTokens;
import com.palestra.notarias.utils.VariableUtils;

/**
 * Dada una lista de tokens validos de la forma
 * ${var:nombreVar}/${gpo:nombreGrupo} crea una lista de
 * {@link com.palestra.notarias.pojo.VariablesTokens}
 * 
 * @author sofia
 * 
 */
public class TokenToVariableTransformer {

	static Logger logger = Logger.getLogger(TokenToVariableTransformer.class);

//	private static final String REGEX_PREFIX_SUBFORM_SHORTNAME = "^" + EnumVariables.TBL_IDENTIFIER.toString();
//	private static final String REGEX_TBL_IDENTIFIER = "^" + EnumVariables.TBL_IDENTIFIER.toString();
	private static final String REGEX_BRACKET_OPEN = "\\" + EnumVariables.BRACKET_OPEN.toString();
	private static final String REGEX_BRACKET_CLOSE = "\\" + EnumVariables.BRACKET_CLOSE.toString();
//	private static final String REGEX_SUFIX_ROWDEFINICION = "\\" + EnumVariables.BRACKET_CLOSE.toString() + "$";
	
	/** Llamadas a BO **/
	private GrupoBoImpl grupoBo = new GrupoBoImpl();
	private VariableBoImpl variableBo = new VariableBoImpl();
	private TokenExtractor tokenExtractor = new TokenExtractor(grupoBo, variableBo);
	private ConSubFormularioBoImpl conSubFormulario = new ConSubFormularioBoImpl();

	public TokenToVariableTransformer() {}

	public TokenToVariableTransformer(GrupoBoImpl grupoBo,
			VariableBoImpl variableBo) {
		this.grupoBo = grupoBo;
		this.variableBo = variableBo;
	}
	
	
	public List<VariablesTokens> obtenSoloVariables(String texto) throws NotariaException {
		Grupo grupoFilter = new Grupo();
		List<VariablesTokens> listaVariables = new ArrayList<>();
		TokenExtractor extractor = new TokenExtractor();
		String[] vars = extractor.obtenTokensPlantilla(texto, EnumVariables.VAR_PREFIX.toString(), EnumVariables.VAR_SUFIX.toString());
		for (String var : vars) {
			if (var.contains(EnumVariables.GPO_IDENTIFIER.toString())) {
				String nombreGrupo = VariableUtils.getNombreGrupo(var);
				grupoFilter.setDsgrupo(nombreGrupo);
				/** Buscar en la BD, las variables que conforman el grupo **/
				List<Grupo> grupos = grupoBo.findByProperties(grupoFilter);
				for (Grupo grupo : grupos) {
					List<VariableGrupo> varsFromGroup = grupoBo.getVariablesByGrupo(grupo.getIdgrupo());
					if (varsFromGroup != null && varsFromGroup.size()>0) {
						for (VariableGrupo varGrupo : varsFromGroup) {
							if (varGrupo.getVariable() != null) {
								VariablesTokens variablesTokens = new VariablesTokens();
								variablesTokens.setOriginalName(varGrupo.getVariable().getDsnombre());
								variablesTokens.setDeficionFuncion(VariableUtils.obtenDefinicionFuncion(var));
								variablesTokens.setName(varGrupo.getVariable().getDsnombre());
								variablesTokens.setGroup(nombreGrupo);
								variablesTokens.setType(EnumVariables.GPO_IDENTIFIER.toString());
								variablesTokens.setOrder(varGrupo.getDsorden());
								listaVariables.add(variablesTokens);
							} else {
								logger.info(String.format("=====> Se localizo una variable NULL dentro del grupo %s.", nombreGrupo));
							}
						}
					} else {
						logger.info(String.format("=====> No se localizaron variables asociadas al grupo %s[%d].", nombreGrupo,varsFromGroup!=null?varsFromGroup.size():"-1"));
					}
				}
			} else {				
				listaVariables.add(construyeVariableTokens(var));				
			}
		}
		
		return listaVariables;
	}

	/**
	 * Procesa los tokens para devolver una lista de variables existentes en la
	 * BD. Los tokens de tipo grupo('gpo:nombreGrupo') son procesados y
	 * descompuestos en variables.
	 * 
	 * @param vars
	 *            Lista de variables y grupos
	 * @param forceReplace
	 *            Boolean para indicar si la variable tiene valor null forzar el
	 *            replace por '_'
	 * @return Lista con unicamente variables
	 * @throws NotariaException 
	 */
	public List<VariablesTokens> obtenSoloVariables(String[] vars,
			Boolean forceReplace) throws NotariaException {
		Grupo grupoFilter = new Grupo();
		List<VariablesTokens> listaVariables = new ArrayList<VariablesTokens>();
		for (String var : vars) {
			if (var.contains(EnumVariables.GPO_IDENTIFIER.toString())) {
				String nombreGrupo = VariableUtils.getNombreGrupo(var);
				grupoFilter.setDsgrupo(nombreGrupo);
				/** Buscar en la BD, las variables que conforman el grupo **/
				List<Grupo> grupos = grupoBo.findByProperties(grupoFilter);
				for (Grupo g : grupos) {
					List<VariableGrupo> varsFromGroup = grupoBo.getVariablesByGrupo(g.getIdgrupo());
					if (varsFromGroup != null && varsFromGroup.size()>0) {
						for (VariableGrupo varGrupo : varsFromGroup) {
							if (varGrupo.getVariable() != null) {
								VariablesTokens variablesTokens = new VariablesTokens();
								variablesTokens.setOriginalName(varGrupo.getVariable().getDsnombre());
								variablesTokens.setDeficionFuncion(VariableUtils.obtenDefinicionFuncion(var));
								variablesTokens.setName(varGrupo.getVariable().getDsnombre());
								variablesTokens.setGroup(nombreGrupo);
								variablesTokens.setType(EnumVariables.GPO_IDENTIFIER.toString());
								variablesTokens.setOrder(varGrupo.getDsorden());
								listaVariables.add(variablesTokens);
							} else {
								logger.info(String.format("=====> Se localizo una variable NULL dentro del grupo %s.", nombreGrupo));
							}
						}
					} else {
						logger.info(String.format("=====> No se localizaron variables asociadas al grupo %s[%d].", nombreGrupo,varsFromGroup!=null?varsFromGroup.size():"-1"));
					}
				}
			} else if(var.contains(EnumVariables.VAR_IDENTIFIER.toString())){	
				VariablesTokens vt = construyeVariableTokens(var);
				if(vt!=null){
					listaVariables.add(vt);
				} else {
					logger.warn(String.format("=====> La variable %s no se logro contruir, por lo que no sera considerada en el proceso de transformacion.",var));
				}
			} else if(var.contains(EnumVariables.COMPARECIENTE_IDENTIFIER.toString()) || var.contains(EnumVariables.COMPARECIENTES_IDENTIFIER.toString())){
				VariablesTokens vt = construyeVariableCompareciente(var);
				if(vt!=null){
					listaVariables.add(vt);
				} else {
					logger.warn(String.format("=====> La variable %s no se logro contruir, por lo que no sera considerada en el proceso de transformacion.",var));
				}
			}
		}
		asignarPropsFromDataBases(listaVariables, forceReplace);
		return listaVariables;
	}

	private VariablesTokens construyeVariableCompareciente(String variable){
		if (variable.indexOf("[")>variable.indexOf(":")){
			VariablesTokens variablesTokens = new VariablesTokens();
			variablesTokens.setOriginalName(VariableUtils.removeVarIdentificador(variable));			
			variablesTokens.setDeficionFuncion(variable.substring(variable.indexOf(":")+1,variable.indexOf("["))); // en definicion funcion se coloca el tipo de compareciente(s)
			if(variablesTokens.getDeficionFuncion().contains(".")){
				String tmp = variablesTokens.getDeficionFuncion();
				variablesTokens.setDeficionFuncion(tmp.substring(0,tmp.indexOf(".")));
				variablesTokens.setTipoDato(tmp.substring(tmp.indexOf(".")+1));
			}			
			// se genera el arreglo con todas las variables a interpretar en el bloque compareciente
			String contenido = variable.substring(variable.indexOf("[")+1,variable.indexOf("]"));
			String[] variables = contenido.split("\\+");
			StringBuilder nombresVariable = new StringBuilder();
			for(String v: variables){
				v = v.replace("&nbsp;", " ");				
				v = v.trim();
				nombresVariable.append(v).append("|");
			}
			variablesTokens.setName(nombresVariable.toString().substring(0,nombresVariable.length()-1));
			if(variable.substring(0,4).equals(EnumVariables.COMPARECIENTE_IDENTIFIER.toString())){
				variablesTokens.setType(EnumVariables.COMPARECIENTE_IDENTIFIER.toString());
			}else{
				variablesTokens.setType(EnumVariables.COMPARECIENTES_IDENTIFIER.toString());
			}
			
			variablesTokens.setFiltroFormulario(null);
			return variablesTokens;
		}
		return null;
	}
	
	
	
	private VariablesTokens construyeVariableTokens(String variable){
		VariablesTokens variablesTokens = new VariablesTokens();
		variablesTokens.setOriginalName(VariableUtils.removeVarIdentificador(variable));
		variablesTokens.setDeficionFuncion(VariableUtils.obtenDefinicionFuncion(variable));
		variablesTokens.setName(VariableUtils.getNombreVariable(variable));				
		variablesTokens.setGroup(null);
		variablesTokens.setType(EnumVariables.VAR_IDENTIFIER.toString());
		if (variable.indexOf("!")>0 && variable.indexOf(".", variable.indexOf("!"))>0){
			//hace referencia a un filtro de variable simple/compleja
			String referencia = variable.substring(variable.indexOf("!")+1);
			referencia = referencia.substring(0, (referencia.indexOf("(")>0?referencia.indexOf("(")
					:referencia.indexOf("}")>0?referencia.indexOf("}"):referencia.length()-1));
			variablesTokens.setFiltroFormulario(referencia);
			variablesTokens.setName(variablesTokens.getName().substring(0, variablesTokens.getName().indexOf("!")));
		}else{
			variablesTokens.setFiltroFormulario(null);;
		}
		return variablesTokens;
	}
	
	/**
	 * Asignar propiedades definidas a variables
	 * 
	 * @param listaVariables
	 * @param forceReplace
	 * @throws NotariaException 
	 */
	public void asignarPropsFromDataBases(List<VariablesTokens> listaVariables,
			Boolean forceReplace) throws NotariaException {
		//List<Variable> variables = variableBo.findAllComplete();
		if (listaVariables != null) {
			for (VariablesTokens varToken : listaVariables) {
				//for (Variable dataBaseVar : variables) {
				try{
					Variable variable = variableBo.findByName(varToken.getName());
					if(variable!=null){
						//if (varToken.getName().equals(dataBaseVar.getDsnombre())) {
						varToken.setLongitud(variable.getCdlongitud());
						varToken.setTipoDato(variable.getTipoDato().getDselemento());
						varToken.setTipoFiltro(variable.getDsfiltrado());
						varToken.setForceReplace(forceReplace);
					}else{
						logger.warn(String.format("=====> La variable %s no se encuentra en la unidad de persistencia. Revise.",varToken.getName()));
						varToken.setLongitud(null);
						//varToken.setTipoDato(null);
						varToken.setTipoFiltro(null);
						varToken.setForceReplace(forceReplace);					
					}
				}catch(NullPointerException e){
					logger.warn(String.format("=====> Ocurrio una excepción al localizar un valor nulo. Revise [%s]", e.getMessage()));
				}catch(NotariaException e){
					logger.warn(String.format("=====> Ocurrio una excepción al localizar la variable %s en la unidad de persistencia. Revise.",varToken.getName()));
					varToken.setLongitud(null);
					varToken.setTipoDato(null);
					varToken.setTipoFiltro(null);
					varToken.setForceReplace(forceReplace);					
				}			
			}
		}
	}

	/**
	 * Obtiene las tablas de tokens de la forma 'tbl:definicionTabla' y obtiene
	 * una lista de TblDinamicFormTokens.
	 * 
	 * Ejemplo de estructura de token de tabla:
	 * 
	 * $(tbl:nombreCortoConSubForm[${frm:nombreCortoConForm[varNombre]}
	 * ${frm:nombreCortoConForm[varNombre]}])
	 * 
	 * De aqui: nombre corto del consubformulario = nombreCortoConSubForm
	 * deficion de la tabla = ${frm:nombreCortoConForm[varNombre]}
	 * ${frm:nombreCortoConForm[varNombre]} definicion de columnas: columna1:
	 * ${frm:nombreCortoConForm[varNombre]} columna2:
	 * ${frm:nombreCortoConForm[varNombre]}
	 * 
	 * @param vars
	 * @return
	 * @throws NotariaException 
	 */
	public List<TableTokens> obtenTablasTokens(String[] vars,List<Formulario> listaFormularios) throws NotariaException {

		List<TableTokens> listaTablas = new ArrayList<TableTokens>();

		for (String var : vars) {// s=
								// 'tbl:nombreCortoConSubForm[${frm:nombreCortoConForm[varNombre]}
								// ${frm:nombreCortoConForm[varNombre]}]'
			// 100914 --> cafaray: se extrae de otra forma el nombre del subform
			var = var+"]"; // le colocamos el cierre del identificador
			// 100914 --> cafaray: se extrae de otra forma el nombre del subform
			TableTokens varTabla = new TableTokens();
			varTabla.setOriginalName(var);
			// Substring entre '^tbl:' y '['
			// 100914 --> cafaray: se extrae de otra forma el nombre del subform
				//ANTES:::String shortNameSubForm = VariableUtils.subStringFromDelimiter(
				//		REGEX_PREFIX_SUBFORM_SHORTNAME, REGEX_BRACKET_OPEN, s);
			String shortNameSubForm = var.substring(0, var.indexOf("["));
			// 100914 --> cafaray: se extrae de otra forma el nombre del subform
			if (shortNameSubForm == null) {
				logger.info("No se pudo obtener nombre corto de confsubformulario");
				continue;
			}
			//ejemplo del nombre del subformulario: subfrm_subform_0: antes del primer _ es el form, lo que sigue es el subform
			//debido a que los nombres se colocan en automatico, la busqueda se hace a través de "_"
			varTabla.setNombreCortoSubForm(shortNameSubForm.substring(shortNameSubForm.indexOf("_subform")+1));
			varTabla.setFiltroFormulario(shortNameSubForm.substring(0, shortNameSubForm.indexOf("_subform")));
			
			// 100914 --> cafaray: se extrae el contenido del subformulario, se deben de colocar los textos 
			// entrcomillados tal cual aparecen en la varibale, luego se hará el análisis para encontrar las variables
			// sus valores y se irá armando el texto.
			
				//String tablaDefinicion = VariableUtils.subStringFromDelimiter(
				//		REGEX_TBL_IDENTIFIER + varTabla.getNombreCortoSubForm()
				//				+ REGEX_BRACKET_OPEN, REGEX_SUFIX_ROWDEFINICION, s);
			
			//identificación de variables:
			String tablaDefinicion = null;
			int posicionInicial = -1;
			int posicionFinal = -1;
			int posicionActual = 0;
			char[] caracteres = var.toCharArray();
			for(char caracter:caracteres){
				if(caracter=='[') posicionInicial = posicionActual+1;
				if(caracter==']') posicionFinal = posicionActual;
				posicionActual++;
			}
			if(posicionInicial>-1 && posicionFinal>posicionInicial){
				tablaDefinicion = var.substring(posicionInicial, posicionFinal);
				if(tablaDefinicion.endsWith("])")){
					tablaDefinicion = tablaDefinicion.replace("])", "");
				}
				//tablaDefinicion = tablaDefinicion.replace("&squot;", "'"); // es necesario para poder hacer la agrupación de elementos
				tablaDefinicion = tablaDefinicion.replace("&lsquo;", "'");
				tablaDefinicion = tablaDefinicion.replace("&rsquo;", "'");
				tablaDefinicion = tablaDefinicion.replace("&#39;", "'");
				//Ahora identificamos las variables de subformulario, estas se ecuentran separadas por espacio y no estan entrecomilladas
				List<String> textoBloques = new ArrayList<>();
				List<String> subVariables = new ArrayList<>();
				if(tablaDefinicion.contains("'") || tablaDefinicion.contains("+")){ //se trata de variables y texto
					boolean inicioComillas = false;
					StringBuilder texto = new StringBuilder();
					StringBuilder variable = new StringBuilder();
					caracteres = tablaDefinicion.toCharArray();
					for(char caracter:caracteres){
						if(caracter=='\'' && inicioComillas){
							inicioComillas = false;
							textoBloques.add(texto.toString());
							subVariables.add("texto="+texto.toString());
							texto = new StringBuilder();
						} else if(caracter == '\''){
							inicioComillas = true;						
						} else if(!(caracter == '+') && !inicioComillas) {
							variable.append(caracter);
						} else if(caracter == '+' && !inicioComillas) {
							if(variable.toString().trim().length()>0)subVariables.add(variable.toString().trim());							
							variable = new StringBuilder();
						} else {
							texto.append(caracter);
						}						
					}
					if(texto.toString().trim().length()>0) {
						textoBloques.add(texto.toString());
						subVariables.add("texto="+texto.toString()); 
					}
					if(variable.toString().trim().length()>0)subVariables.add(variable.toString().trim()); 
					
					logger.info(String.format("Found textBlocks: %d and vars: %d", textoBloques.size(), subVariables.size() ));
				}else if(tablaDefinicion.contains("+")){ //solo se trata de variables
					StringBuilder variable = new StringBuilder();
					caracteres = tablaDefinicion.toCharArray();					
					for(char caracter:caracteres){
						if(!(caracter == '+')) {
							variable.append(caracter);
						} else if(caracter == '+') {
							if(variable.toString().trim().length()>0)subVariables.add(variable.toString().trim());							
							variable = new StringBuilder();
						}						
					}
					if(variable.toString().trim().length()>0)subVariables.add(variable.toString().trim());
					logger.info(String.format("Found vars: %d", subVariables.size() ));
				} else { //solo es una variable
					StringBuilder variable = new StringBuilder();
					caracteres = tablaDefinicion.toCharArray();					
					for(char caracter:caracteres){
							variable.append(caracter);
					}
					if(variable.toString().trim().length()>0)subVariables.add(variable.toString().trim());
					logger.info(String.format("Found vars: %d", subVariables.size() ));
				}
				varTabla.setTextos(textoBloques);
				varTabla.setVariables(subVariables);
			}else{
				logger.info(String.format("La definicion de la variable %s no es correcta, no se ha podido identificar, "
						+ "se esperaba [ ... ] y se tienen los indices %d-%d.%n",
						var,posicionInicial, posicionFinal));
				continue;
			}			
			
			
			varTabla.setDefinicionTextoRow(tablaDefinicion);
			// Obtener definicion de columnas
			//varTabla.setColumnDefinicions(obtenerFrmTokens(varTabla.getDefinicionTextoRow()));
			listaTablas.add(varTabla);
		}
		// Asignar propiedades from database
		dataBasePropsForColumnsDefinition(listaTablas,listaFormularios);		

		logger.info("======== TABLAS TOKENS OBTENIDOS =================== ");
		for (TableTokens token : listaTablas) {
			logger.info(token.getDefinicionTextoRow());
		}
		logger.info("====================================================");
		return listaTablas;
	}

	/**
	 * Se asignan propiedades de base de dato a los componentes de la definicion
	 * de las columnas. Como longitu, id's de formularios
	 * 
	 * @param listaTablas
	 * @throws NotariaException 
	 * ANALISIS DE CAMPOS ANTERIOR -> cafaray: 220914
	 *			if (listaTabla.getColumnDefinicions() != null) {
					for (FormTokens df : listaTabla.getColumnDefinicions()) {
						Componente componente = componenteBo.buscarPorNombreCortoConForm(df.getNombreCortoConForm(), df.getName());
						if (componente == null) {
							logger.warn("=====> No se localizo el componente en el subformulario: "+ df.getName());
							continue;
						}
						df.setIdConFormulario(componente.getConFormulario().getId().getIdconFormulario());
						df.setConFormularioVersion(componente.getConFormulario().getId().getVersion());
						df.setIdComponente(componente.getIdcomponente());
						df.setLongitud(componente.getLongitudmaxima());
					}
				}
	 * 
	 * 
	 */
	
	/*VICOY:NO RECUPERA POR LOCALIDAD LOS SUBFORMULARIOS YA QUE REGRESA EL PRIMERO SIN IMPORTAR SI ES DEL DF O EDOMEX
	private void dataBasePropsForColumnsDefinition(List<TableTokens> listaTablas,List<Formulario> listaFormularios) throws NotariaException {
		for (TableTokens listaTabla : listaTablas) {
			// Obtener id de subformulario by nombre corto
			StringBuilder nombreSubFormulario = new StringBuilder(listaTabla.getFiltroFormulario());
			nombreSubFormulario.append("_").append(listaTabla.getNombreCortoSubForm());
			String idConSubForm = conSubFormulario.obtenerIdConSubFormByShortName(nombreSubFormulario.toString());
			listaTabla.setTipoFiltro(conSubFormulario.obtenerIdConFormularioByIdSubFormulario(idConSubForm));
			if(idConSubForm!=null && !idConSubForm.isEmpty()){
				listaTabla.setIdConSubFormulario(idConSubForm);
			}else {
				logger.warn("=====> No se localizo el identificador del subformulario: "+listaTabla.getNombreCortoSubForm());
			}
		}
	}*/
	
	private void dataBasePropsForColumnsDefinition(List<TableTokens> listaTablas,List<Formulario> listaFormularios) throws NotariaException {
		for (TableTokens listaTabla : listaTablas) {
			// Obtener id de subformulario by nombre corto
			StringBuilder nombreSubFormulario = new StringBuilder(listaTabla.getFiltroFormulario());
			nombreSubFormulario.append("_").append(listaTabla.getNombreCortoSubForm());
			List<ConSubFormulario> subforms = conSubFormulario.obtenerConSubFormsByShortName(nombreSubFormulario.toString());
			String idConForm="";
			String idConSubForm="";
			for(ConSubFormulario csf:subforms){
				for(Formulario frm :listaFormularios){
					System.out.println("***************************************************************");
					System.out.println("FORM::"+frm.getConFormulario().getId().getIdconFormulario());
					System.out.println("FORM VERSION::"+frm.getConFormulario().getId().getVersion());
					System.out.println("CONSUBFORM::"+csf.getConFormulario().getId().getIdconFormulario());
					System.out.println("CONSUBFORM VERSION::"+csf.getConFormulario().getId().getVersion());
					System.out.println("***************************************************************");
					if(frm.getConFormulario().getId().getIdconFormulario().equals(csf.getConFormulario().getId().getIdconFormulario()) && 
							frm.getConFormulario().getId().getVersion().equals(csf.getConFormulario().getId().getVersion())){
						idConForm = csf.getConFormulario().getId().getIdconFormulario();
						idConSubForm = csf.getIdconsubform();
						break;
					}
				}
			}
			// agrego el funcionamiento anterior de buscar el primer resultado
			if(idConForm.isEmpty() && idConSubForm.isEmpty()){
				idConSubForm = conSubFormulario.obtenerIdConSubFormByShortName(nombreSubFormulario.toString());
				listaTabla.setTipoFiltro(conSubFormulario.obtenerIdConFormularioByIdSubFormulario(idConSubForm));
				if(idConSubForm!=null && !idConSubForm.isEmpty()){
					listaTabla.setIdConSubFormulario(idConSubForm);
				}else {
					logger.warn("=====> No se localizo el identificador del subformulario: "+listaTabla.getNombreCortoSubForm());
				}
			}
			
			listaTabla.setTipoFiltro(idConForm);
			if(idConSubForm!=null && !idConSubForm.isEmpty()){
				listaTabla.setIdConSubFormulario(idConSubForm);
			}else {
				logger.warn("=====> No se localizo el identificador del subformulario: "+listaTabla.getNombreCortoSubForm());
			}
		}
	}
	
		
	/**
	 * Procesa la definicion de la tabla, y obtiene cada uno de los tokens que
	 * la forman. Busca '${frm:definicionVariable}'
	 * 
	 * @param texto
	 * @return
	 */
	public List<FormTokens> obtenerFrmTokens(String texto) {
		List<FormTokens> frmTokensDefinitions = new ArrayList<FormTokens>();
		logger.info("===> inicia TokenToVariable.obtenerFrmTokens():");
		if (texto == null || texto.isEmpty()) {
			return null;
		}
		String[] identificadorPermitidos = {EnumVariables.FRM_IDENTIFIER.toString(), ""};
		String[] tokensToReplace = tokenExtractor.getTokensToReplace(texto, 
						EnumVariables.VAR_PREFIX.toString() + EnumVariables.FRM_IDENTIFIER,
						EnumVariables.VAR_FRM_SUFIX.toString(),
						identificadorPermitidos);
		if (tokensToReplace == null) {
			return null;
		}
		for (String s : tokensToReplace) {
			FormTokens var = new FormTokens();
			var.setOriginalName(s);
			var.setType(EnumVariables.FRM_IDENTIFIER.toString());
			if(s.indexOf("(funcion")>0 && s.indexOf(")",s.indexOf("(funcion"))>0){
				String f = s.substring(s.indexOf("(funcion")+1,s.indexOf(")",s.indexOf("(funcion")));
				System.out.println("=====> =====> =====> FUNCION ::::: "+ f);
				var.setDeficionFuncion(f);				
			}
			// Obener 'nombreCortoForm[...'. Se quito el cierre de corchetes, por que habia ambiguedad
			String nombreCortoForm = StringUtils.substringBefore(s,	EnumVariables.BRACKET_OPEN.toString());
			var.setNombreCortoConForm(nombreCortoForm);
			// Agregar el ']', para que quede 'nombreCorto[nombreVariable]' y
			// extraer el nombre variable. Para compraventa, se cambiaron los delimitadores 
			String newS = s + "]";
			newS = newS.replaceAll("\n|\r", "");
			// Substring between '[nombre(contenido=XXXX)] =>
			// nombre(contenido=XXXX)'
			String variableCompleto = VariableUtils.subStringFromDelimiter(REGEX_BRACKET_OPEN, REGEX_BRACKET_CLOSE, newS);
			var.setName(VariableUtils.getNombreVariable(variableCompleto));
			// Si tiene contenido. 'nombre(algo=XXXX)' => extraer 'XXXX'.
			if(variableCompleto.contains("(contenido=")){
				//No confundir contenido con funcion. TODO: mejorar esto!.
				String contenido = variableCompleto.substring(variableCompleto.indexOf("="), variableCompleto.lastIndexOf(")"));
				//String contenido = StringUtils.substringBetween(variableCompleto, "=", ")");
				var.setContenido(contenido);
			}
			frmTokensDefinitions.add(var);
		}
		logger.info(String.format("=======> Termino TokenToVariable.obtenerFrmTokens() con %d variables.", frmTokensDefinitions.size()));
		//for (FormTokens frmVars : frmTokensDefinitions) {
		//	logger.info("=======>" + frmVars.toString());
		//}
		return frmTokensDefinitions;
	}
}