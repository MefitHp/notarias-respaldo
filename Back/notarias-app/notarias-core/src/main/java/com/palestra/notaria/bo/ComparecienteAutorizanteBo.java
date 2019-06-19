package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteAutorizante;

public interface ComparecienteAutorizanteBo extends
		GenericBo<ComparecienteAutorizante> {

	public ComparecienteAutorizante findById(Object id) throws NotariaException;

	public List<ComparecienteAutorizante> findByCompareciente(String idcompareciente)throws NotariaException;
}
