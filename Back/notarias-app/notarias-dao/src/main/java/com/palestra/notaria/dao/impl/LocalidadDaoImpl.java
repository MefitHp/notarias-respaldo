package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.LocalidadDao;
import com.palestra.notaria.modelo.Localidad;

public class LocalidadDaoImpl extends GenericDaoImpl<Localidad, Integer> implements LocalidadDao {

	public LocalidadDaoImpl(){
		super(Localidad.class);
	}
}
