package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.CamposTarjetaAmarillaBo;
import com.palestra.notaria.dao.CamposTarjetaAmarillaDao;
import com.palestra.notaria.dao.impl.CamposTarjetaAmarillaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CamposTarjetaAmarilla;

public class CamposTarjetaAmarillaBoImpl extends
		GenericBoImpl<CamposTarjetaAmarilla> implements CamposTarjetaAmarillaBo {

	private CamposTarjetaAmarillaDao campoTarjetaDao;
	
	public CamposTarjetaAmarillaBoImpl(){
		this.campoTarjetaDao = new CamposTarjetaAmarillaDaoImpl();
		super.dao = this.campoTarjetaDao;
	}
	
	@Override
	public List<CamposTarjetaAmarilla> getCamposPorOperacion(String idacto) throws NotariaException{
		return this.campoTarjetaDao.getCamposPorOperacion(idacto);
	}
}
