package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.GrupoBo;
import com.palestra.notaria.bo.VariableGrupoBo;
import com.palestra.notaria.dao.VariableGrupoDao;
import com.palestra.notaria.dao.impl.VariableGrupoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public class VariableGrupoBoImpl extends GenericBoImpl<VariableGrupo> implements
		VariableGrupoBo {
	private VariableGrupoDao varGrupoDao;
	
	public VariableGrupoBoImpl(){
		this.varGrupoDao = new VariableGrupoDaoImpl();
		super.dao = this.varGrupoDao;
	}
		
	
	@Override
	public List<VariableGrupo> buscarVariablesGrupo(Grupo grupo)
			throws NotariaException {
		List<VariableGrupo> variables;
		GrupoBo grupoBo = new GrupoBoImpl();
		Grupo g = grupoBo.findById(grupo.getIdgrupo());
		if(g==null){
			throw new NotariaException(String.format("El grupo %s no se localizo en la unidad de persistencia.", grupo.getIdgrupo()));
		}else{
			variables = varGrupoDao.obtenerVariablesEnGrupo(g);
		}
		return variables;
	}
	
	@Override
	public void eliminarPorId(String identificador) throws NotariaException {
		varGrupoDao.eliminarPorId(identificador);
	}
	
}
