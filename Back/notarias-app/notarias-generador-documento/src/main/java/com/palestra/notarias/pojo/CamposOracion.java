package com.palestra.notarias.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase que contiene los campos que formaran la oracion para reemplazar la variable
 * 
 * @author sofia
 *
 */
public class CamposOracion {
	
	/** indice del campo debe ser el mismo orden en del query select **/
	private Integer index;
	
	/** valor a reemplazar con el texto deseado **/
	private String campoDefinicion;
	
	/** nombre del tag en querys_variable.xml **/
	private String campoName;
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public String getCampoDefinicion() {
		return campoDefinicion;
	}
	
	public void setCampoDefinicion(String campoDefinicion) {
		this.campoDefinicion = campoDefinicion;
	}
	
	public String getCampoName() {
		return campoName;
	}
	
	public void setCampoName(String campoName) {
		this.campoName = campoName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("index", this.getIndex())
				.append("campoDefinicion", this.getCampoDefinicion())
				.append("campoName", this.getCampoName())
				.toString();
	}
}
