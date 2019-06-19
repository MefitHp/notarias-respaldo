package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.Estado;

public class EstadoEnvio extends GenericEnvio implements Serializable{
	
	private static final long serialVersionUID = -5083330308168555060L;
	Estado estado = null;
	ArrayList<Estado> listaEstados = null;
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public ArrayList<Estado> getListaEstados() {
		return listaEstados;
	}
	public void setListaEstados(ArrayList<Estado> listaEstados) {
		this.listaEstados = listaEstados;
	}
}
