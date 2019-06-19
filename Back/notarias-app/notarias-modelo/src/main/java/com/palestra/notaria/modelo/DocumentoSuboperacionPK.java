package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DocumentoSuboperacionPK implements Serializable {

	private static final long serialVersionUID = -1298241792401651163L;

	private String iddocumento;
	
	private String idsuboperacion;
	
	private String idlocalidad;
	
	private String idformatopdf;
	
	public String getIdformatopdf() {
		return idformatopdf;
	}
	public void setIdformatopdf(String idformatopdf) {
		this.idformatopdf = idformatopdf;
	}
	public String getIddocumento() {
		return iddocumento;
	}

	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	public String getIdsuboperacion() {
		return idsuboperacion;
	}

	public void setIdsuboperacion(String idsuboperacion) {
		this.idsuboperacion = idsuboperacion;
	}

	public String getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(String idlocalidad) {
		this.idlocalidad = idlocalidad;
	}
	
}
