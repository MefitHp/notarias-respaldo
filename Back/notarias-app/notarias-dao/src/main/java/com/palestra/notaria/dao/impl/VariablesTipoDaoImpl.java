package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.VariablesTipoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.modelo.VariablesTipo;

public class VariablesTipoDaoImpl extends
		GenericDaoImpl<VariablesTipo, Integer> implements VariablesTipoDao {

	public VariablesTipoDaoImpl(){
		super(VariablesTipo.class);
	}
	
	@Override
	public List<VariablesTipo> findByVariable(Variable variable) throws NotariaException{
		return executeQuery("SELECT v FROM VariablesTipo v WHERE v.variable.idvariable = ?1", variable.getIdvariable());
	}
	
	@Override
	public List<VariablesTipo> findByComponente(Componente componente) throws NotariaException{
		return executeQuery("SELECT v FROM VariablesTipo v WHERE v.componente.idcomponente = ?1", componente.getIdcomponente());
	}
}
