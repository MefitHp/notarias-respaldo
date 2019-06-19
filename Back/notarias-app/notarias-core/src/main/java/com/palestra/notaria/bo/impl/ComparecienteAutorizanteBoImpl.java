package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ComparecienteAutorizanteBo;
import com.palestra.notaria.dao.ComparecienteAutorizanteDao;
import com.palestra.notaria.dao.impl.ComparecienteAutorizanteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteAutorizante;

public class ComparecienteAutorizanteBoImpl extends
		GenericBoImpl<ComparecienteAutorizante> implements
		ComparecienteAutorizanteBo {

	ComparecienteAutorizanteDao compDao;
	
	public ComparecienteAutorizanteBoImpl(){
		this.compDao = new ComparecienteAutorizanteDaoImpl();
		super.dao = this.compDao;
	}
	
	@Override
	public ComparecienteAutorizante findById(Object id) throws NotariaException{
		return this.compDao.findById(id);
	}
	
	@Override
	public List<ComparecienteAutorizante> findByCompareciente(String idcompareciente)throws NotariaException{
		return this.compDao.findByCompareciente(idcompareciente);
	}
}
