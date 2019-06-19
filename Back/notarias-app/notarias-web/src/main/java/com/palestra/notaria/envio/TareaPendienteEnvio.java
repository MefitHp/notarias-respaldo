package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.TareaPendiente;

public class TareaPendienteEnvio extends GenericEnvio{

	private TareaPendiente tareaPendiente;
	private ArrayList<TareaPendiente> lista = null;
	
	public TareaPendiente getTareaPendiente() {
		return tareaPendiente;
	}
	public void setTareaPendiente(TareaPendiente tareaPendiente) {
		this.tareaPendiente = tareaPendiente;
	}
	public ArrayList<TareaPendiente> getLista() {
		return lista;
	}
	public void setLista(ArrayList<TareaPendiente> lista) {
		this.lista = lista;
	}
	
	
}
