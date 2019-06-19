package com.palestra.notaria.envio;


public class OperacionTarjetonEnvio extends GenericEnvio {

	private String idActo = null;
	private String nombreActo = null;
	private String operacionNombre = null;
	private Boolean tieneDocumentos = null;
	private String[] tarjeton = null;
	
	public String getIdActo() {
		return idActo;
	}
	public void setIdActo(String idActo) {
		this.idActo = idActo;
	}
	public String getNombreActo() {
		return nombreActo;
	}
	public void setNombreActo(String nombreActo) {
		this.nombreActo = nombreActo;
	}
	public String getOperacionNombre() {
		return operacionNombre;
	}
	public void setOperacionNombre(String operacionNombre) {
		this.operacionNombre = operacionNombre;
	}
	public String[] getTarjeton() {
		return tarjeton;
	}
	public void setTarjeton(String[] tarjeton) {
		this.tarjeton = tarjeton;
	}
	public Boolean getTieneDocumentos() {
		return tieneDocumentos;
	}
	public void setTieneDocumentos(Boolean tieneDocumentos) {
		this.tieneDocumentos = tieneDocumentos;
	}
	
	
	
	
	
}
