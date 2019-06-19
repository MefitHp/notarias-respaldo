package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.Expresion;

public class ExpresionEnvio extends GenericEnvio {
	
	private Expresion expresion=null;
	
	private List<Expresion> expresionList = new ArrayList<Expresion>();

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public List<Expresion> getExpresionList() {
		return expresionList;
	}

	public void setExpresionList(List<Expresion> expresionList) {
		this.expresionList = expresionList;
	}
}
