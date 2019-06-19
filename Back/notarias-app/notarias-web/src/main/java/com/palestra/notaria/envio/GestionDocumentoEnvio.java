package com.palestra.notaria.envio;

public class GestionDocumentoEnvio extends GenericEnvio {
	
	private String id=null;
	
	private String tipo=null;
	
	 public String getTipo() {
		return tipo;
	}
	 public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	 public String getId() {
		return id;
	}
	 public void setId(String id) {
		this.id = id;
	}

}
