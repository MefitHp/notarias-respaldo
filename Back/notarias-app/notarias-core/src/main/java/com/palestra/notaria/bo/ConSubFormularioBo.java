package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;

public interface ConSubFormularioBo extends GenericBo<ConSubFormulario>{
	
	String obtenerIdConSubFormByShortName(String shortName) throws NotariaException;

	String obtenerIdConFormularioByIdSubFormulario(String idSubFormulario)
			throws NotariaException;
	
	ConSubFormulario obtenerPorPosicion(Integer posicion,ConFormularioPK conformulario) throws NotariaException;
	
	void borrar(ConSubFormulario objeto) throws NotariaException;

	List<ConSubFormulario> findByFormulario(ConFormularioPK formulario) throws NotariaException;


	List<ConSubFormulario> obtenerConSubFormsByShortName(String shortName)
			throws NotariaException;

}
