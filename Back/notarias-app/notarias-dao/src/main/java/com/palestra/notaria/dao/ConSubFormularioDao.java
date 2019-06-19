package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;

public interface ConSubFormularioDao extends GenericDao<ConSubFormulario, Integer>{
	
	String obtenerIdConSubFormByShortName(String shortName) throws NotariaException;

	String obtenerIdConFormularioByIdSubFormulario(String idSubFormulario)
			throws NotariaException;

	List<ConSubFormulario> findByIdConFormulario(ConFormularioPK idConFormulario) throws NotariaException;
	
	ConSubFormulario obtenerPorPosicion(Integer posicion,ConFormularioPK conformulario) throws NotariaException;


	List<ConSubFormulario> findByFormulario(ConFormularioPK formularioId)
			throws NotariaException;

	List<ConSubFormulario> obtenerConSubFormsByShortName(String shortName)
			throws NotariaException;


	
}
