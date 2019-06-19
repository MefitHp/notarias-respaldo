package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Gestor;

public class GestorEnvio extends GenericEnvio {

	private Gestor gestor=null;
	
	private ArrayList<Gestor> gestorList=null;

	public Gestor getGestor() {
		return gestor;
	}

	public void setGestor(Gestor gestor) {
		this.gestor = gestor;
	}

	public ArrayList<Gestor> getGestorList() {
		return gestorList;
	}

	public void setGestorList(ArrayList<Gestor> gestorList) {
		this.gestorList = gestorList;
	}
	
	
}
