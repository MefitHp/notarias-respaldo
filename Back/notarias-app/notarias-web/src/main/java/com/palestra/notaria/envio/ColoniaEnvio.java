package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.Colonia;

public class ColoniaEnvio extends GenericEnvio implements Serializable{

	private static final long serialVersionUID = 7253244558812530629L;
	
	private Colonia colonia = null;
	private ArrayList<Colonia> listaColonias = null;
	
	public Colonia getColonia() {
		return colonia;
	}
	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}
	public ArrayList<Colonia> getListaColonias() {
		return listaColonias;
	}
	public void setListaColonias(ArrayList<Colonia> listaColonias) {
		this.listaColonias = listaColonias;
	}

}
