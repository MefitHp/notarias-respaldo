package com.palestra.notaria.util;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.CodigoError;

public class FuncionPojo {

	private String nombre;
	private List<Parametro> params;
	public static final String IDENTIFICADOR_FUNCION = "f:";
	private List<CodigoError> errores = new ArrayList<CodigoError>();
	

	public FuncionPojo(String nombre) {
		setNombre(nombre);
		params = new ArrayList<>();
	}
	
	public List<CodigoError> getErrores() {
		return errores;
	}
	public void setErrores(List<CodigoError> errores) {
		this.errores = errores;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Parametro> getParametros() {
		return this.params;
	}

	void setParametros(List<Parametro> params) {
		this.params = params;
	}

	public void agregaParametro(String param) {
		params.add(new Parametro(param));
	}

	public void imprimeValor() {
		System.out.printf("funcion %s%n", this.getNombre());
		for (Parametro p : params) {
			System.out.printf("\tparametro %s%n", p.getParam());
		}
		System.out.println("<===");
	}
}
