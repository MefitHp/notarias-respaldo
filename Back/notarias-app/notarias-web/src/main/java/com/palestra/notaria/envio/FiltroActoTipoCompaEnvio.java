package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.modelo.FiltroActoCompareciente;


public class FiltroActoTipoCompaEnvio extends GenericEnvio {
		
	private FiltroActoCompareciente filtroActoCompareciente = null;
	private List<FiltroActoCompareciente> listafiltros = null;
	
	
	public List<FiltroActoCompareciente> getListafiltros() {
		return listafiltros;
	}
	public void setListafiltros(List<FiltroActoCompareciente> listafiltros) {
		this.listafiltros = listafiltros;
	}
	public FiltroActoCompareciente getFiltroActoCompareciente() {
		return filtroActoCompareciente;
	}
	public void setFiltroActoCompareciente(FiltroActoCompareciente filtroActoCompareciente) {
		this.filtroActoCompareciente = filtroActoCompareciente;
	}
	
	
	
		
}
