package com.palestra.notarias.variables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.LibroBoImpl;
import com.palestra.notaria.bo.impl.PizarronElementoBoImpl;
import com.palestra.notaria.bo.impl.UbicacionVarEstaticaBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notarias.pojo.CamposOracion;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.pojo.SubHql;
import com.palestra.notarias.utils.NumberToLetterConverter;
import com.palestra.notarias.utils.VariableUtils;

/**
 * Clase que contiene la definicion de las variables estaticas que necesitan
 * queries especificos para obtener su valor
 * 
 * @author sofia
 * 
 */
public class VariablesComplejaDefinicion {

	static Logger logger = Logger.getLogger(VariablesComplejaDefinicion.class);
	private final UbicacionVarEstaticaBoImpl estaticaBo = new UbicacionVarEstaticaBoImpl();
	private final EscrituraActoBoImpl escrituraActoBo = new EscrituraActoBoImpl();
	private static final String RESULTADO_SIMPLE = "simple";
	private static final String RESULTADO_COMPUESTO = "compuesto";
	private static final String FILTRO_ESCRITURA = "idescritura";
	private static final String FILTRO_ACTO = "idacto";
	private static final String FILTRO_ACTODOCUMENTO = "idActoDocumento";
	private static final String FILTRO = "filtro";
	private static final String NODE_NAME_RESULTADO = "resultado";
	private static final String NODE_NAME_CAMPOS = "campos";

	/**
	 * Retorna el valor de la variable 'fecha_hoy'
	 * 
	 * @return
	 */
	public String getFechaHoy() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	public String getCapacidadRepresentantes(String idacto) throws NotariaException {
		
		//@VICTOR OBTENGO TODOS LOS ACTOS, POR QUE NO SE, SI MANDO ACTOS INDIVIDUALES AFECTE A OTROS MÉTODOS
		
		ComparecienteBo cobo = new ComparecienteBoImpl();
		
		ActoBo actobo = new ActoBoImpl();
		String exp = actobo.getExpedienteIdByActoId(idacto);
		List<Acto> actos = actobo.filterActoByIdExpediente(exp);
		StringBuilder valor = new StringBuilder();

		for(Acto acto: actos){
			List<Compareciente> comparecientes = cobo.listadoComparecientesByActo(acto.getIdacto());
			
			for (Compareciente co:comparecientes){
				VariableValueAssignerCompareciente asigcompareciente = new VariableValueAssignerCompareciente(co);
				String dato = asigcompareciente.capacidadRepresentantes();
				if(!dato.isEmpty() || !dato.equals("")){
					valor.append(dato);
					valor.append("<br>");
				}
				
			}	
		}
		
		return  valor.toString();
		
	}

	public String getComparecienteEstadoCivilTexto(String variableName,
			String definicionFuncion, String idacto) throws NotariaException {
		logger.info("Ejecutando getComparecienteEstadoCivilTexto");
		if (definicionFuncion.contains("tipo")) {

			String sql = this.obtenQueryCompareciente(
					"compareciente_estado_civil", definicionFuncion, idacto);
			List<Object> resultados = estaticaBo.getSingleValFromHqlQuery(sql);
			List<Object> oraciones = new ArrayList<Object>();
			if (resultados == null) {
				return " ";
				// return null;
			}
			for (Object res : resultados) {
				String oracion;
				if (res.equals("Soltero")) {
					/** De los campos obtenidos se compone cadena deseada. **/
					oracion = " y declara que el inmueble que más adelante se relaciona jamás  lo aportó a sociedad conyugal alguna ";
					oraciones.add(oracion);
					return VariableUtils.daFormatoListas(oraciones);
				} else if (res.equals("Casado Bienes Separados")) {
					oracion = "bajo el régimen de bienes separados (según consta de la copia certificada del acta de matrimonio que me exhibe"
							+ " y que en copia fotostática agrego al apéndice de esta escritura con la letra #LETRA) ";
					oraciones.add(oracion);
					return VariableUtils.daFormatoListas(oraciones);
				} else if (res.equals("Bienes Mancomunados")) {
					oracion = res + "";
					oraciones.add(oracion);
					return VariableUtils.daFormatoListas(oraciones);
				} else {
					oracion = res + "";
					oraciones.add(oracion);
					return VariableUtils.daFormatoListas(oraciones);
				}
			}
		}
		/** Si no contiene definicion funcion retorna nullo **/
		return null;
	}

	private String obtenQueryCompareciente(String variableName,
			String definicionFuncion, String idacto) {
		String tipoCompareciente = StringUtils.substringAfter(
				definicionFuncion, "=");
		String hql = VariableUtils.obtenHQL(variableName);
		if (hql == null) {
			return null;
		}
		String hqlTipo = VariableUtils.getValueByProperty(variableName, "tipo");
		StringBuilder sb = new StringBuilder();
		sb.append(hql.replace("${idacto}", idacto));
		sb.append(hqlTipo.replace("${tipo}", tipoCompareciente));
		logger.info("=======> Query" + sb.toString());
		return sb.toString();

	}

	public String getComparecienteCantidad(String variableName,
			String definicionFuncion, String idacto) throws NotariaException {
		logger.info("Ejecutando getComparecienteCantidad");
		if (definicionFuncion.contains("tipo")) {
			String sql = this.obtenQueryCompareciente(
					"compareciente_compraciente_cantidad", definicionFuncion,
					idacto);
			List<Object> resultados = estaticaBo.getSingleValFromHqlQuery(sql);
			if (resultados == null) {
				return " ";
				// return null;
			}
			if (resultados.size() == 1) {
				StringBuilder sb = new StringBuilder();
				sb.append("I.- Que a mi juicio el compareciente tiene capacidad legal para la celebración de este acto, ");
				sb.append("y que me aseguré de su identidad conforme a la relación que agrego al apéndice de esta escritura con la letra #LETRA. ");
				sb.append("[I].- Que el compareciente declara por sus generales ser: ");
				sb.append("[II].- Que advertí al compareciente de las penas en que incurren quienes declaran falsamente ante notario. ");
				sb.append("[III].- Que tuve a la vista los documentos citados en esta escritura. ");
				sb.append("[IV].- Que a solicitud del compareciente a quien atendí personalmente, leí y expliqué esta escritura al mismo, una vez que le hice saber el derecho");
				sb.append("que tiene de leerla personalmente, manifestando el otorgante su conformidad y comprensión plena y la firmó el día, mismo momento en que la autorizo.");
				sb.append("Doy fe. ");
				return sb.toString();
				
			} else if (resultados.size() > 1) {
				StringBuilder sb = new StringBuilder();
				sb.append("I.- Que a mi juicio las comparecientes tienen capacidad legal para la celebración de este acto, ");
				sb.append("y que me aseguré de su identidad conforme a la relación que agrego al ");
				sb.append("apéndice de esta escritura con la letra #LETRA.");
				return sb.toString();
			}
		}
		/** Si no contiene definicion funcion retorna nullo **/
		return null;
	}

	/**
	 * Obtiene los valores para variables con querys compuestos, se filtra por
	 * idacto.
	 * 
	 * TODO: adaptar para idescritura.
	 * 
	 * @param variableName
	 * @param definicionFuncion
	 * @param idActo
	 * @return
	 */
	public String getValueComposeQueryByActo(String variableName,
			String definicionFuncion, String idActo) {
		try {
			if (definicionFuncion == null) {
				return null;
			}
			List<SubHql> listaHql = this.obtenListSubHql(definicionFuncion,
					variableName);
			/** obtener el hql principal **/
			String hql = VariableUtils.obtenHQL(variableName);
			if (hql == null) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(hql.replace("${idacto}", idActo));
			/** obtener los otros filtros, que no sea el actoid **/
			for (SubHql sq : listaHql) {
				sb.append(sq.getSbhql().replace("${" + sq.getIdsubhql() + "}",
						sq.getValor()));
			}
			// logger.info("====> Query completo: " + sb.toString());
			String tipoResultado = VariableUtils.getValueByProperty(
					variableName, NODE_NAME_RESULTADO);
			if (tipoResultado.equals(RESULTADO_SIMPLE)) {
				return this.obtenValorSimpleFromComposeQuery(sb.toString(),
						variableName);
			} else if (tipoResultado.equals(RESULTADO_COMPUESTO)) {
				return this.obtenValorCompuestoFromComposeQuery(sb.toString(),
						variableName);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene resultados de queries compuestos que retornen MAS de un campo.
	 * 
	 * @param query
	 * @param variableName
	 * @return
	 * @throws NotariaException 
	 */
	private String obtenValorCompuestoFromComposeQuery(String query, String variableName) throws NotariaException {
		List<CamposOracion> campos = VariableUtils.getCamposOracion(
				variableName, NODE_NAME_CAMPOS);
		if (campos == null) {
			return null;
		}
		List<Object[]> resultados = estaticaBo.obtenerValoresFromHqlQuery(query);
		List<Object> oraciones = new ArrayList<Object>();
		if (resultados == null) {
			return null;
		}
		for (Object[] res : resultados) {
			StringBuilder oracion = new StringBuilder();
			for (int i = 0; i <= res.length - 1; i++) {
				// System.out.println("res[" + i + "] : " + res[i]);
				/** Por cada columna(campo) por fila de los resultados, obtener el valor del campo**/
				if (res[i] != null) {
					/** Hacer match entre valor y campo, se supone que tiene el mismo indice i **/
					CamposOracion campo = campos.get(i);
					if (campo != null) {
						/** Reemplazar  ${campo[1]} -> res[1]  **/
						String valor = campo.getCampoDefinicion().replace(
								"${" + campo.getCampoName() + "}",
								(String) res[i]);
						oracion.append(valor);
					}
				}
			}
			oraciones.add(oracion.toString());
		}
		return VariableUtils.daFormatoListas(oraciones);
	}

	/**
	 * Obtiene resultados de queries compuestos que retornen un solo resultado.
	 * 
	 * @param query
	 * @param variableName
	 * @return
	 * @throws NotariaException 
	 */
	private String obtenValorSimpleFromComposeQuery(String query,
			String variableName) throws NotariaException {
		List<Object> resultados = estaticaBo.getSingleValFromHqlQuery(query);
		if (resultados == null) {
			return null;
		}
		return VariableUtils.daFormatoListas(resultados);
	}

	/**
	 * De la variable dada se obtienen los filtros(subqueries) correspondientes
	 * 
	 * @param definicionFuncion
	 * @param variableName
	 * @return
	 */
	private List<SubHql> obtenListSubHql(String definicionFuncion,
			String variableName) {

		String[] filtros = definicionFuncion.split("\\,");
		if (filtros == null || filtros.length == 0) {
			return null;
		}
		List<SubHql> listaHql = new ArrayList<SubHql>();
		for (int i = 0; i <= filtros.length - 1; i++) {
			if (filtros[i] == null)
				continue;
			String[] keyValor = filtros[i].split("\\=");
			if (keyValor != null && keyValor.length == 2) {
				SubHql subhql = new SubHql();
				logger.info("key=" + keyValor[0] + " valor=" + keyValor[1]);
				subhql.setIdsubhql(keyValor[0]);
				subhql.setValor(keyValor[1]);
				/** En vase al idsubhql obtener el hql **/
				String subHql = VariableUtils.getValueByProperty(variableName,
						subhql.getIdsubhql());
				subhql.setSbhql(subHql);
				logger.info(subhql.toString());
				listaHql.add(subhql);
			}
		}

		return listaHql;
	}

	// TODO: punto de entrada.
	public Object obtenValorComplejo(String variableName, FiltroVariables fvariables, String definicionFuncion) throws NotariaException {
		StringBuilder respuesta = new StringBuilder();
		if (variableName.equals("fecha_hoy")) {
			respuesta.append(getFechaHoy().trim());	
		}
		
		else if (variableName.equals("compareciente_estado_civil_texto")) {
			if (fvariables.getIdacto() == null) {
				return null;
			}
			return getComparecienteEstadoCivilTexto(variableName, definicionFuncion, fvariables.getIdacto());
		} else if(variableName.equals("compareciente_compro_estadocivil")){
			if (fvariables.getIdacto() == null) {
				return null;
			}else{
				return VariableUtils.comparecientesCompraronEstadoCivil(fvariables.getIdacto());				
			}
		}else if(variableName.equals("representantes_capacidad")){
			if (fvariables.getIdacto() == null) {
				return null;
			}else{
				return getCapacidadRepresentantes(fvariables.getIdacto());				
			}
		} else if(variableName.equals("compareciente_ambos_compraron")){
			if (fvariables.getIdacto() == null) {
				return null;
			}else{
				return VariableUtils.comparecienteCompraron(fvariables.getIdacto());
			}			
		}else if(variableName.equals("compareciente_ambos_compraron")){
			if (fvariables.getIdacto() == null) {
				return null;
			}else{
				return VariableUtils.comparecienteCompraron(fvariables.getIdacto());
			}			
		}else if(variableName.equals("compareciente_fecha_nacimiento")){
			if (fvariables.getIdacto() == null) {
				return null;
			}else{
				String tipoCompareciente = StringUtils.substringAfter(definicionFuncion, "=");
				return VariableUtils.comparecienteFechaNacimiento(fvariables.getIdacto(), tipoCompareciente, "texto");
			}						
		} else if (variableName.equals("compareciente_compraciente_cantidad")) {
			if (fvariables.getIdacto() == null) {
				return null;
			}
			return getComparecienteCantidad(variableName, definicionFuncion, fvariables.getIdacto());
		} else if (variableName.contains("compareciente_")) { // TODO: quitar mas adelante.
			if (fvariables.getIdacto() == null) {
				return null;
			}
			String valor = getValueComposeQueryByActo(variableName, definicionFuncion, fvariables.getIdacto());			
			return valor;
		} else {
			String valor = accedeValoresVariableComplejas(variableName, fvariables, definicionFuncion);			
			return valor;
		}
		
		return respuesta.toString();
	}
	
	/**
	 * Accede a los valores de la variable clasificando por el tipo de
	 * resultado. Simple debuelve un solo string, Compuesto se obtiene multiples
	 * valores
	 * 
	 * @param variableName
	 * @param fvariables
	 *            Tipo de filtro por el cual se obtendran los valores, ya sea
	 *            filtrado por idescritura o idacto
	 * @param definicionFuncion
	 * @return
	 * @throws NotariaException 
	 */
	public String accedeValoresVariableComplejas(String variableName,
			FiltroVariables fvariables, String definicionFuncion) throws NotariaException {
		String tipo = VariableUtils.getValueByProperty(variableName,
				NODE_NAME_RESULTADO);
		if (RESULTADO_SIMPLE.equals(tipo)) {
			return this.obtenValorAcordeFiltro(variableName, fvariables, definicionFuncion);
		} else {
			// TODO: falta cuando el tipo de resultado sea compuesto.
			return null;
		}
	}

	/**
	 * Obtiene valores de acuerdo al filtro principal ya sea por acto o
	 * expediente.
	 * 
	 * @param variableName
	 * @param fvariable
	 * @param definicionFuncion
	 * @return
	 * @throws NotariaException 
	 */
	private String obtenValorAcordeFiltro(String variableName,
			FiltroVariables fvariables, String definicionFuncion) throws NotariaException {
		/**
		 * obtener por que se va filtrar si idacto o idescritura, acorder lo que
		 * dice el xml
		 **/
		String filtroPrincipal = VariableUtils.getValueByProperty(variableName,
				FILTRO);

		if (filtroPrincipal.equals(FILTRO_ESCRITURA)) {
			if (fvariables.getIdacto() != null) {
				// Obtener idescritura a partir de idacto
				String idescritura = escrituraActoBo
						.obtenIdEscrituraPorActoId(fvariables.getIdacto());
				if (idescritura == null) {
					return null;
				}
				return this.obtenValorFilterByEscritura(variableName,
						idescritura);
			} else if (fvariables.getIdescritura() != null) {
				// Se propociono idescritura
				return this.obtenValorFilterByEscritura(variableName,
						fvariables.getIdescritura());			
			} else {
				return null;
			}

		} else if (filtroPrincipal.equals(FILTRO_ACTO)) {
			if (fvariables.getIdacto() == null) {
				return null;
			}
			String valor = this.obtenValorFilterByActo(variableName,
					fvariables.getIdacto());
			if(definicionFuncion!=null && definicionFuncion.startsWith("contenido=")){
				definicionFuncion = definicionFuncion.substring(definicionFuncion.indexOf("=")+1);
				VariableValueAssigner valueAssigner = new VariableValueAssigner();
				valor = valueAssigner.calculaValorContenido(valor, definicionFuncion);
			}			
			return valor;
		}else if(filtroPrincipal.equals(FILTRO_ACTODOCUMENTO)){
			if(fvariables.getIdActoDocumento()!=null){
				return this.obtenValorFilterByActoDocumento(variableName, fvariables.getIdActoDocumento());
			}else{
				return null;
			}
			
		} else {
			return null;
		}
	}

	/**
	 * Se obtiene el query y lo ejecuta , filtrador por acto.
	 * 
	 * @param variableName
	 * @param idacto
	 * @return
	 * @throws NotariaException 
	 */
	private String obtenValorFilterByActo(String variableName, String idacto) throws NotariaException {
		String hql = VariableUtils.obtenHQL(variableName);
		if (hql == null) {
			return null;
		}
		String hqlFilter = hql.replace("${idacto}", idacto);
		List<Object> resultados = estaticaBo
				.getSingleValFromHqlQuery(hqlFilter);
		if (resultados == null) {
			return null;
		}
		return VariableUtils.daFormatoListas(resultados);
	}

	/**
	 * Se obtiene el query y lo ejecuta , filtrador por escritura.
	 * 
	 * @param variableName
	 * @param idescritura
	 * @return
	 * @throws NotariaException 
	 */
	private String obtenValorFilterByEscritura(String variableName,
			String idescritura) throws NotariaException {
		String hql = VariableUtils.obtenHQL(variableName);
		if (hql == null) {
			return null;
		}
		String hqlFilter = hql.replace("${idescritura}", idescritura);
		List<Object> resultados = estaticaBo
				.getSingleValFromHqlQuery(hqlFilter);
		return VariableUtils.daFormatoListas(resultados);

	}
	
	/***
	 * 
	 * @param variableName Nombre de la variable de la que se recupera el hql
	 * @param idActoDocumento identificador del acto documento a reemplazar
	 * @return valor resultado de la ejecución del hql
	 * @throws NotariaException 
	 */
	private String obtenValorFilterByActoDocumento(String variableName, String idActoDocumento) throws NotariaException{
		String hql = VariableUtils.obtenHQL(variableName);
		if(hql==null){
			return null;
		}
		String hqlFilter = hql.replace("${idactodocumento}", idActoDocumento);
		List<Object> resultados = estaticaBo.getSingleValFromHqlQuery(hqlFilter);
		return VariableUtils.daFormatoListas(resultados);
	}
	
	private List<Escritura> getEscriturasLibro(Boolean status) throws NotariaException{
		LibroBo libroBo = new LibroBoImpl();
		Libro libro =libroBo.obtenUltimoLibro();
		
		EscrituraBo escBo = new EscrituraBoImpl();
		return escBo.getXnumLibroStatus((libro.getInnumlibro()-9), libro.getInnumlibro(),status);
	}

}