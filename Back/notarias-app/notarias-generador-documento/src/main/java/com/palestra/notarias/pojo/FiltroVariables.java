package com.palestra.notarias.pojo;

/**
 * Clase holder que contiene los posibles tipo de filtros
 * y sus valores.
 * 
 * Algunas variables se filtral por escritura como
 * escritura_folio, otras por acto como inmueble_foja.
 * Esta clase se utiliza para saber por que filtrar.
 * 
 * @author sofia
 *
 */
public class FiltroVariables {
	
	/** Filtrar por idacto **/
	private String idacto;
	
	/** Filtrar por idescritura **/
	private String idescritura;
	
	/** Filtrar por idactoDocumento **/
	private String idActoDocumento;
	
	public String getIdacto() {
		return idacto;
	}
	
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	
	public String getIdescritura() {
		return idescritura;
	}
	
	public void setIdescritura(String idescritura) {
		this.idescritura = idescritura;
	}

	public String getIdActoDocumento(){
		return this.idActoDocumento;
	}
	
	public void setIdActoDocumento(String idActoDocumento){
		this.idActoDocumento = idActoDocumento;
	}

}
