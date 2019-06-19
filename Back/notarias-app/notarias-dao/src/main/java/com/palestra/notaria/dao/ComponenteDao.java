package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.dato.DatoVariableFormulario;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConSubFormulario;

public interface ComponenteDao extends GenericDao<Componente, Integer>{
	
	Componente buscarPorNombreCortoConForm(String nombreCorto, String nombreComponente) throws NotariaException;

	List<Componente> listarPorSuboperacion(String idsuboperacion) throws NotariaException;
	
	List<DatoVariableFormulario> listarVariableComponente() throws NotariaException;

	List<DatoVariableFormulario> listarComponentesSubformulario()throws NotariaException;

	List<Componente> obtenerComponenteXSubformulario(ConSubFormulario objeto) throws NotariaException;
	
}
