package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.LocalidadBo;
import com.palestra.notaria.dao.LocalidadDao;
import com.palestra.notaria.dao.impl.LocalidadDaoImpl;
import com.palestra.notaria.modelo.Localidad;

public class LocalidadBoImpl extends GenericBoImpl<Localidad> implements LocalidadBo{
	
	private LocalidadDao localidadDao;
	
	public LocalidadBoImpl(){
		this.localidadDao = new LocalidadDaoImpl();
		super.dao = this.localidadDao;
	}

}
