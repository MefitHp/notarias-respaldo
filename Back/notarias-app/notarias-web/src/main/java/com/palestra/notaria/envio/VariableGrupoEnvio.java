package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public class VariableGrupoEnvio extends GenericEnvio {

	private Grupo grupo;
	//VariableGrupo varGrupo = null;
	
	ArrayList<VariableGrupo> varGrupoList=null;
	
	/*
	public VariableGrupo getVarGrupo() {
		return varGrupo;
	}
	public void setVarGrupo(VariableGrupo grupo) {
		this.varGrupo = grupo;
	}
	*/
	public ArrayList<VariableGrupo> getVarGrupoList() {
		return varGrupoList;
	}
	
	public void setVarGrupoList(ArrayList<VariableGrupo> varGrupoList) {
		this.varGrupoList = varGrupoList;
	}
	
	public Grupo getGrupo(){
		return this.grupo;
	}
	
	public void setGrupo(Grupo grupo){
		this.grupo = grupo;
	}
}
