package com.palestra.notaria.modelo;

public enum EnumAcuseReciboEscritura {

	MENSAJERIA("Mensajeria"),
	NOTARIA("Notaria");
	
	String tipo;
	
	private EnumAcuseReciboEscritura(String tipo) {
		this.tipo = tipo;
	}
}
