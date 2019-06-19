package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.List;

import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public class UsuarioGrupoTrabajoEnvio extends GenericEnvio implements Serializable {

	
	private static final long serialVersionUID = -8587786456836292988L;

	UsuarioGrupoTrabajo usuariogrupotrabajo = null;
	List<UsuarioGrupoTrabajo> gruposdetrabajo = null;
	
	public UsuarioGrupoTrabajo getUsuariogrupotrabajo() {
		return usuariogrupotrabajo;
	}
	public void setUsuariogrupotrabajo(UsuarioGrupoTrabajo usuariogrupotrabajo) {
		this.usuariogrupotrabajo = usuariogrupotrabajo;
	}
	public List<UsuarioGrupoTrabajo> getGruposdetrabajo() {
		return gruposdetrabajo;
	}
	public void setGruposdetrabajo(List<UsuarioGrupoTrabajo> gruposdetrabajo) {
		this.gruposdetrabajo = gruposdetrabajo;
	}
		
	
}
