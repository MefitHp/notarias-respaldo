package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.GrupoBo;
import com.palestra.notaria.dao.GrupoDao;
import com.palestra.notaria.dao.impl.GrupoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public class GrupoBoImpl extends GenericBoImpl<Grupo> implements GrupoBo{
	
	private GrupoDao grupoDao;
	
	public GrupoBoImpl() {
		this.grupoDao = new GrupoDaoImpl();
		super.dao = this.grupoDao;
	}
	
	@Override
	public List<Grupo> findByProperties(
			Grupo grupo) throws NotariaException{
		return grupoDao.findByProperties(grupo);
	}
	
	@Override
	public List<VariableGrupo> getVariablesByGrupo(String id) throws NotariaException{
		return this.grupoDao.getVariablesByGrupo(id);
	}
	
	@Override
	public List<Grupo> findAll() throws NotariaException {
		return grupoDao.findAll();
	}

}
