package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.Municipio;

public class MunicipioEnvio extends GenericEnvio implements Serializable{

	private static final long serialVersionUID = 1121224952921074430L;
	Municipio municipio = null;
	ArrayList<Municipio> listaMunicipios = null;
	
	public Municipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	public ArrayList<Municipio> getListaMunicipios() {
		return listaMunicipios;
	}
	public void setListaMunicipios(ArrayList<Municipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

}
