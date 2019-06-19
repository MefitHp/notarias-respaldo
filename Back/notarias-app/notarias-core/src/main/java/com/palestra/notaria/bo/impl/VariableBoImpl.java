package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.VariableBo;
import com.palestra.notaria.dao.VariableDao;
import com.palestra.notaria.dao.impl.VariableDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Variable;

public class VariableBoImpl extends GenericBoImpl<Variable> implements VariableBo{
	
	private VariableDao variableDao;
	
	public VariableBoImpl() {
		this.variableDao = new VariableDaoImpl();
		super.dao = this.variableDao;
	}

	@Override
	public List<Variable> findAllComplete() throws NotariaException {
		return this.variableDao.findAllComplete();
	}
	
	@Override
	public List<Variable> findAll() throws NotariaException {
		return this.variableDao.findAll();
	}
	
	@Override
	public Variable findByName(String variableName) throws NotariaException{
		return this.variableDao.findByName(variableName);
	}

}
