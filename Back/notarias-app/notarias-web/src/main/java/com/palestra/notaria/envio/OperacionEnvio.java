package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Tramite;

public class OperacionEnvio extends GenericEnvio {

	Operacion operacion=null;
	
	Suboperacion suboperacion=null;
	
	ArrayList<Operacion> operacionList=null;
	
	ArrayList<Suboperacion> suboperacionList=null;
	
	private Tramite tramite = null;
	
	public Tramite getTramite() {
		return tramite;
	}
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public Suboperacion getSuboperacion() {
		return suboperacion;
	}

	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}

	public ArrayList<Operacion> getOperacionList() {
		return operacionList;
	}

	public void setOperacionList(ArrayList<Operacion> operacionList) {
		this.operacionList = operacionList;
	}

	public ArrayList<Suboperacion> getSuboperacionList() {
		return suboperacionList;
	}

	public void setSuboperacionList(ArrayList<Suboperacion> suboperacionList) {
		this.suboperacionList = suboperacionList;
	}
	
}
