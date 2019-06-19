package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.VariablesTipoBo;
import com.palestra.notaria.dao.VariablesTipoDao;
import com.palestra.notaria.dao.impl.VariablesTipoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.modelo.VariablesTipo;

public class VariablesTipoBoImpl extends GenericBoImpl<VariablesTipo> implements
		VariablesTipoBo {

	VariablesTipoDao varTipoDao;
	
	public VariablesTipoBoImpl(){
		this.varTipoDao = new VariablesTipoDaoImpl();
		super.dao = this.varTipoDao;
	}

	@Override
	public List<VariablesTipo> findByComponente(Componente componente)throws NotariaException{
		return this.varTipoDao.findByComponente(componente);
	}

	@Override
	public List<VariablesTipo> findByVariable(Variable variable)throws NotariaException{
		return this.varTipoDao.findByVariable(variable);
	}

}
