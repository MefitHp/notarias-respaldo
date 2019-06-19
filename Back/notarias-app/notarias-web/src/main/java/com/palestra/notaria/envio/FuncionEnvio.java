package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.Funcion;
import com.palestra.notaria.modelo.FuncionParametro;

public class FuncionEnvio extends GenericEnvio {

	private Funcion funcion;
	private List<Funcion> funciones = new ArrayList<>();
	private List<FuncionParametro> parametros = new ArrayList<>();	
	
	public FuncionEnvio() {}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}

	public List<FuncionParametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<FuncionParametro> parametros) {
		this.parametros = parametros;
	}

}
