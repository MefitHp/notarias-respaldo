package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.TipoComparecienteDao;
import com.palestra.notaria.modelo.TipoCompareciente;

public class TipoComparecienteDaoImpl extends GenericDaoImpl<TipoCompareciente, Integer> implements TipoComparecienteDao{

	public TipoComparecienteDaoImpl() {
		super(TipoCompareciente.class);
	}

}
