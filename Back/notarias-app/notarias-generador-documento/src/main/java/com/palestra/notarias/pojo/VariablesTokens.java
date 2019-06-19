package com.palestra.notarias.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class VariablesTokens {
	
	/** Nombre de la variable en la plantilla**/
	public String originalName;
	
	/** Nombre de variable que conincide con el nombre de la BD **/
	private String name;
	
	/** Parametros que ayudan a procesar una variable
	 *  ie, indica si se transforma a texto, si se reciben parametros de busqueda **/
	private String deficionFuncion;
		
	/** Tipo de token: variable(var) / grupo(gpo)**/
	private String type;
	
	/** Grupo al que pertence la variable, si esta pertence a uno **/
	private String group;
	
	/** Ordern para mostrar los elementos del grupo **/
	private Integer order;
	
	/** Valor del token **/
	private String value;
	
	/** Longitud del nombre de la variable **/
	private Integer longitud;
	
	/** Tipo de dato de la variable **/
	private String tipoDato;
	
	/** Si se filtra por idescritura o idacto**/
	private String tipoFiltro;
	
	/** Indica si la variable dada se forazara replace de null por '_' **/
	private Boolean forceReplace;
	
	/* Indica el valor de un filtro que viene desde un formulario. 
	 * Este campo solo puede usarse con variables simples/complejas, no así de formulario.
	 * Si la variable simple/compleja contiene un solo flitro, se colocará el valor de la variable de formulario y se hará la recuperación del dato
	 * En el caso de que la variable simple/compleja contenga más de un filtro se requeriere el nombre del campo, el cual se identificara con "@" (arroba)
	 * Se debe indicar el nombre corto del formulario, identificado con ! (admiración cierre)
	 * Se debe indicar el nombre de la variable de formulario, identificado con el operador "." (punto)
	 * Ejemplos de uso: 
	 * -   	Un solo filtro en la variable -> ${var:variable_x!formulario.componente} 
	 * -	Más de un filtro en la variable -> ${var:variable_x!formulario.componente@filtro} 
	 */
	private String filtroFormulario;
	
	public String getOriginalName() {
		return originalName;
	}
	
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getDeficionFuncion() {
		return deficionFuncion;
	}
	
	public void setDeficionFuncion(String deficionFuncion) {
		this.deficionFuncion = deficionFuncion;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getOrder() {
		return order;
	}
	
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Integer getLongitud() {
		return longitud;
	}
	
	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}
	
	public String getTipoDato() {
		return tipoDato;
	}
	
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	
	public String getTipoFiltro() {
		return tipoFiltro;
	}
	
	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	
	public Boolean getForceReplace() {
		return forceReplace;
	}
	
	public void setForceReplace(Boolean forceReplace) {
		this.forceReplace = forceReplace;
	}
	
	public String getFiltroFormulario() {
		return filtroFormulario;
	}

	public void setFiltroFormulario(String filtroFormulario) {
		this.filtroFormulario = filtroFormulario;
	}

	@Override
	public String toString() {
		return new ToStringBuilder("VarialeTokens")
		        .append("originalName", this.getOriginalName())
		        .append("definicionFuncion", this.getDeficionFuncion())
		        .append("name", this.getName())
		        .append("filtroFormulario", this.getFiltroFormulario())
		        .append("value", this.getValue())
				.append("type", this.getType() )
				.append("group", this.getGroup())
				.append("order", this.getOrder())
				.append("longitud", this.getLongitud())
				.append("tipoDato", this.getTipoDato())
				.append("tipoFiltro", this.getTipoFiltro())
				.append("forceReplace",this.forceReplace)
                .toString();
	}
	
}