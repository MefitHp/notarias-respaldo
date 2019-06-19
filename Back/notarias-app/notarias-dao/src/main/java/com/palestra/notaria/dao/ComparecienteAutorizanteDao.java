package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteAutorizante;

public interface ComparecienteAutorizanteDao extends
		GenericDao<ComparecienteAutorizante, Integer> {

	public ComparecienteAutorizante findById(Object id) throws NotariaException;

	public List<ComparecienteAutorizante> findByCompareciente(String idcompareciente)throws NotariaException;
}
