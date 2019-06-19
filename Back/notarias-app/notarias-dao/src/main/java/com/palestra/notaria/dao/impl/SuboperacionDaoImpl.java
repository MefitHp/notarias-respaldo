package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.SuboperacionDao;
import com.palestra.notaria.modelo.Suboperacion;

public class SuboperacionDaoImpl extends GenericDaoImpl<Suboperacion, Integer> implements SuboperacionDao {

	public SuboperacionDaoImpl(){
		super(Suboperacion.class);
	}
}
