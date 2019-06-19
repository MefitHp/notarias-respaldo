package com.palestra.notarias.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 * Clase que tiene las propiedades adicionales de las 
 * variables de formularios dinamicos. De la forma
 * 
 *    ${frm:nombreCortoConForm[nombreVariable]}
 * 
 * @author sofia
 *
 */
public class FormTokens extends VariablesTokens {
	
	/** id de configuracion de formulario **/
	String idConFormulario;
	
	/** version que es parte de la llave compuesta **/
	Integer conFormularioVersion;
	
	/** id componente **/
	String idComponente;
	
	/** nombre corto de configuracion del formulario **/
	String nombreCortoConForm;
	
	/** contenido de la variable**/
	String contenido;
	
	public String getIdComponente() {
		return idComponente;
	}
	
	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}
	
	public String getIdConFormulario() {
		return idConFormulario;
	}
	
	public void setIdConFormulario(String idConFormulario) {
		this.idConFormulario = idConFormulario;
	}
	
	public Integer getConFormularioVersion() {
		return conFormularioVersion;
	}
	
	public void setConFormularioVersion(Integer conFormularioVersion) {
		this.conFormularioVersion = conFormularioVersion;
	}
	
	public String getNombreCortoConForm() {
		return nombreCortoConForm;
	}
	
	public void setNombreCortoConForm(String nombreCortoConForm) {
		this.nombreCortoConForm = nombreCortoConForm;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder("{")
		        .append("originalName: ", this.getOriginalName())
		        .append("name: ", this.getName())
		        .append("value: ", this.getValue())
				.append("type: ", this.getType() )
				.append("longitud; ", this.getLongitud())
				.append("idConFormulario: ", this.getIdConFormulario())
				.append("conFormularioVersion: ", this.getConFormularioVersion())
				.append("idComponente: " , this.getIdComponente())
				.append("nombreCortoConForm: ", this.getNombreCortoConForm())
				.append("contenido: ", this.getContenido())
				.append("}")
                .toString();
	}
	
}
