package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Variable;

public interface VariableDao extends GenericDao<Variable, Integer> {
	
	public List<Variable> findAllComplete() throws NotariaException;

	@Override
	public Variable findById(String id)throws NotariaException;
	
	@Override
	public List<Variable> findAll() throws NotariaException;

	Variable findByName(String variableName) throws NotariaException;
}
