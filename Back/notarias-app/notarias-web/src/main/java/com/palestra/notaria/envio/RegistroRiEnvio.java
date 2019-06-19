package com.palestra.notaria.envio;

import com.palestra.notaria.modelo.Compareciente;

public class RegistroRiEnvio extends GenericEnvio{
	
	private Compareciente compareciente = null;
	
	private String rutaTempArchivoRi = null;
	
	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}
	public String getRutaTempArchivoRi() {
		return rutaTempArchivoRi;
	}
	public void setRutaTempArchivoRi(String rutaTempArchivoRi) {
		this.rutaTempArchivoRi = rutaTempArchivoRi;
	}
}
