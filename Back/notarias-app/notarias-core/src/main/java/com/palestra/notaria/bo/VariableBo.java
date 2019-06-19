package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Variable;

public interface VariableBo extends GenericBo<Variable>{
	
	public List<Variable> findAllComplete() throws NotariaException;

	@Override
	public List<Variable> findAll() throws NotariaException;
	
	public Variable findByName(String variableName) throws NotariaException;
	
}
