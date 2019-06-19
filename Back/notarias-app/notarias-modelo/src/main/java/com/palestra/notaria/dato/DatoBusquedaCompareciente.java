package com.palestra.notaria.dato;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase wraper para los resultados de busqueda de personas que son comparecientes
 * 
 * @author sofia
 *
 */
public class DatoBusquedaCompareciente implements Serializable{

	private static final long serialVersionUID = 1802799390005179804L;
	
	private String idpersona;
	
	private String dsnombre;
	
	private String dsapellidopat;
	
	private String dsapellidomat;
	
	private String dsnombrecompleto;
	
	private String dsrfc;
	
	public DatoBusquedaCompareciente(){
		
	}

	public String getIdpersona() {
		return idpersona;
	}
	
	public void setIdpersona(String idpersona) {
		this.idpersona = idpersona;
	}
	
	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDsapellidopat() {
		return dsapellidopat;
	}

	public void setDsapellidopat(String dsapellidopat) {
		this.dsapellidopat = dsapellidopat;
	}

	public String getDsapellidomat() {
		return dsapellidomat;
	}

	public void setDsapellidomat(String dsapellidomat) {
		this.dsapellidomat = dsapellidomat;
	}

	public String getDsnombrecompleto() {
		return dsnombrecompleto;
	}

	public void setDsnombrecompleto(String dsnombrecompleto) {
		this.dsnombrecompleto = dsnombrecompleto;
	}

	public String getDsrfc() {
		return dsrfc;
	}

	public void setDsrfc(String dsrfc) {
		this.dsrfc = dsrfc;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("idpersona", this.getIdpersona())
		        .append("dsnombre", this.getDsnombre())
		        .append("dsapellidopat", this.getDsapellidopat())
		        .append("dsapellidomat", this.getDsapellidomat())
		        .append("dsnombrecompleto", this.getDsnombrecompleto())
		        .append("dsrfc", this.getDsrfc())
                .toString();
	}

}
