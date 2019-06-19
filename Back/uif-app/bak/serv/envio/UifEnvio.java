package com.palestra.notaria.uif.envio;

import com.palestra.notaria.uif.models.Uif;

public class UifEnvio {

	Uif uif;
	Integer page;
	String parametrobusqueda;
	String tipobusqueda;
	
	public Uif getPersona() {
		return uif;
	}
	public void setPersona(Uif uif) {
		this.uif = uif;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getParametrobusqueda() {
		return parametrobusqueda;
	}
	public void setParametrobusqueda(String parametrobusqueda) {
		this.parametrobusqueda = parametrobusqueda;
	}
	public String getTipobusqueda() {
		return tipobusqueda;
	}
	public void setTipobusqueda(String tipobusqueda) {
		this.tipobusqueda = tipobusqueda;
	}

}
