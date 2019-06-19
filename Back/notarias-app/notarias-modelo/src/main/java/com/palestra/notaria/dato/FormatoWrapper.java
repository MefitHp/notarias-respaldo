package com.palestra.notaria.dato;

import java.io.Serializable;

import com.palestra.notaria.modelo.ElementoCatalogo;

public class FormatoWrapper implements Serializable{

	private static final long serialVersionUID = 5203090640850034031L;
	
	private String iddocumento;
	
	private String dstitulo;
	
	private String dsdescripcion;
	
	private String isgestionado;
	
	private Integer inorden;
	
	private ElementoCatalogo tipodoc;

	public Integer getInorden() {
		return inorden;
	}
	
	public void setInorden(Integer inorden) {
		this.inorden = inorden;
	}
	public String getIsgestionado() {
		return isgestionado;
	}
	public void setIsgestionado(String isgestionado) {
		this.isgestionado = isgestionado;
	}
	public String getIddocumento() {
		return iddocumento;
	}

	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	public String getDstitulo() {
		return dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}

	public String getDsdescripcion() {
		return dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}
	public ElementoCatalogo getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(ElementoCatalogo tipodoc) {
		this.tipodoc = tipodoc;
	}
}
