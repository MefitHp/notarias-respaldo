package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public interface VariableGrupoDao extends GenericDao<VariableGrupo, Integer> {

	/***
	 * Obtencion de las variables asociadas a un grupo en la persistencia de VariableGrupo
	 * @param grupo identificador del grupo persistido en la unidad
	 * @return List<VariableGrupo> 
	 * @throws NotariaException
	 */
	List<VariableGrupo> obtenerVariablesEnGrupo(Grupo grupo)
			throws NotariaException;
	
	void eliminarPorId(String identificador) throws NotariaException;

}
