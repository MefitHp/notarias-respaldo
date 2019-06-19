package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Funcion;
import com.palestra.notaria.modelo.FuncionParametro;

public interface FuncionDao extends GenericDao<Funcion, Integer> {
	List<FuncionParametro> listaParametros(String identificador)throws NotariaException;
	List<FuncionParametro> listaParametros(Funcion funcion)throws NotariaException;
	void eliminaParametros(String identificador)throws NotariaException;
	void eliminaParametros(Funcion funcion)throws NotariaException;
	Integer actualizaParametros(Funcion funcion)throws NotariaException;
	List<Funcion> listaFunciones() throws NotariaException;
	Funcion findByNombre(String nombreFuncion) throws NotariaException;
}
