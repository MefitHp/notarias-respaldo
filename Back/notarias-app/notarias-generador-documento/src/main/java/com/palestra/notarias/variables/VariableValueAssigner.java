package com.palestra.notarias.variables;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComparecienteRepresentanteBo;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteRepresentanteBoImpl;
import com.palestra.notaria.bo.impl.DocumentoExpedienteBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.UbicacionVarEstaticaBoImpl;
import com.palestra.notaria.bo.impl.ValorFormularioBoImpl;
import com.palestra.notaria.bo.impl.ValorSubFormularioBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.DocumentoExpediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.UbicacionVarEstatica;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;
import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.escritura.FillTemplateWithData;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.pojo.FormTokens;
import com.palestra.notarias.pojo.RegisterTableValues;
import com.palestra.notarias.pojo.TableTokens;
import com.palestra.notarias.pojo.VariablesTokens;
import com.palestra.notarias.utils.EscrituraUtills;
import com.palestra.notarias.utils.VariableUtils;

/**
 * Asigna valores a variables dependiendo del tipo de variable: dinamica,
 * estatica o compleja.
 * 
 * @author sofia
 * 
 */
public class VariableValueAssigner {

	static Logger logger = Logger.getLogger(VariableValueAssigner.class);
	static final String SELECTED_TRUE = "true";
	static final String SELECTED_FALSE = "false";
	final static String PREFIJO_VARIABLE_REFERENCIA = "&";
	final static String SUFIJO_VARIABLE_REFERENCIA = ";"; // EJEMPLO DE
															// REFERECNIA:
															// &notaria_numero;
															// o de formulario
															// &form.notaria_numero;
	final static String IDENTIFICADOR_VALOR_SELECCCION = "::"; // se usa para la
																// seleccion de
																// un valor y su
																// contenido
	final static String IDENTIFICADOR_SALTO_LINEA = "\n\r";

	/** Lamadas BO **/
	private DocumentoExpedienteBoImpl documentoExpedienteBo;
	private UbicacionVarEstaticaBoImpl ubicacionVarEstaticasBo;
	private EscrituraActoBoImpl escrituraActoBo;
	private ValorSubFormularioBoImpl valorSubFormualioBo;
	private ValorFormularioBoImpl valorFormularioBo;

	// private ComponenteBoImpl componenteBo;

	public VariableValueAssigner() {
		documentoExpedienteBo = new DocumentoExpedienteBoImpl();
		ubicacionVarEstaticasBo = new UbicacionVarEstaticaBoImpl();
		escrituraActoBo = new EscrituraActoBoImpl();
		valorSubFormualioBo = new ValorSubFormularioBoImpl();
		valorFormularioBo = new ValorFormularioBoImpl();
	}

	/**
	 * Asigna a VariablesTokes los valores correspindientes en la BD. Un valor
	 * null equivale a que no existe el dato en la bd.
	 * 
	 * @param acto
	 * @param listaVariables
	 * @throws NotariaException
	 */
	public void obtenValoresVariables(FiltroVariables filtro,
			List<VariablesTokens> listaVariables) throws NotariaException {
		/** Variables restantes, buscar sus valores estaticos **/
		this.obtenValoresEstaticosVariables(filtro, listaVariables);
		/** Variables restantes, buscar sus valores complejos **/
		this.obtenValoresComplejosVariables(listaVariables, filtro);
	}

	/**
	 * Se obtiene los valores de los subformularios(tablas) para hacer replace.
	 * 
	 * @param formulario
	 * @param listaTablas
	 * @throws NotariaException
	 */
	public void obtenValoresSubFormulario(List<Formulario> listaForms,
			List<TableTokens> listaTablas) throws NotariaException {
		for (TableTokens tabla : listaTablas) {
			if (tabla.getIdConSubFormulario() != null) {
				for (Formulario form : listaForms) {
					System.out.println("FORMNOMBRE::"
							+ form.getConFormulario().getDsnombrecorto());
					System.out.println("FORM::"
							+ form.getConFormulario().getId()
									.getIdconFormulario());
					System.out.println("TABLA::" + tabla.getTipoFiltro());
					if (form.getConFormulario().getId().getIdconFormulario()
							.equals(tabla.getTipoFiltro())) {
						/*
						 * Obtener datos de cada renglon(registro) de la tabla
						 * por cada form. Dentro tambien busco las funciones
						 */
						obtenRowValues(form, tabla);
						// actualiza valores de referencia
						Map<String, String> referencias = FillTemplateWithData
								.obtieneReferencias();
						boolean formato = false;
						for (String referencia : referencias.keySet()) {
							if (referencias.get(referencia) != null
									&& referencias.get(referencia).contains(
											tabla.originalName)) {
								String elTexto = "";
								formato = true;
								if (tabla.originalName
										.contains("salto_linea_valores")) {
									elTexto = VariableUtils
											.daFormatoFilaSaltoString(tabla
													.getRegisterValues());
								} else {
									elTexto = VariableUtils
											.daFormatoFilaTablaString(tabla
													.getRegisterValues());
								}
								if (elTexto != null && !elTexto.isEmpty()) {
									referencias.put(referencia, elTexto);
								} else {
									referencias.put(referencia, null);
								}
								break;
							}
						}
						if (!formato) {
							if (tabla.originalName
									.contains("salto_linea_valores")) {
								VariableUtils.daFormatoFilaSalto(tabla
										.getRegisterValues());
							} else {
								VariableUtils.daFormatoFilaTabla(tabla
										.getRegisterValues());
							}
						}
						break;
					}
				}
			} else {
				logger.warn("=====> Imposible calcular valores de subformulario cuando el idConFormulario es nulo. ["
						+ tabla.getFiltroFormulario() + "]");
			}
		}
	}

	/*
	 * 
	 * Obtengo valores de subformularios para documentos :TODO revisar la
	 * funcion anterior y unificarlo para que funcione con formularios dinámicos
	 * y documentos
	 */

	private String obtieneSubformReferencia(String ref) {
		int indexFuncion = ref.indexOf("(funcion");
		if (indexFuncion > -1) {
			ref = ref.substring(0, indexFuncion);
		}
		return ref;
	}

	public void obtenValoresSubFormularioDocumentos(
			List<Formulario> listaForms, List<TableTokens> listaTablas,
			Map<String, String> tokenReferencias) throws NotariaException {
		for (TableTokens tabla : listaTablas) {
			if (tabla.getIdConSubFormulario() != null) {
				for (Formulario form : listaForms) {
					if (form.getConFormulario().getId().getIdconFormulario()
							.equals(tabla.getTipoFiltro())) {
						/*
						 * Obtener datos de cada renglon(registro) de la tabla
						 * por cada form.
						 */
						obtenRowValues(form, tabla);
						// actualiza valores de referencia
						for (String referencia : tokenReferencias.keySet()) {
							// referencia =
							// obtieneSubformReferencia(referencia);
							// EL TOKEN REFERENCIA ES EL NOMBRE DEL COMPONENTE
							// SIN FUNCION
							String tokenReferencia = tokenReferencias
									.get(referencia);
							if (tokenReferencia != null) {
								tokenReferencia = obtieneSubformReferencia(tokenReferencia);
							}
							if (tokenReferencias.get(referencia) != null
									&& tabla.originalName
											.contains(tokenReferencia)
									&& tabla.getRegisterValues() != null) {
								String elTexto = VariableUtils
										.daFormatoFilaTablaString(tabla
												.getRegisterValues());
								if (!elTexto.isEmpty() && elTexto != null) {
									tokenReferencias.put(referencia, elTexto);
								} else {
									tokenReferencias.put(referencia, null);
								}
								break;
							}
						}
						break;
					}
				}
			} else {
				logger.warn("=====> Imposible calcular valores de subformulario cuando el idConFormulario es nulo. ["
						+ tabla.getFiltroFormulario() + "]");
			}
		}
	}

	/**
	 * Obtiene valor de cada registro de la tabla dada.
	 * 
	 * @param tabla
	 * @throws NotariaException
	 */
	private void obtenRowValues(Formulario formulario,
			TableTokens tablaDefinition) throws NotariaException {
		Integer numRegistros = valorSubFormualioBo.obtenerNumeroRegistrosTabla(
				tablaDefinition.getIdConSubFormulario(),
				formulario.getIdformulario());
		if (numRegistros != null) {
			tablaDefinition.setNumRegistros(numRegistros);
			List<ValorSubFormulario> valores = valorSubFormualioBo
					.findByIdForm(formulario.getIdformulario());
			if (valores != null) {
				// SE EVALUAN LAS FUNCIONES Y EL VALOR DEL SUBFORMULARIO
				assignValuesToComponentForRow(valores, tablaDefinition);
			} else {
				logger.info("No existen valores de subformulario asociados");
				return;
			}
		} else {
			logger.warn(String
					.format("======> No se encontraron registros asociados al subformulario: %s, subformulario nombre: %s",
							formulario.getIdformulario(),
							tablaDefinition.getNombreCortoSubForm()));
			return;
		}
	}

	/***
	 * Asigna valores de la tabla(subformulario) a cada componente de cada
	 * regitro(fila) de la tabla haciendo match entre los compnentes y sus
	 * valores.
	 * 
	 * @param Formulario
	 * @param tablaDefinition
	 * @param index
	 *            numero de registro de la tabla // NOTA: si idComponente==null
	 *            es por que no se encontro en BD componente.
	 */
	private void assignValuesToComponentForRow(
			List<ValorSubFormulario> valores, TableTokens tabla)
			throws NotariaException {
		// Registro representada por index, almacenan lista de componentes.
		RegisterTableValues rowValue = null;
		List<RegisterTableValues> rowValues = new ArrayList<>();
		StringBuilder valorRenglon = new StringBuilder();
		// NOTA:, se supone que el indice inicial de registros es 0.
		for (int registro = 0; registro <= tabla.getNumRegistros(); registro++) {
			rowValue = new RegisterTableValues();
			rowValue.setIndexRegister(registro);

			for (String variable : tabla.getVariables()) {

				if (variable.contains("--") && variable.startsWith("texto=")) {
					if (registro < 1) {
						variable = variable
								.substring(0, variable.indexOf("--"));
					} else {
						variable = "texto="
								+ variable.substring(
										variable.indexOf("--") + 2,
										variable.length());
					}
				}

				if (variable.contains("contenido=")) { // se trata de una
														// variable de contenido
					String soloValor = interpretaContenido(variable, valores,
							registro);
					// se analiza la variable de contenido
					// localiza el valor de la variable:

					if (soloValor.contains("\\")) { // se trata de una variable
													// de referencia del
													// subformulario
						buscaReferenciaInsideSubForm(soloValor, valorRenglon,
								valores, registro);

					} else {
						valorRenglon.append(soloValor);
					}
				} else if (variable.startsWith("texto=")) {
					valorRenglon.append(variable.substring(variable
							.indexOf("=") + 1));
					System.out.println("Valor de texto obtenido: "
							+ valorRenglon.toString());
				} else if (variable.startsWith("&amp;")
						|| (variable.startsWith("&") && variable.endsWith(";"))) { // se
																					// trata
																					// de
																					// una
																					// variable
																					// de
																					// refencia
																					// del
																					// form
					// ----

				} else if (variable.startsWith("#")) {
					String soloNombre = variable.substring(2);
					if (soloNombre.equals("REGISTRO_NUMERO")) {
						valorRenglon.append(registro);
					} else if (soloNombre.equals("REGISTRO_CUENTA")) {
						valorRenglon.append(tabla.getNumRegistros());
					}
				} else { // se trata de una variable de subformulario
					valorRenglon.append(buscaValorSubFormulario(variable,
							registro, valores, valorRenglon));
				}
			}

			String strValorRenglon = EscrituraUtills.replaceMakerRomano(
					valorRenglon.toString(), "#A");
			// if(strValorRenglon.endsWith("<br />"))
			// strValorRenglon=strValorRenglon.substring(0,strValorRenglon.lastIndexOf("<br />"));
			// if(strValorRenglon.endsWith("</p>"))
			// strValorRenglon=strValorRenglon.substring(0,strValorRenglon.lastIndexOf("</p>"));
			// if(strValorRenglon.endsWith("<p>"))
			// strValorRenglon=strValorRenglon.substring(0,strValorRenglon.lastIndexOf("<p>"));
			strValorRenglon = strValorRenglon.replaceAll("<p>&amp;nbsp;</p>",
					"");

			rowValue.setValueRegisterText(strValorRenglon);
			rowValue.setIndexRegister(registro);
			rowValues.add(rowValue);
			// tabla.setValue(valorRenglon.toString());
			valorRenglon = new StringBuilder();
		}

		tabla.setRegisterValues(rowValues);
	}

	// PRUEBA
	private String interpretaContenido(String variable,
			List<ValorSubFormulario> valores, int registro) {
		String soloValor = "";
		// se analiza la variable de contenido
		// localiza el valor de la variable:
		String soloNombre = variable.substring(0, variable.indexOf("("));
		for (ValorSubFormulario valor : valores) {
			if (valor.getComponente().getDsnombrevariable().equals(soloNombre)
					&& valor.getRegistro() == registro) {
				soloValor = valor.getValorcadena();
				if (soloValor.contains(":")) { // proviene de un select
					String key = soloValor.substring(0, valor.getValorcadena()
							.indexOf("::"));

					int inicio = variable.indexOf(key + "::");

					if (inicio > 0) {
						inicio += key.length() + 2;
						variable = variable.substring(inicio);
						int fin = 0;
						int banderafin = -1;
						// Si no lo encuentra quiere decir que es el último de
						// las opciones y por lo tanto no lo encuentra
						do {
							int buscaCierre = StringUtils.countMatches(
									variable, ")");
							banderafin = variable.indexOf("|", banderafin + 1);
							if (banderafin < 0)
								break;
							fin = banderafin;
							if (buscaCierre < 2)
								break;
						} while (-1 > 0);
						// VICTOR
						// EN LA BUSQUEDA RECURSIVA DE SUBFORMULARIOS NO
						// FUNCIONA POR ESO SE COMENTO.
						// REVISAR CHECAR UNA COMPRAVENTA Y UN PODER O ALGO QUE
						// TENGA UN SUBFORMULARIO CON SUBFORMULARIOS RECURSIVOS
						// O
						// PONER UNA BANDERA
						// }while(banderafin>0);

						if (fin <= 0) {
							logger.warn("EL LARGO ES:" + variable.length());
							fin = variable.lastIndexOf(')');
						}

						soloValor = variable.substring(0, fin);
					} else {
						logger.warn(String
								.format("=====> no se localizo la llave %s en el valor %s",
										key, soloValor));
					}
					break;
				} else if (soloValor.equals(SELECTED_TRUE)
						|| soloValor.toLowerCase().equals(SELECTED_FALSE)) { // proviene
																				// de
																				// un
																				// check
																				// (sino)
					String contenido = variable
							.substring(variable.indexOf("=") + 1);
					if (soloValor.equals(SELECTED_TRUE)) {
						if (contenido.contains("|")) {
							soloValor = contenido.substring(0,
									contenido.indexOf("|"));
						} else {
							soloValor = contenido;
						}
					} else {
						if (contenido.contains("|")) {
							soloValor = contenido.substring(contenido
									.indexOf("|") + 1);
						} else {
							soloValor = " ";
						}
					}
					break;
				}
			} else {
				continue;
			}
		}
		return soloValor;
	}

	// PRUEBA REFERENCIAS INTERNAS
	private void buscaReferenciaInsideSubForm(String soloValor,
			StringBuilder valorRenglon, List<ValorSubFormulario> valores,
			Integer registro) throws NotariaException {
		int index = 0;
		boolean inicioReferenciaSubForm = false;
		StringBuilder referencia = new StringBuilder();
		while (index < soloValor.length()) {
			System.out.println("VALOR REFERENCIA::" + soloValor.charAt(index));
			if (soloValor.charAt(index) == '\\' && inicioReferenciaSubForm) { // es
																				// el
																				// cierre
				// referencias.add(referencia.toString());
				valorRenglon
						.append(buscaValorSubFormulario(referencia.toString(),
								registro, valores, valorRenglon));
				inicioReferenciaSubForm = false;
			} else if (soloValor.charAt(index) == '\\'
					&& !inicioReferenciaSubForm) { // es la apertura
				referencia = new StringBuilder();
				inicioReferenciaSubForm = true;
			} else if (inicioReferenciaSubForm) {
				referencia.append(soloValor.charAt(index));
			} else if (soloValor.charAt(index) == '&') {
				String tmp = soloValor.substring(index, index + 5);
				if (tmp.equals("&amp;")) {
					int idxLast = soloValor.indexOf(";", index + 6);
					String referenciaBusqueda = soloValor.substring(soloValor
							.indexOf("&amp;") + 5, idxLast > 0 ? idxLast + 1
							: 1);
					referenciaBusqueda = "**" + referenciaBusqueda;
					String variableReferencia = FillTemplateWithData
							.obtieneVariable(referenciaBusqueda);
					if (variableReferencia != null) {
						valorRenglon.append(variableReferencia);
					}
					index = idxLast > 0 ? idxLast : index;
				} else {
					valorRenglon.append(soloValor.charAt(index));
				}
			} else {
				valorRenglon.append(soloValor.charAt(index));
			}
			index++;
		}
		System.out.println("Valor obtenido: " + valorRenglon.toString());
	}

	private String buscaValorSubFormulario(String variable, Integer registro,
			List<ValorSubFormulario> valores, StringBuilder valorRenglon)
			throws NotariaException {
		String funcion = "";
		String valorCadena = "";
		if (variable.contains("(funcion=")) {
			funcion = variable.substring(variable.indexOf("funcion="),
					variable.length() - 1);
			variable = variable.substring(0, variable.indexOf("(funcion="));

		}
		if (variable.contains("(contenido=")) {
			valorCadena = interpretaContenido(variable, valores, registro);
			if (valorCadena.contains("\\")) {
				buscaReferenciaInsideSubForm(valorCadena, valorRenglon,
						valores, registro);
			}
			return valorCadena;

		}
		String componenteActual = "";
		int pos = 0;

		for (ValorSubFormulario valor : valores) {

			if (!componenteActual.equals(valor.getComponente()
					.getIdcomponente())) {
				componenteActual = valor.getComponente().getIdcomponente();
				pos = 0;
			}

			if (valor.getRegistro() == registro
					&& valor.getComponente().getDsnombrevariable()
							.equals(variable)) {
				valorCadena = valor.getValorcadena();
				if (valorCadena.contains("::")) {
					valorCadena = valorCadena.substring(valorCadena
							.indexOf("::") + 2);
				}
				if (!funcion.isEmpty()) {
					VariableToTokenTransformer tokenTransformer = new VariableToTokenTransformer();
					valorCadena = tokenTransformer.evaluaFuncionVariable(
							funcion, valorCadena);
				}
				return valorCadena != null ? valorCadena.trim() : "";
			}
			pos++;
		}
		return valorCadena;
	}

	/**
	 * Asigna valores a variables tokens de formularios con datos de la tabla
	 * 'ValorFormulario'
	 * 
	 * @param formulario
	 * @param listaFrmTokens
	 * @throws NotariaException
	 */
	public void assignValuesToFrmToken(List<Formulario> listaForms,
			List<FormTokens> listaFrmTokens) throws NotariaException {
		// esta lista se puede obtener directamente del formulario
		VariableToTokenTransformer tokenTransformer = new VariableToTokenTransformer();
		for (Formulario formulario : listaForms) {
			for (FormTokens dft : listaFrmTokens) {

				System.out.println("*************************");
				System.out.println("******ELEMENTO********" + dft.getName()
						+ "******************");
				System.out.println("*************************");
				formulario = evaluaFormularioEquals(formulario,
						dft.getNombreCortoConForm(), listaForms);
				try {
					if (formulario.getConFormulario().getDsnombrecorto()
							.equals(dft.getNombreCortoConForm())) {
						ValorFormulario valor = valorFormularioBo
								.valorPorNombreVariable(formulario,
										dft.getName());

						if (valor == null) {
							logger.info(String.format(
									"=====> Se encontraron valores nulos que no permiten la obtención del valor de la variable "
											+ "token=%s %n",
									dft.getName() == null ? "NULL" : dft
											.getName()));
						} else {
							logger.info("=====> Se localizo la variable "
									+ valor.getComponente()
											.getDsnombrevariable());
							// --> 110714 cafaray Si el valor de la cadena no es
							// nulo y esta vacio, se genera un error en la
							// tranformación, por lo que validamos que tenga
							// datos
							if (valor.getValorcadena() != null
									&& !valor.getValorcadena().trim().isEmpty()) {
								// <-- 110714 cafaray --> 110714 cafaray
								logger.info(String.format("\t\t%s",
										valor.getValorcadena()));
								if (dft.getContenido() != null) { // TODO
																	// aplicar
																	// aquí la
																	// opción de
																	// manejo de
																	// seleccion
									/**
									 * Si tiene contenido se trata de un
									 * componente de selección de texto Si/No,
									 * separar los valores por '|'.
									 **/
									String contenido = calculaValorContenido(
											valor.getValorcadena(),
											dft.getContenido());
									// TODO: cafaray 040814 --> validar la
									// existencia de referencias a variable(s)
									// en el contenido:
									if (existeVariableReferencia(contenido)) {
										List<String> referencias = obtieneVariableReferencia(contenido);
										for (String referencia : referencias) {
											String referenciaBusqueda = referencia
													.substring(referencia
															.indexOf("&amp;") + 5);
											String variable = FillTemplateWithData
													.obtieneVariable("**"
															+ referenciaBusqueda);

											for (FormTokens token : listaFrmTokens) {

												if (token.getOriginalName()
														.equals(variable)) {

													// @VICOY aqui se evalua la
													// condición y se buscan las
													// referencias

													System.out
															.println("La referencia que calculo es..."
																	+ referenciaBusqueda);
													if (token.getValue() == null) { // intento
																					// recuperar
																					// su
																					// valor
																					// nuevamente
														String nuevoCalculo;
														try {
															formulario = evaluaFormularioEquals(
																	formulario,
																	token.getNombreCortoConForm(),
																	listaForms);
															/*
															 * if(!formulario.
															 * getConFormulario
															 * ()
															 * .getDsnombrecorto
															 * ().equals(token.
															 * getNombreCortoConForm
															 * ())){
															 * for(Formulario
															 * frm:listaForms){
															 * if
															 * (frm.getConFormulario
															 * (
															 * ).getDsnombrecorto
															 * ().equals(token.
															 * getNombreCortoConForm
															 * ())){ formulario
															 * = frm; break; } }
															 * }
															 */
															nuevoCalculo = valorFormularioBo
																	.valorPorNombreVariable(
																			formulario,
																			token.getName())
																	.getValorcadena();
															token.setValue(nuevoCalculo);
															if (token
																	.getContenido() != null) {
																token = calculaReferenciasContenido(
																		token,
																		listaFrmTokens,
																		formulario,
																		listaForms);
																/*
																 * String
																 * nuevoContenido
																 * =
																 * calculaValorContenido
																 * (
																 * token.getValue
																 * (), token.
																 * getContenido
																 * ());
																 * 
																 * if(
																 * existeVariableReferencia
																 * (
																 * nuevoContenido
																 * )){
																 * List<String>
																 * referenciasInside
																 * =
																 * obtieneVariableReferencia
																 * (
																 * nuevoContenido
																 * ); for(String
																 * refInside:
																 * referenciasInside
																 * ){ String
																 * refBusqInside
																 * = refInside.
																 * substring
																 * (refInside
																 * .indexOf
																 * ("&amp;")+5);
																 * String
																 * varInside =
																 * FillTemplateWithData
																 * .
																 * obtieneVariable
																 * ("**"+
																 * refBusqInside
																 * );
																 * for(FormTokens
																 * tok
																 * :listaFrmTokens
																 * ){ if(tok.
																 * getOriginalName
																 * ().equals(
																 * varInside)){
																 * if
																 * (tok.getValue
																 * ()==null) {
																 * //intento
																 * recuperar su
																 * valor
																 * nuevamente
																 * String
																 * nuevoCalculo;
																 * try{
																 * nuevoCalculo
																 * =
																 * valorFormularioBo
																 * .
																 * valorPorNombreVariable
																 * (formulario,
																 * tok
																 * .getName())
																 * .getValorcadena
																 * ();
																 * tok.setValue
																 * (nuevoCalculo
																 * ); }catch(
																 * NullPointerException
																 * e){
																 * System.out
																 * .println(
																 * "No se encuentra dentro del formulario gobernante"
																 * );
																 * tok.setValue
																 * (null); }
																 * 
																 * }
																 * 
																 * tok =
																 * tokenTransformer
																 * .
																 * evaluaFuncionVariable
																 * (tok);
																 * if(tok!=null
																 * &&
																 * tok.getValue
																 * ()!=null){
																 * nuevoContenido
																 * =
																 * nuevoContenido
																 * .
																 * replace(refInside
																 * ,
																 * nuevoContenido
																 * );
																 * token.setValue
																 * (
																 * nuevoContenido
																 * ); break; }
																 * 
																 * } }
																 * 
																 * } }
																 */
															}
														} catch (NullPointerException e) {
															System.out
																	.println("No se encuentra dentro del formulario gobernante");
															token.setValue(null);
														}

													}

													token = tokenTransformer
															.evaluaFuncionVariable(token);
													if (token != null
															&& token.getValue() != null) {
														/*
														 * if(referenciaBusqueda.
														 * equals("ref4;")){
														 * //token.setValue(
														 * "-->Si cambia el contenido de manera perfecta<--"
														 * ); }
														 */
														/*
														 * if(token.getContenido(
														 * )!=null){
														 * token.setValue
														 * (calculaValorContenido
														 * (
														 * token.getValue(),token
														 * .getContenido())); }
														 */
														contenido = contenido
																.replace(
																		referencia,
																		token.getValue());
														break;
													}
												}

											}

											// }
											// localizar la variable en los dft

										}
									}
									dft.setValue(contenido.trim());
									// cafaray 040814 <--
								} else {
									// --> 110714 cafaray Sin contenido,
									// entonces procedemos a obtener el valor
									// del componente
									dft.setValue(valor.getValorcadena());
									if (dft.getDeficionFuncion() != null
											&& !dft.getDeficionFuncion()
													.isEmpty()) {
										dft = tokenTransformer
												.evaluaFuncionVariable(dft);
									}
									// String newValue =
									// VariableUtils.convierteCualquierNumeroEnTexto(dft.getValue());
									// dft.setValue(newValue);

									// <-- 110714 cafaray
								}

							} else {
								// dft.setValue(valor.getValorcadena());
								// --> 110714 cafaray Le asigna el valor nulo,
								// para que no lo tome en cuenta en la
								// transformación
								dft.setValue(null);
								// <-- 110714 cafaray
							}
						}
					} else {
						// logger.info("Este formulario no aplica!!!");
					}

				} catch (Exception e) {
					e.printStackTrace(System.out);
					// logger.info("La excepcion que estaba buscando!!!");
				}
			}
		}

		actualizaReferencias(listaFrmTokens);
		/** si no se encontro un valor de token sustituir su longitud por '_' **/
		// this.updateFrmTokenValue(listaFrmTokens);
		logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	public String calculaValorContenido(String evaluar, String contenido)
			throws NotariaException {
		if (evaluar.equals(SELECTED_TRUE) && !contenido.contains("|")) {
			contenido = contenido.substring(1);
		} else if (evaluar.equals(SELECTED_TRUE) && contenido.contains("|")) {
			contenido = contenido.substring(contenido.indexOf("=") + 1,
					contenido.indexOf("|"));
		} else if (evaluar.equals(SELECTED_FALSE) && contenido.contains("|")) {
			contenido = contenido.substring(contenido.indexOf("|") + 1);
		} else if (contenido.contains(IDENTIFICADOR_VALOR_SELECCCION)) { // se
																			// trata
																			// de
																			// contenido
																			// para
																			// un
																			// <SELECT
																			// />
			StringBuilder key = new StringBuilder(evaluar);
			key.append(IDENTIFICADOR_VALOR_SELECCCION);
			if (contenido.contains(key.toString())) {
				int iInicio = contenido.indexOf(key.toString()) + key.length();
				contenido = contenido.substring(iInicio);
				int iFinal = contenido.contains("|") ? contenido.indexOf("|")
						: contenido.indexOf(")");
				contenido = contenido.substring(0, iFinal > 0 ? iFinal
						: contenido.length());
			} else {
				contenido = String.format("*** SIN VALOR SELECCION[%s] ***",
						key.toString());
			}
		} else {
			contenido = "*** SIN VALOR CONDICION NO VERDADERA ***";
		}
		return contenido;
	}

	public FormTokens calculaReferenciasContenido(FormTokens token,
			List<FormTokens> listaFrmTokens, Formulario formula,
			List<Formulario> listaForms) throws NotariaException {

		String nuevoContenido = calculaValorContenido(token.getValue(),
				token.getContenido());
		VariableToTokenTransformer tokenTransformer = new VariableToTokenTransformer();
		if (existeVariableReferencia(nuevoContenido)) {
			List<String> referenciasInside = obtieneVariableReferencia(nuevoContenido);
			for (String refInside : referenciasInside) {
				String refBusqInside = refInside.substring(refInside
						.indexOf("&amp;") + 5);
				String varInside = FillTemplateWithData.obtieneVariable("**"
						+ refBusqInside);
				for (FormTokens tok : listaFrmTokens) {
					if (tok.getOriginalName().equals(varInside)) {
						if (tok.getValue() == null) { // intento recuperar su
														// valor nuevamente
							String nuevoCalculo;
							try {
								formula = evaluaFormularioEquals(formula,
										token.getNombreCortoConForm(),
										listaForms);

								/*
								 * if(!formula.getConFormulario().getDsnombrecorto
								 * ().equals(token.getNombreCortoConForm())){
								 * for(Formulario frm:listaForms){
								 * if(frm.getConFormulario
								 * ().getDsnombrecorto().equals
								 * (token.getNombreCortoConForm())){ formula =
								 * frm; break; } } }
								 */
								nuevoCalculo = valorFormularioBo
										.valorPorNombreVariable(formula,
												tok.getName()).getValorcadena();
								tok.setValue(nuevoCalculo);

								if (tok.getContenido() != null) {

									tok = calculaReferenciasContenido(tok,
											listaFrmTokens, formula, listaForms);
								}
							} catch (NullPointerException e) {
								System.out
										.println("No se encuentra dentro del formulario gobernante");
								tok.setValue(null);
							}

						}

						tok = tokenTransformer.evaluaFuncionVariable(tok);
						if (tok != null && tok.getValue() != null) {
							nuevoContenido = nuevoContenido.replace(refInside,
									tok.getValue());
							break;
						}

					}
				}

			}
			token.setValue(nuevoContenido);
		}

		return token;
	}

	private void actualizaReferencias(List<FormTokens> formTokens)
			throws NotariaException {
		for (FormTokens formToken : formTokens) {
			if (formToken.getOriginalName() != null
					&& formToken.getValue() != null) {
				if (formToken.getDeficionFuncion() != null
						&& !formToken.getDeficionFuncion().isEmpty()) {
					// VariableToTokenTransformer tokenTransformer = new
					// VariableToTokenTransformer();
					// formToken =
					// tokenTransformer.evaluaFuncionVariable(formToken);
				}
				FillTemplateWithData.actualizaValorReferencia("${frm:"
						+ formToken.getOriginalName() + "]}",
						formToken.getValue());
			}
		}
	}

	/***
	 * obtieneVariableReferencia devuelve un arreglo de cadenas con las
	 * referencias localizadas en el contenido.
	 * 
	 * @param contenido
	 *            texto dentro de la variable de funcion lógica
	 * @return List<String> con el contenido de las referencias encontradas o
	 *         vacia si no se localizaron referencias
	 */
	public List<String> obtieneVariableReferencia(String contenido) {
		Pattern patern = Pattern
				.compile("(&amp;(([a-z]|[A-Z]|[0-9]|[<,>])*);)");
		Matcher match = patern.matcher(contenido);
		List<String> referencias = new ArrayList<>();
		while (match.find()) {
			String referencia = match.group();
			System.out.printf(
					"\t\tSe localizo la referencia %s en el contenido",
					referencia);
			referencias.add(referencia);
		}
		// Elimino las referencias duplicadas
		Set<String> eliminaDup = new HashSet<>(referencias);
		referencias.clear();
		referencias.addAll(eliminaDup);

		return referencias;
	}

	/***
	 * existeVariableReferencia valida la cadena de texto enviada en el
	 * parametro y verifica si contiene una expresion ^&[a-Z0-9];
	 * 
	 * @param contenido
	 *            texto dentro de la variable de funcion lógica
	 * @return true si existen indicios de que hay referencias, false en otro
	 *         caso
	 */
	public boolean existeVariableReferencia(String contenido) {
		return (contenido.contains("&") && contenido.indexOf(";",
				contenido.indexOf("&")) > 0);
	}

	/**
	 * Obtiene los valores de las variables dinamicas de la BD a partir de la
	 * lista de variables(tokens) proporcionada por la plantilla.
	 * 
	 * @param idActo
	 * @param listaVariables
	 * @throws NotariaException
	 */
	public void obtenValoresDinamicosVariables(String idActo,
			List<VariablesTokens> listaVariables) throws NotariaException {
		List<DocumentoExpediente> valoresDocumentos = documentoExpedienteBo
				.findByActoId(idActo);
		if (valoresDocumentos != null) {
			for (DocumentoExpediente varDoc : valoresDocumentos) {
				for (VariablesTokens varTok : listaVariables) {
					if (varTok.getName().equals(varDoc.getDsnombreVar())) {
						// String newValue =
						// VariableUtils.convierteCualquierNumeroEnTexto(varDoc.getDsvalorVar().trim());
						varTok.setValue(varDoc.getDsvalorVar().trim());
					}
				}
			}
		} else {
			logger.info("No se encontraron valores asociados al expediente. ");
		}
	}

	/**
	 * Obtiene los valores de variables estaticas en la BD. A variables con
	 * valor null se procede a buscar su valor estaticos, utilizando tabla
	 * auxiliar UbicacionVarEstatica.
	 * 
	 * @param filtro
	 *            Clase que contiene campos por cual filtrar los valores de la
	 *            variables
	 * @param listaVariables
	 * @throws NotariaException
	 */
	public void obtenValoresEstaticosVariables(FiltroVariables filtro,
			List<VariablesTokens> listaVariables) throws NotariaException {
		logger.info("Obteniendo valores de variables estaticas ");
		for (VariablesTokens varToken : listaVariables) {
			/** Si ya se tiene valor, no se hace nada **/

			if (varToken.getValue() == null
					&& !varToken.getName().startsWith("frm:")) { // esta
																	// validación
																	// se hace
																	// para
																	// evitar el
																	// paso de
																	// variables
																	// de
																	// formulario
				// Posibles variables estaticas
				// for (UbicacionVarEstatica uve : ubicaciones) {
				UbicacionVarEstatica uve = ubicacionVarEstaticasBo
						.obtieneVariableEstaticaPorNombre(varToken.getName());
				// if (varToken.getName().contains(
				// uve.getVariable().getDsnombre())) {
				if (uve != null) {
					if (varToken.getFiltroFormulario() != null
							&& !varToken.getFiltroFormulario().isEmpty()) {
						// hace referencia a un filtro acto diferente

					} else {
						String valorFiltro = this.obtenValorFiltroOnActo(
								uve.getDsfiltro(), filtro);
						if (valorFiltro != null) {
							List<Object> valores = null;
							try {
								/** Obtener valores de variable estatica **/
								valores = ubicacionVarEstaticasBo
										.obtenerValorVarEstatica(uve,
												valorFiltro);
							} catch (NotariaException e) {
								logger.error(String
										.format("====> NotariaException.getCause: %s%n\tOn obtenerValorVarEstatica(%s, %s)%nSee more detailed error on console.",
												e.getCause(), uve.getVariable()
														.getIdvariable(),
												valorFiltro));
								e.printStackTrace(System.out);
							}
							if (valores != null
									&& !VariableUtils.isListaEmpty(valores)) {
								String strValor = VariableUtils
										.daFormatoListas(valores);
								// String newValue =
								// VariableUtils.convierteCualquierNumeroEnTexto(strValor.trim());
								varToken.setValue(strValor);
							}
						} else {
							logger.warn("====> Revisar definicion de variables estaticas. No se pudo obtener el valor del filtro");
						}
					}
				} else {
					logger.warn("====> Revisar definicion de variables estatica. No se localizo el valor para "
							+ varToken.getName());
				}
			} else {
				logger.warn("=====> Al parce varToken.getValue() retornno nill");
			}
		}
	}

	/**
	 * Obtiene valores que por su complejidad y reglas de negocio tienen que ser
	 * obtenidos con queries especiales
	 * 
	 * @param listaVariables
	 * @param filtro
	 *            por el cual se van a obtener los valores.
	 * @throws NotariaException
	 */
	public void obtenValoresComplejosVariables(
			List<VariablesTokens> listaVariables, FiltroVariables fvariables)
			throws NotariaException {
		VariablesComplejaDefinicion varCompleja = new VariablesComplejaDefinicion();

		for (VariablesTokens var : listaVariables) {
			// Trabajar con variables que sus valores sean nullos
			if (var.getValue() == null) {
				if (var.getType().equals(
						EnumVariables.COMPARECIENTE_IDENTIFIER.toString())) {
					boolean escriboAlgo = false; // esta variable es usada para
													// no leer a todos los
													// comparecientes dentro del
													// expediente cuando hay más
													// de un acto involucrado
					// obtiene el listado de comparecientes:
					List<Compareciente> comparecientes = new ArrayList<Compareciente>();
					ComparecienteBo bo = new ComparecienteBoImpl();
					System.out.println("TIPO VARIABLE::" + var.originalName);
					comparecientes = bo.listadoComparecientesByActo(
							fvariables.getIdacto(), var.getDeficionFuncion());
					StringBuilder valorVariable = new StringBuilder();
					int sizeComparecientes = comparecientes.size();
					int actualCompareciente = 0;
					// boolean puesto = false;

					if (var.getName().contains("plural")) {
						var.setName(var.getName().replace("|plural", ""));
						valorVariable.append(calculaTratamientoComparecientes(
								comparecientes, var.getName()));
						escriboAlgo = true;
					} else {
						for (Compareciente compareciente : comparecientes) {
							System.out.println("TIPO::"
									+ compareciente.getTipoCompareciente()
											.getDsnombre());
							actualCompareciente++;
							if (var.getTipoDato() != null
									&& var.getTipoDato()
											.equals("representante")) {
								// if(var.getDeficionFuncion()!=null &&
								// var.getDeficionFuncion().equals("representante")){
								ComparecienteRepresentanteBo boRepresentantes = new ComparecienteRepresentanteBoImpl();
								List<Compareciente> representantes = boRepresentantes
										.findByRepresentadoId(compareciente
												.getIdcompareciente());
								if (representantes != null
										&& representantes.size() > 0) {
									for (Compareciente representante : representantes) {
										valorVariable
												.append(determinaValorCompareciente(
														var.getName(),
														representante));
										escriboAlgo = true;
									}
								} else {
									escriboAlgo = false;
								}
							} else {
								// solo si var.getName() es diferente de
								// datosIdentificacion
								if (!var.getName()
										.equals("datosIdentificacion")
										&& !var.getName().equals(
												"certficiacionpersonalidad")
										&& !var.getName().equals(
												"certificadoExtranjeria")) {
									// CALCULO TRATAMIENTOS PARA PLURAL
									/*
									 * if(compareciente.getTratamiento()
									 * !=null){ if(sizeComparecientes>=2){
									 * String[] tmpString =
									 * var.getName().split("\\|");
									 * 
									 * for (String s:tmpString){ if
									 * (s.contains("(norepeat)")){
									 * norepeatList.add(s.replace("(norepeat)",
									 * ""));
									 * var.setName(var.getName().replace("|"+s,
									 * "")); } }
									 * 
									 * if(!puesto){ valorVariable.append(
									 * calculaTratamientoComparecientes
									 * (comparecientes)); puesto = true;
									 * 
									 * }
									 * 
									 * }else if (sizeComparecientes==1 ) {
									 * valorVariable
									 * .append(compareciente.getTratamiento
									 * ().getDselemento());
									 * 
									 * } }
									 */

									if (sizeComparecientes > 1
											&& sizeComparecientes > (actualCompareciente + 1)) { // colocar
																									// la
																									// ,
										valorVariable
												.append(determinaValorCompareciente(
														var.getName(),
														compareciente));
										if (valorVariable != null
												&& valorVariable.length() > 0) {
											valorVariable.append(", ");
										}
									} else if (sizeComparecientes > 1
											&& sizeComparecientes == (actualCompareciente + 1)) { // colocar
																									// el
																									// y
										valorVariable
												.append(determinaValorCompareciente(
														var.getName(),
														compareciente));
										if (valorVariable != null
												&& valorVariable.length() > 0) {
											valorVariable.append(" y ");
										}
									} else { // solo es un compareciente o es el
												// ultimo de la fila
										valorVariable
												.append(determinaValorCompareciente(
														var.getName(),
														compareciente));
									}
								} else {
									valorVariable
											.append(determinaValorCompareciente(
													var.getName(),
													compareciente));
									if (valorVariable != null
											&& valorVariable.length() > 0
											&& actualCompareciente < sizeComparecientes) {
										valorVariable.append("<br />");
									}
								}
								escriboAlgo = true;
							}
						}
					}

					if (escriboAlgo) {
						var.setValue(valorVariable.toString());
					} else {
						var.setValue("");
					}
				} else if (var.getType().equals(
						EnumVariables.COMPARECIENTES_IDENTIFIER.toString())) {
					/***/

					boolean escriboAlgo = false; // esta variable es usada para
													// no leer a todos los
													// comparecientes dentro del
													// expediente cuando hay más
													// de un acto involucrado
					// obtiene el listado de comparecientes:
					List<Compareciente> comparecientes = new ArrayList<Compareciente>();
					ComparecienteBo bo = new ComparecienteBoImpl();
					comparecientes = bo.listadoComparecientesByActo(
							fvariables.getIdacto(), var.getDeficionFuncion());
					StringBuilder valorVariable = new StringBuilder();
					// boolean puesto = false;
					valorVariable.append(determinaValorComparecientes(
							var.getName(), comparecientes));
					var.setValue(valorVariable.toString());

					/*****/

				} else {
					System.out.println("VARIABLE COMPLEJA"+var.getName());
					Object obj = varCompleja.obtenValorComplejo(var.getName(),
							fvariables, var.getDeficionFuncion());
					if (obj != null) {
						String returnValue = (String) obj;
						var.setValue(returnValue);
					}
				}
			}
		}
	}

	public String calculaVariableCompareciente(String nombreVariable,
			String tipoCompareciente, String idacto) throws NotariaException {
		List<Compareciente> comparecientes = new ArrayList<Compareciente>();
		ComparecienteBo bo = new ComparecienteBoImpl();
		comparecientes = bo.listadoComparecientesByActo(idacto,
				tipoCompareciente);
		StringBuilder valorVariable = new StringBuilder();
		int sizeComparecientes = comparecientes.size();
		int actualCompareciente = 0;
		for (Compareciente compareciente : comparecientes) {
			actualCompareciente++;
			if (tipoCompareciente.equalsIgnoreCase("REPRESENTANTE")) {
				// if(var.getDeficionFuncion()!=null &&
				// var.getDeficionFuncion().equals("representante")){
				ComparecienteRepresentanteBo boRepresentantes = new ComparecienteRepresentanteBoImpl();
				List<Compareciente> representantes = boRepresentantes
						.findByRepresentadoId(compareciente
								.getIdcompareciente());
				if (representantes != null && representantes.size() > 0) {
					for (Compareciente representante : representantes) {
						valorVariable.append(determinaValorCompareciente(
								nombreVariable, representante));
					}
				}
			} else {
				// solo si var.getName() es diferente de datosIdentificacion
				if (!nombreVariable.equals("datosIdentificacion")) {
					if (sizeComparecientes > 1
							&& sizeComparecientes > (actualCompareciente + 1)) { // colocar
																					// la
																					// ,
						valorVariable.append(determinaValorCompareciente(
								nombreVariable, compareciente));
						if (valorVariable != null && valorVariable.length() > 0) {
							valorVariable.append(", ");
						}
					} else if (sizeComparecientes > 1
							&& sizeComparecientes == (actualCompareciente + 1)) { // colocar
																					// el
																					// y
						valorVariable.append(determinaValorCompareciente(
								nombreVariable, compareciente));
						if (valorVariable != null && valorVariable.length() > 0) {
							valorVariable.append(" y ");
						}
					} else { // solo es un compareciente o es el ultimo de la
								// fila
						valorVariable.append(determinaValorCompareciente(
								nombreVariable, compareciente));
					}
				} else {
					valorVariable.append(determinaValorCompareciente(
							nombreVariable, compareciente));
					if (valorVariable != null && valorVariable.length() > 0
							&& actualCompareciente < sizeComparecientes) {
						valorVariable.append("<br />");
					}
				}
			}
		}
		return valorVariable.toString();
	}

	// Victor Espinosa : Al hacer la funcion recursiva no siempre el formulario
	// en turno es igual a las variables que se deben calcular dentro
	// es por eso que tuve que hacer esta funcion para validar y devolver el
	// correcto
	private Formulario evaluaFormularioEquals(Formulario formulario,
			String nombreCorto, List<Formulario> listaForms) {
		if (!formulario.getConFormulario().getDsnombrecorto()
				.equals(nombreCorto)) {
			for (Formulario frm : listaForms) {
				if (frm.getConFormulario().getDsnombrecorto()
						.equals(nombreCorto)) {
					formulario = frm;
					break;
				}
			}
		}
		return formulario;
	}

	private String determinaValorCompareciente(String contenido,
			Compareciente compareciente) throws NotariaException {
		if (contenido.equals("") || contenido.isEmpty()) {
			return compareciente.getPersona().getDsnombrecompleto()
					.toUpperCase();
		}
		StringBuilder valorVariable = new StringBuilder();

		// extrae las variables y constantes para armar solo un texto y eso
		// colocarlo como valor
		String[] variables = contenido.split("\\|");
		for (String variableCompareciente : variables) {
			if (variableCompareciente.contains("&rsquo;")
					|| variableCompareciente.contains("&lsquo;")
					|| variableCompareciente.contains("&#39;")) { // se trata de
																	// una
																	// constante
				// solo quitamos los elementos rsquo y lsquo
				variableCompareciente = variableCompareciente.replace(
						"&rsquo;", "");
				variableCompareciente = variableCompareciente.replace(
						"&lsquo;", "");
				variableCompareciente = variableCompareciente.replace("&#39;",
						"");

				valorVariable.append(variableCompareciente);
			} else { // se trata de una variable, por ende se calcula el valor
						// en las funciones de compareciente
				java.lang.reflect.Method method;
				try {
					String definicionFuncion = "";
					if (variableCompareciente.contains("(")
							&& variableCompareciente.contains(")")) {
						definicionFuncion = variableCompareciente.substring(
								variableCompareciente.indexOf("(") + 1,
								variableCompareciente.indexOf(")"));
						variableCompareciente = variableCompareciente
								.substring(0,
										variableCompareciente.indexOf("("));
					}
					VariableValueAssignerCompareciente obj = new VariableValueAssignerCompareciente(
							compareciente);
					method = obj.getClass().getMethod(variableCompareciente);
					try {
						Object objResponse = method.invoke(obj);
						String response = String.valueOf(objResponse);

						if (definicionFuncion != null
								&& definicionFuncion.length() > 0) {
							VariableToTokenTransformer transformer = new VariableToTokenTransformer();
							response = transformer.evaluaFuncionVariable(
									definicionFuncion, response);
						}
						valorVariable.append(response);
					} catch (IllegalArgumentException e) {
						logger.error("=====> Se esperaba un argumento en "
								+ variableCompareciente + " y no se definio.",
								e);
					} catch (IllegalAccessException e) {
						logger.error("=====> Illegal Access a "
								+ variableCompareciente + ".", e);
					} catch (InvocationTargetException e) {
						logger.error("=====> Invocation Target en "
								+ variableCompareciente + ".", e);
					}
				} catch (SecurityException e) {
					logger.error("=====> No se tiene permiso para el metodo "
							+ variableCompareciente + " en el compareciente.",
							e);
				} catch (NoSuchMethodException e) {
					logger.error("=====> No se localizo el metodo "
							+ variableCompareciente + " en el compareciente.",
							e);
				}
			}
		}
		return valorVariable.toString();
	}

	private String determinaValorComparecientes(String contenido,
			List<Compareciente> comparecientes) throws NotariaException {

		StringBuilder valorVariable = new StringBuilder();
		// extrae las variables y constantes para armar solo un texto y eso
		// colocarlo como valor
		String[] variables = contenido.split("\\|");
		for (String variableCompareciente : variables) {
			if (variableCompareciente.contains("&rsquo;")
					|| variableCompareciente.contains("&lsquo;")
					|| variableCompareciente.contains("&#39;")) { // se trata de
																	// una
																	// constante
				// solo quitamos los elementos rsquo y lsquo
				variableCompareciente = variableCompareciente.replace(
						"&rsquo;", "");
				variableCompareciente = variableCompareciente.replace(
						"&lsquo;", "");
				variableCompareciente = variableCompareciente.replace("&#39;",
						"");

				valorVariable.append(variableCompareciente);
			} else { // se trata de una variable, por ende se calcula el valor
						// en las funciones de compareciente
				java.lang.reflect.Method method;
				try {
					String definicionFuncion = "";
					if (variableCompareciente.contains("(")
							&& variableCompareciente.contains(")")) {
						definicionFuncion = variableCompareciente.substring(
								variableCompareciente.indexOf("(") + 1,
								variableCompareciente.indexOf(")"));
						variableCompareciente = variableCompareciente
								.substring(0,
										variableCompareciente.indexOf("("));
					}
					VariableValueAssignerComparecientes obj = new VariableValueAssignerComparecientes(
							comparecientes);
					method = obj.getClass().getMethod(variableCompareciente);
					try {
						Object objResponse = method.invoke(obj);
						String response = String.valueOf(objResponse);

						if (definicionFuncion != null
								&& definicionFuncion.length() > 0) {
							VariableToTokenTransformer transformer = new VariableToTokenTransformer();
							response = transformer.evaluaFuncionVariable(
									definicionFuncion, response);
						}
						valorVariable.append(response);
					} catch (IllegalArgumentException e) {
						logger.error("=====> Se esperaba un argumento en "
								+ variableCompareciente + " y no se definio.",
								e);
					} catch (IllegalAccessException e) {
						logger.error("=====> Illegal Access a "
								+ variableCompareciente + ".", e);
					} catch (InvocationTargetException e) {
						logger.error("=====> Invocation Target en "
								+ variableCompareciente + ".", e);
					}
				} catch (SecurityException e) {
					logger.error("=====> No se tiene permiso para el metodo "
							+ variableCompareciente + " en el compareciente.",
							e);
				} catch (NoSuchMethodException e) {
					logger.error("=====> No se localizo el metodo "
							+ variableCompareciente + " en el compareciente.",
							e);
				}
			}
		}
		return valorVariable.toString();
	}

	public String calculaTratamientoComparecientes(
			List<Compareciente> comparecientes, String variable)
			throws NotariaException {
		ArrayList<Compareciente> senores = new ArrayList<Compareciente>();
		ArrayList<Compareciente> senoras = new ArrayList<Compareciente>();
		ArrayList<Compareciente> senoritas = new ArrayList<Compareciente>();
		StringBuilder valorVariable = new StringBuilder();
		ArrayList<String> norepeatList = new ArrayList<String>();

		String[] tmpString = variable.split("\\|");

		for (String s : tmpString) {
			if (s.contains("(norepeat)")) {
				norepeatList.add(s.replace("(norepeat)", ""));
				variable = variable.replace("|" + s, "");
			}
		}

		for (Compareciente c : comparecientes) {
			switch (c.getTratamiento().getDscodigo()) {
			case "ELSE":
				senores.add(c);
				break;
			case "LASE":
				senoras.add(c);
				break;
			case "LITA":
				senoritas.add(c);
				break;

			default:
				break;
			}

		}

		// determinaValorCompareciente(variable, compareciente);

		if (senores.size() > 0) {
			if (senores.size() > 1)
				valorVariable.append("los señores ");
			else {
				valorVariable.append(senores.get(0).getTratamiento()
						.getDselemento());
				valorVariable.append(" ");
			}

			valorVariable.append(calculaLetraComaCompareciente(senores,
					variable));
			/*
			 * for(Compareciente com:senores){
			 * valorVariable.append(determinaValorCompareciente(variable,com));
			 * }
			 */
		}
		if (senoras.size() > 0) {
			if (senores.size() > 0) {
				if (senoritas.size() > 0)
					valorVariable.append(", ");
				else
					valorVariable.append(" y ");
			}
			if (senoras.size() > 1)
				valorVariable.append("las señoras ");
			else {
				valorVariable.append(senoras.get(0).getTratamiento()
						.getDselemento());
				valorVariable.append(" ");
			}
			valorVariable.append(calculaLetraComaCompareciente(senoras,
					variable));

		}
		if (senoritas.size() > 0) {
			if (senores.size() > 0 || senoras.size() > 0) {
				valorVariable.append(" y ");
			}
			if (senoritas.size() > 1)
				valorVariable.append("las señoritas ");
			else {
				valorVariable.append(senoritas.get(0).getTratamiento()
						.getDselemento());
				valorVariable.append(" ");
			}
			valorVariable.append(calculaLetraComaCompareciente(senoritas,
					variable));
		}
		if (norepeatList.size() > 0 && comparecientes.size() > 0) {
			for (String datos : norepeatList) {
				valorVariable.append(datos);
			}
		}

		return valorVariable.toString();
	}

	public String calculaLetraComaCompareciente(
			List<Compareciente> comparecientes, String variable)
			throws NotariaException {
		StringBuilder valorVariable = new StringBuilder();
		int actualCompareciente = 0;
		for (Compareciente compareciente : comparecientes) {
			actualCompareciente++;
			if (comparecientes.size() > 1
					&& comparecientes.size() > (actualCompareciente + 1)) { // colocar
																			// la
																			// ,
				valorVariable.append(determinaValorCompareciente(variable,
						compareciente));
				if (valorVariable != null && valorVariable.length() > 0) {
					valorVariable.append(", ");
				}
			} else if (comparecientes.size() > 1
					&& comparecientes.size() == (actualCompareciente + 1)) { 
				// colocar
				// el
				// y
				valorVariable.append(determinaValorCompareciente(variable,
						compareciente));
				if (valorVariable != null && valorVariable.length() > 0) {
					valorVariable.append(" y ");
				}
			} else { // solo es un compareciente o es el ultimo de la fila
				valorVariable.append(determinaValorCompareciente(variable,
						compareciente));
			}
		}

		return valorVariable.toString();
	}

	/**
	 * Regresa el valor del filtro(parametro de busqueda) para obtener el valor
	 * de variable estatica.
	 * 
	 * @param filtro
	 * @param acto
	 * @throws NotariaException
	 */

	public String obtenValorFiltroOnActo(String filtro,
			FiltroVariables fvariable) throws NotariaException {
		// TODO: a futuro mejorar con reflexion
		if (fvariable.getIdacto() != null) {
			/** Solo se cuenta con el idacto **/
			if (StringUtils.contains(filtro, "idacto")) {
				return fvariable.getIdacto();
			} else if (StringUtils.contains(filtro, "idescritura")) {
				/*
				 * Obtener la escritura a travez del acto, si esto sucede no se
				 * cuenta con idescritura
				 */
				return escrituraActoBo.obtenIdEscrituraPorActoId(fvariable
						.getIdacto());
			}
		} else if (fvariable.getIdescritura() != null) {
			/** No se cuenta con el acto, pero se propociono la idescritura **/
			return fvariable.getIdescritura();
		}
		return null;
	}
}
