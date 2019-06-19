package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.modelo.VariablesTipo;

public interface VariablesTipoBo extends GenericBo<VariablesTipo> {

	public List<VariablesTipo> findByComponente(Componente componente)throws NotariaException;

	public List<VariablesTipo> findByVariable(Variable variable)throws NotariaException;

}
