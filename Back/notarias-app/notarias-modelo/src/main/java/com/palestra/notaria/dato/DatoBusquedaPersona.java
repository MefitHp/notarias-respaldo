package com.palestra.notaria.dato;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase wraper para los resultados de busqueda de personas. 
 * 
 * @author sofia
 *
 */
public class DatoBusquedaPersona implements Serializable {

	private static final long serialVersionUID = -3247557824106057622L;

	/** Nombre persona **/
	private String dsnombre;
	
	/** Apellido paterno persona **/ 
	private String dsapellidopat;
	
	/** Apellido materno persona **/
	private String dsapellidomat;

	private String idpersona;
	
	private String idcompareciente;
	
	/** nombre del tipo tipo compareciente**/
	private String tipocompareciente;
	
	private String idtramite;
	
	private String etapa;
	
	private String idacto;
	
	private String idexpediente;
	
	private String numexpediente;
	
	private String idabogado;
	
	private String rfc;

	public DatoBusquedaPersona(){
		
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

	public String getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(String idpersona) {
		this.idpersona = idpersona;
	}

	public String getIdcompareciente() {
		return idcompareciente;
	}

	public void setIdcompareciente(String idcompareciente) {
		this.idcompareciente = idcompareciente;
	}

	public String getIdtramite() {
		return idtramite;
	}

	public void setIdtramite(String idtramite) {
		this.idtramite = idtramite;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getIdacto() {
		return idacto;
	}

	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}

	public String getIdexpediente() {
		return idexpediente;
	}

	public void setIdexpediente(String idexpediente) {
		this.idexpediente = idexpediente;
	}

	public String getIdabogado() {
		return idabogado;
	}

	public void setIdabogado(String idabogado) {
		this.idabogado = idabogado;
	}
	
	public String getTipocompareciente() {
		return tipocompareciente;
	}
	
	public void setTipocompareciente(String tipocompareciente) {
		this.tipocompareciente = tipocompareciente;
	}
	
	public String getNumexpediente() {
		return numexpediente;
	}
	
	public void setNumexpediente(String numexpediente) {
		this.numexpediente = numexpediente;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("dsnombre", this.getDsnombre())
		        .append("dsapellidopat", this.getDsapellidopat())
		        .append("dsapellidomat", this.getDsapellidomat())
		        .append("idcompareciente", this.getIdcompareciente())
		        .append("tipocompareciente", this.getTipocompareciente())
		        .append("idtramite", this.getIdtramite())
		        .append("etapa", this.getEtapa())
		        .append("idacto", this.getIdacto())
		        .append("idexpediente",this.getIdexpediente())
		        .append("numexpediente", this.getNumexpediente())
		        .append("idabogado", this.getIdabogado())
		        .append("rfc", this.getRfc())
                .toString();
	}

}
