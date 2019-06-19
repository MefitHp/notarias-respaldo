package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Funcion;
import com.palestra.notaria.modelo.FuncionParametro;
import com.palestra.notaria.util.FuncionPojo;

public interface FuncionBo extends GenericBo<Funcion> {

	List<Funcion> listarTodas()throws NotariaException;
	List<FuncionParametro> listarParametros(Funcion funcion) throws NotariaException;
	Integer actualizarParametros(Funcion funcion) throws NotariaException;
	void eliminaParametros(Funcion funcion) throws NotariaException;
	Funcion obtenerFuncion(String identificador) throws NotariaException;
	Funcion obtenerPorNombre(String nombreFuncion) throws NotariaException;
	void eliminarFuncion(Funcion funcion) throws NotariaException;
	String analizaExpresion(String expresion, List<FuncionPojo> funciones);
	FuncionPojo analizaFuncion(FuncionPojo funcion) throws NotariaException;
}
