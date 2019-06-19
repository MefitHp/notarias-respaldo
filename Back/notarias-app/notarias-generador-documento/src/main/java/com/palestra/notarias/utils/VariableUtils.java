package com.palestra.notarias.utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComparecienteConyugeBo;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteConyugeBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notarias.constantes.TipoDato;
import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.pojo.CamposOracion;
import com.palestra.notarias.pojo.RegisterTableValues;


public class VariableUtils {

	static String RUTA_XML_QUERY = "querys_variables.xml";
	public static final String CHAR_TO_REPLACE = "_";

	static Logger logger = Logger.getLogger(VariableUtils.class);

	/**
	 * Devuelve el nombre del grupo ${gpo:nombreGrupo(funcion=definicion)} ->
	 * nombreGrupo
	 * 
	 * @param tokenGrupo
	 *            ${gpo:nombreGrupo(funcion=definicion)}
	 * @return nombreGrupo
	 */
	public static String getNombreGrupo(String tokenGrupo) {
		return substringBeforeParenthesis(removeGpoIdenticador(tokenGrupo));
	}

	/**
	 * Del token ${gpo:grupoNombre[(funcion=texto)]}, se elimina el
	 * identificador 'gpo:'
	 **/
	public static String removeGpoIdenticador(String tokenGrupo) {
		return StringUtils.replace(tokenGrupo,
				EnumVariables.GPO_IDENTIFIER.toString(), "");
	}

	/**
	 * Devuelbe el un substring antes de (.
	 * 
	 * @param variable
	 *            valor(algo)
	 * @return valor
	 */
	public static String substringBeforeParenthesis(String valor) {
		return StringUtils.substringBefore(valor, "(");
	}

	/**
	 * Devuelve el nombre de la variable
	 * ${var:nombreVariable(funcion=definicion)} -> nombreVariable
	 * 
	 * @param tokenVariable
	 *            ${gpo:nombreVariable(funcion=definicion)}
	 * @return nombreVariable
	 */
	public static String getNombreVariable(String tokenVariable) {
		return substringBeforeParenthesis(removeVarIdentificador(tokenVariable));
	}

	/**
	 * Del token ${var:grupoNombre[(funcion=texto)]}, se elimina el
	 * identificador 'var:'
	 * 
	 * @param tokenVariable
	 * @return
	 */
	public static String removeVarIdentificador(String tokenVariable) {
		return StringUtils.replace(tokenVariable,
				EnumVariables.VAR_IDENTIFIER.toString(), "");
	}

	/**
	 * Obtiene la definicion de una funcion, retornando una subcadena entre
	 * parentesis.
	 * 
	 * @param tokenVariable
	 *            ${var:nombreVar(definicionFuncion)}
	 * @return definicionFuncion
	 */
	public static String obtenDefinicionFuncion(String tokenVariable) {
		return StringUtils.substringBetween(tokenVariable, "(", ")");
	}

	/**
	 * Obtiene una subcadena entre la expresion regular de prefijo y sufijo
	 * 
	 * @param expresionPrefix
	 * 
	 * @param expresionSufix
	 * @return
	 */
	public static String subStringFromDelimiter(String expresionPrefix,
			String expresionSufix, String cadena) {
		try {
			String regex = expresionPrefix + "(.*?)" + expresionSufix;
			Pattern p = Pattern.compile(regex);
			System.out.println("===== < MATCHER:::"+cadena+":::MATCHER > =====");
			Matcher m = p.matcher(cadena);
			if(m!=null){
				m.find();
				if(m.groupCount()>0){
					String text = m.group(1);
					return text;
				}else{
					return null;
				}
			}else{
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Da formato a listas. Si se obtiene la lista de valores se separan por
	 * comas e y. Ejemplo: valor1, valor2, ... y ValorN
	 * 
	 * @param valores
	 *            Lista de valores
	 * @return
	 */
	public static String daFormatoListas(List<Object> valores) {
		// TODO: validar que ninguno de los elementos venga en nulo, por que se
		// queda cadena null.
		if (valores != null && valores.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(valores.get(0));
			if (valores.size() == 1) {
				if(valores.get(0)==null){
					return null;
				}
				return sb.toString();
			}
			for (int i = 1; i < valores.size() - 1; i++) {
				sb.append(", " + valores.get(i));
			}
			sb.append(" y " + valores.get(valores.size() - 1));

			return sb.toString();
		}
		return null;
	}

	/**
	 * Da formato a listas. Si se obtiene la lista de valores se separan por
	 * comas e y. Ejemplo: valor1, valor2, ... y ValorN
	 * 
	 * @param valores
	 *            Lista de valores
	 * @return
	 */
	public static String daFormatoFilaTablaString(List<RegisterTableValues> valores) {
		if (valores != null && valores.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(valores.get(0).getValueRegisterText());
			if (valores.size() == 1) {
				return sb.toString();
			}
			for (int i = 1; i < valores.size() - 1; i++) {
				sb.append(", " + valores.get(i).getValueRegisterText());
			}
			sb.append(" y ").append(valores.get(valores.size() - 1).getValueRegisterText());
			return sb.toString();
		}
		return null;
	}
	
	public static String daFormatoFilaSaltoString(List<RegisterTableValues> valores) {
		if (valores != null && valores.size() > 0) {
			StringBuilder sb = new StringBuilder();
			if (valores.size() == 1) {
				sb.append(valores.get(0).getValueRegisterText());
				return sb.toString();
			}
			for (int i = 0; i < valores.size(); i++) {
				sb.append("<br/> " + valores.get(i).getValueRegisterText());
			}
			return sb.toString();
		}
		return null;
	}
	
	
	public static void daFormatoFilaTabla(List<RegisterTableValues> valores) {
		if (valores != null && valores.size() > 0) {
			for (int i = 1; i < valores.size() - 1; i++) {
				valores.get(i).setValueRegisterText(", " + valores.get(i).getValueRegisterText());
			}
			valores.get(valores.size() - 1).setValueRegisterText(" y "+valores.get(valores.size() - 1).getValueRegisterText());			
		}
	}
	
	public static void daFormatoFilaSalto(List<RegisterTableValues> valores) {
		if (valores != null && valores.size() > 0) {
			for (int i = 0; i < valores.size() -1; i++) {
				valores.get(i).setValueRegisterText(valores.get(i).getValueRegisterText()+"<br/>");
			}
		}
	}

	/**
	 * Da formato a listas. Si se obtiene la lista de valores se separan por
	 * comas e y. Ejemplo: valor1, valor2, ... y ValorN
	 * 
	 * @param valores
	 *            Lista de valores
	 * @return
	 */
	public static String formatoCSVLista(List<String> valores) {
		if (valores != null && valores.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(valores.get(0));
			if (valores.size() == 1) {
				return sb.toString();
			}
			for (int i = 1; i < valores.size() - 1; i++) {
				sb.append(", " + valores.get(i));
			}
			sb.append(" y ").append(valores.get(valores.size() - 1));
			return sb.toString();
		}
		return null;
	}
	
	/**
	 * Valida si una lista solo esta formada por un elemento null.
	 * 
	 * @param valores
	 * @return
	 */
	public static Boolean isListaEmpty(List<Object> valores) {

		if (valores != null && valores.size() == 1) {
			if (valores.get(0) == null) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Devuele un string con chars separados por pipes, de la forma '$'|'{'|'}'
	 * Que son aquellos caracteres que no deben estar dentro de una variable, se
	 * utiliza para validacion.
	 * 
	 * @return
	 */
	public static String notValidChars(String prefix) {

		StringBuilder invalid = new StringBuilder();
		List<String> items = new ArrayList<String>();
		items.addAll(Arrays.asList(prefix.split("(?!^)")));

		for (int i = 0; i < items.size() - 1; i++) {
			invalid.append("^" + items.get(i) + "|");
		}
		invalid.append("^" + items.get(items.size() - 1));
		return invalid.toString();
	}

	/**
	 * Forma la expresion regular para validar los tokens La expresion ejemplo
	 * de validacion. ^(var:|gpo:).*
	 * 
	 * @return
	 */
	public static String validIdentificadorExpresion(
			String[] idenficadoresValidos) {

		StringBuilder identifiers = new StringBuilder();
		if (idenficadoresValidos == null) {
			return null;
		}
		for (int i = 0; i < idenficadoresValidos.length - 1; i++) {
			identifiers.append(idenficadoresValidos[i] + "|");
		}
		identifiers
				.append(idenficadoresValidos[idenficadoresValidos.length - 1]);

		String expresion = "^(" + identifiers.toString() + ").*";

		return expresion;
	}

	/**
	 * Devuelve el hql de la variable dada. Busca el elemento 'hql' dentro del
	 * archivo resource/querys_variables.xml
	 * 
	 * @param nombreVariable
	 * @return hql de la variable dada.
	 */
	public static String obtenHQL(String nombreVariable) {
		try {
			String expression = "/queries/query[@id='" + nombreVariable
					+ "']/hql";
			String hql = evaluateExpression(expression);
			return hql;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Devuelve el valor de la propiedad proporcionada. Dentro del archivo
	 * resource/querys_variables.xml
	 * 
	 * @param nombreVariable
	 * @param property
	 * @return valor de propiedad
	 */
	public static String getValueByProperty(String nombreVariable,
			String property) {
		try {
			String expression = "/queries/query[@id='" + nombreVariable + "']/"
					+ property;
			String valor = evaluateExpression(expression);
			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Evaluda una expresion en xpath dentro del archivo querys_variables.xml.
	 * 
	 * @param expression
	 * @return
	 */
	private static String evaluateExpression(String expression) {
		try {
			InputStream is = VariableUtils.obtenInputStream();
			if (is == null) {
				return null;
			}
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(is);
			XPath xPath = XPathFactory.newInstance().newXPath();

			/** evaluar la expresion dada **/
			String valor = xPath.compile(expression).evaluate(xmlDocument);

			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static InputStream obtenInputStream() {
		InputStream is = VariableUtils.class.getClassLoader()
				.getResourceAsStream(RUTA_XML_QUERY);
		// TODO: para pruebas pruebas locales y no levantar el servidor
//		 InputStream is =
//		 ClassLoader.class.getResourceAsStream(RUTA_XML_QUERY);
		if (is == null) {
			System.out
					.println("=======> VariableUtils ::: No se pudo obtener archivo xml de definicion de variables complejas");
			return null;
		}
		return is;
	}
	
	/**
	 * Reemplaza 'null' por CHAR_TO_REPLACE tantas veces como la longitud de la
	 * variable lo especifique.
	 * 
	 * @param variable
	 * @return
	 */
	public static String replaceNullForChar(Integer longitud) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < longitud; i++) {
			sb.append(CHAR_TO_REPLACE);
		}
		return sb.toString();
	}

	/***
	 * cafaray 020714 Acoplamiento de la varible compleja, los artefactos actuales (db, xml) no dan para cubrir estos casos, es por esta razon que se acoplan
	 * comparecientesCompraron esta acoplado con la variable compareciente_compraron. donde se determina el texto del antecedente de compra del vendedor
	 * @param idacto String acto donde se buscaran los comparecientes
	 * @return java.lang.String cadena de texto a colocar como valor de la variable
	 * @throws NotariaException 
	 */
	static public String comparecienteCompraron(String idacto) throws NotariaException{
		StringBuilder cadena = new StringBuilder(""); //Cadena donde se devulve el texto de antecedente compra de inmueble
		List<Compareciente> comparecientes = getComparecientes(idacto, "VENDEDOR");
		if(comparecientes!=null && comparecientes.size()>0){
			//localizar la situacion de estado civil de cada compareciente:
			int contComparecientes = comparecientes.size();
			System.out.printf("=======> VariableUtils.comparecienteCompraron(%s) ::: %d%n", idacto, contComparecientes);
			for(Compareciente compareciente: comparecientes){
				cadena.append(calculaTextoComparecienteAmbosCompraron(compareciente)).append("<br />");
			}
			
			return cadena.substring(0, cadena.lastIndexOf("<br />"));
		}else{
			return null;
		}
	}
	
	
	static public String calculaTextoComparecienteAmbosCompraron(Compareciente compareciente) throws NotariaException{
		try{
		StringBuilder cadena = new StringBuilder(""); //Cadena donde se devulve el texto de antecedente compra de inmueble
		ComparecienteConyugeBo conyugeBo = new ComparecienteConyugeBoImpl();				
		ComparecienteConyuge conyuges;
		try {
			conyuges = conyugeBo.findById(compareciente.getIdcompareciente());
			System.out.printf("=======> VariableUtils.comparecienteCompraron ::: conyugue localizado = %s%n", (conyuges==null?"no hay":conyuges.getSujeto().getPersona().getDsnombre()));
		} catch (NotariaException e) {
			System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Exception %s%n",e.getMessage());
			e.printStackTrace();
			return null;
		}
		if(conyuges!=null){
			//aquí van todos los casos:
			if(conyuges.getConyugeCompra()==null){ //se considera que es soltero al momento de la compra
				System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Intuimos que es soltero ya que conyuge compra viene en null");
				String tratamiento = compareciente.getTratamiento().getDselemento();
				char capital = tratamiento.charAt(0);
				tratamiento = String.valueOf(capital).toUpperCase().concat(tratamiento.substring(1, tratamiento.length()));
				System.out.println("tratamiento: "+tratamiento);

				cadena.append(tratamiento).append(" ");
				cadena.append(compareciente.getPersona().getDsnombre().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidopat().toUpperCase()).append(compareciente.getPersona().getDsapellidomat()==null?"":" "+compareciente.getPersona().getDsapellidomat().toUpperCase());
				
				cadena.append(" vende");
			}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){
				if(conyuges.getConyugeCompra().getAmboscompran()){ //es proindiviso
					System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Intuimos que esta casado y ambos compraron por variable de conyugeCompra");
					String tratamiento = compareciente.getTratamiento().getDselemento();
					char capital = tratamiento.charAt(0);
					tratamiento = String.valueOf(capital).toUpperCase().concat(tratamiento.substring(1, tratamiento.length()));
					System.out.println("tratamiento: "+tratamiento);
					cadena.append(tratamiento).append(" ");
					cadena.append(compareciente.getPersona().getDsnombre().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidopat().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat().toUpperCase());
					cadena.append(" y ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
					cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre().toUpperCase()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat().toUpperCase()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat().toUpperCase());
					cadena.append(" venden");
				}else{ //no es proindiviso
					System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Se compro bajo bienes separados");
					String tratamiento = compareciente.getTratamiento().getDselemento();
					char capital = tratamiento.charAt(0);
					tratamiento = String.valueOf(capital).toUpperCase().concat(tratamiento.substring(1, tratamiento.length()));
					System.out.println("tratamiento: "+tratamiento);
					cadena.append(tratamiento).append(" ");
					cadena.append(compareciente.getPersona().getDsnombre().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidopat().toUpperCase()).append(compareciente.getPersona().getDsapellidomat()==null?"":" "+compareciente.getPersona().getDsapellidomat().toUpperCase());
					cadena.append(" vende");
				}
			}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("SOCIEDAD CONYUGAL")){
				if(conyuges.getConyugeCompra().getAmboscompran()){ //es proindiviso
					System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Intuimos que esta casado y ambos compraron por variable de conyugeCompra");
					String tratamiento = compareciente.getTratamiento().getDselemento();
					char capital = tratamiento.charAt(0);
					tratamiento = String.valueOf(capital).toUpperCase().concat(tratamiento.substring(1, tratamiento.length()));
					System.out.println("tratamiento: "+tratamiento);
					cadena.append(tratamiento).append(" ");
					cadena.append(compareciente.getPersona().getDsnombre().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidopat().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat().toUpperCase());
					cadena.append(" y ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
					cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre().toUpperCase()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat().toUpperCase()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat().toUpperCase());
					cadena.append(" venden");
				}else{ //no es proindiviso
					System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Se compro bajo bienes separados");
					String tratamiento = compareciente.getTratamiento().getDselemento();
					char capital = tratamiento.charAt(0);
					tratamiento = String.valueOf(capital).toUpperCase().concat(tratamiento.substring(1, tratamiento.length()));
					System.out.println("tratamiento: "+tratamiento);
					cadena.append(tratamiento).append(" ");
					cadena.append(compareciente.getPersona().getDsnombre().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidopat().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat().toUpperCase());
					cadena.append(" vende con el consentimiento de su conyuge ");
					cadena.append("").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
					cadena.append("").append(conyuges.getConyugeCompra().getPersona().getDsnombre().toUpperCase()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat().toUpperCase()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat().toUpperCase());
				}
			}				
		}else{  //al parecer ha estado y estara solo
			System.out.printf("=======> VariableUtils.comparecienteCompraron ::: Intuimos que es soltero ya que conyuge viene en null");
			String tratamiento = compareciente.getTratamiento().getDselemento();
			char capital = tratamiento.charAt(0);
			tratamiento = String.valueOf(capital).toUpperCase().concat(tratamiento.substring(1, tratamiento.length()));
			System.out.println("tratamiento: "+tratamiento);
			cadena.append(tratamiento).append(" ");
			cadena.append(compareciente.getPersona().getDsnombre().toUpperCase()).append(" ").append(compareciente.getPersona().getDsapellidopat().toUpperCase()).append(compareciente.getPersona().getDsapellidomat()==null?"":" "+compareciente.getPersona().getDsapellidomat().toUpperCase());
			cadena.append(" vende");
		}
		return cadena.toString();
		}catch(NullPointerException e){
			throw new NotariaException("Se localizo un valor nulo en el calculo del texto de compareciente ambos compraron, no se logro calcular la variable. Revise los parametros de compareciente, persona, estado civil y tratamiento.");
		}
	}
	
	/***
	 * cafaray 020714 Acoplamiento de la varible compleja, los artefactos actuales (db, xml) no dan para cubrir estos casos, es por esta razon que se acoplan
	 * comparecientesCompraronEstadoCivil esta acoplado con la variable compareciente_compro_estadocivil. donde se determina el texto del antecedente de compra con base en el estado civil del vendedor
	 * @param idacto String acto donde se buscaran los comparecientes
	 * @return java.lang.String cadena de texto a colocar como valor de la variable
	 * @throws NotariaException 
	 */
	static public String comparecientesCompraronEstadoCivil(String idacto) throws NotariaException{
		StringBuilder cadena = new StringBuilder(); //Cadena donde se devulve el texto de antecedente compra de inmueble
		List<Compareciente> comparecientes = getComparecientes(idacto, "VENDEDOR");
		if(comparecientes!=null && comparecientes.size()>0){
			//localizar la situacion de estado civil de cada compareciente:
			int contComparecientes = comparecientes.size();
			System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil(%s) ::: %d%n", idacto, contComparecientes);
			for(Compareciente compareciente: comparecientes){
				
				ComparecienteConyugeBo conyugeBo = new ComparecienteConyugeBoImpl();				
				ComparecienteConyuge conyuges;
				String tratamiento = compareciente.getTratamiento()!=null?compareciente.getTratamiento().getDselemento():"";
				try {
					conyuges = conyugeBo.findById(compareciente.getIdcompareciente());
					System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: conyugue localizado = %s%n", (conyuges==null?"no hay":conyuges.getSujeto().getPersona().getDsnombre()));
				} catch (NotariaException e) {
					System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Exception %s%n",e.getMessage());
					e.printStackTrace();
					return null;
				}
				if(conyuges!=null){
					//aquí van todos los casos:
					
					if(conyuges.getConyugeCompra()==null){ //se considera que es soltero al momento de la compra
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Intuimos que es soltero ya que conyuge compra viene en null");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando soltero y declara que el inmueble que más adelante se relaciona jamás lo aporto a sociedad conyugal alguna, adquirió por");
					}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getAmboscompran()){
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Intuimos que esta casado y ambos compraron por variable de conyugeCompra");
						cadena.append(tratamiento.toLowerCase()).append(" "); //Adquirieron ambos conyugues 
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat());
						cadena.append(" y ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
						cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());
						cadena.append(" estando casados entre sí bajo el regimen de");
						if(conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){
							cadena.append(" separación de bienes ");
						} else {
							cadena.append(" sociedad conyugal ");
						}						
						cadena.append("(según consta de la copia certificada del acta de matrimonio que se me exhibe y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA), adquirieron por ");
					}else if(conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("BIENES SEPARADOS")){ //es proindiviso						
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Es el mismo caso que si estuviese soltero");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando casado con ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
						cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());						
						cadena.append(" bajo el regimen de separación de bienes (según consta de la copia certificada del acta de matrimonio que se me exhibe y que en copia fotostática agrego al apendice de esta escritura con la letra #LETRA), adquirió por");						
					}else if(conyuges.getConyugeCompra()!=null && conyuges.getConyugeCompra().getRegimen().getDselemento().toUpperCase().equals("SOCIEDAD CONYUGAL")){
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: Se compro bajo sociedad conyugal");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando casado con ").append(conyuges.getConyugeCompra().getTratamiento().getDselemento().toLowerCase()).append(" ");
						//cadena.append("").append(conyuges.getConyugeCompra().getTratamiento().getDselemento()).append(" ");
						cadena.append(conyuges.getConyugeCompra().getPersona().getDsnombre()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidopat()).append(" ").append(conyuges.getConyugeCompra().getPersona().getDsapellidomat()==null?"":conyuges.getConyugeCompra().getPersona().getDsapellidomat());
						cadena.append(" bajo el regimen de sociedad conyugal (según consta de la copia certificada del acta de matrimonio que se me exhibe  y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA) adquirió por");
					}else{
						// NOTHING TO DO, not case for it
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: caso no estimado");
					}
				}else{
					//seguro es soltero:
					if(compareciente.getPersona().getTipopersona().getDscodigo().equals("PF")){
						System.out.printf("=======> VariableUtils.comparecientesCompraronEstadoCivil ::: seguro es soltero, no se encontro registro en conyuge");
						cadena.append("").append(tratamiento.toLowerCase()).append(" ");
						cadena.append(compareciente.getPersona().getDsnombre()).append(" ").append(compareciente.getPersona().getDsapellidopat()).append(" ").append(compareciente.getPersona().getDsapellidomat()==null?"":compareciente.getPersona().getDsapellidomat());
						cadena.append(" estando soltero y declara que el inmueble que más adelante se relaciona jamás lo aporto a sociedad conyugal alguna, adquirió por");
					}
					cadena.append("adquirió por");					
				} 
				
			}
		}else{
			return null;
		}
		return cadena.toString();
	}
	
	/***
	 * cafaray 020714 Acoplamiento de la varible compleja, los artefactos actuales (db, xml) no dan para cubrir estos casos, es por esta razon que se acoplan
	 * comparecientesFechaNacimiento esta acoplado con la variable compareciente_fecha_nacimiento. donde se determina la fecha bajo el formato dado, siendo exclusiones "texto" donde se envia el texto de la fecha y "" donde se envia en formato dd/MM/yyyy; en otros casos se toma el formato que se especifique
	 * @param idacto String acto donde se buscaran los comparecientes
	 * @return java.lang.String cadena de texto a colocar como valor de la variable
	 * @throws NotariaException 
	 */
	static public String comparecienteFechaNacimiento(String idacto, String tipoCompareciente, String formato) throws NotariaException{
		StringBuilder cadena = new StringBuilder(); //Cadena donde se devulve el texto de antecedente compra de inmueble
		List<Compareciente> comparecientes = getComparecientes(idacto, tipoCompareciente.toUpperCase());
		if(comparecientes!=null && comparecientes.size()>0){
			//localizar la situacion de estado civil de cada compareciente:
			int contComparecientes = comparecientes.size();
			System.out.printf("=======> VariableUtils.comparecienteFechaNacimiento(%s,%s,%s) ::: %d%n", idacto,tipoCompareciente,formato, contComparecientes);
			for(Compareciente compareciente: comparecientes){
				SimpleDateFormat dateFormat;
				if(formato.toLowerCase().equals("texto")) {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					cadena.append(NumberToLetterConverter.convertDateToLetter(dateFormat.format(compareciente.getPersona().getFechanacimiento())));
				}else if(formato.trim()==""){
					dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					cadena.append(dateFormat.format(compareciente.getPersona().getFechanacimiento()));
				}else{
					dateFormat = new SimpleDateFormat(formato);
					cadena.append(dateFormat.format(compareciente.getPersona().getFechanacimiento()));
				}
			}
		}
		return cadena.toString();
	}
	
	/***
	 * cafaray 020714: Listado de comparecientes a través del identificador del acto.
	 * @param idacto String identificador del acto al que pertencen los comparecientes
	 * @param tipo String tipo de compareciente, ej. COMPRADOR, VENDEDOR, AUTORIZANTE, etc ...
	 * @return java.util.List<Compareciente> listado de comparecientes de resultado
	 * @throws NotariaException 
	 */	
	public static List<Compareciente> getComparecientes(String idacto, String tipo) throws NotariaException{
		ComparecienteBo bo = new ComparecienteBoImpl();				
		return bo.listadoComparecientesByActo(idacto, tipo);
	}
	
	
	/**
	 * 
	 * @param nodeId
	 * @param nodeName
	 */
	public static List<CamposOracion> getCamposOracion(String nodeId,
			String nodeName) {

		List<CamposOracion> lista = new ArrayList<CamposOracion>();
		String expression = "/queries/query[@id='" + nodeId + "']/" + nodeName;
		try {
			InputStream is = VariableUtils.obtenInputStream();
			if (is == null) {
				return null;
			}
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(is);
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.compile(expression).evaluate(xmlDocument,
					XPathConstants.NODE);

			if (null != node) {
				NodeList nodeList = node.getChildNodes();
				// Indice para registrar los campos obtenidos.No se uso i, por
				// que no siempre es secuencial.
				int index = 0;
				for (int i = 0; null != nodeList && i < nodeList.getLength(); i++) {
					Node nod = nodeList.item(i);
					if (nod.getNodeType() == Node.ELEMENT_NODE) {
						// System.out.println(nodeList.item(i).getNodeName()
						// + " : " + nod.getFirstChild().getNodeValue());
						CamposOracion campos = new CamposOracion();
						// Index no hace match con i. Ojo.
						campos.setIndex(index);
						campos.setCampoName(nodeList.item(i).getNodeName());
						campos.setCampoDefinicion(nod.getFirstChild()
								.getNodeValue());
						lista.add(campos);
						index++;
					}
				}
			}
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	/**
	 * Transforma una lista de numero a texto. Ejemplo 12, 6 y 3 => DOCE, SEIS,
	 * TRES
	 * 
	 * @param dataType
	 * @param value
	 * @param regex
	 * @return
	 */
	public static String transformNumberListToText(String dataType, String value, String regex) {
		if (value == null || regex == null) {
			return null;
		}
		String[] tokens = value.split(regex);
		if (tokens == null || tokens.length==0) {
			return null;
		}		
		List<Object> listaTexto = new ArrayList<Object>();		
		for (String token : tokens) {
			if (token == null) {
				continue;
			}
			String texto = VariableUtils.converSingleValueToLetter(dataType,
					token.trim());
			if(texto!=null){
				listaTexto.add(texto);
			}
		}
		return VariableUtils.daFormatoListas(listaTexto);
	}

	/**
	 * Convierte el numero, moneda o fecha dada en texto. Solo se recibe un
	 * valor.
	 * 
	 * @param dataType
	 * @param value
	 * @return
	 */
	public static String converSingleValueToLetter(String dataType, String value) {
		logger.info(String.format("=====> Entro con valores: converSingleValueToLetter(dataType=%s, value=%s) ",dataType, value));
		if (dataType.equals(TipoDato.ENTERO)) {
			if (value.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
				return NumberToLetterConverter.convertNumberToLetter(value);
			}
		} else if (dataType.equals(TipoDato.MONEDA)) {
			if(value.contains(",")){
				value = value.replace(",", "");
			}
			if (value.matches("^[0-9]+(\\.[0-9]+)?$")) {
				return NumberToLetterConverter.convertMoneyToLetter(value);
			}
		} else if(dataType.equals(TipoDato.PORCENTAJE)){
			if(value.startsWith("."))
				{value = "0" + value; }
			if (value.matches("^[0-9]+(\\.[0-9]+)?$")){
				return NumberToLetterConverter.convertPorcentual(value);
			}
		} else if (dataType.equals(TipoDato.FECHA)) {
			if (value.matches("^\\d{1,4}-\\d{1,2}-\\d{1,2}$")) { // aaaa-mm-dd
				return NumberToLetterConverter.convertDateToLetter(value);
			}else if(value.matches("^\\d{1,2}-\\d{1,2}-\\d{1,4}$")){ // dd-mm-yyyy				
				return NumberToLetterConverter.convertDateDD_MM_YYYToLetter(value);
			}
		} else if(dataType.equals(TipoDato.FECHA_DDMMYYYY)){
			return NumberToLetterConverter.convertDateDDMMYYYToLetter(value);			
		}else if(dataType.equals(TipoDato.LETRAaLETRA)){
			return NumberToLetterConverter.letterToLetter(value);
		}
		/** retorna null si no es un tipo valido o no tiene el formato adecudado **/
		return value;
	}
	
	/**
	 * Dado un valor la valida contra un expresion regular 
	 * y regresa el tipo correspondiente. 
	 * @param value
	 * @return
	 */
	public static String getDataType(String value){
		if (value.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
			return TipoDato.ENTERO;
		}
		if (value.matches("^[0-9]+(\\.[0-9]+)?$")) {
			return TipoDato.MONEDA;
		}
		if (value.matches("^\\d{1,4}-\\d{1,2}-\\d{1,2}$")) { // aaaa-mm-dd
			return TipoDato.FECHA;
		}
		if(value.matches("^\\d{1,2}-\\d{1,2}-\\d{1,4}$")) { // dd-mm-yyyy
			return TipoDato.FECHA;
		}		
		return null;		
	}
	
	public static String convierteCualquierNumeroEnTexto(String value){
		if(value==null || value.trim().length() ==0) return value;
		String[] palabras;
		if(value.contains(" ")){
			palabras = value.split(" ");
		}else{
			palabras = new String[1];
			palabras[0] = value;
		}
		
		StringBuilder nuevo = new StringBuilder("");		
		for(String palabra:palabras){
			if(esNumerico(palabra)){
				boolean esMoneda = false, esDecimal = false, esNumero=false;
				esDecimal = palabra.contains(".");
				esMoneda = palabra.startsWith("$");				
				esNumero = palabra.startsWith("#");
				palabra = palabra.replace("$", "");
				palabra = palabra.replace("#", "");
				
				if(esDecimal) {
					nuevo.append(NumberToLetterConverter.convertNumberToLetter(palabra));
				} else if(esMoneda) {
					nuevo.append(NumberToLetterConverter.convertMoneyToLetter(palabra));
				} else if(esNumero) {
					nuevo.append("número ").append(NumberToLetterConverter.convertNumberToLetter(palabra));
				} else {
					nuevo.append(NumberToLetterConverter.convertNumberToLetter(palabra));
				}
			} else {				
				nuevo.append(palabra);
			}
			nuevo.append(" ");
		}		
		return nuevo.toString().trim();
	}
	
	private static boolean esNumerico(String valor){
		boolean esNumero = false, esDecimal = false;
		if (valor != null && !valor.equals("")){
			if(valor.startsWith("$")||valor.startsWith("#")) valor = valor.substring(1);
			esNumero = false;
			char caracteres[] = valor.toCharArray();
			for(char caracter:caracteres){
				if (Character.isDigit(caracter)){
					esNumero = true;
				} else if(caracter==',' && !esDecimal){
					esNumero = true;
				} else if(caracter == '.' && !esDecimal){
					esDecimal = true;					
				} else {
					esNumero = false;
				}
				if(!esNumero)break;
			}
		}
		return esNumero;
	}
	
}
