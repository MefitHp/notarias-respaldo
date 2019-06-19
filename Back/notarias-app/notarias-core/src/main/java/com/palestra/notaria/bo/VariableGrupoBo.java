package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public interface VariableGrupoBo extends GenericBo<VariableGrupo> {

	/***
	 * Se encarga de hacer la busqueda del detalle de la variable de grupo, es decir las variables contenidas en el agrupamiento
	 * @param grupo El objeto com.palestra.notaria.modelo.Grupo del que solo se requiere el id para hacer la búsqueda en la UP
	 * @return Listado de variables agrupadas en el Grupo que se paso como parametro
	 * @throws NotariaException
	 */
	List<VariableGrupo> buscarVariablesGrupo(Grupo grupo)throws NotariaException;
	
	void eliminarPorId(String identificador)throws NotariaException;
	
}
