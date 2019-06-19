package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.Localidad;

public class LocalidadEnvio extends GenericEnvio implements Serializable {

	private static final long serialVersionUID = 6222673765021677832L;
	private Localidad localidad = null;
	private ArrayList<Localidad> listaLocalidades = null;
	
	public Localidad getLocalidad() {
		return localidad;
	}
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	public ArrayList<Localidad> getListaLocalidades() {
		return listaLocalidades;
	}
	public void setListaLocalidades(ArrayList<Localidad> listaLocalidades) {
		this.listaLocalidades = listaLocalidades;
	}
}
