package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.UbicacionVarEstatica;
import com.palestra.notaria.modelo.Variable;

public interface UbicacionVarEstaticaBo extends GenericBo<UbicacionVarEstatica>{

	public List<Object> obtenerValorVarEstatica(UbicacionVarEstatica ubicacionVarEstatica, Object filtro) throws NotariaException;
	
	public List<Object[]> obtenerValoresFromHqlQuery(String query) throws NotariaException;
	
	public List<Object> getSingleValFromHqlQuery(String query) throws NotariaException;
	
	public List<Object[]> obtenerValoresFromSqlQuery(String query) throws NotariaException;
	
	public List<UbicacionVarEstatica> findAllComplete() throws NotariaException;

	Integer actualizar(UbicacionVarEstatica ubicacion) throws NotariaException;

	UbicacionVarEstatica obtieneVariableEstatica(String idvariable)
			throws NotariaException;

	UbicacionVarEstatica obtieneVariableEstatica(Variable variable)
			throws NotariaException;

	List<String> obtieneCamposEntidad(String entidad) throws NotariaException;

	UbicacionVarEstatica obtieneVariableEstaticaPorNombre(String nombre)
			throws NotariaException;
	
}
