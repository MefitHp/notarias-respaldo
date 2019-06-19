package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.NotariaBo;
import com.palestra.notaria.dao.NotariaDao;
import com.palestra.notaria.dao.impl.NotariaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Notaria;

public class NotariaBoImpl extends GenericBoImpl<Notaria> implements NotariaBo {
	
	NotariaDao notariaDao;
	
	public NotariaBoImpl(){
		this.notariaDao = new NotariaDaoImpl();
		super.dao = notariaDao;
	}
	
	@Override
	public String obtenerNumNotariaByInicialesNotario(String inicialesNotario) throws NotariaException{
		return this.notariaDao.obtenerNumNotariaByInicialesNotario(inicialesNotario);
	}

}
