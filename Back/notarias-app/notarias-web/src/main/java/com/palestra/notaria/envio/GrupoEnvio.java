package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.modelo.Grupo;

public class GrupoEnvio extends GenericEnvio {

	Grupo grupo=null;
	
	List<Grupo> grupoList=null;

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Grupo> getGrupoList() {
		return grupoList;
	}

	public void setGrupoList(List<Grupo> grupoList) {
		this.grupoList = grupoList;
	}
	
}
