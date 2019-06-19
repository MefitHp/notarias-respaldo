package com.palestra.notarias.variables;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ValorSubFormulario;
import com.palestra.notarias.constantes.TipoDato;
import com.palestra.notarias.pojo.FormTokens;
import com.palestra.notarias.pojo.VariablesTokens;
import com.palestra.notarias.utils.NumberToLetterConverter;
import com.palestra.notarias.utils.VariableUtils;

/**
 * Clase que convierte una lista
 * {@link com.palestra.notarias.pojo.VariablesTokens} a un Map<token,valor>
 * 
 * @author sofia
 * 
 */
public class VariableToTokenTransformer {

	static Logger logger = Logger.getLogger(VariableToTokenTransformer.class);

	public static final String TRANSFORMAR_EN_TEXTO = "texto";  // antes funcion=
	public static final String TRANSFORMAR_EN_LETRAS = "letras";
	public static final String TRANSFORMAR_EN_MONEDA = "moneda";
	public static final String TRANSFORMAR_EN_MAYUSCULAS = "mayusculas";
	public static final String TRANSFORMAR_EN_MINUSCULAS = "minusculas";
	public static final String TRANSFORMAR_EN_ORACION = "oracion";
	public static final String TRANSFORMAR_EN_CAPITAL = "capital";	
	public static final String UNIDAD_SUPERFICIE = "unidad_superficie";
	public static final String TEXTO_CON_NUMEROS = "texto_con_numeros";
	public static final String TEXTO_INSERTAR = "insertar";
	public static final String TEXTO_INSERTAR_INICIO = "inicio";
	public static final String TEXTO_INSERTAR_FIN = "fin";
	public static final String CONDICION_SINO = "sino";
	public static final String TRANSFORMA_EN_PORCENTAJE = "porcentaje";
	public static final String TEXTO_CON_SALTOLINEA = "salto_linea";
	public static final String NUMERO_ORDINAL = "numero_ordinal";	
	public static final String REGEX_SPLIT_NUMEROS = "[\\,|y]";
	public static final String FECHA_FORMATO = "fecha_";
	public static final String TOSTOTAS = "tostotas";
	public static final String OPERACION_DECREMENT = "decremento";


	

	/**
	 * Retorna un map con los valores de las variables indicados. Los tokens
	 * permitidos se sustituyen por su valor correspondiente. Principalmente
	 * para grupos
	 * 
	 * @param valores
	 *            , arrayList con los valores provenientes de la base de datos.
	 * @param token
	 *            , variables(tokens) que seran sustituidas por los valores
	 *            correspondientes, este arreglo contiene tanto tokens
	 *            permitidos como no permitidos
	 * 
	 * @return
	 */
	public HashMap<String, String> obtenTokenConValores(
			List<VariablesTokens> valores, String[] tokensToReplace) {
		logger.info("===> Obtener tokens con valores. ");
		HashMap<String, String> mapaVariables = new HashMap<String, String>();
		/**
		 * Los tokens tienen la estructura 'var:nombreVariable' y
		 * 'gpo:nombreGrupo'
		 **/
		for (String token : tokensToReplace) {
			//if (token.contains(EnumVariables.VAR_IDENTIFIER.toString())) {
				for (VariablesTokens var : valores) {
					// Hacer match entre tokens originales plantilla y sus
					// valores procesados
					if (VariableUtils.removeVarIdentificador(token).equals(
							var.getOriginalName())) {
						mapaVariables.put(token, this.updateValue(var));
						break;
					}
				}
				//TODO cambiar esto al activar las variables de GRUPO
//			} else if (token.contains(EnumVariables.GPO_IDENTIFIER.toString())) {
//				List<VariablesTokens> variablesGrupo = new ArrayList<VariablesTokens>();
//				/** Obtener las variables que conforman el grupo **/
//				for (VariablesTokens var : valores) {
//					if (var.getGroup() != null
//							&& token.contains(var.getGroup())) {
//						variablesGrupo.add(var);
//					}
//				}
//				mapaVariables.put(token, this.generaValorGrupo(variablesGrupo));
//			} else {
//				/** No se encontro valor **/
//				mapaVariables.put(token, null);
//			}
		}
//		for (Map.Entry<String, String> entry : mapaVariables.entrySet()) {
//			logger.info("key= " + entry.getKey() + ", val= " + entry.getValue());
//		}
		return mapaVariables;
	}

	/**
	 * Asigna los valores procesados a los tokes de variables que seran
	 * sustituidos en la BD.
	 * 
	 * @param variable
	 * @return
	 */
	private String updateValue(VariablesTokens variable) {
		
		try{
			if(variable==null || variable.getValue()==null){
				return null;
			} else if(variable.getValue().trim().isEmpty()){
				variable.setValue(" ");
			}else{
				/** Si se indica, una funcion **/
				try{
					variable = this.evaluaFuncionVariable(variable);
				}catch(NotariaException e){
					logger.warn(String.format("=====> evaluaFuncionVariable(%s) retorno una excepcion.%n\t%s",variable,e.getMessage()),e);
				}
			}
			//variable.setValue(VariableUtils.convierteCualquierNumeroEnTexto(variable.getValue()));
			variable.setValue(variable.getValue());
			return variable.getValue();
		}catch(NullPointerException e){
			logger.warn(String.format("=====> La variable del parametro en updateValue es nula."),e);
			return null;
		}
	}


	/**
	 * Genera el valor de un grupo generando una cadena con los valores de las
	 * variables ordenadas
	 * 
	 * @param variablesGrupo
	 *            Variables de grupo
	 * @return Valor del grupo
	 */
	public String generaValorGrupo(List<VariablesTokens> variablesGrupo) {
		if (variablesGrupo == null || variablesGrupo.size() < 0) {
			return null;
		}
		/** ordenar las variables del grupo acorde a su orden **/
		Collections.sort(variablesGrupo, new Comparator<VariablesTokens>() {
			@Override
			public int compare(VariablesTokens var1, VariablesTokens var2) {
				if (var1.getOrder() == null && var2.getOrder() != null) {
					return -1;
				}
				if (var1.getOrder() != null && var2.getOrder() == null) {
					return 1;
				}
				if (var1.getOrder() == null && var2.getOrder() == null) {
					return 0;
				}
				return var1.getOrder().compareTo(var2.getOrder());
			}
		});
		StringBuilder valorGrupo = new StringBuilder();
		for (VariablesTokens var : variablesGrupo) {
			valorGrupo.append(" ");
			valorGrupo.append(this.updateValue(var));
		}
		return valorGrupo.toString();
	}
	
	public String getDefinicionFuncion(String variable)throws NotariaException{
		String funcion = "";
		if(variable.contains("(funcion=")){
			funcion = variable.substring(variable.indexOf("funcion="), variable.length()-1);
		}else if(variable.contains("(insertar=")){
			funcion = variable.substring(variable.indexOf("insertar="), variable.length()-1);
		}else if(variable.contains("(contenido=")){
			funcion = variable.substring(variable.indexOf("contenido="), variable.length()-1);
		}
			return funcion;
		
	}

	
	@SuppressWarnings("deprecation")
	public String evaluaFuncionVariable(String definicionFuncion, String valor)throws NotariaException{
		
		if (valor != null && definicionFuncion!=null){
			
			if(definicionFuncion.startsWith("contenido")) return valor;
			String[] funciones;
			if(definicionFuncion.contains(",")){
				funciones =definicionFuncion.split("\\,");
			}else{
				funciones = new String[1];
				funciones[0] = definicionFuncion;
			}
			//Quito los valores especiales que se capturan como tabulación
			if(valor.endsWith("\t")){
				valor = valor.replace("\t", "");
			}
			for(String funcion:funciones){
				logger.info("=====> EJECUTANDO LA FUNCION: "+funcion);
				if(funcion.endsWith(TRANSFORMAR_EN_TEXTO)) {
					if(valor.contains(",")){
						valor = valor.replace(",", "");
					}
					String dataType = VariableUtils.getDataType(valor);
					if (dataType != null) {
						String texto = VariableUtils.converSingleValueToLetter(dataType, valor);
						valor = texto.trim();
					}
				} else if(funcion.endsWith(TRANSFORMAR_EN_LETRAS)) {
					String dataType = TipoDato.LETRAaLETRA;
					if (dataType != null && !valor.contains(" ")) {
						String texto = VariableUtils.converSingleValueToLetter(dataType, valor);
						valor = texto.trim();
					}
				} else if(funcion.endsWith(TRANSFORMAR_EN_MONEDA)){
					String dataType = TipoDato.MONEDA;
					if (dataType != null) {
						String texto = VariableUtils.converSingleValueToLetter(dataType, valor);
						valor = texto.trim();
					}
				} else if(funcion.endsWith(TRANSFORMAR_EN_MAYUSCULAS)){
					valor = valor.toUpperCase().trim();
				} else if(funcion.endsWith(TRANSFORMAR_EN_MINUSCULAS)){
					valor = valor.toLowerCase().trim();
				}else if(funcion.endsWith(TOSTOTAS)){
					if(valor.endsWith("TOS")){
						valor = valor.substring(0,valor.length()-3);
						valor +="TAS";
					}
				}else if(funcion.endsWith(OPERACION_DECREMENT)){
					int tmp = Integer.parseInt(valor)-1;
					valor = tmp+"";
					
				}
				else if(funcion.endsWith(TRANSFORMAR_EN_ORACION)){
					String tmp = valor;
					tmp = tmp.toLowerCase().trim();
					valor = Character.toUpperCase(tmp.charAt(0))+tmp.substring(1);
				} else if(funcion.endsWith(TRANSFORMAR_EN_CAPITAL)){
					String tmp = valor;
					tmp = tmp.toLowerCase().trim();
					if(tmp.contains(" ")){
						String[] palabras = tmp.split(" ");
						StringBuilder oracion = new StringBuilder();
						for(String palabra:palabras){
							oracion.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
						}
						valor = oracion.toString();
					}else {
						valor = Character.toUpperCase(tmp.charAt(0))+tmp.substring(1).trim();
					}
				} else if(funcion.endsWith(UNIDAD_SUPERFICIE)){
					valor = NumberToLetterConverter.getUnitSurface(valor);
				} else if(funcion.endsWith(TEXTO_CON_NUMEROS)) {					
					String texto = VariableUtils.convierteCualquierNumeroEnTexto(valor);
					valor = texto.trim();
				} else if(funcion.startsWith(TEXTO_INSERTAR)){
					if(valor != null && valor.length()>0){ 
						if(funcion.contains("insertar=")) funcion = funcion.substring(funcion.indexOf("=")+1);
						if(funcion.contains(";")){ // hay parametros, se permiten hasta dos: inicio, texto | fin, texto
							if(funcion.startsWith(TEXTO_INSERTAR_INICIO)){
								valor = funcion.substring(funcion.indexOf(";")+1) + valor;
							} else if (funcion.startsWith(TEXTO_INSERTAR_FIN)){
								valor = valor + funcion.substring(funcion.indexOf(";")+1);
							} else {
								// se retorna el valor como si fuese al final, que es la operacion por defecto
								valor = valor + funcion.substring(funcion.indexOf(";")+1);
							}
						} else { // solo hay texto 
							valor = valor + funcion;
						}					
					}
				} else if(funcion.startsWith(CONDICION_SINO)) {
					if(funcion.contains("=")){
						funcion = funcion.substring(funcion.indexOf("=")+1);
					}
					if(valor.toLowerCase().equals("true")||valor.equals("1")){
						if(funcion.contains(";")){
							valor = funcion.substring(0,funcion.indexOf(";"));	
						} else {
							valor = funcion;
						}						
					}else{
						if(funcion.contains(";")){
							valor = funcion.substring(funcion.indexOf(";")+1);	
						} else {
							valor = " ";
						}						
					}
					
				} else if(funcion.endsWith(TRANSFORMA_EN_PORCENTAJE)){
					String dataType = TipoDato.PORCENTAJE;
					if (dataType != null) {
						String texto = VariableUtils.converSingleValueToLetter(dataType, valor);
						valor = texto.trim();
					}		
				}else if(funcion.endsWith(TEXTO_CON_SALTOLINEA)){
					//if(valor.contains("\\n\\r")){
					if(valor.contains("-s-")){
						valor = valor.replace("-s-", "</p><p>");
						//valor = valor.replace("\\n\\r", "<br />");
					}
					return valor;
				} else if(funcion.endsWith(NUMERO_ORDINAL)){
					try{
						Integer entero = Integer.parseInt(valor);
						if(entero>99){
							System.out.println("=====> El Número ordinal es mayor a 99 que es el permitido ");
							return valor;
						}
						//VICTOR: POR AHORA SOLO TRANSFORMA HASTA EL NÚMERO 10
							return NumberToLetterConverter.getNumeroOrdinario(entero);
						
					}catch(NumberFormatException e){
						System.out.println("=====> evaluaFuncionVariable: el valor para transformar a número ordinal no es un entero ["+valor+"].");
						return valor;
					}
				} else if(funcion.contains(FECHA_FORMATO)){
					//primero verificamos que el valor sea una fecha valida en formato dd/mm/yyyy
					try{						
						Date fecha = esFecha(valor);
						String formato = funcion.substring(funcion.indexOf("fecha_")+"fecha_".length()).toLowerCase();
						formato = formato.replace("m", "M");
						SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
						return dateFormat.format(fecha);
					}catch(IllegalArgumentException e){
						//throw new NotariaException("Al parecer el formato no es para una fecha como se esperaba.");
						System.out.println("=====> evaluaFuncionVariable: Al parecer el formato no es para una fecha como se esperaba. ["+valor+"].");
						return valor;
					}catch(NotariaException e){
						//throw new NotariaException("Al parecer el valor no es una fecha como se esperaba en formato dd/mm/yyyy");
						System.out.println("=====> evaluaFuncionVariable: Al parecer el valor no es una fecha como se esperaba en formato dd/mm/yyyy: ["+valor+"].");
						return valor;
					}
					
				}else{
					System.out.println("=====> evaluaFuncionVariable: No se ha localizado el nombre de funcion ["+funcion+"].");
				}
			}
		}
		return valor;
	}
	
	private Date esFecha(String fecha)throws NotariaException{
		try{
			fecha = fecha.replace("/", "-");
			String dia="", mes="", any ="";
			String[] sFecha = fecha.split("-");
			if(sFecha.length==3){
				if (fecha.matches("^(\\d{4}-?\\d{2}-?\\d{2})$")) {
					dia = sFecha[2];
					mes = sFecha[1];
					any = sFecha[0];
				} else if(fecha.matches("^(\\d{2}-?\\d{2}-?\\d{4})$")) {
					dia = sFecha[0];
					mes = sFecha[1];
					any = sFecha[2];					
				}
				
				int idia, imes, iany;
				idia = Integer.parseInt(dia);
				imes = Integer.parseInt(mes);
				iany = Integer.parseInt(any);
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DATE, idia);
				calendar.set(Calendar.MONTH, imes-1);
				calendar.set(Calendar.YEAR, iany);
				
				return calendar.getTime();
			} else {
				throw new NotariaException("No se localizo una fecha valida, se esperaban 3 parametros y se tienen "+sFecha.length);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			throw new NotariaException("No se localizo una fecha valida. El valor de un parametro esta fuera de rango." + e.getMessage());		
		}catch(IndexOutOfBoundsException e){
			throw new NotariaException("No se localizo una fecha valida." + e.getMessage());
		}catch(NumberFormatException e){
			throw new NotariaException("No se localizo una fecha valida. Se esperaban numeros, se localizo " + e.getMessage());
		} 
	}
	
	public FormTokens evaluaFuncionVariable(FormTokens variable)throws NotariaException{
		String valor = evaluaFuncionVariable(variable.getDeficionFuncion(), variable.getValue());		
		variable.setValue(valor!=null?valor.trim():null);
		return variable;
	}

	public VariablesTokens evaluaFuncionVariable(VariablesTokens variable)throws NotariaException{
		String valor = evaluaFuncionVariable(variable.getDeficionFuncion(), variable.getValue());		
		variable.setValue(valor!=null?valor.trim():valor);
		return variable;
	}

	
	/**
	 * Retorna un mapa<tokenToReplace, value>
	 * 
	 * @param valores
	 * @param tokensToReplace
	 * @return
	 */
	public Map<String, String> obtenFrmTokenConValores(
			List<FormTokens> listaFrmTokens) throws NotariaException {
		Map<String, String> mapaVariables = new TreeMap<String, String>();
		for (FormTokens dft : listaFrmTokens) {
			System.out.printf("=====>>>>> Variable : %s%n", dft.getOriginalName());
			//dft = evaluaFuncionVariable(dft);
			if(dft.getValue()!=null){
				mapaVariables.put(dft.getOriginalName(), dft.getValue());
			}
		}
		return mapaVariables;
	}

}
