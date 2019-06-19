package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.MunicipioDao;
import com.palestra.notaria.modelo.Municipio;

public class MunicipioDaoImpl extends GenericDaoImpl<Municipio, Integer>
		implements MunicipioDao {

	public MunicipioDaoImpl() {
		super(Municipio.class);
	}

}
