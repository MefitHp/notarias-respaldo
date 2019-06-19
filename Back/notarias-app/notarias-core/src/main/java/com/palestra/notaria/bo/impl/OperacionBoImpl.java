package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.OperacionBo;
import com.palestra.notaria.dao.OperacionDao;
import com.palestra.notaria.dao.impl.OperacionDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;

public class OperacionBoImpl extends GenericBoImpl<Operacion> implements
		OperacionBo {

	private OperacionDao operacionDao;
	
	public OperacionBoImpl(){
		this.operacionDao = new OperacionDaoImpl();
		super.dao = this.operacionDao;
	}

	@Override
	public List<Suboperacion> OperacionesPorOperacion(String idoperacion)throws NotariaException {
		return this.operacionDao.OperacionesPorOperacion(idoperacion);
	}
}
