package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.List;

import com.palestra.notaria.modelo.Incidencia;

public class IncidenciaEnvio extends GenericEnvio implements Serializable{

	
	List<Incidencia> incidencias = null;
	Incidencia incidencia = null;
	
	
	private static final long serialVersionUID = 5966124888547339235L;

	
	
	public IncidenciaEnvio() {
		// TODO Auto-generated constructor stub
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

}
