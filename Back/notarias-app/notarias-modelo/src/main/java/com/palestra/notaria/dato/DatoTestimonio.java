package com.palestra.notaria.dato;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.RelEtapaTestimonio;
import com.palestra.notaria.modelo.Testimonio;

public class DatoTestimonio implements Serializable{

	private static final long serialVersionUID = -2576660743311379050L;
	private Testimonio testimonio = null;
	private ArrayList<RelEtapaTestimonio> listaEtapas = null;
	
	public Testimonio getTestimonio() {
		return testimonio;
	}
	public void setTestimonio(Testimonio testimonio) {
		this.testimonio = testimonio;
	}
	public ArrayList<RelEtapaTestimonio> getListaEtapas() {
		return listaEtapas;
	}
	public void setListaEtapas(ArrayList<RelEtapaTestimonio> listaEtapas) {
		this.listaEtapas = listaEtapas;
	}
	
}
