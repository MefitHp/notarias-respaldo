package com.palestra.notarias.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.impl.GrupoBoImpl;
import com.palestra.notaria.bo.impl.VariableBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.utils.VariableUtils;

/**
 * Clase que de una plantilla de documento los tokens de variables delimitados
 * por ${ y }.
 * 
 * @author sofia
 * 
 */
public class TokenExtractor {

	static Logger logger = Logger.getLogger(TokenExtractor.class);
	
	static final String VARIABLES_INEXISTENTES = "No existen variables dentro de la plantilla de documento.";
	static final String VARIABLE_NO_PERMITIDAS = "Las variables encontradas en la plantilla no son permitidas.";


	/** Llamadas a BO **/
	private GrupoBoImpl grupoBo;
	private VariableBoImpl variableBo;

	public TokenExtractor() {
		if (grupoBo == null) {
			grupoBo = new GrupoBoImpl();
		}
		if (variableBo == null) {
			variableBo = new VariableBoImpl();
		}

	}

	public TokenExtractor(GrupoBoImpl grupoBo, VariableBoImpl variableBo) {
		this.grupoBo = grupoBo;
		this.variableBo = variableBo;
	}

	/**
	 * Obtiene tokens dentro de la plantilla del documento. Deliminados por
	 * el sufijo y prefidjo dado. 
	 * 
	 * @param htmlText
	 * @return Lista de variables encontradas en el documento
	 * 
	 */
	public String[] obtenTokensPlantilla(String htmlText, String varPrefix, String varSufix) {
		logger.info("=====> obtenTokensPlantilla() : " + varPrefix + " ... " + varSufix);
		
		String[] tokensPlantilla = StringUtils.substringsBetween(htmlText,
				varPrefix, varSufix);
		
		if (tokensPlantilla == null) {
			return null;
		}
		
		
		/** Remover duplicados **/
		String[] varNoDuplicate = new TreeSet<String>(
				Arrays.asList(tokensPlantilla)).toArray(new String[0]);
		logger.info("====> " + varPrefix + " ... " + varSufix + " #Tokens obetenidos: " + varNoDuplicate.length);
		return varNoDuplicate;
	}
	

	/**
	 * Devuelve una lista de tokens permitidos definido por los identificadores
	 * Ejemplo: 'identificador:variableDefinicion'
	 * 
	 * @param tokens
	 *            Tokens obtenidos de la plantilla
	 */
	public String[] obtenTokensPermitidos(String[] tokens, String[] identificadores) {
		logger.info("===> obtenTokensPermitidos(): validar que la lista de tokens obtenidos son permitidos ");
		//for(String s: tokens){
		//	logger.info("===> Token: " + s );
		//}
		List<String> arrayPermitidos = new ArrayList<String>();
		for (String s : tokens) {
			//Se quito validacion por compraventa 
//			if (s.matches(VariableUtils.validIdentificadorExpresion(identificadores))) { 
//				logger.info(s + " -> token valido");
				arrayPermitidos.add(s);
//			} else {
//				logger.info(s + " -> token no valido");
//			}
		}
		return new HashSet<String>(arrayPermitidos)
				.toArray(new String[0]);
	}
	
	/**
	 * Obtiene los tokens a reemplazar, tokens validos.
	 * @param htmlText
	 * @param prefix
	 * @param sufix
	 * @param idenfiersAllow
	 * @return
	 */
	public String[] getTokensToReplace(String htmlText, String prefix, String sufix, String[] idenfiersAllow){
		String[] tokensPlantilla = this.obtenTokensPlantilla(htmlText, prefix, sufix);
		if (tokensPlantilla == null) {
			logger.info(VARIABLES_INEXISTENTES);
			return null;
		}
		//String[] tokensToReplace = this.obtenTokensPermitidos(tokensPlantilla, idenfiersAllow);
		//if (tokensToReplace == null || tokensToReplace.length <= 0) {
		//	logger.info(VARIABLE_NO_PERMITIDAS);
		//	return null;
		//}
		//return tokensToReplace;
		return tokensPlantilla;
	}


	/**
	 * Devuelve solo tokens que existan en la base de datos
	 * 
	 * @return Tokens registrados en bd.
	 * @throws NotariaException 
	 */
	public String[] tokensEnBaseDatos(List<String> tokenPermitidos) throws NotariaException {

		List<String> validTokens = new ArrayList<>();
		List<Grupo> grupos = new ArrayList<>();
		try {
			grupos = grupoBo.findAll();
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		List<Variable> variables = variableBo.findAllComplete();

		for (String token : tokenPermitidos) {

			if (token.contains(EnumVariables.GPO_IDENTIFIER.toString())) {
				for (Grupo g : grupos) {
					if (VariableUtils.getNombreGrupo(token).equals(
							g.getDsgrupo())) {
						validTokens.add(token);
					}
				}
			} else if (token.contains(EnumVariables.VAR_IDENTIFIER.toString())) {
				for (Variable var : variables) {
					if (VariableUtils.getNombreVariable(token).equals(
							var.getDsnombre())) {
						validTokens.add(token);
					}
				}
			}
		}

		/** Remover duplicados **/
		String[] varNoDuplicate = new HashSet<String>(validTokens)
				.toArray(new String[0]);

		return varNoDuplicate;
	}

}
