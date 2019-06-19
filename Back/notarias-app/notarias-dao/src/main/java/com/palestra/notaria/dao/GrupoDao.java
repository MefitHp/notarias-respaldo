package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public interface GrupoDao extends GenericDao<Grupo, Integer> {

	public List<Grupo> findByProperties(Grupo grupo) throws NotariaException;
	
	public List<VariableGrupo> getVariablesByGrupo(String id) throws NotariaException;
	
	@Override
	public List<Grupo> findAll() throws NotariaException;
	
}
