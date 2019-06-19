package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Valuador;

public class ValuadorEnvio extends GenericEnvio {

	private ArrayList<Valuador> valuadorList = null;
	
	private Valuador valuador=null;

	public ArrayList<Valuador> getValuadorList() {
		return valuadorList;
	}

	public void setValuadorList(ArrayList<Valuador> valuadorList) {
		this.valuadorList = valuadorList;
	}

	public Valuador getValuador() {
		return valuador;
	}

	public void setValuador(Valuador valuador) {
		this.valuador = valuador;
	}
}
