package com.palestra.notaria.dato;

import java.io.Serializable;

import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Formulario;

/**
 * Clase que contiene los objetos filtro a partir de los cuales
 * se acceden a los valores que reemplazaran las variables
 * en las plantillas.
 * 
 * @author sofia
 *
 */
public class DatoTemplate implements Serializable{

	private static final long serialVersionUID = 1223533506201094908L;
	
	private Acto acto;
	
	private Formulario formulario;
	
	public DatoTemplate(){
		
	}
	
	public Acto getActo() {
		return acto;
	}
	
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	
	public Formulario getFormulario() {
		return formulario;
	}
	
	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}
	
}
