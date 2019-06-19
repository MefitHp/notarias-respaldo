package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.dato.DatoInmueble;
import com.palestra.notaria.modelo.Inmueble;

public class InmuebleEnvio extends GenericEnvio implements Serializable{

	private static final long serialVersionUID = -8931223925935360761L;
	
	private Inmueble inmueble = null;
	private ArrayList<DatoInmueble> listaInmuebles = null;
	
	public Inmueble getInmueble() {
		return inmueble;
	}
	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}
	public ArrayList<DatoInmueble> getListaInmuebles() {
		return listaInmuebles;
	}
	public void setListaInmuebles(ArrayList<DatoInmueble> listaInmuebles) {
		this.listaInmuebles = listaInmuebles;
	}
}
