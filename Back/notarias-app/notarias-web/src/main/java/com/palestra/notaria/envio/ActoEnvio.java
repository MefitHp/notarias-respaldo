package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Acto;

public class ActoEnvio extends GenericEnvio{

	private Acto acto;
	
	private ArrayList<Acto> actoList=null;
	
	public ArrayList<Acto> getActoList() {
		return actoList;
	}
	
	public void setActoList(ArrayList<Acto> actoList) {
		this.actoList = actoList;
	}

	public Acto getActo() {
		return acto;
	}

	public void setActo(Acto acto) {
		this.acto = acto;
	}
	
	
}
