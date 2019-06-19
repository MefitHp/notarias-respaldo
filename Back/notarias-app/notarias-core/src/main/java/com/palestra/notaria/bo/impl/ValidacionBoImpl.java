package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ValidacionBo;
import com.palestra.notaria.dao.ValidacionDao;
import com.palestra.notaria.dao.impl.ValidacionDaoImpl;
import com.palestra.notaria.modelo.Validacion;

public class ValidacionBoImpl extends GenericBoImpl<Validacion> implements
		ValidacionBo {

	ValidacionDao validacionDao;
	
	public ValidacionBoImpl(){
		this.validacionDao = new ValidacionDaoImpl();
		super.dao = this.validacionDao;
	}
}
