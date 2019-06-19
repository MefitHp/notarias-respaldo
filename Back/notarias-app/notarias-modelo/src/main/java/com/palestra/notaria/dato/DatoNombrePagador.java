package com.palestra.notaria.dato;

import java.io.Serializable;

public class DatoNombrePagador implements Serializable{

	private static final long serialVersionUID = -5756722907211025508L;
	
	private String idCompareciente;
	
	/** Nombre completo del pagador compareciente **/
	private String nombreCompletoPagador;
	
	public String getIdCompareciente() {
		return idCompareciente;
	}
	
	public void setIdCompareciente(String idCompareciente) {
		this.idCompareciente = idCompareciente;
	}
	
	public String getNombreCompletoPagador() {
		return nombreCompletoPagador;
	}
	
	public void setNombreCompletoPagador(String nombreCompletoPagador) {
		this.nombreCompletoPagador = nombreCompletoPagador;
	}
}
