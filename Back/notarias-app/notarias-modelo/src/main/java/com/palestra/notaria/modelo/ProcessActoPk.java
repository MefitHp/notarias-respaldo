package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProcessActoPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280653115729521932L;

	private String idacto;
	private Long idproceso;

	public String getActo() {
		return idacto;
	}
	public void setActo(String acto) {
		this.idacto = acto;
	}
	public Long getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(Long idproceso) {
		this.idproceso = idproceso;
	}
	
	
	
}


