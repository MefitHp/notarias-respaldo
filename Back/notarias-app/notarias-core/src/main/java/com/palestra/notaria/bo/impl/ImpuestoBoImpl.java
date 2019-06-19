package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ImpuestoBo;
import com.palestra.notaria.dao.ImpuestoDao;
import com.palestra.notaria.dao.impl.ImpuestoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Impuesto;

public class ImpuestoBoImpl extends GenericBoImpl<Impuesto> implements ImpuestoBo{

	private ImpuestoDao impuestoDao;
	
	public ImpuestoBoImpl() {
		this.impuestoDao = new ImpuestoDaoImpl();
		super.dao = this.impuestoDao;
	}

	@Override
	public Impuesto obtenerImpuestoById(String idimpuesto, String sigla)throws NotariaException {
		return this.impuestoDao.obtenerImpuestoById(idimpuesto, sigla);
	}
	
}
