package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.dato.ConsultaPredefinida;

public class ConsultaPredefinidaEnvio extends GenericEnvio {

	private ConsultaPredefinida consulta;
	private List<String> valores;
	
	public ConsultaPredefinidaEnvio() {
		
	}

	public List<String> getValores() {
		return valores;
	}

	public void setValores(List<String> valores) {
		this.valores = valores;
	}

	public ConsultaPredefinida getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaPredefinida consulta) {
		this.consulta = consulta;
	}

}
