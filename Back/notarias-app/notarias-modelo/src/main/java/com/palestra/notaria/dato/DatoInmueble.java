package com.palestra.notaria.dato;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DatoInmueble implements java.io.Serializable{

	private static final long serialVersionUID = 2376622914563656443L;
	private String idinmueble;
	/** Precio del inmueble**/
	//TODO: por definir
	private Double valcatastral;
	private String dsdescripcion;
	private String idacto;
	private String actoNombre;
	private String actoDescripcion;
	
	public String getIdinmueble() {
		return idinmueble;
	}
	public void setIdinmueble(String idinmueble) {
		this.idinmueble = idinmueble;
	}
	public Double getValcatastral() {
		return valcatastral;
	}
	public void setValcatastral(Double valcatastral) {
		this.valcatastral = valcatastral;
	}
	
	public String getDsdescripcion() {
		return dsdescripcion;
	}
	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public String getActoNombre() {
		return actoNombre;
	}
	public void setActoNombre(String actoNombre) {
		this.actoNombre = actoNombre;
	}
	public String getActoDescripcion() {
		return actoDescripcion;
	}
	public void setActoDescripcion(String actoDescripcion) {
		this.actoDescripcion = actoDescripcion;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("idinmueble", this.getIdinmueble())
		        .append("valcatastral", this.getValcatastral())
		        .append("dsdescripcion", this.getDsdescripcion())
		        .append("idacto", this.getIdacto())
		        .append("actoNombre", this.getActoNombre())
		        .append("actoDescripcion", this.getActoDescripcion())
                .toString();
	}
}
