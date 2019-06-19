package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Notaria;

public class NotariaEnvio extends GenericEnvio {

	private ArrayList<Notaria> notariaList=null;
	
	private Notaria notaria=null;
	
	public Notaria getNotaria() {
		return notaria;
	}
	
	public void setNotaria(Notaria notaria) {
		this.notaria = notaria;
	}
	
	public void setNotariaList(ArrayList<Notaria> notariaList) {
		this.notariaList = notariaList;
	}
	
	public ArrayList<Notaria> getNotariaList() {
		return notariaList;
	}
}
