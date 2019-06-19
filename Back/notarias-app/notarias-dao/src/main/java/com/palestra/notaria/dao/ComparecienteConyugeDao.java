package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteConyuge;

public interface ComparecienteConyugeDao extends GenericDao<ComparecienteConyuge, Integer> {

	public ComparecienteConyuge getRelacionInversa(ComparecienteConyuge comp)throws NotariaException;

	@Override
	public ComparecienteConyuge findById(String id) throws NotariaException;

	public ComparecienteConyuge findComparecienteConyuge(String idcompareciente) throws NotariaException;
}
