package com.palestra.notaria.dato;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Escritura;

public class DatoEscritura implements java.io.Serializable{

	private static final long serialVersionUID = -8491546306472655694L;
	private Escritura escritura;
	private ArrayList<DatoActoMultiSelect> actos = null;
	
	public Escritura getEscritura() {
		return escritura;
	}
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	public ArrayList<DatoActoMultiSelect> getActos() {
		return actos;
	}
	public void setActos(ArrayList<DatoActoMultiSelect> actos) {
		this.actos = actos;
	}
		
}
