package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import pojos.pojos.Tarea;

	public class TareasEnvio extends GenericEnvio {

	
	private List<Tarea> tareaspendientes;
	private List<Tarea> tareasasignadas;
	private Long idTarea;
	private Boolean pagado;
	private Boolean paso;
	private Boolean prorroga;
	

	public List<Tarea> getTareaspendientes() {
		return tareaspendientes;
	}


	public void setTareaspendientes(List<Tarea> tareaspendientes) {
		this.tareaspendientes = tareaspendientes;
	}
	
	public void setTareasasignadas(List<Tarea> tareasasignadas) {
		this.tareasasignadas = tareasasignadas;
	}
	public List<Tarea> getTareasasignadas() {
		return tareasasignadas;
	}


	public Long getIdTarea() {
		return idTarea;
	}


	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}


	public TareasEnvio() {
		// TODO Auto-generated constructor stub
	}



	public Boolean getPagado() {
		return pagado;
	}


	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}


	


	public Boolean getPaso() {
		return paso;
	}


	public void setPaso(Boolean paso) {
		this.paso = paso;
	}


	public Boolean getProrroga() {
		return prorroga;
	}


	public void setProrroga(Boolean prorroga) {
		this.prorroga = prorroga;
	}

}
