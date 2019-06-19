package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ComparecienteConyugeBo;
import com.palestra.notaria.dao.ComparecienteConyugeDao;
import com.palestra.notaria.dao.impl.ComparecienteConyugeDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteConyuge;

public class ComparecienteConyugeBoImpl extends
		GenericBoImpl<ComparecienteConyuge> implements ComparecienteConyugeBo {
	
	ComparecienteConyugeDao comparecienteConyugeDao;
	
	public ComparecienteConyugeBoImpl(){
		this.comparecienteConyugeDao = new ComparecienteConyugeDaoImpl();
		super.dao = this.comparecienteConyugeDao;
	}

	@Override
	public ComparecienteConyuge getRelacionInversa(ComparecienteConyuge comp) throws NotariaException{
		return this.comparecienteConyugeDao.getRelacionInversa(comp);
	}
	
	@Override
	public ComparecienteConyuge findById(String id) throws NotariaException {
		return this.comparecienteConyugeDao.findById(id);
	}
	
	@Override
	public ComparecienteConyuge findComparecienteConyuge(String idcompareciente) throws NotariaException{
		return this.comparecienteConyugeDao.findComparecienteConyuge(idcompareciente);
	}
}
