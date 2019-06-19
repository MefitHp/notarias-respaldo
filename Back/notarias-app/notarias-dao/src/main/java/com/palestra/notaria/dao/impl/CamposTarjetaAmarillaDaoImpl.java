package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.CamposTarjetaAmarillaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CamposTarjetaAmarilla;

public class CamposTarjetaAmarillaDaoImpl extends GenericDaoImpl<CamposTarjetaAmarilla, Integer> implements
		CamposTarjetaAmarillaDao {
	
	public CamposTarjetaAmarillaDaoImpl(){
		super(CamposTarjetaAmarilla.class);
	}
	
	@Override
	public List<CamposTarjetaAmarilla> getCamposPorOperacion(String idacto) throws NotariaException{
			return executeQuery("SELECT c FROM CamposTarjetaAmarilla c WHERE c.operacion.idoperacion = " +
					"(SELECT a.suboperacion.operacion.idoperacion FROM Acto a WHERE a.idacto = ?1)", idacto);
	}

}
