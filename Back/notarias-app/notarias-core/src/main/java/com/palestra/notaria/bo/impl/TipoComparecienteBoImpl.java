package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.TipoComparecienteBo;
import com.palestra.notaria.dao.TipoComparecienteDao;
import com.palestra.notaria.dao.impl.TipoComparecienteDaoImpl;
import com.palestra.notaria.modelo.TipoCompareciente;

public class TipoComparecienteBoImpl extends GenericBoImpl<TipoCompareciente> implements TipoComparecienteBo{

	private TipoComparecienteDao tipoComparecienteDao;
	
	public TipoComparecienteBoImpl() {
		this.tipoComparecienteDao = new TipoComparecienteDaoImpl();
		super.dao = this.tipoComparecienteDao;
	}
}
