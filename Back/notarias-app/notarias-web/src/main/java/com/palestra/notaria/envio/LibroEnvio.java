package com.palestra.notaria.envio;

import java.io.Serializable;

import com.palestra.notaria.modelo.Libro;



public class LibroEnvio extends GenericEnvio implements Serializable{
	
	private static final long serialVersionUID = -8587786456836292988L;
	
	Libro libro = null;
	
	public Libro getLibro() {
		return libro;
	}
	
	public void setLibro(Libro libro) {
		this.libro = libro;
	}


}
