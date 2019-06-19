package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.List;

import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;

public class GrupoTrabajoEnvio extends GenericEnvio implements Serializable {

	
	private static final long serialVersionUID = -8587786456836292988L;

	GrupoTrabajo grupotrabajo = null;
	@SuppressWarnings("rawtypes")
	List grupos = null;
	

	public List getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoTrabajo> list) {
		this.grupos = (List) list;
	}

	public GrupoTrabajo getGrupotrabajo() {
		return grupotrabajo;
	}

	public void setGrupotrabajo(GrupoTrabajo grupotrabajo) {
		this.grupotrabajo = grupotrabajo;
	}
	
	
	
}
