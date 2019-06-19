package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.UbicacionVarEstatica;

public interface UbicacionVarEstaticaDao extends GenericDao<UbicacionVarEstatica, Integer> {
	
	public List<Object> obtenerValorVarEstaticas(
			UbicacionVarEstatica ubicacionVar, Object filtro) throws NotariaException;
	
	public List<Object[]> obtenerValoresFromHqlQuery(String query) throws NotariaException;
	
	public List<Object> getSingleValFromHqlQuery(String query) throws NotariaException;
	
	public List<Object[]> obtenerValoresFromSqlQuery(String query) throws NotariaException;
	
	public List<UbicacionVarEstatica> findAllComplete() throws NotariaException;

	UbicacionVarEstatica guardar(UbicacionVarEstatica ubicacionVariable)
			throws NotariaException;

	UbicacionVarEstatica actualizar(UbicacionVarEstatica ubicacionVariable)
			throws NotariaException;

	List<String> listarEntidades() throws NotariaException;

	UbicacionVarEstatica findByVariable(String idvariable)
			throws NotariaException;

	UbicacionVarEstatica findByNombre(String nombre) throws NotariaException;
	
}
