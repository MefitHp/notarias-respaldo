package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.Formulario;

public interface FormularioDao extends GenericDao<Formulario, Integer>{
	
	List<Formulario> findByActoId(String id) throws NotariaException;
	
	List<Formulario> buscarFormulariosPorActo(String idacto) throws NotariaException;
	
	boolean guardarValoresFormulario(Formulario formulario) throws NotariaException;
	
	boolean actualizarValoresFormulario(Formulario formulario) throws NotariaException;

	Formulario buscarFormulariosPorActo(String idacto,
			ConFormularioPK idConFormulario) throws NotariaException;
	
	List<Formulario> findByConFormulario(ConFormularioPK pk) throws NotariaException;

	Formulario buscarFormulariosPorActoYnombrecorto(String idacto, String nombrecorto)throws NotariaException;

}
