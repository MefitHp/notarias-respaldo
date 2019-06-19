package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.ValidacionDao;
import com.palestra.notaria.modelo.Validacion;

public class ValidacionDaoImpl extends GenericDaoImpl<Validacion, Integer>
		implements ValidacionDao {

	public ValidacionDaoImpl() {
		super(Validacion.class);
	}

}
