package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DomicilioBo;
import com.palestra.notaria.dao.DomicilioDao;
import com.palestra.notaria.dao.impl.DomicilioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Domicilio;

public class DomicilioBoImpl extends GenericBoImpl<Domicilio> implements DomicilioBo {

	DomicilioDao domicilioDao;
	
	public DomicilioBoImpl(){
		this.domicilioDao = new DomicilioDaoImpl();
		super.dao = this.domicilioDao;
	}

	@Override
	public List<Domicilio> listarDomiciliosDeActo(String idacto)throws NotariaException {
		return domicilioDao.listarDomiciliosDeActo(idacto);
	}

}
