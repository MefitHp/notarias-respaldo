package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.EstadoDao;
import com.palestra.notaria.modelo.Estado;

public class EstadoDaoImpl extends GenericDaoImpl<Estado, Integer> implements
		EstadoDao {

	public EstadoDaoImpl() {
		super(Estado.class);
	}

}
