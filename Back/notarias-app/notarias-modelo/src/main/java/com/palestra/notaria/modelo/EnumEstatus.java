package com.palestra.notaria.modelo;

import java.io.Serializable;

public enum EnumEstatus implements Serializable {
	
	CERRADO("Cerrado"),
	CANCELADO("Cancelado"),
	ABIERTO("Abierto");
	
	String estatus;
	
	public String getEstatus() {
		return estatus;
	}
	
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	EnumEstatus(String estatus){
		this.estatus = estatus;
	}

}
