package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteConyuge;

public interface ComparecienteConyugeBo extends GenericBo<ComparecienteConyuge> {

	public ComparecienteConyuge getRelacionInversa(ComparecienteConyuge comp) throws NotariaException;

	@Override
	public ComparecienteConyuge findById(String id) throws NotariaException;

	public ComparecienteConyuge findComparecienteConyuge(String idcompareciente)throws NotariaException;
}
